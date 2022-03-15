package br.com.kikuchi.henrique.starwarsapi.handler;

import br.com.kikuchi.henrique.starwarsapi.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

record Error(
        String message,
        String description
){}

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler({RebelNotFoundException.class})
    public ResponseEntity<Error> handlerRebelNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Error("Rebel not found",
                        "Rebel not found"));
    }

    @ExceptionHandler({BetrayerDetectedException.class})
    public ResponseEntity<Error> handlerBetrayerException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Error("You are a betrayer",
                        "Betrayers are not allowed to do this"));
    }

    @ExceptionHandler({RebelResourcesIsNotEnoughException.class})
    public ResponseEntity<Error> handlerRebelResourceIsNotEnoughException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Error("insufficient resources",
                        "You or the other rebel don't have enough resources to carry out this operation."));
    }

    @ExceptionHandler({ReportAlreadyExistsException.class})
    public ResponseEntity<Error> handlerReportAlreadyExistsExceptions(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Error("Invalid report",
                        "You cannot report the same rebel reported by you more than once."));
    }

    @ExceptionHandler({SelfReportException.class})
    public ResponseEntity<Error> handlerSelfReportException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Error("Invalid report",
                        "You can't report of yourself."));
    }

    @ExceptionHandler({SelfNegotiationException.class})
    public ResponseEntity<Error> handlerSelfNegotiationException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Error("Invalid negotiation",
                        "You can't negotiate with yourself."));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Error> handlerDataIntegrityViolationException(){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new Error("Data integrity violation.",
                        "You are trying add an object that violates data integrity values."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
