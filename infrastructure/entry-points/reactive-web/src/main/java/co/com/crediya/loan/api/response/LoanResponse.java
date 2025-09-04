package co.com.crediya.loan.api.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanResponse {
    private UUID id;
    private BigDecimal amount;
    private Integer term;
    private String identification;
    private String stateName;
    private String loanTypeName;
}
