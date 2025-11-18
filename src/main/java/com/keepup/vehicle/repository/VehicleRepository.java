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
    Optional<Vehicle> findByLicensePlate(String placa);

    // Buscar vehículos por usuario
    List<Vehicle> findByUserId(Long userId);

    // Buscar vehículos por marca
    List<Vehicle> findByMake(String marca);

    // Buscar vehículos por modelo
    List<Vehicle> findByModel(String modelo);

    // Buscar vehículos por año
    List<Vehicle> findByYear(Integer anio);

    // Buscar vehículos por marca y modelo
    List<Vehicle> findByMakeAndModel(String marca, String modelo);

    // Verificar si existe un vehículo con esa placa
    boolean existsByLicensePlate(String placa);

    // Buscar vehículos de un usuario ordenados por fecha de creación
    List<Vehicle> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Contar vehículos por usuario
    long countByUserId(Long userId);

    // Query personalizada: buscar vehículos por rango de años
    @Query("SELECT v FROM Vehicles v WHERE v.year BETWEEN :yearStart AND :yearEnd")
    List<Vehicle> findByYearRange(@Param("yearStart") Integer yearStart,
                                  @Param("yearEnd") Integer yearEnd);

    // Query personalizada: buscar vehículos de un usuario por marca
    @Query("SELECT v FROM Vehicles v WHERE v.user.id = :userId AND v.make = :marca")
    List<Vehicle> findByUserIdAndMarca(@Param("userId") Long userId,
                                       @Param("marca") String marca);


}
