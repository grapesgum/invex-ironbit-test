package com.invex.test.invextest.controller.advice;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.invex.test.invextest.exceptions.ApiRuntimeException;
import com.invex.test.invextest.exceptions.NotFoundEmployeeException;
import com.invex.test.invextest.model.ErrorDetails;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundEmployeeException.class)
    public ResponseEntity<ErrorDetails> exceptionNotFoundEmployeeHandler() {
        ErrorDetails errorDetails = new ErrorDetails("El empleado no fue encontrado.");
        return ResponseEntity
                             .badRequest()
                             .body(errorDetails);
    }

    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ErrorDetails> exceptionApiRuntimeHandler(Exception ex) {
        log.debug(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails("Ocurrió un error al procesar la petición.");
        return ResponseEntity
                             .internalServerError()
                             .body(errorDetails);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorDetails> exceptionDateTimeParseHandler(DateTimeParseException ex){
        log.debug(ex.getMessage());
        return ResponseEntity
                             .badRequest()
                             .body(new ErrorDetails("La fecha de nacimiento debe estar en el siguiente formato: 'dd-MM-yyyy'"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> exceptionHttpMessageNotReadableHandler(HttpMessageNotReadableException ex){
        log.debug(ex.getMessage());
        return ResponseEntity
                             .badRequest()
                             .body(new ErrorDetails("Verifica el tipo de dato requerido en el cuerpo de la petición."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <List<ErrorDetails>> exceptionMethodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage());
        List<ErrorDetails> listaMensajesError = new ArrayList<>();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            listaMensajesError.add(new ErrorDetails(fieldError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(listaMensajesError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> exceptionMethodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException ex) {
        log.debug(ex.getMessage());
        
        return ResponseEntity.badRequest().body(new ErrorDetails("Verifica el tipo de dato enviado en la petición."));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity <List<ErrorDetails>> exceptionConstraintViolationHandler(ConstraintViolationException ex) {
        log.debug(ex.getMessage());
        List<ErrorDetails> listaMensajesError = new ArrayList<>();

        for(ConstraintViolation<?> constraintViolation : ex.getConstraintViolations())
            listaMensajesError.add(new ErrorDetails(constraintViolation.getMessage()));
        
            return ResponseEntity.badRequest().body(listaMensajesError);
    }

}