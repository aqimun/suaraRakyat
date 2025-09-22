package com.tejasTanra.suaraRakyat.dto;

import jakarta.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    private Long electionEventId;

    @NotNull
    private Long candidateId;

    // Getters and Setters
    public Long getElectionEventId() {
        return electionEventId;
    }

    public void setElectionEventId(Long electionEventId) {
        this.electionEventId = electionEventId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
