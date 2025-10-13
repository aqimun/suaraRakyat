package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException;
import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tejasTanra.suaraRakyat.dto.RegisterRequest;
import com.tejasTanra.suaraRakyat.service.AuditLogService;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService; // To assign roles

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditLogService auditLogService;

    public User registerUser(RegisterRequest request) {
        // Check if email or phone already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered.");
        }

        Optional<Role> roleOptional = roleService.findByName("ROLE_USER_RAKYAT"); // Default role for new registrations
        if (roleOptional.isEmpty()) {
            throw new IllegalStateException("Default role ROLE_USER_RAKYAT not found. Please ensure DataLoader runs.");
        }

        User user = new User();
        user.setEmail(request.getEmail()); // Hashing of email/phone will be added later
        user.setPhone(request.getPhone()); // Hashing of email/phone will be added later
        user.setNameDisplay(request.getNameDisplay());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        user.setEncryptedKtpRef(request.getKtpRef()); // Store reference to encrypted KTP
        user.setEncryptedSelfieRef(request.getSelfieRef()); // Store reference to encrypted selfie
        user.setRole(roleOptional.get());
        user.setStatus(UserStatus.PENDING); // New users are PENDING until KYC is approved
        User savedUser = userRepository.save(user);
        auditLogService.log(null, "USER_REGISTER", "User", savedUser.getId(), null, savedUser); // Log registration
        return savedUser;
    }

    // This method can be kept for internal use or removed if all user creation goes through registerUser
    public User createUser(String email, String phone, String nameDisplay, String roleName, String password, String encryptedKtpRef, String encryptedSelfieRef) {
        Optional<Role> roleOptional = roleService.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setNameDisplay(nameDisplay);
        user.setPassword(passwordEncoder.encode(password));
        user.setEncryptedKtpRef(encryptedKtpRef);
        user.setEncryptedSelfieRef(encryptedSelfieRef);
        user.setRole(roleOptional.get());
        user.setStatus(UserStatus.PENDING);
        User savedUser = userRepository.save(user);
        auditLogService.log(null, "USER_CREATE_INTERNAL", "User", savedUser.getId(), null, savedUser); // Log internal creation
        return savedUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + email));
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("User not found with phone : " + phone));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try finding by email first, then by phone
        User user = userRepository.findByEmail(username)
                .orElseGet(() -> userRepository.findByPhone(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + username)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().getPermissions().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
