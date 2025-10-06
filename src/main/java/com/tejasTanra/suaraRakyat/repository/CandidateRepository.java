package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.Candidate;
import com.tejasTanra.suaraRakyat.model.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByPejabatIdAndElectionEventId(Long pejabatId, Long electionEventId);
    List<Candidate> findByElectionEventId(Long electionEventId);
    List<Candidate> findByStatus(CandidateStatus status);
}
