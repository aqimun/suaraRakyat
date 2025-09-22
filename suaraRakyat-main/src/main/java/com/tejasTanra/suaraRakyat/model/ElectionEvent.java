package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "election_events")
public class ElectionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the election event

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "election_candidates", joinColumns = @JoinColumn(name = "election_id"))
    @Column(name = "candidate_id")
    private Set<Long> candidateIds = new HashSet<>(); // IDs of User Penjabat candidates

    @Column(name = "voter_roll_hash", columnDefinition = "TEXT")
    private String voterRollHash; // Hash of the voter roll (TODO: cryptographic implementation)

    @Column(name = "ballot_encrypted", columnDefinition = "TEXT")
    private String ballotEncrypted; // Placeholder for encrypted ballots (TODO: E2E verifiable protocol)

    @Column(name = "receipt_hash", columnDefinition = "TEXT")
    private String receiptHash; // Placeholder for receipt hashes (TODO: cryptographic implementation)

    @Column(name = "tally_proof", columnDefinition = "TEXT")
    private String tallyProof; // Placeholder for tally proofs (TODO: cryptographic implementation)

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Whether the election is active

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

    public ElectionEvent() {
    }

    // Constructor for audit logging/copying state
    public ElectionEvent(String name, LocalDateTime startTime, LocalDateTime endTime, Set<Long> candidateIds, String voterRollHash, String ballotEncrypted, String receiptHash, String tallyProof, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.candidateIds = new HashSet<>(candidateIds);
        this.voterRollHash = voterRollHash;
        this.ballotEncrypted = ballotEncrypted;
        this.receiptHash = receiptHash;
        this.tallyProof = tallyProof;
        this.isActive = isActive;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<Long> getCandidateIds() {
        return candidateIds;
    }

    public void setCandidateIds(Set<Long> candidateIds) {
        this.candidateIds = candidateIds;
    }

    public String getVoterRollHash() {
        return voterRollHash;
    }

    public void setVoterRollHash(String voterRollHash) {
        this.voterRollHash = voterRollHash;
    }

    public String getBallotEncrypted() {
        return ballotEncrypted;
    }

    public void setBallotEncrypted(String ballotEncrypted) {
        this.ballotEncrypted = ballotEncrypted;
    }

    public String getReceiptHash() {
        return receiptHash;
    }

    public void setReceiptHash(String receiptHash) {
        this.receiptHash = receiptHash;
    }

    public String getTallyProof() {
        return tallyProof;
    }

    public void setTallyProof(String tallyProof) {
        this.tallyProof = tallyProof;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
