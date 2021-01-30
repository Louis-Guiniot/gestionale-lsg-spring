package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;
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
	

}
