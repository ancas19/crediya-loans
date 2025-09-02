package co.com.crediya.loan.webclient.adapter;

import co.com.crediya.loan.model.commons.exception.BadRequestException;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static co.com.crediya.loan.model.commons.enums.ErrorMessages.ERROR_SEARCHING_USER_INFORMATION;

@Service
@RequiredArgsConstructor
public class UserWebClientAdapter implements UserWebClientRepository {
    public static final Logger log= LoggerFactory.getLogger(UserWebClientAdapter.class);
    @Value("${user.service.url}")
    private String userServiceUrl;
    @Value("${user.service.endpoint}")
    private String userServiceEndpoint;
    private final WebClient webClient;

    @Override
    public Mono<UserInformation> getUserInformation(UserDocument userDocument) {
        return webClient.get()
                .uri(userServiceUrl + userServiceEndpoint, userDocument.getIdentification())
                .retrieve()
                .bodyToMono(UserInformation.class)
                .doOnSuccess(userInfo -> log.info("User information retrieved: {}", userInfo))
                .doOnError(error -> log.error("Error retrieving user information: {}", error.getMessage(), error))
                .onErrorResume(error->Mono.error(new BadRequestException(ERROR_SEARCHING_USER_INFORMATION.getMessage())));
    }
}
