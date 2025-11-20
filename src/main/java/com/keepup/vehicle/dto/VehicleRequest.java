package com.keepup.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.keepup.vehicle.enums.VehicleType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para recibir datos de creación/actualización de vehículos.
 * Este objeto se usa en las peticiones POST y PUT del API.
 * Incluye validaciones para garantizar la integridad de los datos.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@Builder // Permite construir objetos usando el patrón Builder
@AllArgsConstructor // Genera constructor con todos los parámetros
@NoArgsConstructor // Genera constructor sin parámetros (requerido para deserialización JSON)
public class VehicleRequest {

    @JsonProperty("licensePlate")
    // Validación: No puede ser nulo, vacío o solo espacios en blanco
    @NotBlank(message = "License plate is required")
    // Validación: Longitud entre 5 y 10 caracteres
    @Size(min = 5, max = 10, message = "License plate must be between 5 and 10 characters")
    // Validación: Solo permite letras mayúsculas, números y guiones
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "License plate must contain only uppercase letters, numbers, and hyphens")
    private String licensePlate; // Placa del vehículo (única)

    // Validación: Campo obligatorio
    @NotBlank(message = "Make is required")
    // Validación: Longitud entre 2 y 50 caracteres
    @Size(min = 2, max = 50, message = "Make must be between 2 and 50 characters")
    private String make; // Marca del vehículo (ej: Toyota, Honda)

    // Validación: Campo obligatorio
    @NotBlank(message = "Model is required")
    // Validación: Longitud entre 1 y 50 caracteres
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model; // Modelo del vehículo (ej: Corolla, Civic)

    // Validación: No puede ser nulo (pero sí puede ser 0)
    @NotNull(message = "Year is required")
    // Validación: Valor mínimo de 1900
    @Min(value = 1900, message = "Year must be 1900 or later")
    // Validación: Valor máximo de 2100
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    private Integer year; // Año de fabricación del vehículo

    // Validación: Campo opcional, pero si se envía, máximo 30 caracteres
    @Size(max = 30, message = "Color must not exceed 30 characters")
    private String color; // Color del vehículo (opcional)

    @JsonProperty("vehicleType")
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    
    // Validación: Debe ser un número positivo
    @Positive(message = "User ID must be positive")
    private Long userId; // ID del usuario propietario del vehículo
}