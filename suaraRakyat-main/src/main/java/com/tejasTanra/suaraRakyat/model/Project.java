package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pejabat_id", nullable = false)
    private Long pejabatId; // ID of the User Penjabat who owns the project

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal budget; // Using BigDecimal for financial data

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_expense_items", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "expense_item")
    private Set<String> expenseItems = new HashSet<>(); // Details of expenses

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_evidence_refs", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "evidence_ref")
    private Set<String> evidenceRefs = new HashSet<>(); // References to uploaded evidence (photos, videos)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status; // PENDING, IN_PROGRESS, COMPLETED, CANCELLED

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

    public Project() {
    }

    // Constructor for audit logging/copying state
    public Project(Long pejabatId, String title, String description, BigDecimal budget, Set<String> expenseItems, Set<String> evidenceRefs, ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.pejabatId = pejabatId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.expenseItems = new HashSet<>(expenseItems);
        this.evidenceRefs = new HashSet<>(evidenceRefs);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Set<String> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(Set<String> expenseItems) {
        this.expenseItems = expenseItems;
    }

    public Set<String> getEvidenceRefs() {
        return evidenceRefs;
    }

    public void setEvidenceRefs(Set<String> evidenceRefs) {
        this.evidenceRefs = evidenceRefs;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
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
