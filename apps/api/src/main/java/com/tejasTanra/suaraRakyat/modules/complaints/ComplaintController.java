package com.tejasTanra.suaraRakyat.modules.complaints;

import com.tejasTanra.suaraRakyat.dto.ComplaintAssignmentRequest;
import com.tejasTanra.suaraRakyat.dto.ComplaintModerationRequest;
import com.tejasTanra.suaraRakyat.dto.ComplaintRequest;
import com.tejasTanra.suaraRakyat.exception.BadRequestException; // Import custom exceptions
import com.tejasTanra.suaraRakyat.exception.ConflictException;
import com.tejasTanra.suaraRakyat.exception.ForbiddenException;
import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException; // Already exists
import com.tejasTanra.suaraRakyat.model.Complaint;
import com.tejasTanra.suaraRakyat.model.ComplaintStatus;
import com.tejasTanra.suaraRakyat.model.User; // Import User model
import com.tejasTanra.suaraRakyat.service.ComplaintService;
import com.tejasTanra.suaraRakyat.service.UserService; // Import UserService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints") // Changed to /api/complaints for consistency
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService; // Inject UserService

    // User Rakyat submits a complaint
    @PostMapping
    @PreAuthorize("hasRole('RAKYAT')") // Only RAKYAT can submit complaints
    public ResponseEntity<?> submitComplaint(@Valid @RequestBody ComplaintRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName()); // Assuming email is the principal name
        Long reporterId = currentUser.getId();

        try {
            Complaint newComplaint = complaintService.submitComplaint(reporterId, request.getAnonFlag(), request.getCategory(), request.getLocationGeo(), request.getDescription(), request.getMediaRefs());
            return new ResponseEntity<>(newComplaint, HttpStatus.CREATED);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin moderates a complaint
    @PutMapping("/{id}/moderate")
    @PreAuthorize("hasRole('STAFF_ADMIN')") // Only STAFF_ADMIN can moderate complaints
    public ResponseEntity<?> moderateComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintModerationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long moderatorId = currentUser.getId();

        try {
            Complaint updatedComplaint = complaintService.moderateComplaint(moderatorId, id, request.getNewStatus(), request.getReason());
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin assigns a complaint
    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('STAFF_ADMIN')") // Only STAFF_ADMIN can assign complaints
    public ResponseEntity<?> assignComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintAssignmentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long staffAdminId = currentUser.getId();

        try {
            Complaint updatedComplaint = complaintService.assignComplaint(staffAdminId, id, request.getPenjabatId());
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Penjabat marks a complaint as resolved (proposal)
    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('PENJABAT')") // Only PENJABAT can resolve complaints
    public ResponseEntity<?> resolveComplaint(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long penjabatId = currentUser.getId();

        try {
            Complaint updatedComplaint = complaintService.resolveComplaintProposal(penjabatId, id);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ForbiddenException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Staff Admin approves resolution and closes complaint
    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('STAFF_ADMIN')") // Only STAFF_ADMIN can close complaints
    public ResponseEntity<?> closeComplaint(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long staffAdminId = currentUser.getId();

        try {
            Complaint updatedComplaint = complaintService.closeComplaint(staffAdminId, id);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ConflictException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin escalates a complaint
    @PutMapping("/{id}/escalate")
    @PreAuthorize("hasRole('SUPER_ADMIN')") // Only SUPER_ADMIN can escalate complaints
    public ResponseEntity<?> escalateComplaint(@PathVariable Long id, @RequestParam String newLevel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long superAdminId = currentUser.getId();

        try {
            Complaint updatedComplaint = complaintService.escalateComplaint(superAdminId, id, newLevel);
            return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all complaints (for Admins/Penjabat) with pagination and sorting
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN', 'PENJABAT')") // Admins and Penjabat can view all complaints
    public ResponseEntity<Page<Complaint>> getAllComplaints(Pageable pageable) { // Modified to accept Pageable
        // TODO: Implement filtering based on user roles and permissions
        return new ResponseEntity<>(complaintService.findAll(pageable), HttpStatus.OK);
    }

    // Get complaint by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RAKYAT', 'STAFF_ADMIN', 'SUPER_ADMIN', 'PENJABAT')") // All authenticated users can view a complaint
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        // TODO: Add logic to ensure RAKYAT can only view their own complaints, and PENJABAT can only view assigned complaints
        return complaintService.findById(id)
                .map(complaint -> new ResponseEntity<>(complaint, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id)); // Use custom exception
    }
}
