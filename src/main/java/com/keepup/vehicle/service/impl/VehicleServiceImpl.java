package com.keepup.vehicle.service.impl;

import com.keepup.auth.entity.User;
import com.keepup.auth.repository.UserRepository;
import com.keepup.vehicle.dto.VehicleRequest;
import com.keepup.vehicle.dto.VehicleResponse;
import com.keepup.vehicle.entity.Vehicle;
import com.keepup.vehicle.mapper.VehicleMapper;
import com.keepup.vehicle.repository.VehicleRepository;
import com.keepup.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de vehículos.
 * TODAS las operaciones están restringidas al usuario autenticado.
 * Esto previene accesos no autorizados a datos de otros usuarios (IDOR attacks).
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final UserRepository userRepository;
    // TODO: Agregar UserRepository cuando esté disponible
    // private final UserRepository userRepository;

    /**
     * Crear un nuevo vehículo para el usuario autenticado.
     *
     * SEGURIDAD: El vehículo se crea automáticamente asociado al usuario autenticado,
     * ignorando cualquier userId que venga en el request.
     *
     * @param request DTO con los datos del vehículo a crear
     * @param authenticatedUserId ID del usuario autenticado (obtenido del JWT/Security Context)
     * @return VehicleResponse con los datos del vehículo creado
     * @throws IllegalArgumentException si la placa ya existe para este usuario
     */
    @Override
    @Transactional
    public VehicleResponse create(VehicleRequest request, Long authenticatedUserId) {
        // 1. Validar que la placa no exista para este usuario
        // Permitimos que diferentes usuarios tengan la misma placa (ej: diferentes países)
        if (vehicleRepository.existsByLicensePlateAndUserId(request.getLicensePlate(), authenticatedUserId)) {
            throw new IllegalArgumentException(
                    "You already have a vehicle with license plate: " + request.getLicensePlate()
            );
        }

        // 2. TODO: Validar que el usuario autenticado exista en la BD
        // User user = userRepository.findById(authenticatedUserId)
        //     .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // 3. Convertir Request a Entity
        Vehicle vehicle = vehicleMapper.toEntity(request);

        // 4. CRÍTICO: Forzar el userId al usuario autenticado (seguridad)

        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        vehicle.setUser(user);

        // 5. Guardar en la base de datos
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // 6. Convertir Entity a Response y retornar
        return vehicleMapper.toResponse(savedVehicle);
    }

    /**
     * Obtener un vehículo por su ID.
     *
     * SEGURIDAD: Solo retorna el vehículo si pertenece al usuario autenticado.
     *
     * @param id ID del vehículo a buscar
     * @param authenticatedUserId ID del usuario autenticado
     * @return VehicleResponse con los datos del vehículo
     * @throws RuntimeException si el vehículo no existe o no pertenece al usuario
     */
    @Override
    public VehicleResponse getById(Long id, Long authenticatedUserId) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(id, authenticatedUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Vehicle not found with id: " + id + " or you don't have access to it"
                ));

        return vehicleMapper.toResponse(vehicle);
    }

    /**
     * Obtener todos los vehículos del usuario autenticado.
     *
     * @param authenticatedUserId ID del usuario autenticado
     * @return Lista de VehicleResponse con todos los vehículos del usuario
     */
    @Override
    public List<VehicleResponse> getAll(Long authenticatedUserId) {
        return vehicleRepository.findByUserId(authenticatedUserId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículo por placa del usuario autenticado.
     *
     * @param licensePlate Placa del vehículo
     * @param authenticatedUserId ID del usuario autenticado
     * @return VehicleResponse con los datos del vehículo
     * @throws RuntimeException si el vehículo no existe o no pertenece al usuario
     */
    @Override
    public VehicleResponse getByLicensePlate(String licensePlate, Long authenticatedUserId) {
        Vehicle vehicle = vehicleRepository.findByLicensePlateAndUserId(licensePlate, authenticatedUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Vehicle not found with license plate: " + licensePlate +
                                " or you don't have access to it"
                ));

        return vehicleMapper.toResponse(vehicle);
    }

    /**
     * Obtener vehículos por marca del usuario autenticado.
     *
     * @param make Marca del vehículo
     * @param authenticatedUserId ID del usuario autenticado
     * @return Lista de VehicleResponse con esa marca
     */
    @Override
    public List<VehicleResponse> getByMake(String make, Long authenticatedUserId) {
        return vehicleRepository.findByMakeAndUserId(make, authenticatedUserId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por modelo del usuario autenticado.
     *
     * @param model Modelo del vehículo
     * @param authenticatedUserId ID del usuario autenticado
     * @return Lista de VehicleResponse con ese modelo
     */
    @Override
    public List<VehicleResponse> getByModel(String model, Long authenticatedUserId) {
        return vehicleRepository.findByModelAndUserId(model, authenticatedUserId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por año del usuario autenticado.
     *
     * @param year Año del vehículo
     * @param authenticatedUserId ID del usuario autenticado
     * @return Lista de VehicleResponse de ese año
     */
    @Override
    public List<VehicleResponse> getByYear(Integer year, Long authenticatedUserId) {
        return vehicleRepository.findByYearAndUserId(year, authenticatedUserId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por rango de años del usuario autenticado.
     *
     * @param yearStart Año inicial
     * @param yearEnd Año final
     * @param authenticatedUserId ID del usuario autenticado
     * @return Lista de VehicleResponse en ese rango
     */
    @Override
    public List<VehicleResponse> getByYearRange(Integer yearStart, Integer yearEnd, Long authenticatedUserId) {
        return vehicleRepository.findByYearRangeAndUserId(yearStart, yearEnd, authenticatedUserId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar un vehículo existente del usuario autenticado.
     *
     * SEGURIDAD: Solo permite actualizar si el vehículo pertenece al usuario autenticado.
     * No permite cambiar el usuario propietario.
     *
     * @param id ID del vehículo a actualizar
     * @param request DTO con los nuevos datos
     * @param authenticatedUserId ID del usuario autenticado
     * @return VehicleResponse con los datos actualizados
     * @throws RuntimeException si el vehículo no existe o no pertenece al usuario
     * @throws IllegalArgumentException si la nueva placa ya existe en otro vehículo del usuario
     */
    @Override
    @Transactional
    public VehicleResponse update(Long id, VehicleRequest request, Long authenticatedUserId) {
        // 1. Buscar el vehículo existente Y verificar que pertenece al usuario
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(id, authenticatedUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Vehicle not found with id: " + id + " or you don't have access to it"
                ));

        // 2. Validar que la nueva placa no esté en uso por otro vehículo del mismo usuario
        if (!vehicle.getLicensePlate().equals(request.getLicensePlate()) &&
                vehicleRepository.existsByLicensePlateAndUserId(request.getLicensePlate(), authenticatedUserId)) {
            throw new IllegalArgumentException(
                    "You already have a vehicle with license plate: " + request.getLicensePlate()
            );
        }

        // 3. Actualizar los campos del vehículo
        vehicleMapper.updateEntityFromRequest(request, vehicle);


        // 4. Guardar cambios
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        // 5. Retornar respuesta
        return vehicleMapper.toResponse(updatedVehicle);
    }

    /**
     * Eliminar un vehículo del usuario autenticado.
     *
     * SEGURIDAD: Solo permite eliminar si el vehículo pertenece al usuario autenticado.
     *
     * @param id ID del vehículo a eliminar
     * @param authenticatedUserId ID del usuario autenticado
     * @throws RuntimeException si el vehículo no existe o no pertenece al usuario
     */
    @Override
    @Transactional
    public void delete(Long id, Long authenticatedUserId) {
        // Verificar que el vehículo existe Y pertenece al usuario antes de eliminar
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(id, authenticatedUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Vehicle not found with id: " + id + " or you don't have access to it"
                ));

        vehicleRepository.delete(vehicle);
    }

    /**
     * Contar vehículos del usuario autenticado.
     *
     * @param authenticatedUserId ID del usuario autenticado
     * @return Cantidad de vehículos del usuario
     */
    @Override
    public long countVehicles(Long authenticatedUserId) {
        return vehicleRepository.countByUserId(authenticatedUserId);
    }

    /**
     * Verificar si existe un vehículo con una placa específica para el usuario autenticado.
     *
     * @param licensePlate Placa a verificar
     * @param authenticatedUserId ID del usuario autenticado
     * @return true si el usuario ya tiene un vehículo con esa placa, false si no
     */
    @Override
    public boolean existsByLicensePlate(String licensePlate, Long authenticatedUserId) {
        return vehicleRepository.existsByLicensePlateAndUserId(licensePlate, authenticatedUserId);
    }
}