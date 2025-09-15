package co.com.crediya.loan.usecase.emailnotification;

import co.com.crediya.loan.model.emailnotification.gateways.EmailNotificationPort;
import co.com.crediya.loan.model.emailnotification.models.EmailNotification;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.userwebclient.gateways.UserWebClientRepository;
import co.com.crediya.loan.model.userwebclient.models.UserDocument;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class EmailNotificationUseCase {
    private final EmailNotificationPort emailNotificationPort;
    private final UserWebClientRepository userWebClientRepository;

    public Mono<Void> sendEmailNotification(Loans loans) {
        return this.userWebClientRepository.getUserInformation(new UserDocument(loans.getIdentification()))
                .flatMap(userInformation -> this.emailNotificationPort.sendEmailNotification(
                                    EmailNotification.builder()
                                            .to(userInformation.getEmail())
                                            .subject("Loan Application Received")
                                            .body(
                                                    "Dear %s %s,\n\n".formatted(userInformation.getNames(), userInformation.getLastName())
                                            )
                                            .build()
                            )

                );
    }
}
