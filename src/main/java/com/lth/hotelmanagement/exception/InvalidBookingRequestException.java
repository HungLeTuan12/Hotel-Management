package com.lth.hotelmanagement.exception;

public class InvalidBookingRequestException extends RuntimeException{
    public InvalidBookingRequestException(String mess) {
        super(mess);
    }
}
