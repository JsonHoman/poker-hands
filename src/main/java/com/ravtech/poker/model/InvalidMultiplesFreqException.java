package com.ravtech.poker.model;

public class InvalidMultiplesFreqException extends RuntimeException {
    public InvalidMultiplesFreqException(String message) {
        super(message);
    }

    public InvalidMultiplesFreqException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMultiplesFreqException(Throwable cause) {
        super(cause);
    }
}
