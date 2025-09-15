package co.com.crediya.loan.model.emailnotification.models;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmailNotification {
    private String to;
    private String subject;
    private String body;
}
