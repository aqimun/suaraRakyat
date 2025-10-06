package com.tejasTanra.suaraRakyat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tejasTanra.suaraRakyat.model.AuditLog;
import com.tejasTanra.suaraRakyat.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    public void log(Long actorId, String action, String resourceType, Long resourceId, Object beforeState, Object afterState) {
        String beforeJson = null;
        String afterJson = null;
        try {
            if (beforeState != null) {
                beforeJson = objectMapper.writeValueAsString(beforeState);
            }
            if (afterState != null) {
                afterJson = objectMapper.writeValueAsString(afterState);
            }
        } catch (JsonProcessingException e) {
            // Log the error, but don't prevent audit log creation
            System.err.println("Error serializing audit log state: " + e.getMessage());
        }

        // Placeholder for cryptographic signature
        String signature = "TODO_GENERATE_SIGNATURE"; // Generate actual signature here

        AuditLog auditLog = new AuditLog(actorId, action, resourceType, resourceId, beforeJson, afterJson, signature);
        auditLogRepository.save(auditLog);
    }

    // Method to retrieve audit logs can be added here
}
