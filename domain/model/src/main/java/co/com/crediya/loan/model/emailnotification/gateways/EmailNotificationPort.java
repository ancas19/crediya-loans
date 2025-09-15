package co.com.crediya.loan.model.emailnotification.gateways;

import co.com.crediya.loan.model.emailnotification.models.EmailNotification;
import reactor.core.publisher.Mono;

public interface EmailNotificationPort {
    Mono<Void> sendEmailNotification(EmailNotification emailNotification);
}
