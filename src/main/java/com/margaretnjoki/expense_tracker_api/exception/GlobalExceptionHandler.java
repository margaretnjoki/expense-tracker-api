package com.margaretnjoki.expense_tracker_api.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));
        return Map.of("error", "validation failed", "fields", fields);
    }

    @ExceptionHandler(ConstraintViolationException.class)   // @RequestParam validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraint(ConstraintViolationException ex) {
        return Map.of("error", "validation failed", "message", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)            // business rules
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(BadRequestException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleEmailInUse(EmailAlreadyInUseException ex) {
        return Map.of("error", ex.getMessage());
    }
}
