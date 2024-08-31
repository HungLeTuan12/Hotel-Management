package com.lth.bookingcare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class MajorDTO {
    @NotNull(message = "Name can't blank !")
    private String name;
    @Size(max = 64, message = "Length of description must longer than 64")
    private String description;
    private List<String> images = new ArrayList<>();

    // Data

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
}
