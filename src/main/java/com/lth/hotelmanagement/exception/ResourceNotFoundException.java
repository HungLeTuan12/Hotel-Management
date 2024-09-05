package com.lth.hotelmanagement.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mess) {
        super(mess);
    }
}
