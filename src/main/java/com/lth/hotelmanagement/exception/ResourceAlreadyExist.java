package com.lth.hotelmanagement.exception;

public class ResourceAlreadyExist extends RuntimeException{
    public ResourceAlreadyExist(String mess) {
        super(mess);
    }
}
