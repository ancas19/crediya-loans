package co.com.crediya.loan.model.loans.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanNotificationInformation {
    private UUID id;
    private String identification;
    private String names;
    private String lastName;
    private BigDecimal interest;
    private BigDecimal amount;
    private Integer term;
    private String status;
    private String statusDescription;
    private String loanType;
    private String email;
    private BigDecimal monthlyPayment;
    private List<Payments> payments;
}
