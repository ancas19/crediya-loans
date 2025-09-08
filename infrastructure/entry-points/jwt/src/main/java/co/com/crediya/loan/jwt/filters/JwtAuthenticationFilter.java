package co.com.crediya.loan.jwt.filters;

import co.com.crediya.loan.model.commons.models.UserRequestInfo;
import co.com.crediya.loan.usecase.token.TokenUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static co.com.crediya.loan.model.commons.enums.Constants.*;


public class JwtAuthenticationFilter implements WebFilter {
    private final TokenUseCase jwtPort;
    private final Set<String> cachedPublicPaths;
    ///TODO: Analyze ratelimit
    /*private final ConcurrentHashMap<String, UserRequestInfo> requestLimits = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 100;
    private final Duration TIME_WINDOW = Duration.ofMinutes(1);*/

    public JwtAuthenticationFilter(TokenUseCase jwtPort, Set<String> publicPaths) {
        this.jwtPort = jwtPort;
        this.cachedPublicPaths = publicPaths;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().toString();
        if (isPublicPath(path, method)) {
            return chain.filter(exchange);
        }
        String token = extractToken(exchange);
        if (Objects.isNull(token)) {
            return invalidToken(exchange);
        }
        return this.jwtPort.validateJwt(token)
                .flatMap(isValid -> isValid ? validAndContinue(token, exchange, chain) : invalidToken(exchange));
    }

    private Mono<Void> validAndContinue(String token, ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.just(jwtPort.getClaims(token))
                .flatMap(claims -> {
                            String identification = claims.get(CALIM_IDENTIFICATION.getValue()).toString();
                            String role = claims.get(CLAIM_ROLE.getValue()).toString();
                            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(ROLE.getValue().formatted(role)));
                            Authentication authentication = new UsernamePasswordAuthenticationToken(identification, null, authorities);
                            return chain
                                    .filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                                    .contextWrite(context -> context.put("token", token));
                        }
                );
    }

    private Mono<Void> invalidToken(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private String extractToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER.getValue())) {
            return token.substring(7).trim();
        }
        return null;
    }

    private boolean isPublicPath(String path, String method) {
        String key = method.toUpperCase() + ":" + path.toUpperCase();
        return cachedPublicPaths.contains(key);
    }

   /* private boolean isRateLimited(String token) {
        UserRequestInfo userInfo = requestLimits.computeIfAbsent(token, k -> new UserRequestInfo(System.currentTimeMillis(),new AtomicInteger(0)));
        synchronized (userInfo) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - userInfo.getStartTime() > TIME_WINDOW.toMillis()) {
                userInfo.setStartTime(currentTime);
                userInfo.setRequestCount(new AtomicInteger(0));            }
            if (userInfo.getRequestCount().incrementAndGet() > MAX_REQUESTS) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> rateLimitExceeded(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }*/
}
