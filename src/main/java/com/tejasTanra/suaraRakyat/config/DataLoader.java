package com.tejasTanra.suaraRakyat.config;

import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus; // Import UserStatus
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import com.tejasTanra.suaraRakyat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDateTime; // Import LocalDateTime

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createRolesIfNotFound();
        createSuperAdminUserIfNotFound();
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
            roleService.createRole("ROLE_SUPER_ADMIN", superAdminPermissions);
        }

        // Staff Admin Role
        if (roleService.findByName("ROLE_STAFF_ADMIN").isEmpty()) {
            Set<String> staffAdminPermissions = new HashSet<>();
            staffAdminPermissions.add("AUTH_REGISTER_R");
            staffAdminPermissions.add("COMPLAINTS_MODERATE_A");
            staffAdminPermissions.add("COMPLAINTS_ESCALATE_E");
            staffAdminPermissions.add("COMPLAINTS_CLOSE_A");
            staffAdminPermissions.add("NEWS_CREATE_EDIT_PUBLISH_C_A");
            staffAdminPermissions.add("NEWS_DELETE_D");
            roleService.createRole("ROLE_STAFF_ADMIN", staffAdminPermissions);
        }

        // User Penjabat Role
        if (roleService.findByName("ROLE_USER_PENJABAT").isEmpty()) {
            Set<String> userPenjabatPermissions = new HashSet<>();
            userPenjabatPermissions.add("AUTH_REGISTER_R");
            userPenjabatPermissions.add("COMPLAINTS_CREATE_R");
            userPenjabatPermissions.add("PROJECTS_EDIT_U");
            userPenjabatPermissions.add("PROJECT_EVIDENCE_R_U");
            userPenjabatPermissions.add("VOTING_CAST_R");
            roleService.createRole("ROLE_USER_PENJABAT", userPenjabatPermissions);
        }

        // User Rakyat Role
        if (roleService.findByName("ROLE_USER_RAKYAT").isEmpty()) {
            Set<String> userRakyatPermissions = new HashSet<>();
            userRakyatPermissions.add("AUTH_REGISTER_R");
            userRakyatPermissions.add("COMPLAINTS_CREATE_R");
            userRakyatPermissions.add("VOTING_CAST_R");
            roleService.createRole("ROLE_USER_RAKYAT", userRakyatPermissions);
        }
    }

    private void createSuperAdminUserIfNotFound() {
        if (userRepository.findByEmail("superadmin@suararakyat.com").isEmpty()) { // Changed to findByEmail
            Optional<Role> superAdminRole = roleService.findByName("ROLE_SUPER_ADMIN");
            if (superAdminRole.isPresent()) {
                User superAdmin = new User();
                superAdmin.setEmail("superadmin@suararakyat.com");
                superAdmin.setPassword(passwordEncoder.encode("password")); // Encrypt the password
                superAdmin.setRole(superAdminRole.get()); // Set single role
                superAdmin.setStatus(UserStatus.ACTIVE); // Set the status for the super admin user
                superAdmin.setCreatedAt(LocalDateTime.now()); // Set createdAt
                superAdmin.setUpdatedAt(LocalDateTime.now()); // Set updatedAt

                userRepository.save(superAdmin);
                System.out.println("Super Admin user created.");
            } else {
                System.err.println("ROLE_SUPER_ADMIN not found. Cannot create Super Admin user.");
            }
        }
    }
}
