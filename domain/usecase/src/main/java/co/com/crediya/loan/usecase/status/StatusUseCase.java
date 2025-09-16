package co.com.crediya.loan.usecase.status;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.status.gateways.StatusRepositoryPort;
import co.com.crediya.loan.model.status.models.Status;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class StatusUseCase {
    private final StatusRepositoryPort statusRepositoryPort;

    public Mono<Status> findByName(String stateName){
        return this.statusRepositoryPort.findByName(stateName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_STATE_NOT_FOUND.getMessage().formatted(stateName))));
    }

    public Mono<Status> findById(UUID stateId){
        return this.statusRepositoryPort.findById(stateId)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_STATE_ID_NOT_FOUND.getMessage().formatted(stateId))));
    }
}
