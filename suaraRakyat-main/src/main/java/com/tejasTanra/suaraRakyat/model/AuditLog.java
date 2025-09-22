package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_id", nullable = false)
    private Long actorId; // ID of the user who performed the action

    @Column(nullable = false)
    private String action; // e.g., "USER_EDIT", "ROLE_PROMOTION", "COMPLAINT_ESCALATE"

    @Column(name = "resource_type", nullable = false)
    private String resourceType; // e.g., "User", "Role", "Complaint"

    @Column(name = "resource_id", nullable = false)
    private Long resourceId; // ID of the resource affected

    @Column(name = "before_json", columnDefinition = "TEXT")
    private String beforeJson; // JSON representation of the resource before the action

    @Column(name = "after_json", columnDefinition = "TEXT")
    private String afterJson; // JSON representation of the resource after the action

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String signature; // Cryptographic signature for immutability (placeholder for now)

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    public AuditLog() {
    }

    public AuditLog(Long actorId, String action, String resourceType, Long resourceId, String beforeJson, String afterJson, String signature) {
        this.actorId = actorId;
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.beforeJson = beforeJson;
        this.afterJson = afterJson;
        this.signature = signature;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getBeforeJson() {
        return beforeJson;
    }

    public void setBeforeJson(String beforeJson) {
        this.beforeJson = beforeJson;
    }

    public String getAfterJson() {
        return afterJson;
    }

    public void setAfterJson(String afterJson) {
        this.afterJson = afterJson;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
