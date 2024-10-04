package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.exception.InvalidBookingRequestException;
import com.lth.hotelmanagement.exception.ResourceNotFoundException;
import com.lth.hotelmanagement.repository.BookedRoomRepository;
import com.lth.hotelmanagement.repository.RoomRepository;
import com.lth.hotelmanagement.response.BookingResponse;
import com.lth.hotelmanagement.response.RoomResponse;
import com.lth.hotelmanagement.service.impl.BookingService;
import com.lth.hotelmanagement.service.impl.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @GetMapping("/")
    public ResponseEntity<?> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        System.out.println(bookingResponses);
        return ResponseEntity.ok(bookingResponses);
    }

    public BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomRepository.findById(booking.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getPrice());
        System.out.println("roomP" + roomResponse.getId());
        return new BookingResponse(booking.getId(), booking.getCheckInDate(), booking.getCheckOutDate(),
                 booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getTotalNumOfGuest(), booking.getBookingConfirmCode(), roomResponse);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/room/{roomId}/booking/{userId}")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @PathVariable Long userId,
                                         @RequestBody BookedRoom bookingRequest) {
        try {
            String confirmationCode = bookingService.saveBooking(roomId, userId, bookingRequest);
            return ResponseEntity.ok(
                    "Room booked successfully ! Your booking confirmation code is: " + confirmationCode);
        }
        catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}
