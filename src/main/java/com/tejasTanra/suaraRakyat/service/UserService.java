package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tejasTanra.suaraRakyat.dto.RegisterRequest;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService; // To assign roles

    @Autowired
    private PasswordEncoder passwordEncoder;



    public User registerUser(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        Optional<Role> roleOptional = roleService.findByName("ROLE_USER_RAKYAT"); // Default role for new registrations
        if (roleOptional.isEmpty()) {
            throw new IllegalStateException("Default role ROLE_USER_RAKYAT not found. Please ensure DataLoader runs.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        user.setRole(roleOptional.get());
        user.setStatus(UserStatus.PENDING); // New users are PENDING until KYC is approved
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    // This method can be kept for internal use or removed if all user creation goes through registerUser
    public User createUser(String email, String roleName, String password) {
        Optional<Role> roleOptional = roleService.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleOptional.get());
        user.setStatus(UserStatus.PENDING);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
