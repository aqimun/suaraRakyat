package com.tejasTanra.suaraRakyat.dto;

import com.tejasTanra.suaraRakyat.model.ProjectStatus;
import jakarta.validation.constraints.NotNull;

public class ProjectStatusUpdateRequest {

    @NotNull
    private ProjectStatus newStatus;

    // Getters and Setters
    public ProjectStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ProjectStatus newStatus) {
        this.newStatus = newStatus;
    }
}
