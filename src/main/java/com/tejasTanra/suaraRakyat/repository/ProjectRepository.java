package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByPejabatId(Long pejabatId);
    List<Project> findByStatus(String status);
}
