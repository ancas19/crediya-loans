package co.com.crediya.loan.jwt.secutiry;

import co.com.crediya.loan.jwt.config.SecurityProperties;
import co.com.crediya.loan.jwt.filters.JwtAuthenticationFilter;
import co.com.crediya.loan.usecase.token.TokenUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static co.com.crediya.loan.model.commons.enums.Endpoints.CREATE_LOANS;
import static co.com.crediya.loan.model.commons.enums.Endpoints.FIND_LOAN;
import static co.com.crediya.loan.model.commons.enums.Roles.*;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurity {
    private final SecurityProperties securityProperties;

    public WebSecurity(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public SecurityWebFilterChain httpSecurityFilterChain(ServerHttpSecurity http,TokenUseCase jwtService) {
        Set<String> publicUrls = new HashSet<>(securityProperties.getPublicPaths());
        return http.authorizeExchange(
                        exchanges -> exchanges
                                .pathMatchers(CREATE_LOANS.getValue()).hasAnyRole(CLIENTE.getValue(),ADMIN.getValue(),ASESOR.getValue())
                                .pathMatchers(FIND_LOAN.getValue()).hasAnyRole(ADMIN.getValue(),ASESOR.getValue())
                                .anyExchange()
                                .authenticated()
                )
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .addFilterAt(new JwtAuthenticationFilter(jwtService,publicUrls), SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }


    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
