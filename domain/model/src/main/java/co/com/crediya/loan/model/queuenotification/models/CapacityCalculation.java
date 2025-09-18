package co.com.crediya.loan.model.queuenotification.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CapacityCalculation {
    private BigDecimal baseSalary;
    private UUID id;
    private BigDecimal interest;
    private Integer term;
    private BigDecimal amount;
    private String token;
    private List<LoansApproved> loans;
}
