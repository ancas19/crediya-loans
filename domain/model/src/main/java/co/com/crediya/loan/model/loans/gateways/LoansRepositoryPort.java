package co.com.crediya.loan.model.loans.gateways;

import co.com.crediya.loan.model.loans.models.LoanSearch;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.loans.models.LoansPaginated;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoansRepositoryPort {
    Mono<Loans> createLoan(Loans loanInformation);
    Mono<LoansPaginated> searchLoans(LoanSearch loanSearch);
    Mono<Loans> findById(UUID id);
}
