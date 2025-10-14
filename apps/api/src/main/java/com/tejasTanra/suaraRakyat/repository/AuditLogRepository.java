package com.tejasTanra.suaraRakyat.repository;

import com.tejasTanra.suaraRakyat.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Custom query methods can be added here if needed
}
