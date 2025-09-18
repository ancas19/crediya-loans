package co.com.crediya.loan.sqs.sender.adapter;

import co.com.crediya.loan.model.queuenotification.gateways.QueueNotificationPort;
import co.com.crediya.loan.model.queuenotification.models.CapacityCalculation;
import co.com.crediya.loan.model.queuenotification.models.EmailNotification;
import co.com.crediya.loan.sqs.sender.config.SQSSenderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements QueueNotificationPort {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;

    @Override
    public Mono<Void> sendEmailNotification(EmailNotification emailNotification) {
        return Mono.fromCallable(()->buildRequestDTOToJSON(emailNotification,properties.queueUrlEmail()))
                .flatMap(request->Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Email notification sent {}", response.messageId()))
                .then();
    }

    @Override
    public Mono<Void> calculateCapacity(CapacityCalculation capacityCalculation) {
        return Mono.fromCallable(()->buildRequestDTOToJSON(capacityCalculation,properties.queueUrlCapacity()))
                .flatMap(request->Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Capacity calculation request sent {}", response.messageId()))
                .then();
    }


    private SendMessageRequest buildRequestDTOToJSON(Object dto,String url) {
        JSONObject jsonObject=new JSONObject(dto);
        return SendMessageRequest.builder()
                .queueUrl(url)
                .messageBody(jsonObject.toString())
                .build();
    }
}
