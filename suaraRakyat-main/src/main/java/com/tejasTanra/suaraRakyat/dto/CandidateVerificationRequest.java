package com.tejasTanra.suaraRakyat.dto;

import com.tejasTanra.suaraRakyat.model.CandidateStatus;
import jakarta.validation.constraints.NotNull;

public class CandidateVerificationRequest {

    @NotNull
    private CandidateStatus newStatus; // VERIFIED or REJECTED

    // Getters and Setters
    public CandidateStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(CandidateStatus newStatus) {
        this.newStatus = newStatus;
    }
}
