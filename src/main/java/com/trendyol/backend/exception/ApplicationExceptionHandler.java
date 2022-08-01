package com.trendyol.backend.exception;

import com.trendyol.backend.core.utilities.results.ErrorDataResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class ApplicationExceptionHandler extends RuntimeException {
    @ExceptionHandler(NullPointerException.class)//sistemde hata olursa bu methodu çağır hataları paramatre olarak geç
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDataResult<Object> handleValidationException(NullPointerException exception) {
        Map<String, String> validationErrors = new HashMap<String, String>();
        validationErrors.put("message", exception.getMessage());
        ErrorDataResult<Object> doğrulama_hatası = new ErrorDataResult<>(validationErrors, "Not null");
        return doğrulama_hatası;
    }

}
