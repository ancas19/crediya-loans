package co.com.crediya.loan.model.loans.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoansPaginated {
    private Integer page;
    private Integer size;
    private Integer loansQuantity;
    private List<LoanInformation> loans;
}
