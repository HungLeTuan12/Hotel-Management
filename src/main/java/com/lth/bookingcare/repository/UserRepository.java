package com.lth.bookingcare.repository;

import com.lth.bookingcare.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u " +
            "WHERE (:id IS NULL OR u.major.id = :id) " +
            "AND (:fullName IS NULL OR u.fullName LIKE %:fullName%) " +
            "AND (:enabled IS NULL OR u.enabled := enabled) "
    )
    Page<User> getAllDoctors(Long id, String fullName, Pageable pageable, Boolean enabled);
}
