package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pejabat_id", nullable = false)
    private Long pejabatId; // ID of the User Penjabat who is the candidate

    @Column(name = "election_event_id", nullable = false)
    private Long electionEventId; // ID of the election event this candidate is for

    @Column(nullable = false, columnDefinition = "TEXT")
    private String vision;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus status; // PENDING_VERIFICATION, VERIFIED, REJECTED

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

    public Candidate() {
    }

    // Constructor for audit logging/copying state
    public Candidate(Long pejabatId, Long electionEventId, String vision, String mission, CandidateStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.pejabatId = pejabatId;
        this.electionEventId = electionEventId;
        this.vision = vision;
        this.mission = mission;
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

    public Long getPejabatId() {
        return pejabatId;
    }

    public void setPejabatId(Long pejabatId) {
        this.pejabatId = pejabatId;
    }

    public Long getElectionEventId() {
        return electionEventId;
    }

    public void setElectionEventId(Long electionEventId) {
        this.electionEventId = electionEventId;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public CandidateStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateStatus status) {
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
