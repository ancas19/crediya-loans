package co.com.crediya.loan.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanRequest {
    private UUID id;
    @NotNull(message="the field is required")
    @NotEmpty(message="the field cannot be empty")
    @Pattern(regexp = "^\\d{10,15}$", message = "the field must be between 10 and 15 digits")
    private String identification;
    @NotNull(message="the field is required")
    @NotEmpty(message="the field cannot be empty")
    private BigDecimal amount;
    @NotNull(message="the field is required")
    private Integer term;
    @NotNull(message="the field is required")
    @Pattern(regexp = "[A-Z]+$", message = "the field must be uppercase letters only")
    private String loanType;
}
