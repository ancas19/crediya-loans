package co.com.crediya.loan.model.status.models;
import co.com.crediya.loan.model.commons.models.AbstractAuditoria;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
public class Status extends AbstractAuditoria {
    private UUID id;
    private String name;
    private String description;
}
