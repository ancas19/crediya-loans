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
@Table(name="tipo_prestamo")
@NoArgsConstructor
@AllArgsConstructor
public class LoanTypeEntity  extends AbstractAuditoriaEntity{
    @Id
    @Column("id")
    private UUID id;
    @Column("nombre")
    private String name;
    @Column("monto_minimo")
    private BigDecimal minAmount;
    @Column("monto_maximo")
    private BigDecimal maxAmount;
    @Column("tasa_interes")
    private BigDecimal interestRate;
    @Column("validacion_automatica")
    private String autoValidation;
}
