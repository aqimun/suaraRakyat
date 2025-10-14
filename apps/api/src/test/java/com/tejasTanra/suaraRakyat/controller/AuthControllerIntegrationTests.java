package com.tejasTanra.suaraRakyat.modules.auth;

import com.tejasTanra.suaraRakyat.config.JwtTokenProvider;
import com.tejasTanra.suaraRakyat.dto.LoginRequest;
import com.tejasTanra.suaraRakyat.dto.LoginResponse;
import com.tejasTanra.suaraRakyat.dto.RegisterRequest;
import com.tejasTanra.suaraRakyat.exception.ConflictException;
import com.tejasTanra.suaraRakyat.model.User;
import com.tejasTanra.suaraRakyat.model.UserStatus;
import com.tejasTanra.suaraRakyat.modules.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User mockUser;
    private Authentication mockAuthentication;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPhone("1234567890");
        registerRequest.setNameDisplay("Test User");
        registerRequest.setPassword("password123");
        registerRequest.setKtpRef("ktpRef");
        registerRequest.setSelfieRef("selfieRef");

        loginRequest = new LoginRequest();
        loginRequest.setEmailPhone("test@example.com");
        loginRequest.setPassword("password123");

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPhone("1234567890");
        mockUser.setNameDisplay("Test User");
        mockUser.setStatus(UserStatus.PENDING);
        mockUser.setPassword("encodedPassword"); // Password is encoded in real app

        // Mock Authentication object
        mockAuthentication = new UsernamePasswordAuthenticationToken(
                mockUser.getEmail(),
                mockUser.getPassword(),
                Set.of("ROLE_USER_RAKYAT").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    @Test
    void registerUser_Success() {
        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(mockUser);

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/register", registerRequest, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User registered successfully with ID: 1. Status: PENDING", response.getBody());
    }

    @Test
    void registerUser_Conflict() {
        when(userService.registerUser(any(RegisterRequest.class))).thenThrow(new ConflictException("Email already registered."));

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/register", registerRequest, String.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already registered.", response.getBody());
    }

    @Test
    void loginUser_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn("mockJwtToken");

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/auth/login", loginRequest, LoginResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mockJwtToken", response.getBody().getAccessToken());
    }

    @Test
    void loginUser_InvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginRequest, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void loginUser_UserNotFound() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("User not found"));

        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginRequest, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody()); // AuthController converts UsernameNotFound to BadCredentials
    }
}
