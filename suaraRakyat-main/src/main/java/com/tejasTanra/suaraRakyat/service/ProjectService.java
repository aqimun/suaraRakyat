package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Project;
import com.tejasTanra.suaraRakyat.model.ProjectStatus;
import com.tejasTanra.suaraRakyat.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditLogService auditLogService;

    // User Penjabat creates a new project
    public Project createProject(Long pejabatId, String title, String description, BigDecimal budget, Set<String> expenseItems, Set<String> evidenceRefs) {
        Project project = new Project();
        project.setPejabatId(pejabatId);
        project.setTitle(title);
        project.setDescription(description);
        project.setBudget(budget);
        project.setExpenseItems(expenseItems);
        project.setEvidenceRefs(evidenceRefs);
        project.setStatus(ProjectStatus.PENDING); // Default status for new projects
        Project savedProject = projectRepository.save(project);
        auditLogService.log(pejabatId, "PROJECT_CREATE", "Project", savedProject.getId(), null, savedProject);
        return savedProject;
    }

    // User Penjabat updates an existing project
    public Project updateProject(Long editorId, Long projectId, String title, String description, BigDecimal budget, Set<String> expenseItems, Set<String> evidenceRefs, ProjectStatus status) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new IllegalArgumentException("Project not found.");
        }

        Project existingProject = optionalProject.get();
        // Ensure only the assigned pejabat can edit their project
        if (!editorId.equals(existingProject.getPejabatId())) {
            throw new SecurityException("User is not authorized to edit this project.");
        }

        Project oldProjectState = new Project(existingProject.getPejabatId(), existingProject.getTitle(), existingProject.getDescription(), existingProject.getBudget(), existingProject.getExpenseItems(), existingProject.getEvidenceRefs(), existingProject.getStatus(), existingProject.getCreatedAt(), existingProject.getUpdatedAt());

        existingProject.setTitle(title);
        existingProject.setDescription(description);
        existingProject.setBudget(budget);
        existingProject.setExpenseItems(expenseItems);
        existingProject.setEvidenceRefs(evidenceRefs);
        existingProject.setStatus(status); // Allow status update by owner
        existingProject.setUpdatedAt(LocalDateTime.now());
        Project updatedProject = projectRepository.save(existingProject);
        auditLogService.log(editorId, "PROJECT_UPDATE", "Project", updatedProject.getId(), oldProjectState, updatedProject);
        return updatedProject;
    }

    // Super Admin / Staff Admin can update project status (e.g., after verification)
    public Project updateProjectStatus(Long actorId, Long projectId, ProjectStatus newStatus) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new IllegalArgumentException("Project not found.");
        }

        Project existingProject = optionalProject.get();
        Project oldProjectState = new Project(existingProject.getPejabatId(), existingProject.getTitle(), existingProject.getDescription(), existingProject.getBudget(), existingProject.getExpenseItems(), existingProject.getEvidenceRefs(), existingProject.getStatus(), existingProject.getCreatedAt(), existingProject.getUpdatedAt());

        existingProject.setStatus(newStatus);
        existingProject.setUpdatedAt(LocalDateTime.now());
        Project updatedProject = projectRepository.save(existingProject);
        auditLogService.log(actorId, "PROJECT_STATUS_UPDATE", "Project", updatedProject.getId(), oldProjectState, updatedProject);
        return updatedProject;
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> findByPejabatId(Long pejabatId) {
        return projectRepository.findByPejabatId(pejabatId);
    }

    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status.name());
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
