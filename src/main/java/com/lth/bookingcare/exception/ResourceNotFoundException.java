package com.lth.bookingcare.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mess) {
        super(mess);
    }
}
