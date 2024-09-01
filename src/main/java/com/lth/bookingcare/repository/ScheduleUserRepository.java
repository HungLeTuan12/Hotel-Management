package com.lth.bookingcare.repository;

import com.lth.bookingcare.entity.ScheduleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {
    List<ScheduleUser> findByUserId(Long id);
    ScheduleUser findByUser_IdAndSchedule_Id(Long id, Long id2);
}
