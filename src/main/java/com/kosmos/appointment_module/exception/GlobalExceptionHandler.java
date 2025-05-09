package com.kosmos.appointment_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Regla de negocio", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String combinedErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildError(HttpStatus.BAD_REQUEST, "Validación de datos", combinedErrors);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBind(BindException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Error de formato", "Los datos enviados no tienen el formato correcto.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", "Se ha producido un error inesperado.");
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String error, String message) {
        ApiError apiError = new ApiError(status.value(), error, message);
        return new ResponseEntity<>(apiError, status);
    }
}
