package co.com.crediya.loan.model.state.gateways;

import co.com.crediya.loan.model.state.models.State;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StateRepositoryPort {
    Mono<State> findByName(String name);
    Mono<State> findById(UUID stateId);
}
