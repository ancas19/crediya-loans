package co.com.crediya.loan.model.queuenotification.gateways;

import co.com.crediya.loan.model.queuenotification.models.CapacityCalculation;
import co.com.crediya.loan.model.queuenotification.models.EmailNotification;
import reactor.core.publisher.Mono;

public interface QueueNotificationPort {
    Mono<Void> sendEmailNotification(EmailNotification emailNotification);
    Mono<Void> calculateCapacity(CapacityCalculation capacityCalculation);
}
