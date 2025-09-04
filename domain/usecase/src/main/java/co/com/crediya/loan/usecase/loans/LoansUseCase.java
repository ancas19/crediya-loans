package co.com.crediya.loan.usecase.loans;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.loantype.models.LoanType;
import co.com.crediya.loan.model.state.models.State;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.usecase.loantype.LoanTypeUseCase;
import co.com.crediya.loan.usecase.state.StateUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
                })
                );
    }


}
