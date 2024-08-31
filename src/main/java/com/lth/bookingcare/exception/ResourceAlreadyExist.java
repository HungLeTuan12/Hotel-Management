package com.lth.bookingcare.exception;

public class ResourceAlreadyExist extends RuntimeException{
    public ResourceAlreadyExist(String mess) {
        super(mess);
    }
}
