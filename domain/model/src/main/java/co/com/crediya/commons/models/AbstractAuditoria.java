package co.com.crediya.commons.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
public class AbstractAuditoria {
    private String usuarioCreacion;
    private String usuarioActualizacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
