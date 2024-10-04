package com.lth.hotelmanagement.service;

import com.lth.hotelmanagement.entity.Role;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.UserAlreadyExistsException;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);
    void deleteRole(Long id);
    Role findByName(String name);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId) throws UserAlreadyExistsException;
    Role removeAllUsersFromRole(Long roleId);
}
