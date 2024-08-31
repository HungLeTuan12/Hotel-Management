package com.lth.bookingcare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ArticleDTO {
    @NotNull(message = "Title can't null !")
    @Size(min = 10, max = 255, message = "Length of title must between 10 and 255 !")
    private String title;
    @NotNull(message = "Content can't null !")
    @Size(min = 10, max = 255, message = "Length of content must between 10 and 255 !")
    private String content;
    private List<String> images = new ArrayList<>();
    // Data

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
