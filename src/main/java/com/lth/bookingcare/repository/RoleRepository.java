package com.lth.bookingcare.repository;

import com.lth.bookingcare.constant.RoleName;
import com.lth.bookingcare.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
