package com.lth.hotelmanagement.service.impl;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.repository.BookedRoomRepository;
import com.lth.hotelmanagement.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingService implements IBookingService {
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Override
    public List<BookedRoom> getAllBookingByRoomId(Long id) {
        return null;
    }
}
