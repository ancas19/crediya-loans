package co.com.crediya.loan.api.services;

import co.com.crediya.loan.api.response.LoanResponse;
import co.com.crediya.loan.api.utils.Mapper;
import co.com.crediya.loan.model.commons.gateways.CurretnUserPort;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.usecase.loans.LoansUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

import static co.com.crediya.loan.model.commons.enums.ErrorMessages.ERROR_MESSAGES_USER_CREATE_LOAN;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoansAppService {
    private final LoansUseCase loansUseCase;
    private final CurretnUserPort curretnUserPort;

    @Transactional("r2dbcTransactionManager")
    public Mono<LoanResponse> createLoan(Loans loansinformation) {
        return  curretnUserPort.getCurrentUser()
                .flatMap(currentUsr-> {
                    if (!loansinformation.getIdentification().equals(currentUsr)){
                        return Mono.error(new AccessDeniedException(ERROR_MESSAGES_USER_CREATE_LOAN.getMessage()));
                    }
                    return loansUseCase.creteLoan(loansinformation)
                            .map(Mapper::toResponse)
                            .doOnNext(loanResponse -> log.info("Loan created with id: {}", loanResponse.getId()));
                });
    }

}
