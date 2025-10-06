package com.tejasTanra.suaraRakyat.dto;

import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComplaintModerationRequest {

    @NotNull
    private ComplaintStatus newStatus;

    @NotBlank
    private String reason; // Reason for moderation action (e.g., rejection, return for correction)

    // Getters and Setters
    public ComplaintStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ComplaintStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
