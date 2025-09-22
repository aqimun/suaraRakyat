package com.tejasTanra.suaraRakyat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long authorId; // ID of the user who created/edited the news

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_media_refs", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "media_ref")
    private Set<String> mediaRefs = new HashSet<>(); // References to uploaded media

    @Version
    private int version; // For optimistic locking and content versioning

    @Column(name = "published_at")
    private LocalDateTime publishedAt; // Null if not yet published

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // Soft delete flag

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

    public News() {
    }

    // Constructor for audit logging/copying state
    public News(Long authorId, String title, String body, Set<String> mediaRefs, int version, LocalDateTime publishedAt, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.authorId = authorId;
        this.title = title;
        this.body = body;
        this.mediaRefs = new HashSet<>(mediaRefs);
        this.version = version;
        this.publishedAt = publishedAt;
        this.isDeleted = isDeleted;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getMediaRefs() {
        return mediaRefs;
    }

    public void setMediaRefs(Set<String> mediaRefs) {
        this.mediaRefs = mediaRefs;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
