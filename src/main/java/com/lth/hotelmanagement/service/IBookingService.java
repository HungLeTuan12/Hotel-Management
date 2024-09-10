package com.lth.hotelmanagement.service;

import com.lth.hotelmanagement.entity.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookingByRoomId(Long id);
}
