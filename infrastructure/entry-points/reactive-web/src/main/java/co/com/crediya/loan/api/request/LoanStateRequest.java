package co.com.crediya.loan.api.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanStateRequest {
    private UUID id;
    private String stateName;
}
