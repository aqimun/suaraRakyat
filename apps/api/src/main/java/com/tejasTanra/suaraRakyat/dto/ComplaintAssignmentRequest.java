package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotNull;

public class ComplaintAssignmentRequest {

    @NotNull
    private Long penjabatId; // ID of the User Penjabat to assign the complaint to

    // Getters and Setters
    public Long getPenjabatId() {
        return penjabatId;
    }

    public void setPenjabatId(Long penjabatId) {
        this.penjabatId = penjabatId;
    }
}
