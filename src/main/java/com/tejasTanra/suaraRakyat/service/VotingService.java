package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Candidate;
import com.tejasTanra.suaraRakyat.model.CandidateStatus;
import com.tejasTanra.suaraRakyat.model.ElectionEvent;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.repository.CandidateRepository;
import com.tejasTanra.suaraRakyat.repository.ElectionEventRepository;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VotingService {

    @Autowired
    private ElectionEventRepository electionEventRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserRepository userRepository; // To check user roles

    @Autowired
    private AuditLogService auditLogService;

    // Super Admin creates an election event
    public ElectionEvent createElectionEvent(Long superAdminId, String name, LocalDateTime startTime, LocalDateTime endTime) {
        ElectionEvent event = new ElectionEvent();
        event.setName(name);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setActive(true); // Active by default upon creation
        ElectionEvent savedEvent = electionEventRepository.save(event);
        auditLogService.log(superAdminId, "ELECTION_CREATE", "ElectionEvent", savedEvent.getId(), null, savedEvent);
        return savedEvent;
    }

    // User Penjabat nominates self as a candidate
    public Candidate nominateCandidate(Long pejabatId, Long electionEventId, String vision, String mission) {
        Optional<User> optionalUser = userRepository.findById(pejabatId);
        if (optionalUser.isEmpty() || !optionalUser.get().getRole().getName().equals("ROLE_USER_PENJABAT")) {
            throw new SecurityException("Only User Penjabat can nominate themselves.");
        }

        Optional<ElectionEvent> optionalEvent = electionEventRepository.findById(electionEventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalArgumentException("Election event not found.");
        }
        if (candidateRepository.findByPejabatIdAndElectionEventId(pejabatId, electionEventId).isPresent()) {
            throw new IllegalArgumentException("User already nominated for this election.");
        }

        Candidate candidate = new Candidate();
        candidate.setPejabatId(pejabatId);
        candidate.setElectionEventId(electionEventId);
        candidate.setVision(vision);
        candidate.setMission(mission);
        candidate.setStatus(CandidateStatus.PENDING_VERIFICATION); // Pending verification by Staff Admin
        Candidate savedCandidate = candidateRepository.save(candidate);

        // Add candidate to election event's candidate list
        ElectionEvent event = optionalEvent.get();
        ElectionEvent oldEventState = new ElectionEvent(event.getName(), event.getStartTime(), event.getEndTime(), event.getCandidateIds(), event.getVoterRollHash(), event.getBallotEncrypted(), event.getReceiptHash(), event.getTallyProof(), event.isActive(), event.getCreatedAt(), event.getUpdatedAt());
        event.getCandidateIds().add(savedCandidate.getId());
        electionEventRepository.save(event); // Update event with new candidate

        auditLogService.log(pejabatId, "CANDIDATE_NOMINATE", "Candidate", savedCandidate.getId(), null, savedCandidate);
        auditLogService.log(pejabatId, "ELECTION_UPDATE_CANDIDATE_LIST", "ElectionEvent", event.getId(), oldEventState, event);
        return savedCandidate;
    }

    // Staff Admin verifies a candidate
    public Candidate verifyCandidate(Long staffAdminId, Long candidateId, CandidateStatus newStatus) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidateId);
        if (optionalCandidate.isEmpty()) {
            throw new IllegalArgumentException("Candidate not found.");
        }

        Candidate candidate = optionalCandidate.get();
        Candidate oldCandidateState = new Candidate(candidate.getPejabatId(), candidate.getElectionEventId(), candidate.getVision(), candidate.getMission(), candidate.getStatus(), candidate.getCreatedAt(), candidate.getUpdatedAt());

        candidate.setStatus(newStatus); // VERIFIED or REJECTED
        candidate.setUpdatedAt(LocalDateTime.now());
        Candidate updatedCandidate = candidateRepository.save(candidate);
        auditLogService.log(staffAdminId, "CANDIDATE_VERIFY", "Candidate", updatedCandidate.getId(), oldCandidateState, updatedCandidate);
        // TODO: Notify candidate about verification status
        return updatedCandidate;
    }

    // User Rakyat casts a vote (simplified for pilot)
    public void castVote(Long userId, Long electionEventId, Long candidateId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty() || !optionalUser.get().getRole().getName().equals("ROLE_USER_RAKYAT")) {
            throw new SecurityException("Only User Rakyat can cast votes.");
        }

        Optional<ElectionEvent> optionalEvent = electionEventRepository.findById(electionEventId);
        if (optionalEvent.isEmpty() || !optionalEvent.get().isActive() || LocalDateTime.now().isBefore(optionalEvent.get().getStartTime()) || LocalDateTime.now().isAfter(optionalEvent.get().getEndTime())) {
            throw new IllegalArgumentException("Election event not found or not active for voting.");
        }

        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidateId);
        if (optionalCandidate.isEmpty() || !optionalCandidate.get().getElectionEventId().equals(electionEventId) || optionalCandidate.get().getStatus() != CandidateStatus.VERIFIED) {
            throw new IllegalArgumentException("Invalid candidate for this election.");
        }

        // TODO: Implement actual E2E verifiable voting protocol (Helios-style)
        // For pilot, just log the vote
        auditLogService.log(userId, "VOTE_CAST", "ElectionEvent", electionEventId, "Voted for candidate " + candidateId, "Vote recorded");
        // In a real system, this would involve cryptographic operations and storing encrypted ballots
    }

    // Super Admin / Staff Admin views results (simplified)
    public List<Candidate> getElectionResults(Long electionEventId) {
        Optional<ElectionEvent> optionalEvent = electionEventRepository.findById(electionEventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalArgumentException("Election event not found.");
        }
        // TODO: Implement actual tallying and verification based on cryptographic proofs
        // For pilot, just return verified candidates
        return candidateRepository.findByElectionEventId(electionEventId);
    }

    public Optional<ElectionEvent> findElectionEventById(Long id) {
        return electionEventRepository.findById(id);
    }

    public List<ElectionEvent> findAllActiveElectionEvents() {
        return electionEventRepository.findByIsActiveTrueAndStartTimeBeforeAndEndTimeAfter(LocalDateTime.now(), LocalDateTime.now());
    }

    public List<Candidate> findCandidatesByElectionEvent(Long electionEventId) {
        return candidateRepository.findByElectionEventId(electionEventId);
    }
}
