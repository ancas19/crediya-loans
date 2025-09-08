package co.com.crediya.loan.token.adapters;

import co.com.crediya.loan.model.commons.gateways.TokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenAdapter implements TokenPort {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Integer expiration;


    public Mono<Boolean> validateJwt(String token) {
        return Mono.just(token)
                .map(this::getClaims)
                .map(claims -> claims.getExpiration().after(new Date()))
                .onErrorReturn(false);
    }

    public String extractTokenSubject(String token) {
        return getClaims(token)
                .getSubject();
    }

    public String extractTokenClaim(String token, String key){
        return getClaims(token).get(key).toString();
    }

    @Override
    public Map<String, Object> getClaimsMap(String token) {
        return this.getClaims(token)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(){
        return Optional.ofNullable(secretKey)
                .map(String::getBytes)
                .map(bytes-> Keys.hmacShaKeyFor(bytes))
                .orElseThrow(()->new IllegalArgumentException("Secret key is null"));
    }
}
