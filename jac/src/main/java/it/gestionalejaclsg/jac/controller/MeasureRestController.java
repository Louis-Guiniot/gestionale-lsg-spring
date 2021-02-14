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
		
		String measureId = body.substring(arr[2] + 1, arr[3]);
		String tipoM = body.substring(arr[6] + 1, arr[7]);
		

		m.setId(Integer.parseInt(measureId));
		m.setType(tipoM);
		
		return this.measureService.createMeasureUnit(m);
		
	}
	
	
	
	@PostMapping(path="/delete")
	public Response<?> deleteMeasureById(@RequestBody String body){
	
		
		log.info("\n\n\n\nbody: " + body + "\n\n\n"+"eliminazione\n\n\n");
		int pPartenza=0;
		int pArrivo=0;
		
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == ':') {
				pPartenza = i+1;
			}
			if(body.charAt(i)=='}') {
				pArrivo=i;
			}
		}
		String id = body.substring(pPartenza,pArrivo);
		
		return measureService.deleteMeasureUnitById(Integer.parseInt(id));
	}
	
	
	@PostMapping("/update")
	public Response<?> updateMeasure(@RequestBody String body){
		log.info("\n\n\n\nbody: " + body + "\n\n\n"+"update\n\n\n");
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		String id = body.substring(arr[2] + 1, arr[3]);
		log.info("id: "+id);
		
		
		String tipoM = body.substring(arr[6] + 1, arr[7]);
		log.info("tipo: "+tipoM);
		if(tipoM.equals("")) {
			tipoM=measureService.findMeasureUnitById(Integer.parseInt(id)).getResult().getType();
		}
		
		return measureService.updateMeasureUnit(Integer.parseInt(id), tipoM);
	
	}
	

}
