package com.lth.hotelmanagement.exception;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String s) {
        super(s);
    }
}
