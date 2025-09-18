package co.com.crediya.loan.sqs.listener.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanSQSRequest {
    private UUID id;
    private String satateName;
    private String token;
}
