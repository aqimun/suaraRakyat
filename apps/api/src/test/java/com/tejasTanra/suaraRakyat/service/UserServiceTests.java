package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.dto.RegisterRequest;
import com.tejasTanra.suaraRakyat.exception.BadRequestException;
import com.tejasTanra.suaraRakyat.exception.ConflictException;
import com.tejasTanra.suaraRakyat.exception.ResourceNotFoundException;
import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus;
import com.tejasTanra.suaraRakyat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role userRole;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setId(1L);
        userRole.setName("ROLE_USER_RAKYAT");
        userRole.setPermissions(Set.of("READ_COMPLAINT", "CREATE_COMPLAINT"));

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        user.setNameDisplay("Test User");
        user.setPassword("encodedPassword");
        user.setRole(userRole);
        user.setStatus(UserStatus.PENDING);

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("newuser@example.com");
        registerRequest.setPhone("0987654321");
        registerRequest.setNameDisplay("New User");
        registerRequest.setPassword("rawPassword");
        registerRequest.setKtpRef("ktpRef");
        registerRequest.setSelfieRef("selfieRef");
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(roleService.findByName("ROLE_USER_RAKYAT")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(registerRequest);

        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals(UserStatus.PENDING, registeredUser.getStatus());
        verify(userRepository, times(1)).save(any(User.class));
        verify(auditLogService, times(1)).log(any(), eq("USER_REGISTER"), eq("User"), eq(user.getId()), any(), any());
    }

    @Test
    void registerUser_EmailConflict() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () -> userService.registerUser(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_PhoneConflict() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () -> userService.registerUser(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_RoleNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(roleService.findByName("ROLE_USER_RAKYAT")).thenReturn(Optional.empty());

        assertThrows(ConflictException.class, () -> userService.registerUser(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(1L);
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    void findById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    void findByEmail_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void findByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail("nonexistent@example.com"));
    }

    @Test
    void findByPhone_Success() {
        when(userRepository.findByPhone("1234567890")).thenReturn(Optional.of(user));
        User foundUser = userService.findByPhone("1234567890");
        assertNotNull(foundUser);
        assertEquals("1234567890", foundUser.getPhone());
    }

    @Test
    void findByPhone_NotFound() {
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findByPhone("0000000000"));
    }

    @Test
    void loadUserByUsername_ByEmail_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("test@example.com");
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("READ_COMPLAINT")));
    }

    @Test
    void loadUserByUsername_ByPhone_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone("1234567890")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("1234567890");
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername()); // UserDetails uses email as username
    }

    @Test
    void loadUserByUsername_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistent"));
    }
}
