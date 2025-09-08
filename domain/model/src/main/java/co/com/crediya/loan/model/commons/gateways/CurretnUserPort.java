package co.com.crediya.loan.model.commons.gateways;

import reactor.core.publisher.Mono;

public interface CurretnUserPort {
    Mono<String> getCurrentUser();
}
