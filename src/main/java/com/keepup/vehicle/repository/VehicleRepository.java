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

    Optional<Vehicle> findByPlaca(String placa);

    // Buscar vehículos por usuario
    List<Vehicle> findByUserId(Long userId);

    // Buscar vehículos por marca
    List<Vehicle> findByMarca(String marca);

    // Buscar vehículos por modelo
    List<Vehicle> findByModelo(String modelo);

    // Buscar vehículos por año
    List<Vehicle> findByAnio(Integer anio);

    // Buscar vehículos por marca y modelo
    List<Vehicle> findByMarcaAndModelo(String marca, String modelo);

    // Verificar si existe un vehículo con esa placa
    boolean existsByPlaca(String placa);

    // Buscar vehículos de un usuario ordenados por fecha de creación
    List<Vehicle> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Contar vehículos por usuario
    long countByUserId(Long userId);

    // Query personalizada: buscar vehículos por rango de años
    @Query("SELECT v FROM Vehicle v WHERE v.anio BETWEEN :yearStart AND :yearEnd")
    List<Vehicle> findByYearRange(@Param("yearStart") Integer yearStart,
                                  @Param("yearEnd") Integer yearEnd);

    // Query personalizada: buscar vehículos de un usuario por marca
    @Query("SELECT v FROM Vehicle v WHERE v.user.id = :userId AND v.marca = :marca")
    List<Vehicle> findByUserIdAndMarca(@Param("userId") Long userId,
                                       @Param("marca") String marca);


}
