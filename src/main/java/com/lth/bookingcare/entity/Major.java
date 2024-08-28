package com.lth.bookingcare.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ElementCollection
    private List<String> images;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "major")
    private List<User> users = new ArrayList<>();
    // Constructor

    public Major() {
    }

    public Major(Long id, String name, String description, List<String> images, List<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
        this.users = users;
    }
    // Data

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
