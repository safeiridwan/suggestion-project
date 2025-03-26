package com.suggestion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex) {
        switch (ex) {
            case MethodArgumentNotValidException er -> {

                List<String> errors = new ArrayList<>();
                er.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

                Map<String, String> error = new HashMap<>();
                error.put("code", "400");
                error.put("message", errors.toString());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            case HttpMessageNotReadableException ignored -> {
                Map<String, String> error = new HashMap<>();
                error.put("code", "400");
                error.put("message", "Bad Request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            case HttpRequestMethodNotSupportedException methodNotSupportedEx -> {

                String methodName = methodNotSupportedEx.getMethod();
                String message = "Request method '" + methodName + "' not supported.";

                Map<String, String> error = new HashMap<>();
                error.put("code", "405");
                error.put("message", message);
                return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
            }
            case null, default -> {
                Map<String, String> error = new HashMap<>();
                error.put("code", "500");
                error.put("message", ex.getMessage());
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
