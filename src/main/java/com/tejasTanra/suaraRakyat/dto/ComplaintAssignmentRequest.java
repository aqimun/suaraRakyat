package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ComplaintAssignmentRequest {

    @NotNull
    private UUID penjabatId; // ID of the User Penjabat to assign the complaint to

    // Getters and Setters
    public UUID getPenjabatId() {
        return penjabatId;
    }

    public void setPenjabatId(UUID penjabatId) {
        this.penjabatId = penjabatId;
    }
}
