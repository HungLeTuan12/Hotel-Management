package com.lth.bookingcare.repository;

import com.lth.bookingcare.entity.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourRepository extends JpaRepository<Hour, Long> {
}
