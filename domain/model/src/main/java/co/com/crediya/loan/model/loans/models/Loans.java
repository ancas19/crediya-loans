package co.com.crediya.loan.model.loans.models;
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
public class Loans extends AbstractAuditoria {
    private UUID id;
    private BigDecimal amount;
    private Integer term;
    private String identification;
    private UUID stateId;
    private UUID loanTypeId;
    private String stateName;
    private String loanTypeName;
}
