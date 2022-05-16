package com.api.parkingcontrol.service;

import org.springframework.stereotype.Service;

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
	
}