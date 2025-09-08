package co.com.crediya.loan.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoanSearchRequest {
    @NotNull(message="the field is required")
    @Pattern(regexp = "^(\\\\d{10,15})?$", message = "the field must be between 10 and 15 digits")
    private String identification;
    @NotNull(message="the field is required")
    @Pattern(regexp = "^[A-Z]*$", message = "the field must be uppercase letters only")
    private String state;
}
