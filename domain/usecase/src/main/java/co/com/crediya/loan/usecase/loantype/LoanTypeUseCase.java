package co.com.crediya.loan.usecase.loantype;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.loantype.gateways.LoanTypeRepositoryPort;
import co.com.crediya.loan.model.loantype.models.LoanType;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanTypeUseCase {
    private final LoanTypeRepositoryPort loanTypeRepositoryPort;

    public Mono<LoanType> findByName(String loanTypeName){
        return this.loanTypeRepositoryPort.findByName(loanTypeName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_LOAN_TYPE_NOT_FOUND.getMessage().formatted(loanTypeName))));
    }
}
