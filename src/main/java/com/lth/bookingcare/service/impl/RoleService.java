package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.constant.RoleName;
import com.lth.bookingcare.dto.RoleDTO;
import com.lth.bookingcare.entity.Role;
import com.lth.bookingcare.repository.RoleRepository;
import com.lth.bookingcare.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public boolean saveRole(RoleDTO roleDTO, Long id) {
        Role role = new Role();
        if(id != null)  role.setId(id);
        convertDtoToEntity(role, roleDTO);
        return roleRepository.save(role) != null;
    }

    @Override
    public boolean findById(Long id) {
        return roleRepository.findById(id) != null;
    }

    @Override
    public boolean checkNameExists(RoleName roleName) {
        return roleRepository.findByName(roleName) != null;
    }
    public void convertDtoToEntity(Role role,RoleDTO roleDTO) {
        role.setRoleName(roleDTO.getRoleName());
        role.setDescription(roleDTO.getDescription());
    }
}
