package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "mst_complain")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    // ✅ Relasi ke user pelapor (Reported_UUID)
    @NotNull(message = "Reporter cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_uuid", nullable = false)
    private User reporter;

    // ✅ Relasi ke user petugas (Assigned_to)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    // ✅ Relasi ke alamat (UUID_Address)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_address")
    private Address address;

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    @Column(nullable = false)
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "complaint_media_refs",
            joinColumns = @JoinColumn(name = "complaint_uuid")
    )
    @Column(name = "media_ref")
    private Set<String> mediaRefs = new HashSet<>();

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status; // PENDING, IN_REVIEW, ASSIGNED, RESOLVED, CLOSED, REJECTED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Complaint() {}

    public Complaint(User reporter, User assignedTo, Address address, String category, String description, Set<String> mediaRefs, ComplaintStatus status) {
        this.reporter = reporter;
        this.assignedTo = assignedTo;
        this.address = address;
        this.category = category;
        this.description = description;
        this.mediaRefs = new HashSet<>(mediaRefs);
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getMediaRefs() {
        return mediaRefs;
    }

    public void setMediaRefs(Set<String> mediaRefs) {
        this.mediaRefs = mediaRefs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
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
