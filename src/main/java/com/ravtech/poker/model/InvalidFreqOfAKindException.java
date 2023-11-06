package com.ravtech.poker.model;

public class InvalidFreqOfAKindException extends RuntimeException {
    public InvalidFreqOfAKindException(String message) {
        super(message);
    }

    public InvalidFreqOfAKindException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFreqOfAKindException(Throwable cause) {
        super(cause);
    }
}
