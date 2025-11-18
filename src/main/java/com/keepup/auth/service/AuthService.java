package com.keepup.auth.service;

import com.keepup.auth.dto.AuthResponse;
import com.keepup.auth.dto.RegisterRequest;
import com.keepup.auth.entity.Role;
import com.keepup.auth.entity.User;
import com.keepup.auth.repository.UserRepository;
import com.keepup.core.security.CustomerDetails;
import com.keepup.core.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // 1. Validar existencia
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Crear Entidad User
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CLIENT)
                .build();

        // 3. Guardar en BBDD
        User savedUser = userRepository.save(user);

        // 4. Envolver el usuario

        CustomerDetails userDetails = new CustomerDetails(savedUser);

        // 5. Generar Token
        String jwtToken = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}