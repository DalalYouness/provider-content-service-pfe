package com.dalal.providercontentservicepfe.handler;

import com.dalal.providercontentservicepfe.dtos.AddCategoryResponseDTO;
import com.dalal.providercontentservicepfe.exceptions.CategoryException;
import com.dalal.providercontentservicepfe.exceptions.ServiceAlreadyExistsException;
import com.dalal.providercontentservicepfe.exceptions.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // for handling the CategoryException
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<AddCategoryResponseDTO> handleCategoryAlreadyExists (CategoryException categoryException){
        AddCategoryResponseDTO categoryResponseDTO = new AddCategoryResponseDTO(categoryException.getMessage());
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.BAD_REQUEST);
    }

    // for handling MethodArgumentNotValidException after validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("validationErrors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleServiceNotFoundException(ServiceNotFoundException serviceNotFoundException){
        Map<String,Object> response = new HashMap<>();
        // i convert the localdate to string because it will send as an array so it's a little ambiguous
        response.put("timestamp",LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", serviceNotFoundException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleServiceAlreadyExistsException(ServiceAlreadyExistsException serviceAlreadyExistsException){
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp",LocalDateTime.now().toString());
        response.put("status", HttpStatus.CONFLICT.value()); // status : 409
        response.put("message", serviceAlreadyExistsException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
