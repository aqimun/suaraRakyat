package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Email or phone cannot be blank")
    private String emailPhone;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Getters and Setters
    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
