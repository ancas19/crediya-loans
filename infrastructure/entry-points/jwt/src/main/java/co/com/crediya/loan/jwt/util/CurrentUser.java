package co.com.crediya.loan.jwt.util;

import co.com.crediya.loan.model.commons.exception.BadCredentialsException;
import co.com.crediya.loan.model.commons.gateways.CurrentUserPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CurrentUser implements CurrentUserPort {
    @Override
    public Mono<String> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth != null && auth.getName() != null)
                .map(Authentication::getName)
                .switchIfEmpty(Mono.error(new BadCredentialsException("No authenticated user found")));
    }

    @Override
    public Mono<String> getCurrentRole() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth != null && auth.getAuthorities() != null)
                .map(auth -> auth.getAuthorities().iterator().next().getAuthority())
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .switchIfEmpty(Mono.error(new BadCredentialsException("No authenticated user found")));
    }


}
