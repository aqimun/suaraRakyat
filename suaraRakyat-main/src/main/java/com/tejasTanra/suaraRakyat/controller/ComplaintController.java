package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.dto.ComplaintAssignmentRequest;
import com.tejasTanra.suaraRakyat.dto.ComplaintModerationRequest;
import com.tejasTanra.suaraRakyat.dto.ComplaintRequest;
import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // User Rakyat submits a complaint
    @PostMapping
    public ResponseEntity<?> submitComplaint(@Valid @RequestBody ComplaintRequest request) {
        // TODO: Get actual reporterId from authenticated user context
        Long reporterId = 1L; // Placeholder for authenticated user ID

        try {
            Complaint newComplaint = complaintService.submitComplaint(reporterId, request.getAnonFlag(), request.getCategory(), request.getLocationGeo(), request.getDescription(), request.getMediaRefs());
            return new ResponseEntity<>(newComplaint, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin moderates a complaint
    @PutMapping("/{id}/moderate")
    public ResponseEntity<?> moderateComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintModerationRequest request) {
        // TODO: Get actual moderatorId from authenticated user context
        Long moderatorId = 2L; // Placeholder for Staff Admin ID

        try {
            Complaint updatedComplaint = complaintService.moderateComplaint(moderatorId, id, request.getNewStatus(), request.getReason());
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin assigns a complaint
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintAssignmentRequest request) {
        // TODO: Get actual staffAdminId from authenticated user context
        Long staffAdminId = 2L; // Placeholder for Staff Admin ID

        try {
            Complaint updatedComplaint = complaintService.assignComplaint(staffAdminId, id, request.getPenjabatId());
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Penjabat marks a complaint as resolved (proposal)
    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolveComplaint(@PathVariable Long id) {
        // TODO: Get actual penjabatId from authenticated user context
        Long penjabatId = 3L; // Placeholder for User Penjabat ID

        try {
            Complaint updatedComplaint = complaintService.resolveComplaintProposal(penjabatId, id);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin approves resolution and closes complaint
    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeComplaint(@PathVariable Long id) {
        // TODO: Get actual staffAdminId from authenticated user context
        Long staffAdminId = 2L; // Placeholder for Staff Admin ID

        try {
            Complaint updatedComplaint = complaintService.closeComplaint(staffAdminId, id);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin escalates a complaint
    @PutMapping("/{id}/escalate")
    public ResponseEntity<?> escalateComplaint(@PathVariable Long id, @RequestParam String newLevel) {
        // TODO: Get actual superAdminId from authenticated user context
        Long superAdminId = 4L; // Placeholder for Super Admin ID

        try {
            Complaint updatedComplaint = complaintService.escalateComplaint(superAdminId, id, newLevel);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all complaints (for Admins/Penjabat)
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        // TODO: Implement filtering and pagination based on user roles and permissions
        // For now, return all complaints
        return new ResponseEntity<>(complaintService.findAll(), HttpStatus.OK);
    }

    // Get complaint by ID
    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        return complaintService.findById(id)
                .map(complaint -> new ResponseEntity<>(complaint, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
