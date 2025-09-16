package co.com.crediya.loan.model.template.models;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Template {
    private UUID id;
    private String name;
    private String subject;
    private String content;
}
