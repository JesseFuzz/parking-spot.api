package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.service.ParkingSpotService;

@RestController
@RequestMapping("/parking-spot")
@CrossOrigin(origins="*", maxAge=3600)// para permitir que seja acessado de qualquer fonte
public class ParkingSpotController {
	
	//@Autowired
	//ParkingSpotService parkingSpotService;
	
	//Usarei um constructor para fazer essa injeção, mas agora será do service para o controller e não do repository para o service
	
	final ParkingSpotService parkingSpotService;
	
	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){ //@Valid por conta do @NotBlank do DTO e o @RequestBody é porq receberá um Json
		
		if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
		}
		if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
		}
		if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
		}
		var parkingSpotModel = new ParkingSpotModel(); //uma instância (usando var) de SpotModel a ser convertida 
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); //converte o SpotDTO em SpotModel
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));//seta a data de registro que ta no controller, não no DTO
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));//retorna o status http e no body o método save do Service com o SpotModel de parâmetro
		
	}
	
	
	
}
