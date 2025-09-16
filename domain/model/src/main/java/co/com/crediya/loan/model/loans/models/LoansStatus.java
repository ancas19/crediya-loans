package co.com.crediya.loan.model.loans.models;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoansStatus {
    private UUID id;
    private String satateName;
}
