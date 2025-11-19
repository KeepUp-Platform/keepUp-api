package com.keepup.vehicle.service;

import com.keepup.vehicle.dto.VehicleRequest;
import com.keepup.vehicle.dto.VehicleResponse;

import java.util.List;


public interface VehicleService {


    VehicleResponse create(VehicleRequest request);

    VehicleResponse getById(Long id);

    List<VehicleResponse> getAll();

    List<VehicleResponse> getByUserId(Long userId);

    VehicleResponse getByLicensePlate(String licensePlate);

    List<VehicleResponse> getByMake(String make);

    List<VehicleResponse> getByModel(String model);

    List<VehicleResponse> getByYear(Integer year);

    List<VehicleResponse> getByYearRange(Integer yearStart, Integer yearEnd);

    VehicleResponse update(Long id, VehicleRequest request);

    void delete(Long id);

    long countByUserId(Long userId);

    boolean existsByLicensePlate(String licensePlate);
}