package com.keepup.auth.service;

import com.keepup.auth.dto.AuthResponse;
import com.keepup.auth.dto.LoginRequest;
import com.keepup.auth.dto.RegisterRequest;
import com.keepup.auth.entity.Role;
import com.keepup.auth.entity.User;
import com.keepup.auth.repository.RoleRepository;
import com.keepup.auth.repository.UserRepository;
import com.keepup.core.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        // 1. ARRANGE (Preparar datos y simulaciones)
        LoginRequest request = new LoginRequest("test@mail.com", "123456");

        User mockUser = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("encodedPass")
                .role(new Role(1L, "ROLE_CLIENT", "Desc"))
                .build();

        // Simulamos que el usuario SÍ existe en la BD
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        // Simulamos que el generador de tokens devuelve un string
        when(jwtUtil.generateToken(any())).thenReturn("mocked-jwt-token");

        // 2. ACT (Ejecutar el método real)
        AuthResponse response = authService.login(request);

        // 3. ASSERT (Verificar resultado)
        assertNotNull(response);
        assertNotNull(response.getToken());
    }

    @Test
    void register_ShouldReturnToken_WhenUserIsNew() {
        // 1. ARRANGE
        RegisterRequest request = new RegisterRequest("Juan", "juan@mail.com", "123456");
        Role clientRole = new Role(1L, "ROLE_CLIENT", "Desc");

        // Simulamos que el email NO existe
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        // Simulamos que el rol SÍ existe
        when(roleRepository.findByName("ROLE_CLIENT")).thenReturn(Optional.of(clientRole));
        // Simulamos el guardado
        when(userRepository.save(any(User.class))).thenReturn(new User());
        // Simulamos el token
        when(jwtUtil.generateToken(any())).thenReturn("mocked-jwt-token");

        // 2. ACT
        AuthResponse response = authService.register(request);

        // 3. ASSERT
        assertNotNull(response);
        assertNotNull(response.getToken());
    }
}