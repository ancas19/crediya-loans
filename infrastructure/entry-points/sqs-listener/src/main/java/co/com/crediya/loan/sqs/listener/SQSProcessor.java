package co.com.crediya.loan.sqs.listener;

import co.com.crediya.loan.model.loans.models.LoansStatus;
import co.com.crediya.loan.sqs.listener.request.LoanSQSRequest;
import co.com.crediya.loan.usecase.loans.LoansUseCase;
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
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), LoanSQSRequest.class))
                .flatMap(loanSQSRequest -> loansUseCase.updateLoanState(new LoansStatus(loanSQSRequest.getId(), loanSQSRequest.getSatateName()))
                        .doOnNext(loan -> log.info("Loan with id {} updated to state {}", loan.getId(), loan.getStateName()))
                        .then()
                        .contextWrite(ctx -> ctx.put(AUTHORIZATION.getValue(), loanSQSRequest.getToken()))
                )
                .onErrorResume(e -> {
                            log.error("Failed to process message: {}", message.body(), e);
                            return Mono.empty();
                        }
                );
    }
}
