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
            superAdminPermissions.add("AUDIT_LOGS_VIEW_R");
            roleService.createRole("ROLE_SUPER_ADMIN", superAdminPermissions);
        }

        // Staff Admin Role
        if (roleService.findByName("ROLE_STAFF_ADMIN").isEmpty()) {
            Set<String> staffAdminPermissions = new HashSet<>();
            staffAdminPermissions.add("AUTH_REGISTER_R");
            staffAdminPermissions.add("AUTH_APPROVE_KYC_A_R_PENJABAT");
            staffAdminPermissions.add("USER_EDIT_NON_PII_U");
            staffAdminPermissions.add("USER_CHANGE_IDENTITY_U_REQUEST");
            staffAdminPermissions.add("USER_SOFT_DELETE_U_CANT_DEACTIVATE_ADMIN");
            staffAdminPermissions.add("PERMISSION_CHANGE_C_A_R_PENJABAT");
            staffAdminPermissions.add("NEWS_CREATE_EDIT_PUBLISH_C_U");
            staffAdminPermissions.add("COMPLAINTS_CREATE_R");
            staffAdminPermissions.add("COMPLAINTS_MODERATE_M_A");
            staffAdminPermissions.add("COMPLAINTS_CLOSE_A");
            staffAdminPermissions.add("PROJECTS_EDIT_R");
            staffAdminPermissions.add("PROJECT_EVIDENCE_R_VALIDATE");
            staffAdminPermissions.add("VOTING_CANDIDATE_VERIFICATION_A");
            staffAdminPermissions.add("VOTING_CAST_R");
            staffAdminPermissions.add("VOTING_VIEW_RESULTS_R_A");
            staffAdminPermissions.add("AUDIT_LOGS_VIEW_R_LIMITED");
            roleService.createRole("ROLE_STAFF_ADMIN", staffAdminPermissions);
        }

        // User Penjabat Role
        if (roleService.findByName("ROLE_USER_PENJABAT").isEmpty()) {
            Set<String> userPenjabatPermissions = new HashSet<>();
            userPenjabatPermissions.add("AUTH_REGISTER_R");
            userPenjabatPermissions.add("USER_EDIT_PROFILE_PUBLIC_U");
            userPenjabatPermissions.add("USER_CHANGE_IDENTITY_R_U_REQUEST");
            userPenjabatPermissions.add("PERMISSION_CHANGE_R_VIEW_OWN");
            userPenjabatPermissions.add("NEWS_CREATE_EDIT_PUBLISH_R");
            userPenjabatPermissions.add("COMPLAINTS_CREATE_R");
            userPenjabatPermissions.add("COMPLAINTS_MODERATE_R_RESPOND");
            userPenjabatPermissions.add("COMPLAINTS_CLOSE_U_PROPOSAL");
            userPenjabatPermissions.add("PROJECTS_EDIT_C_U_OWN");
            userPenjabatPermissions.add("PROJECT_EVIDENCE_C_U_OWN");
            userPenjabatPermissions.add("VOTING_CREATE_ELECTION_R_CANDIDATE");
            userPenjabatPermissions.add("VOTING_CANDIDATE_VERIFICATION_C_NOMINATE");
            userPenjabatPermissions.add("VOTING_CAST_R");
            userPenjabatPermissions.add("VOTING_VIEW_RESULTS_R");
            roleService.createRole("ROLE_USER_PENJABAT", userPenjabatPermissions);
        }

        // User Rakyat Role
        if (roleService.findByName("ROLE_USER_RAKYAT").isEmpty()) {
            Set<String> userRakyatPermissions = new HashSet<>();
            userRakyatPermissions.add("AUTH_REGISTER_C");
            userRakyatPermissions.add("USER_EDIT_PHOTO_U");
            userRakyatPermissions.add("USER_CHANGE_IDENTITY_C_REQUEST");
            userRakyatPermissions.add("USER_SOFT_DELETE_C_U_SELF_DEACT");
            userRakyatPermissions.add("PERMISSION_CHANGE_R_VIEW_OWN");
            userRakyatPermissions.add("NEWS_CREATE_EDIT_PUBLISH_R");
            userRakyatPermissions.add("COMPLAINTS_CREATE_C");
            userRakyatPermissions.add("COMPLAINTS_MODERATE_R_SUBMIT_TRACK");
            userRakyatPermissions.add("COMPLAINTS_CLOSE_R_CONFIRM");
            userRakyatPermissions.add("PROJECTS_EDIT_R");
            userRakyatPermissions.add("PROJECT_EVIDENCE_R_CONTRIBUTORS");
            userRakyatPermissions.add("VOTING_CREATE_ELECTION_R");
            userRakyatPermissions.add("VOTING_CANDIDATE_VERIFICATION_R");
            userRakyatPermissions.add("VOTING_CAST_V");
            userRakyatPermissions.add("VOTING_VIEW_RESULTS_R_AFTER_PUBLISH");
            roleService.createRole("ROLE_USER_RAKYAT", userRakyatPermissions);
        }
    }
}
