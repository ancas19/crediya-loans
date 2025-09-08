package co.com.crediya.loan.api.response;

import co.com.crediya.loan.model.loans.models.LoanInformation;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoansPaginatedResponse {
    private Integer page;
    private Integer size;
    private Integer loansQuantity;
    private List<LoanInformation> loans;
}
