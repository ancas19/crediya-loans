package co.com.crediya.loan.sqs.listener;

import co.com.crediya.loan.model.loans.models.LoansStatus;
import co.com.crediya.loan.usecase.loans.LoansUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

import static co.com.crediya.loan.model.commons.enums.Constants.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final LoansUseCase loansUseCase;
    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public Mono<Void> apply(Message message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), LoansStatus.class))
                .flatMap(loansUseCase::updateLoanState)
                .doOnNext(loans ->
                        log.info("Successfully processed message: {} for loan id: {}", message.body(), loans.getId())
                )
                .then() // return Mono<Void>
                .onErrorResume(e -> {
                    log.error("Failed to process message: {}", message.body(), e);
                    return Mono.empty();
                })
                //TODO: propagate the token from the message attribute to the context
                .contextWrite(ctx -> ctx.put(AUTHORIZATION.getValue(), "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huLmRvZUBlbWFpbC5jb20iLCJST0xFIjoiQURNSU4iLCJJREVOVElGSUNBVElPTiI6Ijg3Njc4NjY4NDU0IiwiaWF0IjoxNzU4MDg3MTUxLCJleHAiOjE3NTgwODg5NTF9.QIUjwzA1IBq3bFFkbYN57nkEOYbZBNQJI-3nnW-tljxi7T8vFpunsFz0f_CHvjKFS8Jiv4elDBdqNNDuEONAyQ"))
                ;
    }
}
