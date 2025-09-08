package co.com.crediya.loan.webclient.adapter;

import co.com.crediya.loan.model.commons.exception.BadRequestException;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static co.com.crediya.loan.model.commons.enums.Constants.AUTHORIZATION;
import static co.com.crediya.loan.model.commons.enums.Constants.BEARER;
import static co.com.crediya.loan.model.commons.enums.ErrorMessages.ERROR_SEARCHING_USER_INFORMATION;

@Service
@RequiredArgsConstructor
public class UserWebClientAdapter implements UserWebClientRepository {
    public static final Logger log = LoggerFactory.getLogger(UserWebClientAdapter.class);
    @Value("${user.service.host}")
    private String userServiceUrl;
    @Value("${user.service.uri}")
    private String userServiceEndpoint;
    private final WebClient webClient;

    @Override
    public Mono<UserInformation> getUserInformation(UserDocument userDocument) {
        return this.getCurrentToken()
                .flatMap(token -> webClient.post()
                        .uri(userServiceUrl + userServiceEndpoint)
                        .header(HttpHeaders.AUTHORIZATION, BEARER.getValue().formatted(token))
                        .bodyValue(userDocument)
                        .retrieve()
                        .bodyToMono(UserInformation.class)
                        .doOnSuccess(userInfo -> log.info("User information retrieved: {}", userInfo))
                        .doOnError(error -> log.error("Error retrieving user information: {}", error.getMessage(), error))
                        .onErrorResume(error -> Mono.error(new BadRequestException(ERROR_SEARCHING_USER_INFORMATION.getMessage())))
                );
    }

    private Mono<String> getCurrentToken() {
        return Mono.deferContextual(contextView ->
                Mono.justOrEmpty(contextView.getOrEmpty(AUTHORIZATION.getValue()))
                        .cast(String.class)
        );
    }
}
