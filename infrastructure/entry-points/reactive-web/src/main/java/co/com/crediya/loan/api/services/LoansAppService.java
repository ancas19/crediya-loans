package co.com.crediya.loan.api.services;

import co.com.crediya.loan.api.response.LoansPaginatedResponse;
import co.com.crediya.loan.api.response.LoanResponse;
import co.com.crediya.loan.api.utils.Mapper;
import co.com.crediya.loan.model.commons.gateways.CurrentUserPort;
import co.com.crediya.loan.model.loans.models.LoanSearch;
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
    private final CurrentUserPort currentUserPort;

    @Transactional("r2dbcTransactionManager")
    public Mono<LoanResponse> createLoan(Loans loansinformation) {
        Mono<String> roleFound= currentUserPort.getCurrentRole();
        Mono<String> currentUser= currentUserPort.getCurrentUser();
        return  Mono.zip(roleFound,currentUser)
                .flatMap(tupleInformation-> {
                    String userRole=tupleInformation.getT1();
                    String currentUserDocument=tupleInformation.getT2();
                    if (!loansinformation.getIdentification().equals(currentUserDocument) && userRole.equals("CLIENTE")){
                        return Mono.error(new AccessDeniedException(ERROR_MESSAGES_USER_CREATE_LOAN.getMessage()));
                    }
                    return loansUseCase.creteLoan(loansinformation)
                            .map(Mapper::toResponse)
                            .doOnNext(loanResponse -> log.info("Loan created with id: {}", loanResponse.getId()));

                });
    }

    @Transactional("r2dbcTransactionManager")
    public Mono<LoansPaginatedResponse> searchLoans(LoanSearch loanSearch){
        return this.loansUseCase.searchLoans(loanSearch)
                .map(Mapper::toLoanPagiantionResponse);
    }

}
