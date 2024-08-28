package com.lth.bookingcare.entity;

import com.lth.bookingcare.constant.RoleName;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoleName roleName;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<User> users = new ArrayList<>();
    // Constructor

    public Role() {
    }

    public Role(Long id, RoleName roleName, String description, List<User> users) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.users = users;
    }
    public Role(RoleName roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
    // Getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
