package com.keepup.vehicle.dto;

import com.keepup.vehicle.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para enviar datos de vehículos al cliente.
 * Este objeto se usa en las respuestas del API (GET, POST, PUT).
 * Incluye toda la información del vehículo incluyendo datos del usuario propietario.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@Builder // Permite construir objetos usando el patrón Builder
@AllArgsConstructor // Genera constructor con todos los parámetros
@NoArgsConstructor // Genera constructor sin parámetros (requerido para serialización JSON)
public class VehicleResponse {

    // ID único del vehículo generado por la base de datos
    private Long id;

    // Placa del vehículo (identificador único)
    private String licensePlate;

    // Marca del vehículo (ej: Toyota, Honda, Ford)
    private String make;

    // Modelo del vehículo (ej: Corolla, Civic, Mustang)
    private String model;

    // Año de fabricación del vehículo
    private Integer year;

    // Color del vehículo (puede ser null)
    private String color;

    // Tipo de vehiculo
    private VehicleType vehicleType;

    // ID del usuario propietario del vehículo
    private Long userId;

    // Email del usuario propietario (información adicional útil para el cliente)
    private String userEmail;

    // Fecha y hora de creación del registro (auditoría)
    private LocalDateTime createdAt;

    // Fecha y hora de última actualización del registro (auditoría)
    private LocalDateTime updatedAt;
}