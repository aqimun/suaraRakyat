package com.tejasTanra.suaraRakyat.modules.users;

import com.tejasTanra.suaraRakyat.exception.BadRequestException; // Import custom exceptions
import com.tejasTanra.suaraRakyat.exception.ConflictException;
import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException;
import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import com.tejasTanra.suaraRakyat.service.AuditLogService; // Keep this import for now, will move AuditLogService later
import com.tejasTanra.suaraRakyat.service.RoleService; // Keep this import for now, will move RoleService later
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Keep for loadUserByUsername signature
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tejasTanra.suaraRakyat.dto.RegisterRequest;

@Service
@Transactional // Apply transactional to all methods in this service by default
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService; // To assign roles

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditLogService auditLogService;

    // @Transactional is applied at class level
    public User registerUser(RegisterRequest request) {
        // Check if email or phone already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("Email already registered."); // Use ConflictException
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new ConflictException("Phone number already registered."); // Use ConflictException
        }

        Optional<Role> roleOptional = roleService.findByName("ROLE_USER_RAKYAT"); // Default role for new registrations
        if (roleOptional.isEmpty()) {
            throw new ConflictException("Default role ROLE_USER_RAKYAT not found. Please ensure DataLoader runs."); // Use ConflictException
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
    // @Transactional is applied at class level
    public User createUser(String email, String phone, String nameDisplay, String roleName, String password, String encryptedKtpRef, String encryptedSelfieRef) {
        Optional<Role> roleOptional = roleService.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new BadRequestException("Role not found: " + roleName); // Use BadRequestException
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

    @Transactional(readOnly = true) // Read-only method
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
    }

    @Transactional(readOnly = true) // Read-only method
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + email));
    }

    @Transactional(readOnly = true) // Read-only method
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("User not found with phone : " + phone));
    }

    // @Transactional is applied at class level
    public User save(User user) {
        return userRepository.save(user);
    }

    // @Transactional is applied at class level
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) // Read-only method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try finding by email first, then by phone
        User user = userRepository.findByEmail(username)
                .orElseGet(() -> userRepository.findByPhone(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + username))); // Keep UsernameNotFoundException for signature

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().getPermissions().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
