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
import it.gestionalejaclsg.jac.service.CustomerService;

@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController {
	
	private static Logger log = LoggerFactory.getLogger(CustomerRestController.class);
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllProducts() {

		log.info("Ricevuta richiesta della lista di tutti i prodotti");
		
		return customerService.findAllCustomers();
		
	}
	
	@PostMapping("/create")
	public Response<?> createProduct(@RequestBody String body) {
		
		log.info("body customer -->"+body);
		
		return null;
	}

}
