package com.lth.bookingcare.controller;

import com.lth.bookingcare.constant.Status;
import com.lth.bookingcare.dto.BookingDTO;
import com.lth.bookingcare.entity.Booking;
import com.lth.bookingcare.service.impl.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam(value = "status", required = false) Status status,
                                            @RequestParam(value = "idDoctor", required = false) Long id,
                                            @RequestParam(value = "start", required = false) String start,
                                            @RequestParam(value = "end", required = false) String end) {
        try {
            List<Booking> bookings = bookingService.findByParam(status, id, start, end);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Bookings ! ", bookings));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            String s = bookingService.createBooking(bookingDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create booking successfully ! "));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @PostMapping("/booking/{id}")
    public ResponseEntity<?> updateBooking(@RequestBody BookingDTO bookingDTO,
                                           @PathVariable("id") Long id
                                           )
    {
        try {
            if(bookingService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Booking not found !"));
            }
            bookingService.updateBooking(id, Status.valueOf(bookingDTO.getStatus()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update booking successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @GetMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable("id") Long id,
                                            @RequestParam("token") String token) {
        try {
            Booking booking = bookingService.findById(id);
            if(booking == null || booking.getToken() == null) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse<>("Link url is not found !"));
            }
            bookingService.updateBooking(id, Status.CONFIRMING);
            return ResponseEntity.ok().body(new SuccessResponse<>("Update successfully with schedule id: " + id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id) {
        try {
            if(bookingService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Booking not found !"));
            }
            bookingService.deleteBooking(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete booking successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
