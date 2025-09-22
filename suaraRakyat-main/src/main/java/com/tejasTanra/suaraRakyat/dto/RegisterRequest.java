package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    @Size(min = 3, max = 50)
    private String nameDisplay;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    // References to uploaded KYC documents (e.g., file paths or IDs)
    @NotBlank
    private String ktpRef;

    @NotBlank
    private String selfieRef;

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKtpRef() {
        return ktpRef;
    }

    public void setKtpRef(String ktpRef) {
        this.ktpRef = ktpRef;
    }

    public String getSelfieRef() {
        return selfieRef;
    }

    public void setSelfieRef(String selfieRef) {
        this.selfieRef = selfieRef;
    }
}
