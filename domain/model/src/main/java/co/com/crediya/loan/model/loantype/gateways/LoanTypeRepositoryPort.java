package co.com.crediya.loan.model.loantype.gateways;

import co.com.crediya.loan.model.loantype.models.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepositoryPort {
    Mono<LoanType> findByName(String name);
}
