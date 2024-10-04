package com.lth.hotelmanagement.service.impl;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.InvalidBookingRequestException;
import com.lth.hotelmanagement.repository.BookedRoomRepository;
import com.lth.hotelmanagement.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingService implements IBookingService {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Override
    public List<BookedRoom> getAllBookingByRoomId(Long id) {
        return bookedRoomRepository.findByRoomId(id);
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookedRoomRepository.findAll();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookedRoomRepository.findByBookingConfirmCode(confirmationCode);
    }

    @Override
    public String saveBooking(Long roomId, Long userId, BookedRoom bookingRequest) {
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check in date must come before check-out date !");
        }
        Room room = roomService.getRoomById(roomId).get();
        User user = userService.getUser(userId);
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);

        if(roomIsAvailable) {
            bookingRequest.setTotalNumOfGuest(bookingRequest.getNumOfAdults()  +bookingRequest.getNumOfChildren());

            bookingRequest.setUser(user);
            room.addBooking(bookingRequest);
            bookedRoomRepository.save(bookingRequest);
        }
        else {
            throw new InvalidBookingRequestException("This room has been booked for the selected dates !");
        }
        return bookingRequest.getBookingConfirmCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream().noneMatch(
                existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate()))
                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()))

                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())
                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()))

                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate())

                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()))
                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate())

                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()))
                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())
        );
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookedRoomRepository.deleteById(bookingId);
    }
}
