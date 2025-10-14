package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId; // ID of User Rakyat, nullable if anonymous

    @Column(name = "anon_flag", nullable = false)
    private boolean anonFlag; // True if anonymous

    @Column(nullable = false)
    private String category;

    @Column(name = "location_geo")
    private String locationGeo; // e.g., "latitude,longitude" or descriptive text

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "complaint_media_refs", joinColumns = @JoinColumn(name = "complaint_id"))
    @Column(name = "media_ref")
    private Set<String> mediaRefs = new HashSet<>(); // References to uploaded media evidence

    @Column(name = "assigned_to")
    private Long assignedTo; // ID of User Penjabat, nullable if not yet assigned

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status; // PENDING, IN_REVIEW, ASSIGNED, RESOLVED, CLOSED, REJECTED

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

    public Complaint() {
    }

    public Complaint(Long reporterId, boolean anonFlag, String category, String locationGeo, String description, Set<String> mediaRefs, Long assignedTo, ComplaintStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reporterId = reporterId;
        this.anonFlag = anonFlag;
        this.category = category;
        this.locationGeo = locationGeo;
        this.description = description;
        this.mediaRefs = new HashSet<>(mediaRefs); // Ensure deep copy for mutable collections
        this.assignedTo = assignedTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public boolean isAnonFlag() {
        return anonFlag;
    }

    public void setAnonFlag(boolean anonFlag) {
        this.anonFlag = anonFlag;
    }

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

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
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
