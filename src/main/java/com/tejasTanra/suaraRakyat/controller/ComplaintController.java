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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // User Rakyat submits a complaint
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitComplaint(
            @RequestPart("complaint") @Valid ComplaintRequest request,
            @RequestPart(value = "media", required = false) MultipartFile[] mediaFiles) {
        // TODO: Get actual reporterId from authenticated user context
        UUID reporterUuid = UUID.randomUUID(); // Placeholder for authenticated user ID
        UUID addressUuid = UUID.randomUUID(); // Placeholder for address ID

        try {
            // You would typically save the mediaFiles to a storage service and get their references (e.g., URLs)
            // For now, we'll pass the original mediaRefs from the request, and you can extend this to handle actual file uploads.
            Complaint newComplaint = complaintService.submitComplaint(reporterUuid, addressUuid, request.getCategory(), request.getDescription(), request.getMediaRefs());
            return new ResponseEntity<>(newComplaint, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin moderates a complaint
    @PutMapping("/{id}/moderate")
    public ResponseEntity<?> moderateComplaint(@PathVariable UUID id, @Valid @RequestBody ComplaintModerationRequest request) {
        // TODO: Get actual moderatorId from authenticated user context
        UUID moderatorUuid = UUID.randomUUID(); // Placeholder for Staff Admin ID

        try {
            Complaint updatedComplaint = complaintService.moderateComplaint(moderatorUuid, id, request.getNewStatus(), request.getReason());
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin assigns a complaint
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignComplaint(@PathVariable UUID id, @Valid @RequestBody ComplaintAssignmentRequest request) {
        // TODO: Get actual staffAdminId from authenticated user context
        UUID staffAdminUuid = UUID.randomUUID(); // Placeholder for Staff Admin ID
        UUID penjabatUuid = request.getPenjabatId(); // Assuming penjabatId is now UUID

        try {
            Complaint updatedComplaint = complaintService.assignComplaint(staffAdminUuid, id, penjabatUuid);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Penjabat marks a complaint as resolved (proposal)
    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolveComplaint(@PathVariable UUID id) {
        // TODO: Get actual penjabatId from authenticated user context
        UUID penjabatUuid = UUID.randomUUID(); // Placeholder for User Penjabat ID

        try {
            Complaint updatedComplaint = complaintService.resolveComplaintProposal(penjabatUuid, id);
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
    public ResponseEntity<?> closeComplaint(@PathVariable UUID id) {
        // TODO: Get actual staffAdminId from authenticated user context
        UUID staffAdminUuid = UUID.randomUUID(); // Placeholder for Staff Admin ID

        try {
            Complaint updatedComplaint = complaintService.closeComplaint(staffAdminUuid, id);
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
    public ResponseEntity<?> escalateComplaint(@PathVariable UUID id, @RequestParam String newLevel) {
        // TODO: Get actual superAdminId from authenticated user context
        UUID superAdminUuid = UUID.randomUUID(); // Placeholder for Super Admin ID

        try {
            Complaint updatedComplaint = complaintService.escalateComplaint(superAdminUuid, id, newLevel);
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
    public ResponseEntity<Complaint> getComplaintById(@PathVariable UUID id) {
        return complaintService.findById(id)
                .map(complaint -> new ResponseEntity<>(complaint, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
