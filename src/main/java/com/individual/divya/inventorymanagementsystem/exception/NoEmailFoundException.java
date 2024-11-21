package com.individual.divya.inventorymanagementsystem.exception;

public class NoEmailFoundException extends RuntimeException {
    public NoEmailFoundException(String emailNotFound) {
        super(emailNotFound);
    }
}
