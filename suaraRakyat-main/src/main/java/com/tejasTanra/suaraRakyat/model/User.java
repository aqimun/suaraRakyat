package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password; // Hashed password

    @Column(nullable = false, unique = true)
    private String email; // Stored as hashed_idx as per spec, actual hashing logic in service layer

    @Column(nullable = false, unique = true)
    private String phone; // Stored as hashed_idx as per spec, actual hashing logic in service layer

    @Column(name = "encrypted_ktp_ref")
    private String encryptedKtpRef; // Reference to encrypted KTP data

    @Column(name = "encrypted_selfie_ref")
    private String encryptedSelfieRef; // Reference to encrypted selfie data

    @Column(name = "name_display", nullable = false)
    private String nameDisplay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status; // PENDING, ACTIVE, SUSPENDED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public User() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEncryptedKtpRef() {
        return encryptedKtpRef;
    }

    public void setEncryptedKtpRef(String encryptedKtpRef) {
        this.encryptedKtpRef = encryptedKtpRef;
    }

    public String getEncryptedSelfieRef() {
        return encryptedSelfieRef;
    }

    public void setEncryptedSelfieRef(String encryptedSelfieRef) {
        this.encryptedSelfieRef = encryptedSelfieRef;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
