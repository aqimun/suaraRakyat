package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ComplaintRequest {

    @NotBlank
    private String category;

    private String locationGeo; // Optional

    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;

    private Set<String> mediaRefs; // Optional, references to uploaded media

    @NotNull
    private Boolean anonFlag; // True if anonymous

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationGeo() {
        return locationGeo;
    }

    public void setLocationGeo(String locationGeo) {
        this.locationGeo = locationGeo;
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

    public Boolean getAnonFlag() {
        return anonFlag;
    }

    public void setAnonFlag(Boolean anonFlag) {
        this.anonFlag = anonFlag;
    }
}
