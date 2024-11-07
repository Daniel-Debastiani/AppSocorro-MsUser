package com.ms.user.exception;

import com.ms.user.exception.customException.CustomNotFoundException;
import com.ms.user.exception.details.ErrorMessage;
import com.ms.user.exception.details.ErrorMessageMethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        System.out.println("\nERROR|00\n" + ex.toString() + "\nERROR|00 \n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        System.out.println("\nERROR|01\n" + ex.toString() + "\nERROR|01 \n");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> handleCustomNotFoundException(CustomNotFoundException ex) {
        System.out.println("\nERROR|02\n" + ex.toString() + "\nERROR|02 \n");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageMethodArgumentNotValidException>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        System.out.println("\nERROR|03\n" + ex.toString() + "\nERROR|03 \n");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ErrorMessageMethodArgumentNotValidException> errors = new ArrayList<>();
        fieldErrors.forEach(fieldError ->
                errors.add(new ErrorMessageMethodArgumentNotValidException(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        fieldError.getObjectName(),
                        fieldError.getRejectedValue()
                )));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        System.out.println("\nERROR|04\n" + ex.toString() + "\nERROR|04 \n");

        if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
            String errorSplit = ex.getMostSpecificCause().getMessage().split(":")[2];
            String field = errorSplit.split("\\(")[1].split("\\)")[0];
            String value = errorSplit.split("\\(")[2].split("\\)")[0];
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("DataIntegrityViolationException", "This '%s'(%s) already exists".formatted(field, value)));
        } else if (ex.getMessage().contains("violates foreign key constraint")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("DataIntegrityViolationException", "This data is being used"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("DataIntegrityViolationException", "Data integrity violation"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> HttpMessageNotReadableException(Exception e) {
        System.out.println("\nERROR|05\n" + e.toString() + "\nERROR|05\n");
        String msg1 = e.getMessage().split(":")[2];
        String msg2 = e.getMessage().split(":")[3];
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("HttpMessageNotReadableException", (msg1 + ":" + msg2)));
    }
}
