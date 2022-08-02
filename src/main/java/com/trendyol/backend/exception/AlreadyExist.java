package com.trendyol.backend.exception;

public class AlreadyExist extends RuntimeException {
    public AlreadyExist(String message) {
        super(message);
    }
}
