package com.keepup.vehicle.service.impl;

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
 * Contiene toda la lógica de negocio para operaciones CRUD y búsquedas.
 */
@Service // Marca esta clase como un servicio de Spring
@RequiredArgsConstructor // Genera constructor con dependencias final (inyección por constructor)
@Transactional(readOnly = true) // Todas las operaciones son de solo lectura por defecto
public class VehicleServiceImpl implements VehicleService {

    // Inyección de dependencias mediante constructor (inmutables con final)
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    // TODO: Agregar UserRepository cuando esté disponible
    // private final UserRepository userRepository;

    /**
     * Crear un nuevo vehículo.
     * Valida que la placa no exista y que el usuario exista.
     *
     * @param request DTO con los datos del vehículo a crear
     * @return VehicleResponse con los datos del vehículo creado
     * @throws IllegalArgumentException si la placa ya existe
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    @Transactional // Operación de escritura, necesita transacción
    public VehicleResponse create(VehicleRequest request) {
        // 1. Validar que la placa no exista
        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new IllegalArgumentException(
                    "Vehicle with license plate " + request.getLicensePlate() + " already exists"
            );
        }

        // 2. TODO: Validar que el usuario exista
        // User user = userRepository.findById(request.getUserId())
        //     .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // 3. Convertir Request a Entity
        Vehicle vehicle = vehicleMapper.toEntity(request);

        // 4. TODO: Asignar el usuario al vehículo
        // vehicle.setUser(user);

        // 5. Guardar en la base de datos
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // 6. Convertir Entity a Response y retornar
        return vehicleMapper.toResponse(savedVehicle);
    }

    /**
     * Obtener un vehículo por su ID.
     *
     * @param id ID del vehículo a buscar
     * @return VehicleResponse con los datos del vehículo
     * @throws RuntimeException si el vehículo no existe
     */
    @Override
    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        return vehicleMapper.toResponse(vehicle);
    }

    /**
     * Obtener todos los vehículos.
     *
     * @return Lista de VehicleResponse con todos los vehículos
     */
    @Override
    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por usuario.
     *
     * @param userId ID del usuario
     * @return Lista de VehicleResponse de ese usuario
     */
    @Override
    public List<VehicleResponse> getByUserId(Long userId) {
        return vehicleRepository.findByUserId(userId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículo por placa.
     *
     * @param licensePlate Placa del vehículo
     * @return VehicleResponse con los datos del vehículo
     * @throws RuntimeException si el vehículo no existe
     */
    @Override
    public VehicleResponse getByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException(
                        "Vehicle not found with license plate: " + licensePlate
                ));

        return vehicleMapper.toResponse(vehicle);
    }

    /**
     * Obtener vehículos por marca.
     *
     * @param make Marca del vehículo
     * @return Lista de VehicleResponse con esa marca
     */
    @Override
    public List<VehicleResponse> getByMake(String make) {
        return vehicleRepository.findByMake(make)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por modelo.
     *
     * @param model Modelo del vehículo
     * @return Lista de VehicleResponse con ese modelo
     */
    @Override
    public List<VehicleResponse> getByModel(String model) {
        return vehicleRepository.findByModel(model)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por año.
     *
     * @param year Año del vehículo
     * @return Lista de VehicleResponse de ese año
     */
    @Override
    public List<VehicleResponse> getByYear(Integer year) {
        return vehicleRepository.findByYear(year)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener vehículos por rango de años.
     *
     * @param yearStart Año inicial
     * @param yearEnd Año final
     * @return Lista de VehicleResponse en ese rango
     */
    @Override
    public List<VehicleResponse> getByYearRange(Integer yearStart, Integer yearEnd) {
        return vehicleRepository.findByYearRange(yearStart, yearEnd)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar un vehículo existente.
     * No permite cambiar el usuario propietario.
     *
     * @param id ID del vehículo a actualizar
     * @param request DTO con los nuevos datos
     * @return VehicleResponse con los datos actualizados
     * @throws RuntimeException si el vehículo no existe
     * @throws IllegalArgumentException si la nueva placa ya existe en otro vehículo
     */
    @Override
    @Transactional // Operación de escritura
    public VehicleResponse update(Long id, VehicleRequest request) {
        // 1. Buscar el vehículo existente
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        // 2. Validar que la nueva placa no esté en uso por otro vehículo
        if (!vehicle.getLicensePlate().equals(request.getLicensePlate()) &&
                vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new IllegalArgumentException(
                    "License plate " + request.getLicensePlate() + " is already in use"
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
     * Eliminar un vehículo por su ID.
     *
     * @param id ID del vehículo a eliminar
     * @throws RuntimeException si el vehículo no existe
     */
    @Override
    @Transactional // Operación de escritura
    public void delete(Long id) {
        // Verificar que el vehículo exista antes de eliminar
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }

        vehicleRepository.deleteById(id);
    }

    /**
     * Contar vehículos por usuario.
     *
     * @param userId ID del usuario
     * @return Cantidad de vehículos del usuario
     */
    @Override
    public long countByUserId(Long userId) {
        return vehicleRepository.countByUserId(userId);
    }

    /**
     * Verificar si existe un vehículo con una placa específica.
     *
     * @param licensePlate Placa a verificar
     * @return true si existe, false si no
     */
    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return vehicleRepository.existsByLicensePlate(licensePlate);
    }
}