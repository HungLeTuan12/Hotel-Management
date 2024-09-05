package com.lth.hotelmanagement.response;

import com.lth.hotelmanagement.entity.Room;
import jakarta.persistence.*;

import java.time.LocalDate;

public class BookingResponse {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmCode;
    private RoomResponse room;

    // Constructor

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmCode = bookingConfirmCode;
    }
}
