package com.api.parkingcontrol.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.model.ParkingSpotModel;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{ //passo o model (entity) e passo o tipo do id
	
	public boolean existsByLicensePlateCar(String licensePlateCar);
	public boolean existsByParkingSpotNumber(String parkingSpotNumber);
	public boolean existsByApartmentAndBlock(String apartment, String block);
	public Optional<ParkingSpotModel> findByLicensePlateCar(String plate);
}

