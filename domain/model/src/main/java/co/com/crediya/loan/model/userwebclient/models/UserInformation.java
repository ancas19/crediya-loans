package co.com.crediya.loan.model.userwebclient.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInformation {
    private UUID id;
    private String identification;
    private String names;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private BigDecimal baseSalary;
    private String email;
}
