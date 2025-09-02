package co.com.crediya.loan.api.utils;

import co.com.crediya.commons.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomValidator {
    private final Validator validator;
    public <T> Mono<T> validate(T dto) {
        Errors errors = new BeanPropertyBindingResult(dto, dto.getClass().getName());
        validator.validate(dto, errors);
        if (errors.hasErrors()) {
            String errorMessages = errors.getAllErrors()
                    .stream()
                    .map(oError -> {
                        if (oError instanceof FieldError fieldError) {
                            return "%s: %s".formatted(fieldError.getField().trim(), fieldError.getDefaultMessage());
                        }
                        return "%s: %s".formatted(oError.getObjectName(), oError.getDefaultMessage());
                    })
                    .collect(Collectors.joining(", "));

            return Mono.error(new BadRequestException(errorMessages));
        }
        return Mono.just(dto);
    }
}
