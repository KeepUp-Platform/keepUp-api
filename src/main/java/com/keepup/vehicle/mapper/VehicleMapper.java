package com.keepup.vehicle.mapper;

import com.keepup.vehicle.dto.VehicleRequest;
import com.keepup.vehicle.dto.VehicleResponse;
import com.keepup.vehicle.entity.Vehicle;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades Vehicle y sus DTOs.
 * Centraliza la lógica de conversión para mantener el código limpio y mantenible.
 * Se marca como @Component para que Spring lo gestione como un bean.
 */
@Component
public class VehicleMapper {

    /**
     * Convierte una entidad Vehicle a VehicleResponse (DTO de salida).
     * Se usa cuando enviamos datos al cliente en respuestas del API.
     *
     * @param vehicle La entidad Vehicle de la base de datos
     * @return VehicleResponse con toda la información para el cliente
     */
    public VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .color(vehicle.getColor())
                .vehicleType(vehicle.getVehicleType())
                // Verifica que el usuario no sea null antes de acceder a sus propiedades
                .userId(vehicle.getUser() != null ? vehicle.getUser().getId() : null)
                .userEmail(vehicle.getUser() != null ? vehicle.getUser().getEmail() : null)
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un VehicleRequest (DTO de entrada) a una entidad Vehicle.
     * Se usa cuando creamos un nuevo vehículo desde una petición POST.
     * Nota: El User debe ser asignado después en el Service usando el userId.
     *
     * @param request El DTO con los datos del vehículo a crear
     * @return Una nueva entidad Vehicle (sin ID, sin User, sin timestamps)
     */
    public Vehicle toEntity(VehicleRequest request) {
        return Vehicle.builder()
                // Convertimos la placa a mayúsculas para estandarizar el formato
                .licensePlate(request.getLicensePlate().toUpperCase())
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .color(request.getColor())
                .vehicleType(request.getVehicleType())
                // No asignamos user aquí, se hace en el Service con userRepository
                .build();
    }

    /**
     * Actualiza una entidad Vehicle existente con datos de VehicleRequest.
     * Se usa cuando actualizamos un vehículo existente con una petición PUT/PATCH.
     * Mantiene el ID, User y timestamps originales.
     *
     * @param request El DTO con los nuevos datos del vehículo
     * @param vehicle La entidad Vehicle existente a actualizar
     */
    public void updateEntityFromRequest(VehicleRequest request, Vehicle vehicle) {
        // Actualizamos solo los campos que vienen en el request
        vehicle.setLicensePlate(request.getLicensePlate().toUpperCase());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setColor(request.getColor());
        vehicle.setVehicleType(request.getVehicleType());
        // No tocamos: id, user, createdAt (se mantienen)
        // updatedAt se actualiza automáticamente con @PreUpdate
    }
}