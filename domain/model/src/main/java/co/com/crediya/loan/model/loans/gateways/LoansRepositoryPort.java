package co.com.crediya.loan.model.loans.gateways;

import co.com.crediya.loan.model.loans.models.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoansRepositoryPort {
    Mono<Loans> createLoan(Loans loanInformation);
    Mono<LoansPaginated> searchLoans(LoanSearch loanSearch);
    Mono<Loans> findById(UUID id);
    Mono<LoanNotificationInformation> findLoanInformationById(UUID id);
}
