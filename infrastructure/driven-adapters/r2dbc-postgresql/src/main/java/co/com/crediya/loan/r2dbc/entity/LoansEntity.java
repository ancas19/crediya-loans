package co.com.crediya.loan.r2dbc.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Table(name="solicitud")
@NoArgsConstructor
@AllArgsConstructor
public class LoansEntity extends AbstractAuditoriaEntity{
    @Id
    @Column("id")
    private UUID id;
    @Column("monto")
    private BigDecimal amount;
    @Column("plazo")
    private Integer term;
    @Column("identification")
    private String identification;
    @Column("id_estado")
    private UUID stateId;
    @Column("tipo_prestamos")
    private UUID loanTypeId;

}
