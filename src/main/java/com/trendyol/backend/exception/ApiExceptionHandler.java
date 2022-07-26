package com.trendyol.backend.exception;

import com.trendyol.backend.core.utilities.results.ErrorDataResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Validation Error");
        return errors;
    }
/*
    @ExceptionHandler(NullPointerException.class)//sistemde hata olursa bu methodu çağır hataları paramatre olarak geç
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDataResult<Object> handleValidationException(NullPointerException exception) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", exception.getMessage());
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(hashMap, "Null Pointer Exception");
        return errorDataResult;
    }
*/


}
