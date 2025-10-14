package com.tejasTanra.suaraRakyat.modules.projects;

import com.tejasTanra.suaraRakyat.dto.ProjectRequest;
import com.tejasTanra.suaraRakyat.dto.ProjectStatusUpdateRequest;
import com.tejasTanra.suaraRakyat.exception.BadRequestException; // Import custom exceptions
import com.tejasTanra.suaraRakyat.exception.ForbiddenException;
import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException; // Already exists
import com.tejasTanra.suaraRakyat.model.Project;
import com.tejasTanra.suaraRakyat.model.ProjectStatus;
import com.tejasTanra.suaraRakyat.model.User; // Import User model
import com.tejasTanra.suaraRakyat.modules.projects.ProjectService;
import com.tejasTanra.suaraRakyat.modules.users.UserService; // Import UserService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects") // Changed to /api/projects for consistency
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService; // Inject UserService

    // User Penjabat creates a new project
    @PostMapping
    @PreAuthorize("hasRole('PENJABAT')") // Only PENJABAT can create projects
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long pejabatId = currentUser.getId();

        try {
            Project newProject = projectService.createProject(pejabatId, request.getTitle(), request.getDescription(), request.getBudget(), request.getExpenseItems(), request.getEvidenceRefs());
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Penjabat updates an existing project
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PENJABAT')") // Only PENJABAT can update projects
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long editorId = currentUser.getId();

        try {
            // For simplicity, status is also passed in the request, but can be managed separately
            Project updatedProject = projectService.updateProject(editorId, id, request.getTitle(), request.getDescription(), request.getBudget(), request.getExpenseItems(), request.getEvidenceRefs(), ProjectStatus.PENDING); // Assuming status is not directly updated via this endpoint, or needs to be derived
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
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

    // Super Admin / Staff Admin updates project status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN')") // Only STAFF_ADMIN or SUPER_ADMIN can update project status
    public ResponseEntity<?> updateProjectStatus(@PathVariable Long id, @Valid @RequestBody ProjectStatusUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        Long actorId = currentUser.getId();

        try {
            Project updatedProject = projectService.updateProjectStatus(actorId, id, request.getNewStatus());
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (ResourceNotFoundException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) { // Use custom exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all projects (for Admins/Penjabat)
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF_ADMIN', 'SUPER_ADMIN', 'PENJABAT')") // Admins and Penjabat can view all projects
    public ResponseEntity<List<Project>> getAllProjects() {
        // TODO: Implement filtering and pagination based on user roles and permissions
        // For now, return all projects
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    // Get project by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Any authenticated user can view a project by ID
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        // TODO: Add logic in service to ensure RAKYAT can only view public projects,
        // and PENJABAT can only view their own projects or public projects.
        return projectService.findById(id)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id)); // Use custom exception
    }

    // Get projects by Pejabat ID
    @GetMapping("/pejabat/{pejabatId}")
    @PreAuthorize("isAuthenticated()") // Any authenticated user can view projects by Pejabat ID
    public ResponseEntity<List<Project>> getProjectsByPejabatId(@PathVariable Long pejabatId) {
        // TODO: Add logic in service to ensure RAKYAT can only view public projects,
        // and PENJABAT can only view their own projects or public projects.
        return new ResponseEntity<>(projectService.findByPejabatId(pejabatId), HttpStatus.OK);
    }
}
