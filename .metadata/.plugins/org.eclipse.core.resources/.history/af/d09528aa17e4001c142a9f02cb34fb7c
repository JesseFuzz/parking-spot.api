package com.api.parkingcontrol.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.parkingcontrol.controller.Optional;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {
	
	//@Autowired
	//ParkingSpotRepository parkingSpotRepository; //uso o autowired para que eu possa criar uma variável do meu repositório nessa classe e para que eu diga para o spring que em alguns momentos ele terá que injetar algumas dependencias dentro do repository nessa classe
	
	//poderia usar um construtor para fazer isso:
	
	final ParkingSpotRepository parkingSpotRepository;
	
	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		this.parkingSpotRepository = parkingSpotRepository;
	}
	
	@Transactional //pra que as transações ocorram corretamente 
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		// TODO Auto-generated method stub
		return parkingSpotRepository.save(parkingSpotModel);
	}

	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	public List<ParkingSpotModel> findAll() {
		return parkingSpotRepository.findAll();
	}

	public java.util.Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id); //esse método retorna um Optional, por isso tive que criar lá no método do Controller 
	}
	@Transactional //pra que as transações ocorram corretamente (casos com relações JPA, um pra muitos, muitos pra muitos) caso dê errado tenha Rollback
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);
	}
	
	//public void getById() {
		//return parkingSpotRepository.findById(@RequestBody UUID id);
	//}
	
}
