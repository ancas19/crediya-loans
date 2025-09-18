package co.com.crediya.loan.model.queuenotification.models;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoansApproved {
    private BigDecimal interest;
    private Integer term;
    private BigDecimal amount;
}
