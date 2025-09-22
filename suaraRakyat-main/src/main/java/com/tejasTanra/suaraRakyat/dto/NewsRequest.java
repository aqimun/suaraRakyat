package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class NewsRequest {

    @NotBlank
    @Size(min = 5, max = 255)
    private String title;

    @NotBlank
    @Size(min = 20)
    private String body;

    private Set<String> mediaRefs; // Optional, references to uploaded media

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getMediaRefs() {
        return mediaRefs;
    }

    public void setMediaRefs(Set<String> mediaRefs) {
        this.mediaRefs = mediaRefs;
    }
}
