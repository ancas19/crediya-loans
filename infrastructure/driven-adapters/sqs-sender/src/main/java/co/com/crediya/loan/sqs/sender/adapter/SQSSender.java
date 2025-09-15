package co.com.crediya.loan.sqs.sender.adapter;

import co.com.crediya.loan.model.emailnotification.gateways.EmailNotificationPort;
import co.com.crediya.loan.model.emailnotification.models.EmailNotification;
import co.com.crediya.loan.sqs.sender.config.SQSSenderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements EmailNotificationPort {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;

    @Override
    public Mono<Void> sendEmailNotification(EmailNotification emailNotification) {
        return Mono.fromCallable(()->buildRequestDTOToJSON(emailNotification))
                .flatMap(request->Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Email notification sent {}", response.messageId()))
                .then();
    }
    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }

    private SendMessageRequest buildRequestDTOToJSON(Object dto) {
        JSONObject jsonObject=new JSONObject(dto);
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(jsonObject.toString())
                .build();
    }
}
