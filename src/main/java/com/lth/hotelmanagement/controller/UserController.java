package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.repository.RoomRepository;
import com.lth.hotelmanagement.response.BookingResponse;
import com.lth.hotelmanagement.response.RoomResponse;
import com.lth.hotelmanagement.response.UserResponse;
import com.lth.hotelmanagement.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoomRepository roomRepository;
    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("userId") Long id) {
        try {
            User theUser = userService.getUser(id);
            UserResponse userResponse = getUserResponse(theUser);
            return ResponseEntity.ok(userResponse);
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    private UserResponse getUserResponse(User theUser) {
        return new UserResponse(theUser.getId(), theUser.getFirstName(), theUser.getLastName(), theUser.getEmail(),
                theUser.getPassword());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') and #email = principal.username")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable("userId") String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("Deleted user successfully !");
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
    @GetMapping("/get-booking-history/{userId}")
    public ResponseEntity<?> getBookingHistory(@PathVariable("userId") Long userId) {
        System.out.println("==========userId: " + userId);
        try {
            List<BookedRoom> bookings = userService.getBookingHistory(userId);
            List<BookingResponse> bookingResponses = new ArrayList<>();

            for(BookedRoom bookedRoom : bookings) {
                BookingResponse bookingResponse = getBookingResponse(bookedRoom);
                bookingResponses.add(bookingResponse);
            }
            return ResponseEntity.ok(bookingResponses);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
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
}
