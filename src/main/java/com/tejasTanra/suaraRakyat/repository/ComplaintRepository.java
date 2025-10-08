package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
    List<Complaint> findByReporter(User reporter);
    List<Complaint> findByAssignedTo(User assignedTo);
    List<Complaint> findByStatus(ComplaintStatus status);
}
