package com.adams.gaspricecalculator.error;

import com.adams.gaspricecalculator.model.outcoming.ApiError;
import com.adams.gaspricecalculator.model.outcoming.ApiFieldError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Clock clock;

    public RestExceptionHandler(Clock clock) {
        this.clock = clock;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ApiFieldError> fieldErrorList = ex.getBindingResult().getFieldErrors().stream()
                .map(a -> new ApiFieldError(a.getField(), a.getDefaultMessage())).toList();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                "Your Request has a few field errors", fieldErrorList);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(
            IllegalAccessException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now(clock),
                ex.getMessage(),
                null);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(GasValidationException.class)
    protected ResponseEntity<ApiError> handleValidationException(
            GasValidationException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now(clock),
                ex.getMessage(),
                ex.getApiFieldErrors());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ApiFieldError> fieldErrorList = ex.getConstraintViolations().stream()
                .map(a -> new ApiFieldError(a.getPropertyPath().toString(), a.getMessage())).toList();

        ApiError apiError = new ApiError(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now(clock),
                ex.getMessage(),
                fieldErrorList);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

}