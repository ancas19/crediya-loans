package co.com.crediya.loan.api.exceptions;

import co.com.crediya.commons.enums.ErrorMessages;
import co.com.crediya.commons.exception.BadRequestException;
import co.com.crediya.commons.exception.NotFoundException;
import co.com.crediya.loan.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomExceptionhandler {
    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBadRequest(BadRequestException ex, ServerWebExchange exchange) {
        log.error("BadRequestException: ", ex);
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(  new ErrorResponse(
                                parseErrorMessages(ex.getMessage()),
                                HttpStatus.BAD_REQUEST.toString(),
                                exchange.getRequest().getURI().toString(),
                                LocalDateTime.now())
                        )
        );
    }
    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFoundException(NotFoundException ex, ServerWebExchange exchange) {
        log.error("Resource not found: ", ex);
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(  new ErrorResponse(
                                parseErrorMessages(ex.getMessage()),
                                HttpStatus.NOT_FOUND.toString(),
                                exchange.getRequest().getURI().toString(),
                                LocalDateTime.now())
                        )
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleNoResourceFoundException(NoResourceFoundException ex, ServerWebExchange exchange) {
        log.error("NoResourceFoundException: ", ex);
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(  new ErrorResponse(
                                List.of(ex.getMessage()),
                                HttpStatus.NOT_FOUND.toString(),
                                exchange.getRequest().getURI().toString(),
                                LocalDateTime.now())
                        )
        );
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleServerWebInputException(ServerWebInputException ex, ServerWebExchange exchange) {
        log.error("ServerWebInputException: ", ex);
        String userMessage = "Invalid request data. Please check your input and try again.";
        String detailedMessage = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        String fieldError = null;
        if (detailedMessage != null && detailedMessage.contains("field")) {
            fieldError = detailedMessage;
        }
        List<String> errors = fieldError != null ? List.of(userMessage, fieldError) : List.of(userMessage, detailedMessage);
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(
                                errors,
                                HttpStatus.BAD_REQUEST.toString(),
                                exchange.getRequest().getURI().toString(),
                                LocalDateTime.now())
                        )
        );
    }
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGlobalExceptions(Exception ex, ServerWebExchange exchange) {
        log.error("An unexpected error occurred: ", ex);
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(  new ErrorResponse(
                                List.of(ErrorMessages.ERROR_MESSAGE_GLOBAL_EXCEPTION.getMessage()),
                                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                exchange.getRequest().getURI().toString(),
                                LocalDateTime.now())
                        )
        );
    }


    private List<String> parseErrorMessages(String messages) {
        return List.of(messages.split(","));
    }
}
