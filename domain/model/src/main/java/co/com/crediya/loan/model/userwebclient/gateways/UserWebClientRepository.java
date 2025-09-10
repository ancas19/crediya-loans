package co.com.crediya.loan.model.userwebclient.gateways;

import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import co.com.crediya.loan.model.userwebclient.models.UserIdentifications;
import co.com.crediya.loan.model.userwebclient.models.UserInformation;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserWebClientRepository {
    Mono<UserInformation> getUserInformation(UserDocument userDocument);
    Mono<List<UserInformation>> getUsersByInformation(UserIdentifications userDocument);
}
