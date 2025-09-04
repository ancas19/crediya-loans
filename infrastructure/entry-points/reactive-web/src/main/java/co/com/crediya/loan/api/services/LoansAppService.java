package co.com.crediya.loan.api.services;

import co.com.crediya.loan.api.response.LoanResponse;
import co.com.crediya.loan.api.utils.Mapper;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.usecase.loans.LoansUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoansAppService {
    private final LoansUseCase loansUseCase;

    @Transactional("r2dbcTransactionManager")
    public Mono<LoanResponse> createLoan(Loans loansinformation) {
        return  loansUseCase.creteLoan(loansinformation)
                .map(Mapper::toResponse)
                .doOnNext(loanResponse -> log.info("Loan created with id: {}", loanResponse.getId()));
    }

}
