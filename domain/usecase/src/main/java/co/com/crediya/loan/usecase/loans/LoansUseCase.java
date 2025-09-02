package co.com.crediya.loan.usecase.loans;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.usecase.loantype.LoanTypeUseCase;
import co.com.crediya.loan.usecase.state.StateUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class LoansUseCase {
    private final UserWebClientRepository userWebClientRepository;
    private final LoansRepositoryPort loansRepositoryPort;
    private final LoanTypeUseCase loanTypeUseCase;
    private final StateUseCase stateUseCase;

    public Mono<Loans> creteLoan(Loans loanInformation) {
        return userWebClientRepository.getUserInformation(new UserDocument(loanInformation.getIdentification()))
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_USER_NOT_FOUND.getMessage().formatted(loanInformation.getIdentification()))))
                .flatMap(userInformation ->  loanTypeUseCase.findByName(loanInformation.getLoanTypeName()))
                .flatMap(loanType -> {
                            loanInformation.setLoanTypeId(loanType.getId());
                            return stateUseCase.findByName("CREATED");
                        }
                )
                .flatMap(stateFound -> {
                            loanInformation.setStateId(stateFound.getId());
                            return this.loansRepositoryPort.createLoan(loanInformation);

                        }
                );
    }


}
