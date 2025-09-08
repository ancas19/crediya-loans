package co.com.crediya.loan.model.loans.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanSearch {
    private String identification;
    private String state;
    private Integer page;
    private Integer size;
}
