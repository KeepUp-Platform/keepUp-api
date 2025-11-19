package com.keepup.vehicle.repository;

import com.keepup.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    // Buscar vehículo por placa
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    // Buscar vehículos por usuario
    List<Vehicle> findByUserId(Long userId);

    // Buscar vehículos por marca
    List<Vehicle> findByMake(String make);

    // Buscar vehículos por modelo
    List<Vehicle> findByModel(String model);

    // Buscar vehículos por año
    List<Vehicle> findByYear(Integer year);

    // Buscar vehículos por marca y modelo
    List<Vehicle> findByMakeAndModel(String licensePlate, String model);

    // Verificar si existe un vehículo con esa placa
    boolean existsByLicensePlate(String licensePlate);

    // Buscar vehículos de un usuario ordenados por fecha de creación
    List<Vehicle> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Contar vehículos por usuario
    long countByUserId(Long userId);

    // Query personalizada: buscar vehículos por rango de años
    @Query("SELECT v FROM Vehicle v WHERE v.year BETWEEN :yearStart AND :yearEnd")
    List<Vehicle> findByYearRange(@Param("yearStart") Integer yearStart,
                                  @Param("yearEnd") Integer yearEnd);

    // Query personalizada: buscar vehículos de un usuario por marca
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId AND v.make = :marca")
    List<Vehicle> findByUserIdAndMarca(@Param("userId") Long userId,
                                       @Param("marca") String marca);

    /**
     * Buscar vehículo por ID y verificar que pertenece al usuario.
     * Previene ataques IDOR (Insecure Direct Object Reference).
     */
    Optional<Vehicle> findByIdAndUserId(Long id, Long userId);

    /**
     * Buscar vehículo por placa y verificar que pertenece al usuario.
     */
    Optional<Vehicle> findByLicensePlateAndUserId(String licensePlate, Long userId);

    /**
     * Verificar si existe una placa para un usuario específico.
     * Permite que diferentes usuarios tengan la misma placa.
     */
    boolean existsByLicensePlateAndUserId(String licensePlate, Long userId);

    /**
     * Buscar vehículos por marca, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.make = :make AND v.userId = :userId")
    List<Vehicle> findByMakeAndUserId(@Param("make") String make, @Param("userId") Long userId);

    /**
     * Buscar vehículos por modelo, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.model = :model AND v.userId = :userId")
    List<Vehicle> findByModelAndUserId(@Param("model") String model, @Param("userId") Long userId);

    /**
     * Buscar vehículos por año, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year = :year AND v.userId = :userId")
    List<Vehicle> findByYearAndUserId(@Param("year") Integer year, @Param("userId") Long userId);

    /**
     * Buscar vehículos por rango de años, filtrados por usuario.
     */
    @Query("SELECT v FROM Vehicle v WHERE v.year BETWEEN :yearStart AND :yearEnd AND v.userId = :userId")
    List<Vehicle> findByYearRangeAndUserId(
            @Param("yearStart") Integer yearStart,
            @Param("yearEnd") Integer yearEnd,
            @Param("userId") Long userId
    );

}
