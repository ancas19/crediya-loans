package co.com.crediya.loan.r2dbc.adapters;

import co.com.crediya.loan.model.state.gateways.StateRepositoryPort;
import co.com.crediya.loan.model.state.models.State;
import co.com.crediya.loan.r2dbc.entity.StateEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.StateRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class StateReactiveRepositoryAdapter extends ReactiveAdapterOperations<State, StateEntity, UUID, StateRepository> implements StateRepositoryPort {
    public StateReactiveRepositoryAdapter(StateRepository stateRepository, ObjectMapper objectMapper){
        super(stateRepository,objectMapper,d->objectMapper.map(d, State.class));
    }

    @Override
    public Mono<State> findByName(String name) {
        return this.repository.findByName(name)
                .map(stateEntity -> mapper.map(stateEntity, State.class));
    }
}
