package com.keepup.vehicle.controller;

import com.keepup.core.security.CustomerDetails;
import com.keepup.vehicle.dto.VehicleRequest;
import com.keepup.vehicle.dto.VehicleResponse;
import com.keepup.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de vehículos.
 * Todos los endpoints están protegidos y operan sobre vehículos del usuario autenticado.
 *
 * Base URL: /api/v1/vehicles
 */
@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Crear un nuevo vehículo para el usuario autenticado.
     *
     * POST /api/v1/vehicles
     *
     * @param request Datos del vehículo a crear (validados)
     * @param authentication Información del usuario autenticado (inyectada por Spring Security)
     * @return 201 CREATED con los datos del vehículo creado
     */
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @Valid @RequestBody VehicleRequest request,
            Authentication authentication) {

        // Obtener el ID del usuario autenticado desde el token JWT
        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        VehicleResponse response = vehicleService.create(request, authenticatedUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener un vehículo por su ID.
     * Solo retorna el vehículo si pertenece al usuario autenticado.
     *
     * GET /api/v1/vehicles/{id}
     *
     * @param id ID del vehículo
     * @param authentication Usuario autenticado
     * @return 200 OK con los datos del vehículo
     * @throws RuntimeException 404 NOT FOUND si el vehículo no existe o no pertenece al usuario
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(
            @PathVariable Long id,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        VehicleResponse response = vehicleService.getById(id, authenticatedUserId);

        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los vehículos del usuario autenticado.
     *
     * GET /api/v1/vehicles
     *
     * @param authentication Usuario autenticado
     * @return 200 OK con lista de vehículos (puede estar vacía)
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        List<VehicleResponse> vehicles = vehicleService.getAll(authenticatedUserId);

        return ResponseEntity.ok(vehicles);
    }

    /**
     * Buscar vehículo por placa (del usuario autenticado).
     *
     * GET /api/v1/vehicles/license-plate/{licensePlate}
     *
     * @param licensePlate Placa del vehículo
     * @param authentication Usuario autenticado
     * @return 200 OK con los datos del vehículo
     * @throws RuntimeException 404 NOT FOUND si no existe o no pertenece al usuario
     */
    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<VehicleResponse> getVehicleByLicensePlate(
            @PathVariable String licensePlate,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        VehicleResponse response = vehicleService.getByLicensePlate(licensePlate, authenticatedUserId);

        return ResponseEntity.ok(response);
    }

    /**
     * Buscar vehículos por marca (del usuario autenticado).
     *
     * GET /api/v1/vehicles/make/{make}
     *
     * @param make Marca del vehículo
     * @param authentication Usuario autenticado
     * @return 200 OK con lista de vehículos de esa marca
     */
    @GetMapping("/make/{make}")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByMake(
            @PathVariable String make,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        List<VehicleResponse> vehicles = vehicleService.getByMake(make, authenticatedUserId);

        return ResponseEntity.ok(vehicles);
    }

    /**
     * Buscar vehículos por modelo (del usuario autenticado).
     *
     * GET /api/v1/vehicles/model/{model}
     *
     * @param model Modelo del vehículo
     * @param authentication Usuario autenticado
     * @return 200 OK con lista de vehículos de ese modelo
     */
    @GetMapping("/model/{model}")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByModel(
            @PathVariable String model,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        List<VehicleResponse> vehicles = vehicleService.getByModel(model, authenticatedUserId);

        return ResponseEntity.ok(vehicles);
    }

    /**
     * Buscar vehículos por año (del usuario autenticado).
     *
     * GET /api/v1/vehicles/year/{year}
     *
     * @param year Año del vehículo
     * @param authentication Usuario autenticado
     * @return 200 OK con lista de vehículos de ese año
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByYear(
            @PathVariable Integer year,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        List<VehicleResponse> vehicles = vehicleService.getByYear(year, authenticatedUserId);

        return ResponseEntity.ok(vehicles);
    }

    /**
     * Buscar vehículos por rango de años (del usuario autenticado).
     *
     * GET /api/v1/vehicles/year-range?start={yearStart}&end={yearEnd}
     *
     * @param start Año inicial
     * @param end Año final
     * @param authentication Usuario autenticado
     * @return 200 OK con lista de vehículos en ese rango
     */
    @GetMapping("/year-range")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByYearRange(
            @RequestParam Integer start,
            @RequestParam Integer end,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        List<VehicleResponse> vehicles = vehicleService.getByYearRange(start, end, authenticatedUserId);

        return ResponseEntity.ok(vehicles);
    }

    /**
     * Actualizar un vehículo existente.
     * Solo permite actualizar si pertenece al usuario autenticado.
     *
     * PUT /api/v1/vehicles/{id}
     *
     * @param id ID del vehículo a actualizar
     * @param request Nuevos datos del vehículo (validados)
     * @param authentication Usuario autenticado
     * @return 200 OK con los datos actualizados
     * @throws RuntimeException 404 NOT FOUND si no existe o no pertenece al usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        VehicleResponse response = vehicleService.update(id, request, authenticatedUserId);

        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar un vehículo.
     * Solo permite eliminar si pertenece al usuario autenticado.
     *
     * DELETE /api/v1/vehicles/{id}
     *
     * @param id ID del vehículo a eliminar
     * @param authentication Usuario autenticado
     * @return 204 NO CONTENT
     * @throws RuntimeException 404 NOT FOUND si no existe o no pertenece al usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        vehicleService.delete(id, authenticatedUserId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Contar vehículos del usuario autenticado.
     *
     * GET /api/v1/vehicles/count
     *
     * @param authentication Usuario autenticado
     * @return 200 OK con el número de vehículos
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVehicles(Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        long count = vehicleService.countVehicles(authenticatedUserId);

        return ResponseEntity.ok(count);
    }

    /**
     * Verificar si existe una placa para el usuario autenticado.
     *
     * GET /api/v1/vehicles/exists/license-plate/{licensePlate}
     *
     * @param licensePlate Placa a verificar
     * @param authentication Usuario autenticado
     * @return 200 OK con true/false
     */
    @GetMapping("/exists/license-plate/{licensePlate}")
    public ResponseEntity<Boolean> existsByLicensePlate(
            @PathVariable String licensePlate,
            Authentication authentication) {

        Long authenticatedUserId = extractUserIdFromAuthentication(authentication);

        boolean exists = vehicleService.existsByLicensePlate(licensePlate, authenticatedUserId);

        return ResponseEntity.ok(exists);
    }

    /**
     * Metodo auxiliar para extraer el ID del usuario desde el objeto Authentication.
     *
     * NOTA: La implementación exacta depende de cómo esté configurado tu JWT.
     * Puede ser que necesites usar UserDetails, o un objeto User personalizado.
     *
     * @param authentication Objeto de autenticación de Spring Security
     * @return ID del usuario autenticado
     */
    private Long extractUserIdFromAuthentication(Authentication authentication) {

        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        return customerDetails.getUserId();

    }
}