package it.gestionalejaclsg.jac.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;

import it.gestionalejaclsg.jac.entity.MeasureUnit;
import it.gestionalejaclsg.jac.service.MeasureService;

@RestController
@RequestMapping("/rest/measure")
public class MeasureRestController {
	private static Logger log = LoggerFactory.getLogger(MeasureRestController.class);
	
	@Autowired
	private MeasureService measureService;
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllProducts() {

		log.info("\n\n\nfind all measures");
		
		return measureService.findAllMeasureUnits();
		
	}
	
	

	
	@PostMapping("/update")
	public Response<?> updateMeasure(@RequestBody String body) {
		
		MeasureUnit m=new MeasureUnit();

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		String tipoM  = body.substring(arr[2] + 1, arr[3]);
		String measureId = body.substring(arr[6] + 1, arr[7]);
		

		m.setId(Integer.parseInt(measureId));
		m.setType(tipoM);
		
		return this.measureService.createMeasureUnit(m);
		
	}
	
	@PostMapping("/create")
	public Response<?> createMeasure(@RequestBody String body) {
		
		MeasureUnit m=new MeasureUnit();

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		int measureId = this.measureService.findLastMeasure().getResult().getId()+1;
		String tipoM = body.substring(arr[2] + 1, arr[3]);
		

		m.setId(measureId);
		m.setType(tipoM);
		
		return this.measureService.createMeasureUnit(m);
		
	}
	
	
	
	@PostMapping(path="/delete")
	public Response<?> deleteMeasureById(@RequestBody String body){
	
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		
		String id = body.substring(arr[2] + 1, arr[3]);
		
		return measureService.deleteMeasureUnitById(Integer.parseInt(id));
	}
	
	
	
	

}
