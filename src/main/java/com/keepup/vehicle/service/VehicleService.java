package com.keepup.vehicle.service;

import com.keepup.vehicle.dto.VehicleRequest;
import com.keepup.vehicle.dto.VehicleResponse;

import java.util.List;

/**
 * Interfaz del servicio de vehículos.
 * Todas las operaciones están restringidas al usuario autenticado.
 */
public interface VehicleService {

    // Crear vehículo para el usuario autenticado
    VehicleResponse create(VehicleRequest request, Long authenticatedUserId);

    // Obtener vehículo por ID (solo si pertenece al usuario autenticado)
    VehicleResponse getById(Long id, Long authenticatedUserId);

    // Obtener todos los vehículos del usuario autenticado
    List<VehicleResponse> getAll(Long authenticatedUserId);

    // Obtener vehículo por placa (solo si pertenece al usuario autenticado)
    VehicleResponse getByLicensePlate(String licensePlate, Long authenticatedUserId);

    // Obtener vehículos por marca (solo del usuario autenticado)
    List<VehicleResponse> getByMake(String make, Long authenticatedUserId);

    // Obtener vehículos por modelo (solo del usuario autenticado)
    List<VehicleResponse> getByModel(String model, Long authenticatedUserId);

    // Obtener vehículos por año (solo del usuario autenticado)
    List<VehicleResponse> getByYear(Integer year, Long authenticatedUserId);

    // Obtener vehículos por rango de años (solo del usuario autenticado)
    List<VehicleResponse> getByYearRange(Integer yearStart, Integer yearEnd, Long authenticatedUserId);

    // Actualizar vehículo (solo si pertenece al usuario autenticado)
    VehicleResponse update(Long id, VehicleRequest request, Long authenticatedUserId);

    // Eliminar vehículo (solo si pertenece al usuario autenticado)
    void delete(Long id, Long authenticatedUserId);

    // Contar vehículos del usuario autenticado
    long countVehicles(Long authenticatedUserId);

    // Verificar si existe placa (solo entre vehículos del usuario autenticado)
    boolean existsByLicensePlate(String licensePlate, Long authenticatedUserId);
}