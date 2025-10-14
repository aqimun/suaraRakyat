package com.tejasTanra.suaraRakyat.dto.response;

import com.tejasTanra.suaraRakyat.model.UserStatus;

public class UserResponse {
    private Long id;
    private String email;
    private String phone;
    private String nameDisplay;
    private String role;
    private UserStatus status;

    public UserResponse(Long id, String email, String phone, String nameDisplay, String role, UserStatus status) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.nameDisplay = nameDisplay;
        this.role = role;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
