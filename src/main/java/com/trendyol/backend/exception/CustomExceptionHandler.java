package com.trendyol.backend.exception;

import com.trendyol.backend.core.utilities.results.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice//custom exception
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException notFoundException) {
        ErrorResult errorResult = new ErrorResult(notFoundException.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExist.class)
    public ResponseEntity<?> alreadyExist(AlreadyExist alreadyExist) {
        ErrorResult errorResult = new ErrorResult(alreadyExist.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.ALREADY_REPORTED);
    }
}