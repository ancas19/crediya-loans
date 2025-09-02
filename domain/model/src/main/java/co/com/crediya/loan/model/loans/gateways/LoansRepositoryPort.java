package co.com.crediya.loan.model.loans.gateways;

import co.com.crediya.loan.model.loans.models.Loans;
import reactor.core.publisher.Mono;

public interface LoansRepositoryPort {
    Mono<Loans> createLoan(Loans loanInformation);
}
