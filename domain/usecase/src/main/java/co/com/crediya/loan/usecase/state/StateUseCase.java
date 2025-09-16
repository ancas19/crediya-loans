package co.com.crediya.loan.usecase.state;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.state.gateways.StateRepositoryPort;
import co.com.crediya.loan.model.state.models.State;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class StateUseCase {
    private final StateRepositoryPort stateRepositoryPort;

    public Mono<State> findByName(String stateName){
        return this.stateRepositoryPort.findByName(stateName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_STATE_NOT_FOUND.getMessage().formatted(stateName))));
    }

    public Mono<State> findById(UUID stateId){
        return this.stateRepositoryPort.findById(stateId)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_STATE_ID_NOT_FOUND.getMessage().formatted(stateId))));
    }
}
