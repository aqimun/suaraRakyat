package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private AuditLogService auditLogService;

    // User Rakyat submits a complaint
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
    public Complaint moderateComplaint(Long moderatorId, Long complaintId, ComplaintStatus newStatus, String reason) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt()); // Create a copy for beforeState

        complaint.setStatus(newStatus);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(moderatorId, "COMPLAINT_MODERATE", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Add notification logic based on newStatus and reason
        return updatedComplaint;
    }

    // Staff Admin assigns a complaint to a User Penjabat
    public Complaint assignComplaint(Long staffAdminId, Long complaintId, Long penjabatId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt()); // Create a copy for beforeState

        complaint.setAssignedTo(penjabatId);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(staffAdminId, "COMPLAINT_ASSIGN", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Add notification to assigned penjabat
        return updatedComplaint;
    }

    // User Penjabat marks a complaint as resolved (proposal)
    public Complaint resolveComplaintProposal(Long penjabatId, Long complaintId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (!penjabatId.equals(complaint.getAssignedTo())) {
            throw new SecurityException("User Penjabat is not assigned to this complaint.");
        }
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt()); // Create a copy for beforeState

        complaint.setStatus(ComplaintStatus.RESOLVED); // Penjabat proposes resolution
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(penjabatId, "COMPLAINT_RESOLVE_PROPOSAL", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Notify Staff Admin for approval
        return updatedComplaint;
    }

    // Staff Admin approves resolution and closes complaint
    public Complaint closeComplaint(Long staffAdminId, Long complaintId) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            throw new IllegalStateException("Complaint must be in RESOLVED status to be closed.");
        }
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt()); // Create a copy for beforeState

        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        auditLogService.log(staffAdminId, "COMPLAINT_CLOSE", "Complaint", updatedComplaint.getId(), oldComplaintState, updatedComplaint);
        // TODO: Notify User Rakyat about closure
        return updatedComplaint;
    }

    // Super Admin escalates a complaint
    public Complaint escalateComplaint(Long superAdminId, Long complaintId, String newLevel) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporterId(), complaint.isAnonFlag(), complaint.getCategory(), complaint.getLocationGeo(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getAssignedTo(), complaint.getStatus(), complaint.getCreatedAt(), complaint.getUpdatedAt()); // Create a copy for beforeState

        // For simplicity, newLevel can be stored in description or a new field
        // For now, just log the escalation
        auditLogService.log(superAdminId, "COMPLAINT_ESCALATE", "Complaint", complaint.getId(), oldComplaintState, complaint);
        // TODO: Implement actual escalation logic (e.g., re-assign to higher-level penjabat, update status)
        return complaint;
    }

    public Optional<Complaint> findById(Long id) {
        return complaintRepository.findById(id);
    }

    public List<Complaint> findByReporterId(Long reporterId) {
        return complaintRepository.findByReporterId(reporterId);
    }

    public List<Complaint> findByAssignedTo(Long assignedTo) {
        return complaintRepository.findByAssignedTo(assignedTo);
    }

    public List<Complaint> findByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status.name());
    }

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }
}
