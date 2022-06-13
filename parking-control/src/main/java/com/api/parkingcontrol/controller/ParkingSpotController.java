package com.api.parkingcontrol.controller;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Id;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping
	public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page=0, size=10, sort="id", direction=Sort.Direction.ASC) org.springframework.data.domain.Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable)); //ñ passei msg  no body pois caso não tenha irá retornar uma lista vazia
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value="id") UUID id){ //usei ResponseEntity<Object> ao invés de List prq caso n exista esse registro do ID ele vai retornar que não existe esse objeto
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
 		if(!parkingSpotModelOptional.isPresent()) { //a exclamação significa NÃO "se parkingSpotModelOptional não estiver presente retorne..."
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A vaga não foi achada!");
 		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}
	
	@GetMapping("/plate/{licensePlateCar}")
	public ResponseEntity<Object> getLicensePlateCar(@PathVariable String licensePlateCar){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findByLicensePlateCar(licensePlateCar);
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value="id")UUID id){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
 		if(!parkingSpotModelOptional.isPresent()) { //a exclamação significa NÃO "se parkingSpotModelOptional não estiver presente retorne..."
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A vaga não foi achada!");
 		}
 		parkingSpotService.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("essa vaga foi deletada com sucesso" );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable(value="id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
		Optional<ParkingSpotModel> parkingSpotModelOptional = null; //eu que coloquei o de cima foi Michelli e o null foi pra inicializar (ide sugeriu inicializar)
		if(!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("essa vaga não foi encontrada!");
		}
		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
		parkingSpotModel.setRegistrationDate(parkingSpotModel.get().getRegistrationDate());
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
		
		
		
//		var parkingSpotModel parkingSpotModel = parkingSpotModelOptional.get();
//		parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
//		parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
//		parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
//		parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
//		parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
//		parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
//		parkingSpotModel.setApartment(parkingSpotDto.getApartment());
//		parkingSpotModel.setBlock(parkingSpotDto.getBlock());
	}
		
}
