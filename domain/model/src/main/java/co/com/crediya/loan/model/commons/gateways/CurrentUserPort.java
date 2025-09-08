package co.com.crediya.loan.model.commons.gateways;

import reactor.core.publisher.Mono;

public interface CurrentUserPort {
    Mono<String> getCurrentUser();
    Mono<String> getCurrentRole();
}
