package com.lth.hotelmanagement.repository;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.response.BookingResponse;
import com.lth.hotelmanagement.service.impl.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    @Query("SELECT new com.lth.hotelmanagement.response.BookingResponse(b.id, b.guestFullName, b.guestEmail, b.numOfAdults, b.numOfChildren, b.totalGuest, b.bookingConfirmCode) FROM BookedRoom b")
    List<BookingResponse> findAllInfor();

    List<BookedRoom> findByRoomId(Long id);

    List<BookedRoom> findByUserId(Long userId);

    BookedRoom findByBookingConfirmCode(String confirmationCode);
}
