package com.keepup.vehicle.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 20)
    private String placa;


    //Marca del vehículo (ej: Toyota, Honda, Chevrolet)
    @Column(nullable = false, length = 50)
    private String marca;

    //Modelo del vehículo (ej: Corolla, Civic, Spark)
    @Column(nullable = false, length = 50)
    private String modelo;

    //Año de fabricación del vehículo
    @Column(nullable = false)
    private Integer anio;


    @Column(length = 30)
    private String color;


    /**
     * Método que se ejecuta automáticamente antes de persistir la entidad
     * Establece la fecha de creación y actualización
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Método que se ejecuta automáticamente antes de actualizar la entidad
     * Actualiza solo la fecha de última modificación
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
