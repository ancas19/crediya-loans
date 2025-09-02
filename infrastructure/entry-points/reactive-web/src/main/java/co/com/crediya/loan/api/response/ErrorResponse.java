package co.com.crediya.loan.api.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        List<String> messages,
        String code,
        String detail,
        LocalDateTime timestamp
) {
}
