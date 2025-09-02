package co.com.crediya.loan.r2dbc.adapters;

import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.r2dbc.entity.LoansEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.LoansRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class LoanReactiveRepositoryAdapter extends ReactiveAdapterOperations<Loans, LoansEntity, UUID, LoansRepository> implements LoansRepositoryPort {
    public LoanReactiveRepositoryAdapter(LoansRepository loansRepository, org.reactivecommons.utils.ObjectMapper objectMapper){
        super(loansRepository,objectMapper,d->objectMapper.map(d, Loans.class));
    }


    @Override
    public Mono<Loans> createLoan(Loans loanInformation) {
        return this.repository.save(toData(loanInformation))
                .map(loanCreated -> mapper.map(loanCreated, Loans.class));
    }
}
