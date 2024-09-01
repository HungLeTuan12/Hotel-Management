package com.lth.bookingcare.service;

import com.lth.bookingcare.constant.Status;
import com.lth.bookingcare.dto.BookingDTO;
import com.lth.bookingcare.entity.Booking;

import java.util.List;

public interface IBookingService {
    Booking findById(Long id);
    void updateBooking(Long id, Status status);
    String createBooking(BookingDTO bookingDTO);
    void deleteBooking(Long id);
    void deleteScheduleOfDoctor(Booking booking);
    List<Booking> findByParam(Status status, Long id, String start, String end);
}
