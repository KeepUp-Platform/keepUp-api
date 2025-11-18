package com.keepup.auth.service;

import com.keepup.auth.dto.AuthResponse;
import com.keepup.auth.dto.RegisterRequest;
import com.keepup.auth.entity.Role;
import com.keepup.auth.entity.User;
import com.keepup.auth.repository.RoleRepository; // Importante
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // 1. Validar usuario existente
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Buscar el Rol en la BD (Esto es lo nuevo)
        Role userRole = roleRepository.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("Error: Rol ROLE_CLIENT no encontrado en BD."));

        // 3. Crear el Usuario
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole) // Asignamos el objeto Rol completo
                .build();

        // 4. Guardar y Generar Token
        User savedUser = userRepository.save(user);
        CustomerDetails userDetails = new CustomerDetails(savedUser);
        String jwtToken = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder().token(jwtToken).build();
    }
}