package co.com.crediya.loan.model.loans.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanInformation {
    private UUID id;
    private BigDecimal amount;
    private Integer term;
    private String identification;
    private String loanTypeName;
    private String stateName;
    private String email;
    private String names;
    private String lastNames;
}
