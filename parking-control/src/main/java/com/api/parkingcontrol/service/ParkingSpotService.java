package com.api.parkingcontrol.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		// TODO Auto-generated method stub
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		// TODO Auto-generated method stub
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}
	
}
