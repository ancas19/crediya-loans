package co.com.crediya.loan.model.state.models;
import co.com.crediya.loan.model.commons.models.AbstractAuditoria;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
public class State  extends AbstractAuditoria {
    private UUID id;
    private String name;
    private String description;
}
