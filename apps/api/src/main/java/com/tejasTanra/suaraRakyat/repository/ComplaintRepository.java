package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByReporterId(Long reporterId);
    List<Complaint> findByAssignedTo(Long assignedTo);
    List<Complaint> findByStatus(String status);
}
