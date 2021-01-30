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
import it.gestionalejaclsg.jac.entity.Customer;
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
	public Response<?> createCustomer(@RequestBody String body) {
		

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		String email = body.substring(arr[2] + 1, arr[3]);
		String name = body.substring(arr[6] + 1, arr[7]);
		String surname = body.substring(arr[10] + 1, arr[11]);
		
		Customer c=new Customer();
		c.setEmail(email);
		c.setName(name);
		c.setSurname(surname);
		return customerService.createCustomer(c);
	}
	
	@PostMapping("/delete")
	public Response<?> deleteCustomer(@RequestBody String body){
		
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
		log.info("\n\n\n\nbody: " + id + "\n\n\n"+"eliminazione\n\n\n");
		return customerService.deleteCustomerById(Integer.parseInt(id));
	}
	
	@PostMapping("/update")
	public Response<?> updateCustomer(@RequestBody String body){
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
		String name = body.substring(arr[6] + 1, arr[7]);
		if(name.equals("")) {
			name=customerService.findCustomerById(Integer.parseInt(id)).getResult().getName();
		}
		String surname = body.substring(arr[10] + 1, arr[11]);
		if(surname.equals("")) {
			surname=customerService.findCustomerById(Integer.parseInt(id)).getResult().getSurname();
		}
		String email = body.substring(arr[14] + 1, arr[15]);
		if(email.equals("")) {
			email=customerService.findCustomerById(Integer.parseInt(id)).getResult().getEmail();
		}
		
		return customerService.updateUser(Integer.parseInt(id), name, surname, email);
	
	}

}
