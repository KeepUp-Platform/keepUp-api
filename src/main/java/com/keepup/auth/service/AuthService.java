package com.keepup.auth.service;

import com.keepup.auth.dto.AuthResponse;
import com.keepup.auth.dto.LoginRequest;
import com.keepup.auth.dto.RegisterRequest;
import com.keepup.auth.entity.Role;
import com.keepup.auth.entity.User;
import com.keepup.auth.repository.RoleRepository;
import com.keepup.auth.repository.UserRepository;
import com.keepup.core.security.CustomerDetails;
import com.keepup.core.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // --- Metodo de registro--
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Role userRole = roleRepository.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("Error: Rol ROLE_CLIENT no encontrado en BD."));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);
        CustomerDetails userDetails = new CustomerDetails(savedUser);
        String jwtToken = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder().token(jwtToken).build();
    }

    // Metodo de login
    public AuthResponse login(LoginRequest request) {
        // 1. Intentar autenticar (Spring Security se encarga de verificar el password)
        // Si las credenciales están mal, esto lanza una excepción "BadCredentialsException" automáticamente.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Si pasamos la línea anterior, el usuario es válido. Lo buscamos en BD.
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Preparamos los detalles para el Token
        CustomerDetails userDetails = new CustomerDetails(user);

        // 4. Generamos el Token
        String jwtToken = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder().token(jwtToken).build();
    }
}