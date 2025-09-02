package co.com.crediya.loan.model.loantype.models;
import co.com.crediya.loan.model.commons.models.AbstractAuditoria;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
public class LoanType extends AbstractAuditoria {
    private UUID id;
    private String name;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal interestRate;
    private String autoValidation;
}
