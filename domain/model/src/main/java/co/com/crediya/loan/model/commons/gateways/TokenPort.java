package co.com.crediya.loan.model.commons.gateways;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface TokenPort {

    Mono<Boolean> validateJwt(String token);

    String extractTokenSubject(String token);

    String extractTokenClaim(String token, String key);

    Map<String, Object> getClaimsMap(String token);
}
