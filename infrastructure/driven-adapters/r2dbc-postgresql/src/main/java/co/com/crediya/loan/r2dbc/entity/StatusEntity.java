package co.com.crediya.loan.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Table(name="estado")
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntity extends AbstractAuditoriaEntity{
    @Id
    @Column("id")
    private UUID id;
    @Column("name")
    private String name;
    @Column("descripcion")
    private String description;
}
