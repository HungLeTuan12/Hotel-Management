package com.lth.hotelmanagement.response;

import com.lth.hotelmanagement.entity.BookedRoom;
import jakarta.persistence.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal price;
    private boolean isBooked = false;
    private String photo;
    private List<BookingResponse> bookings = new ArrayList<>();
    // Constructor
    public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.price = roomPrice;
    }

    public RoomResponse(Long id, String roomType, BigDecimal price, boolean isBooked, byte[] photo, List<BookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.isBooked = isBooked;
        this.photo = photo != null ? Base64.encodeBase64String(photo) : null;
        this.bookings = bookings;
    }
}
