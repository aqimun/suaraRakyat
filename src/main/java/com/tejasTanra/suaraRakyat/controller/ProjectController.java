package com.tejasTanra.suaraRakyat.controller;

import com.tejasTanra.suaraRakyat.dto.ProjectRequest;
import com.tejasTanra.suaraRakyat.dto.ProjectStatusUpdateRequest;
import com.tejasTanra.suaraRakyat.model.Project;
import com.tejasTanra.suaraRakyat.model.ProjectStatus;
import com.tejasTanra.suaraRakyat.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // User Penjabat creates a new project
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest request) {
        // TODO: Get actual pejabatId from authenticated user context
        Long pejabatId = 3L; // Placeholder for User Penjabat ID

        try {
            Project newProject = projectService.createProject(pejabatId, request.getTitle(), request.getDescription(), request.getBudget(), request.getExpenseItems(), request.getEvidenceRefs());
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Penjabat updates an existing project
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        // TODO: Get actual editorId from authenticated user context
        Long editorId = 3L; // Placeholder for User Penjabat ID

        try {
            // For simplicity, status is also passed in the request, but can be managed separately
            Project updatedProject = projectService.updateProject(editorId, id, request.getTitle(), request.getDescription(), request.getBudget(), request.getExpenseItems(), request.getEvidenceRefs(), ProjectStatus.PENDING); // Assuming status is not directly updated via this endpoint, or needs to be derived
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Super Admin / Staff Admin updates project status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateProjectStatus(@PathVariable Long id, @Valid @RequestBody ProjectStatusUpdateRequest request) {
        // TODO: Get actual actorId from authenticated user context (Super Admin or Staff Admin)
        Long actorId = 2L; // Placeholder for Staff Admin / Super Admin ID

        try {
            Project updatedProject = projectService.updateProjectStatus(actorId, id, request.getNewStatus());
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all projects (for Admins/Penjabat)
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        // TODO: Implement filtering and pagination based on user roles and permissions
        // For now, return all projects
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    // Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.findById(id)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get projects by Pejabat ID
    @GetMapping("/pejabat/{pejabatId}")
    public ResponseEntity<List<Project>> getProjectsByPejabatId(@PathVariable Long pejabatId) {
        return new ResponseEntity<>(projectService.findByPejabatId(pejabatId), HttpStatus.OK);
    }
}
