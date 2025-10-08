package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ComplaintRequest {

    @NotBlank
    private String category;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;

    private Set<String> mediaRefs; // Optional, references to uploaded media

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getMediaRefs() {
        return mediaRefs;
    }

    public void setMediaRefs(Set<String> mediaRefs) {
        this.mediaRefs = mediaRefs;
    }
}
