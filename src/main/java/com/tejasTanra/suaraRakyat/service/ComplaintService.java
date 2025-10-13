package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Address;
import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.repository.ComplaintRepository;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import com.tejasTanra.suaraRakyat.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    // User Rakyat submits a complaint
    public Complaint submitComplaint(UUID reporterUuid, UUID addressUuid, String category, String description, Set<String> mediaRefs) {
        Optional<User> reporterOptional = userRepository.findById(reporterUuid);
        if (reporterOptional.isEmpty()) {
            throw new IllegalArgumentException("Reporter user not found.");
        }
        Optional<Address> addressOptional = addressRepository.findById(addressUuid);
        if (addressOptional.isEmpty()) {
            throw new IllegalArgumentException("Address not found.");
        }

        Complaint complaint = new Complaint();
        complaint.setReporter(reporterOptional.get());
        complaint.setAddress(addressOptional.get());
        complaint.setCategory(category);
        complaint.setDescription(description);
        complaint.setMediaRefs(mediaRefs);
        complaint.setStatus(ComplaintStatus.PENDING);
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        return savedComplaint;
    }

    // Staff Admin moderates a complaint (approve/reject/return for correction)
    public Complaint moderateComplaint(UUID moderatorUuid, UUID complaintUuid, ComplaintStatus newStatus, String reason) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintUuid);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporter(), complaint.getAssignedTo(), complaint.getAddress(), complaint.getCategory(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getStatus()); // Create a copy for beforeState

        complaint.setStatus(newStatus);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
         // TODO: Add notification logic based on newStatus and reason
        return updatedComplaint;
    }

    // Staff Admin assigns a complaint to a User Penjabat
    public Complaint assignComplaint(UUID staffAdminUuid, UUID complaintUuid, UUID penjabatUuid) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintUuid);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }
        Optional<User> penjabatOptional = userRepository.findById(penjabatUuid);
        if (penjabatOptional.isEmpty()) {
            throw new IllegalArgumentException("Assigned user (penjabat) not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporter(), complaint.getAssignedTo(), complaint.getAddress(), complaint.getCategory(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getStatus()); // Create a copy for beforeState

        complaint.setAssignedTo(penjabatOptional.get());
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
         // TODO: Add notification to assigned penjabat
        return updatedComplaint;
    }

    // User Penjabat marks a complaint as resolved (proposal)
    public Complaint resolveComplaintProposal(UUID penjabatUuid, UUID complaintUuid) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintUuid);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (complaint.getAssignedTo() == null || !penjabatUuid.equals(complaint.getAssignedTo().getId())) {
            throw new SecurityException("User Penjabat is not assigned to this complaint.");
        }
        Complaint oldComplaintState = new Complaint(complaint.getReporter(), complaint.getAssignedTo(), complaint.getAddress(), complaint.getCategory(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getStatus()); // Create a copy for beforeState

        complaint.setStatus(ComplaintStatus.RESOLVED); // Penjabat proposes resolution
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        // TODO: Notify Staff Admin for approval
        return updatedComplaint;
    }

    // Staff Admin approves resolution and closes complaint
    public Complaint closeComplaint(UUID staffAdminUuid, UUID complaintUuid) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintUuid);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            throw new IllegalStateException("Complaint must be in RESOLVED status to be closed.");
        }
        Complaint oldComplaintState = new Complaint(complaint.getReporter(), complaint.getAssignedTo(), complaint.getAddress(), complaint.getCategory(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getStatus()); // Create a copy for beforeState

        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        // TODO: Notify User Rakyat about closure
        return updatedComplaint;
    }

    // Super Admin escalates a complaint
    public Complaint escalateComplaint(UUID superAdminUuid, UUID complaintUuid, String newLevel) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintUuid);
        if (optionalComplaint.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found.");
        }

        Complaint complaint = optionalComplaint.get();
        Complaint oldComplaintState = new Complaint(complaint.getReporter(), complaint.getAssignedTo(), complaint.getAddress(), complaint.getCategory(), complaint.getDescription(), complaint.getMediaRefs(), complaint.getStatus()); // Create a copy for beforeState

        // For simplicity, newLevel can be stored in description or a new field
        // For now, just log the escalation
        // TODO: Implement actual escalation logic (e.g., re-assign to higher-level penjabat, update status)
        return complaint;
    }

    public Optional<Complaint> findById(UUID id) {
        return complaintRepository.findById(id);
    }

    public List<Complaint> findByReporter(User reporter) {
        return complaintRepository.findByReporter(reporter);
    }

    public List<Complaint> findByAssignedTo(User assignedTo) {
        return complaintRepository.findByAssignedTo(assignedTo);
    }

    public List<Complaint> findByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }
}
