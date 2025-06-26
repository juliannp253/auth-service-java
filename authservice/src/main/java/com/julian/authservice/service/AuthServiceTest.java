package com.julian.authservice.service;

import com.julian.authservice.dto.LoginRequest;
import com.julian.authservice.dto.RegisterRequest;
import com.julian.authservice.model.Role;
import com.julian.authservice.model.User;
import com.julian.authservice.repository.UserRepository;
import com.julian.authservice.security.JwtUtil;
import com.julian.authservice.dto.LoginResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtService;

    @InjectMocks
    private UserService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los @Mock y @InjectMocks
    }

    @Test
    void testRegisterUser_WhenEmailIsNew_ShouldCreateUser() {
        // Arrange
        RegisterRequest request = new RegisterRequest("julian", "julian@example.com", "123");
        when(userRepository.findByEmail("julian@example.com")).thenReturn(Optional.empty());
        //when(passwordEncoder.encode("123")).thenReturn("hashed123");

        // Simula que save devuelve el mismo usuario
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User createdUser = authService.registerUser(request);

        // Assert
        assertEquals("julian", createdUser.getUsername());
        assertEquals("julian@example.com", createdUser.getEmail());
        //assertEquals("hashed123", createdUser.getPassword());
        assertNotNull(createdUser.getRole()); // si tu lógica asigna un rol por default
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_WhenEmailExists_ShouldThrowException() {
        // Arrange
        RegisterRequest request = new RegisterRequest("julian", "julian@example.com", "123");
        User existing = new User();
        existing.setEmail("julian@example.com");

        when(userRepository.existsByEmail("julian@example.com")).thenReturn(true);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.registerUser(request);
        });

        assertEquals("El correo ya está registrado", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testLoginUser_WhenCredentialsAreCorrect_ShouldReturnTokens() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("julian@example.com");
        request.setPassword("123");

        User user = new User();
        user.setEmail("julian@example.com");
        user.setPassword("hashed123");

        when(userRepository.findByEmail("julian@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "hashed123")).thenReturn(true);
        when(jwtService.generateAccessToken(user)).thenReturn("accessToken123");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken456");

        // Act
        LoginResponse response = authService.loginUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken123", response.getAccessToken());
        assertEquals("refreshToken456", response.getRefreshToken());
    }

    @Test
    void testLoginUser_WhenEmailNotFound_ShouldThrowException() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("noexiste@example.com");
        request.setPassword("123");

        when(userRepository.findByEmail("noexiste@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.loginUser(request);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testLoginUser_WhenPasswordIncorrect_ShouldThrowException() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("julian@example.com");
        request.setPassword("wrong");

        User user = new User();
        user.setEmail("julian@example.com");
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail("julian@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashedPassword")).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.loginUser(request);
        });

        assertEquals("Contraseña incorrecta", exception.getMessage());
    }
}
