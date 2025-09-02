package co.com.crediya.loan.model.userwebclient.gateways;

import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import reactor.core.publisher.Mono;

public interface UserWebClientRepository {
    Mono<UserInformation> getUserInformation(UserDocument userDocument);
}
