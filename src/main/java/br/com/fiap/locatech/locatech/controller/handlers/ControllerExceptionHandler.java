package br.com.fiap.locatech.locatech.controller.handlers;

import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDTO;
import br.com.fiap.locatech.locatech.dtos.ValidationErrorDTO;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
       var status = HttpStatus.NOT_FOUND;
       return ResponseEntity.status(status.value()).body(new ResourceNotFoundDTO(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() +  ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDTO(errors, status.value()));
    }
}
