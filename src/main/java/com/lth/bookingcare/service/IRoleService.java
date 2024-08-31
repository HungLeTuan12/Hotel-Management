package com.lth.bookingcare.service;

import com.lth.bookingcare.constant.RoleName;
import com.lth.bookingcare.dto.RoleDTO;

public interface IRoleService {
    void deleteRole(Long id);
    boolean saveRole(RoleDTO roleDTO, Long id);
    boolean findById(Long id);
    boolean checkNameExists(RoleName roleName);
}
