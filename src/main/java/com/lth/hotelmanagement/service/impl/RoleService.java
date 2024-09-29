package com.lth.hotelmanagement.service.impl;

import com.lth.hotelmanagement.entity.Role;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.RoleAlreadyExistException;
import com.lth.hotelmanagement.exception.UserAlreadyExistsException;
import com.lth.hotelmanagement.repository.RoleRepository;
import com.lth.hotelmanagement.repository.UserRepository;
import com.lth.hotelmanagement.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if(roleRepository.existsByName(role)) {
            throw new RoleAlreadyExistException(theRole.getName() + "role already exist !");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        this.removeAllUsersFromRole(id);
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(String email, Long roleId) {
        User user = userService.getUser(email);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user)) {
            role.get().removeUserFromRole(user);
            roleRepository.save(role.get());
            return user;
        }
        throw new UsernameNotFoundException("User not found !!");

    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) throws UserAlreadyExistsException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new UserAlreadyExistsException(
                    user.get().getFirstName() + " is already exist !!! " + role.get().getName() + " role !!"
            );
        }
        if(role.isPresent()) {
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();
        return roleRepository.save(role.get());
    }
}
