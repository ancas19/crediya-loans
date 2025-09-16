package co.com.crediya.loan.r2dbc.adapters;

import co.com.crediya.loan.model.status.gateways.StatusRepositoryPort;
import co.com.crediya.loan.model.status.models.Status;
import co.com.crediya.loan.r2dbc.entity.StatusEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.StatusRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class StatusReactiveRepositoryAdapter extends ReactiveAdapterOperations<Status, StatusEntity, UUID, StatusRepository> implements StatusRepositoryPort {
    public StatusReactiveRepositoryAdapter(StatusRepository stateRepository, ObjectMapper objectMapper){
        super(stateRepository,objectMapper,d->objectMapper.map(d, Status.class));
    }

    @Override
    public Mono<Status> findByName(String name) {
        return this.repository.findByName(name)
                .map(statusEntity -> mapper.map(statusEntity, Status.class));
    }

    @Override
    public Mono<Status> findById(UUID id) {
        return this.repository.findById(id)
                .map(statusEntity -> mapper.map(statusEntity, Status.class));
    }
}
