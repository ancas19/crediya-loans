package co.com.crediya.loan.r2dbc.adapters;


import co.com.crediya.loan.model.loantype.gateways.LoanTypeRepositoryPort;
import co.com.crediya.loan.model.loantype.models.LoanType;
import co.com.crediya.loan.r2dbc.entity.LoanTypeEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.LoanTypeRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class LoanTypeReactiveRepositoryAdapter  extends ReactiveAdapterOperations<LoanType, LoanTypeEntity, UUID, LoanTypeRepository> implements LoanTypeRepositoryPort {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeRepository loanTypeRepository, ObjectMapper objectMapper){
        super(loanTypeRepository,objectMapper,d->objectMapper.map(d, LoanType.class));
    }

    @Override
    public Mono<LoanType> findByName(String name) {
        return this.repository.findByName(name)
                .map(loanTypeEntity -> mapper.map(loanTypeEntity, LoanType.class));
    }
}
