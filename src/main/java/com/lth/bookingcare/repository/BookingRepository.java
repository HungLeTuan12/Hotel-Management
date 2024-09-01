package com.lth.bookingcare.repository;

import com.lth.bookingcare.constant.Status;
import com.lth.bookingcare.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT u FROM USER u " +
            "WHERE (:id IS NULL OR u.user.id = :id) " +
            "AND (:status IS NULL OR u.status = :status) " +
            "AND (:start IS NULL OR u.date >= start) " +
            "AND (:end IS NULL OR u.date >= end) " +
            "order by u.date"
    )
    List<Booking> findByCustom(Long id, Status status, Date start, Date end);
}
