package com.keepup.vehicle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Placa del carro
    @Column(nullable = false, unique = true, length = 20)
    private String licensePlate;


    //Marca del vehículo (ej: Toyota, Honda, Chevrolet)
    @Column(nullable = false, length = 50)
    private String make;

    //Modelo del vehículo (ej: Corolla, Civic, Spark)
    @Column(nullable = false, length = 50)
    private String model;

    //Año de fabricación del vehículo
    @Column(nullable = false)
    private Integer year;

    //Color del vehiculo
    @Column(length = 30)
    private String color;


    // Campo para la fecha de creación
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Campo para la fecha de última modificación
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relación con la entidad User
    @ManyToOne(fetch = FetchType.LAZY) // Se recomienda LAZY para ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    /**
     * Metodo que se ejecuta automaticamente antes de persistir la entidad
     * Establece la fecha de creación y actualizacion
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Metodo que se ejecuta automaticamente antes de actualizar la entidad
     * Actualiza solo la fecha de ultima modificación
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
