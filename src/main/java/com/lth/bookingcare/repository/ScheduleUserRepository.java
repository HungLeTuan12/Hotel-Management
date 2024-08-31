package com.lth.bookingcare.repository;

import com.lth.bookingcare.entity.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {
}
