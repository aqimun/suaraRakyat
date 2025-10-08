package com.tejasTanra.suaraRakyat.config;

import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        createRolesIfNotFound();
    }

    private void createRolesIfNotFound() {
        // Super Admin Role
        if (roleService.findByName("ROLE_SUPER_ADMIN").isEmpty()) {
            Set<String> superAdminPermissions = new HashSet<>();
            superAdminPermissions.add("AUTH_REGISTER_R");
            superAdminPermissions.add("AUTH_APPROVE_KYC_A");
            superAdminPermissions.add("USER_EDIT_PII_U");
            superAdminPermissions.add("USER_CHANGE_IDENTITY_A");
            superAdminPermissions.add("USER_DELETE_D");
            superAdminPermissions.add("USER_SOFT_DELETE_C_U");
            superAdminPermissions.add("PERMISSION_CHANGE_C_A");
            superAdminPermissions.add("NEWS_CREATE_EDIT_PUBLISH_C_A");
            superAdminPermissions.add("NEWS_DELETE_D");
            superAdminPermissions.add("COMPLAINTS_CREATE_R");
            superAdminPermissions.add("COMPLAINTS_MODERATE_A");
            superAdminPermissions.add("COMPLAINTS_ESCALATE_E");
            superAdminPermissions.add("COMPLAINTS_CLOSE_A");
            superAdminPermissions.add("PROJECTS_EDIT_U");
            superAdminPermissions.add("PROJECT_EVIDENCE_R_U");
            superAdminPermissions.add("VOTING_CREATE_ELECTION_C");
            superAdminPermissions.add("VOTING_CANDIDATE_VERIFICATION_A");
            superAdminPermissions.add("VOTING_CAST_R");
            superAdminPermissions.add("VOTING_VIEW_RESULTS_R_A");
            roleService.createRole("ROLE_SUPER_ADMIN");
        }

        // Staff Admin Role
        if (roleService.findByName("ROLE_STAFF_ADMIN").isEmpty()) {
            roleService.createRole("ROLE_STAFF_ADMIN");
        }

        // User Penjabat Role
        if (roleService.findByName("ROLE_USER_PENJABAT").isEmpty()) {
            roleService.createRole("ROLE_USER_PENJABAT");
        }

        // User Rakyat Role
        if (roleService.findByName("ROLE_USER_RAKYAT").isEmpty()) {
            roleService.createRole("ROLE_USER_RAKYAT");
        }
    }
}
