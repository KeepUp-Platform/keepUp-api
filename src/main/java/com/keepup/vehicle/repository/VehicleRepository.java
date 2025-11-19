package com.keepup.vehicle.repository;

import com.keepup.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // ==================== MÉTODOS CON SEGURIDAD POR USUARIO (USADOS EN VehicleService) ====================
    // Estos son los que usa tu VehicleServiceImpl con authenticatedUserId

    /**
     * Buscar vehículo por ID y verificar que pertenece al usuario.
     * Previene ataques IDOR (Insecure Direct Object Reference).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.id = :id AND v.user.id = :userId")
    Optional<Vehicle> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * Buscar vehículo por placa y verificar que pertenece al usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.licensePlate = :licensePlate AND v.user.id = :userId")
    Optional<Vehicle> findByLicensePlateAndUserId(
            @Param("licensePlate") String licensePlate,
            @Param("userId") Long userId
    );

    /**
     * Verificar si existe una placa para un usuario específico.
     * Permite que diferentes usuarios tengan la misma placa.
     */
    @Query("SELECT COUNT(v) > 0 FROM Vehicle v WHERE v.licensePlate = :licensePlate AND v.user.id = :userId")
    boolean existsByLicensePlateAndUserId(
            @Param("licensePlate") String licensePlate,
            @Param("userId") Long userId
    );

    /**
     * Buscar vehículos por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId")
    List<Vehicle> findByUserId(@Param("userId") Long userId);

    /**
     * Buscar vehículos por marca, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.make = :make AND v.user.id = :userId")
    List<Vehicle> findByMakeAndUserId(@Param("make") String make, @Param("userId") Long userId);

    /**
     * Buscar vehículos por modelo, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.model = :model AND v.user.id = :userId")
    List<Vehicle> findByModelAndUserId(@Param("model") String model, @Param("userId") Long userId);

    /**
     * Buscar vehículos por año, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year = :year AND v.user.id = :userId")
    List<Vehicle> findByYearAndUserId(@Param("year") Integer year, @Param("userId") Long userId);

    /**
     * Buscar vehículos por rango de años, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year BETWEEN :yearStart AND :yearEnd AND v.user.id = :userId")
    List<Vehicle> findByYearRangeAndUserId(
            @Param("yearStart") Integer yearStart,
            @Param("yearEnd") Integer yearEnd,
            @Param("userId") Long userId
    );

    /**
     * Contar vehículos por usuario.
     */
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    // ==================== MÉTODOS GENERALES (Sin filtro de usuario) ====================
    // Útiles para búsquedas administrativas o reportes generales

    /**
     * Buscar vehículo por placa (sin filtro de usuario).
     */
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    /**
     * Verificar si existe un vehículo con esa placa (sin filtro de usuario).
     */
    boolean existsByLicensePlate(String licensePlate);

    /**
     * Buscar vehículos por marca (sin filtro de usuario).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.make = :make")
    List<Vehicle> findByMake(@Param("make") String make);

    /**
     * Buscar vehículos por modelo (sin filtro de usuario).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.model = :model")
    List<Vehicle> findByModel(@Param("model") String model);

    /**
     * Buscar vehículos por año (sin filtro de usuario).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year = :year")
    List<Vehicle> findByYear(@Param("year") Integer year);

    /**
     * Buscar vehículos por rango de años (sin filtro de usuario).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year BETWEEN :yearStart AND :yearEnd")
    List<Vehicle> findByYearRange(
            @Param("yearStart") Integer yearStart,
            @Param("yearEnd") Integer yearEnd
    );

    // ==================== MÉTODOS ADICIONALES ÚTILES ====================

    /**
     * Buscar vehículos de un usuario ordenados por fecha de creación (más recientes primero).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId ORDER BY v.createdAt DESC")
    List<Vehicle> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    /**
     * Buscar vehículos por marca y modelo (sin filtro de usuario).
     */
    @Query("SELECT v FROM Vehicle v WHERE v.make = :make AND v.model = :model")
    List<Vehicle> findByMakeAndModel(
            @Param("make") String make,
            @Param("model") String model
    );

    /**
     * Buscar vehículos de un usuario por marca.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId AND v.make = :marca")
    List<Vehicle> findByUserIdAndMarca(
            @Param("userId") Long userId,
            @Param("marca") String marca
    );
}