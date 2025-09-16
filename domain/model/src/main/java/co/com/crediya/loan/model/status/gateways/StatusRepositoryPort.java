package co.com.crediya.loan.model.status.gateways;

import co.com.crediya.loan.model.status.models.Status;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StatusRepositoryPort {
    Mono<Status> findByName(String name);
    Mono<Status> findById(UUID stateId);
}
