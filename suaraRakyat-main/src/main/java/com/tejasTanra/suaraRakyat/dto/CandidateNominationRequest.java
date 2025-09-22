package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CandidateNominationRequest {

    @NotNull
    private Long electionEventId;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String vision;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String mission;

    // Getters and Setters
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
}
