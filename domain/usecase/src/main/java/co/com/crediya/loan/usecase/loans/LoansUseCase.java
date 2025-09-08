package co.com.crediya.loan.usecase.loans;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.LoanInformation;
import co.com.crediya.loan.model.loans.models.LoanSearch;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.loans.models.LoansPaginated;
import co.com.crediya.loan.model.loantype.models.LoanType;
import co.com.crediya.loan.model.state.models.State;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import co.com.crediya.loan.usecase.loantype.LoanTypeUseCase;
import co.com.crediya.loan.usecase.state.StateUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static co.com.crediya.loan.model.commons.enums.Constants.PENDING;


@RequiredArgsConstructor
public class LoansUseCase {
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
        return Flux.fromIterable(identifications)
                .flatMap(identification -> userWebClientRepository.getUserInformation(new UserDocument(identification)))
                .collectMap(UserInformation::getIdentification)
                .flatMap(userMap ->
                        Flux.fromIterable(loans)
                                .map((LoanInformation loan) -> {
                                            UserInformation userInformation = userMap.get(loan.getIdentification());
                                            loan.setNames(userInformation.getNames());
                                            loan.setEmail(userInformation.getEmail());
                                            loan.setLastNames(userInformation.getLastName());
                                            return loan;
                                        }
                                )
                                .collectList()
                );
    }
}
