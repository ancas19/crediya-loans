package co.com.crediya.loan.usecase.loans;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.*;
import co.com.crediya.loan.model.loantype.models.LoanType;
import co.com.crediya.loan.model.state.models.State;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserIdentifications;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import co.com.crediya.loan.usecase.emailnotification.EmailNotificationUseCase;
import co.com.crediya.loan.usecase.loantype.LoanTypeUseCase;
import co.com.crediya.loan.usecase.state.StateUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static co.com.crediya.loan.model.commons.enums.Constants.PENDING;


@RequiredArgsConstructor
public class LoansUseCase {
    private final EmailNotificationUseCase emailNotificationUseCase;
    private final UserWebClientRepository userWebClientRepository;
    private final LoansRepositoryPort loansRepositoryPort;
    private final LoanTypeUseCase loanTypeUseCase;
    private final StateUseCase stateUseCase;

    public Mono<Loans> creteLoan(Loans loanInformation) {
        return userWebClientRepository.getUserInformation(new UserDocument(loanInformation.getIdentification()))
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_USER_NOT_FOUND.getMessage().formatted(loanInformation.getIdentification()))))
                .flatMap(userInfo ->
                        Mono.zip(
                                loanTypeUseCase.findByName(loanInformation.getLoanTypeName()),
                                stateUseCase.findByName(PENDING.getValue())
                        )
                ).flatMap(tuple -> {
                    LoanType loanType = tuple.getT1();
                    State state = tuple.getT2();
                    loanInformation.setLoanTypeId(loanType.getId());
                    loanInformation.setStateId(state.getId());
                    return loansRepositoryPort.createLoan(loanInformation)
                            .map(savedLoan -> {
                                savedLoan.setLoanTypeName(loanType.getName());
                                savedLoan.setStateName(state.getDescription());
                                savedLoan.setIdentification(loanInformation.getIdentification());
                                return savedLoan;
                            });
                });
    }

    public Mono<LoansPaginated> searchLoans(LoanSearch loanSearch) {
        return this.loansRepositoryPort.searchLoans(loanSearch)
                .flatMap(this::processData);
    }


    public Mono<Loans> updateLoanState(LoansStates loansStates) {
        return this.findById(loansStates.getId())
                .flatMap(loanFound -> this.stateUseCase.findByName(loansStates.getSatateName())
                        .flatMap(state -> {
                                    loanFound.setStateId(state.getId());
                                    return this.loansRepositoryPort.createLoan(loanFound);
                                }
                        )
                        .flatMap(savedLoan ->
                                this.emailNotificationUseCase.sendEmailNotification(savedLoan)
                                        .thenReturn(savedLoan)
                        )

                );
    }

    public Mono<Loans> findById(UUID id) {
        return this.loansRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_LOAN_NOT_FOUND.getMessage().formatted(id))));
    }

    private Mono<LoansPaginated> processData(LoansPaginated loansPaginated) {
        if (loansPaginated.getLoans().isEmpty()) {
            return Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_LOANS_NOT_FOUND.getMessage()));
        }
        return enrichLoansWithUserInfo(loansPaginated.getLoans())
                .map(enrichedLoans -> LoansPaginated.builder()
                        .page(loansPaginated.getPage())
                        .size(loansPaginated.getSize())
                        .loansQuantity(loansPaginated.getLoansQuantity())
                        .loans(enrichedLoans)
                        .build());
    }

    private Mono<List<LoanInformation>> enrichLoansWithUserInfo(List<LoanInformation> loans) {
        Set<String> identifications = loans.stream()
                .map(LoanInformation::getIdentification)
                .collect(Collectors.toSet());
        return userWebClientRepository.getUsersByInformation(new UserIdentifications(identifications))
                .map(users -> users.stream().collect(Collectors.toMap(UserInformation::getIdentification, userInfo -> userInfo)))
                .flatMap(userMap ->
                        Flux.fromIterable(loans)
                                .map((LoanInformation loan) -> {
                                            UserInformation userInformation = userMap.get(loan.getIdentification());
                                            loan.setNames(userInformation.getNames());
                                            loan.setEmail(userInformation.getEmail());
                                            loan.setLastNames(userInformation.getLastName());
                                            loan.setBaseSalary(userInformation.getBaseSalary());
                                            loan.setMonthlyDebt(monthlyPayment(loan.getAmount(), loan.getInterestRate(), loan.getTerm()));
                                            return loan;
                                        }
                                )
                                .collectList()
                );
    }

    private BigDecimal monthlyPayment(BigDecimal principal, BigDecimal annualInterestRate, int termInMonths) {
        if (annualInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(termInMonths), RoundingMode.HALF_UP);
        }
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12L * 100L), 10, RoundingMode.HALF_UP);
        BigDecimal onePlus = BigDecimal.ONE.add(monthlyInterestRate);
        BigDecimal denominator = BigDecimal.ONE.subtract(onePlus.pow(-termInMonths, MathContext.DECIMAL128));
        return principal
                .multiply(monthlyInterestRate)
                .divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
