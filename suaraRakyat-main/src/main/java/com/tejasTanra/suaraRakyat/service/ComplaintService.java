package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional // Apply transactional to all methods in this service by default
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private AuditLogService auditLogService;

    // User Rakyat submits a complaint
    // @Transactional is applied at class level
    public Complaint submitComplaint(Long reporterId, boolean anonFlag, String category, String locationGeo, String description, Set<String> mediaRefs) {
        Complaint complaint = new Complaint();
        complaint.setReporterId(reporterId);
        complaint.setAnonFlag(anonFlag);
        complaint.setCategory(category);
        complaint.setLocationGeo(locationGeo);
        complaint.setDescription(description);
        complaint.setMediaRefs(mediaRefs);
        complaint.setStatus(ComplaintStatus.PENDING);
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint savedComplaint = complaintRepository.save(complaint);
        auditLogService.log(reporterId, "COMPLAINT_SUBMIT", "Complaint", savedComplaint.getId(), null, savedComplaint);
        return savedComplaint;
    }

    // Staff Admin moderates a complaint (approve/reject/return for correction)
    // @Transactional is applied at class level
    public Complaint moderateComplaint(Long moderatorId, Long complaintId, ComplaintStatus newStatus, String reason) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        // Using Lombok's @Builder or @Value would simplify this copy
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt());

        complaint.setStatus(newStatus);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(moderatorId, "COMPLAINT_MODERATE", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Add notification logic based on newStatus and reason
        return updatedComplaint;
    }

    // Staff Admin assigns a complaint to a User Penjabat
    // @Transactional is applied at class level
    public Complaint assignComplaint(Long staffAdminId, Long complaintId, Long penjabatId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        // Using Lombok's @Builder or @Value would simplify this copy
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt());

        complaint.setAssignedTo(penjabatId);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(staffAdminId, "COMPLAINT_ASSIGN", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Add notification to assigned penjabat
        return updatedComplaint;
    }

    // User Penjabat marks a complaint as resolved (proposal)
    // @Transactional is applied at class level
    public Complaint resolveComplaintProposal(Long penjabatId, Long complaintId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (!penjabatId.equals(complaint.getAssignedTo())) {
            throw new SecurityException("User Penjabat is not assigned to this complaint.");
        }
        // Using Lombok's @Builder or @Value would simplify this copy
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt());

        complaint.setStatus(ComplaintStatus.RESOLVED); // Penjabat proposes resolution
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(penjabatId, "COMPLAINT_RESOLVE_PROPOSAL", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Notify Staff Admin for approval
        return updatedComplaint;
    }

    // Staff Admin approves resolution and closes complaint
    // @Transactional is applied at class level
    public Complaint closeComplaint(Long staffAdminId, Long complaintId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            throw new IllegalStateException("Complaint must be in RESOLVED status to be closed.");
        }
        // Using Lombok's @Builder or @Value would simplify this copy
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt());

        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(staffAdminId, "COMPLAINT_CLOSE", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Notify User Rakyat about closure
        return updatedComplaint;
    }

    // Super Admin escalates a complaint
    // @Transactional is applied at class level
    public Complaint escalateComplaint(Long superAdminId, Long complaintId, String newLevel) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        // Using Lombok's @Builder or @Value would simplify this copy
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt());

        // For simplicity, newLevel can be stored in description or a new field
        // For now, just log the escalation
        auditLogService.log(superAdminId, "COMPLAINT_ESCALATE", "Complaint", complaint.getId(), oldComplaintState, complaint);
        // TODO: Implement actual escalation logic (e.g., re-assign to higher-level penjabat, update status)
        return complaint;
    }

    @Transactional(readOnly = true) // Read-only methods
    public Optional<Complaint> findById(Long id) {
        return complaintRepository.findById(id);
    }

    @Transactional(readOnly = true) // Read-only methods
    public List<Complaint> findByReporterId(Long reporterId) {
        return complaintRepository.findByReporterId(reporterId);
    }

    @Transactional(readOnly = true) // Read-only methods
    public List<Complaint> findByAssignedTo(Long assignedTo) {
        return complaintRepository.findByAssignedTo(assignedTo);
    }

    @Transactional(readOnly = true) // Read-only methods
    public List<Complaint> findByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status.name());
    }

    @Transactional(readOnly = true) // Read-only methods
    public Page<Complaint> findAll(Pageable pageable) { // Modified to accept Pageable
        return complaintRepository.findAll(pageable);
    }
}
