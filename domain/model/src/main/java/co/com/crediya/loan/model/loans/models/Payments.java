package co.com.crediya.loan.model.loans.models;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Payments {
    private Long id;
    private BigDecimal amount;
    private BigDecimal interest;
    private BigDecimal capital;
    private BigDecimal balance;
}
