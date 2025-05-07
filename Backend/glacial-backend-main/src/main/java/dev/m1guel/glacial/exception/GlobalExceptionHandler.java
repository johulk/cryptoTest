package dev.m1guel.glacial.exception;

import dev.m1guel.glacial.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleGlobalException(Exception e) {
        return new ResponseEntity<>(
                new ResponseDto<>(false, e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            FieldError firstError = ex.getBindingResult().getFieldErrors().getFirst();
            return new ResponseEntity<>(
                    new ResponseDto<>(false, firstError.getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                new ResponseDto<>(false, "Invalid input.", null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        if (!e.getConstraintViolations().isEmpty()) {
            String firstError = e.getConstraintViolations().iterator().next().getMessage();
            return new ResponseEntity<>(
                    new ResponseDto<>(false, firstError, null),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                new ResponseDto<>(false, "Validation error.", null),
                HttpStatus.BAD_REQUEST
        );
    }

}