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
import it.gestionalejaclsg.jac.entity.Product;
import it.gestionalejaclsg.jac.service.MeasureService;
import it.gestionalejaclsg.jac.service.ProductService;


@RestController
@RequestMapping("/rest/product")
public class ProductRestController {
	private static Logger log = LoggerFactory.getLogger(ProductRestController.class);
	
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private MeasureService measureService;
	
	
	@PostMapping("/create")
	public Response<?> createProduct(@RequestBody String body) {

		/*
		 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ + json
		 * per body 
		 * {"code":"code",
		 * "description":"description",
		 * "measure_unit":"measure_unit",
		 * "name":"name",
		 * "price":"price"}
		 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 */
		
		//da fare : controlli su integrità dati, con catch per gli errori

		log.info("Ricevuta richiesta di creazione nuovo product");

		Product product = new Product();
		log.info("\n\n\n\nQUESTO è IL BODY \n\n\n\n" + body + "\n\n\n\n\n");
		

		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		String code = body.substring(arr[2] + 1, arr[3]);
		String description = body.substring(arr[6] + 1, arr[7]);
		String measureUnit = body.substring(arr[10] + 1, arr[11]);
		String name = body.substring(arr[14] + 1, arr[15]);
		String price = body.substring(arr[18] + 1, arr[19]);
		log.info("code: " + code + "\ndescr: " + description +"\n" + "measure unit: " + measureUnit+"\nname "+name+"\nprice: "+price);
		
		product.setCodeProduct(code);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setMeasureUnit(measureUnit);
		
		product.setMeasureUnit_id(measureService.findMeasureIdByMeasureUnit(measureUnit));
		
		return productService.createProduct(product);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllProducts() {

		log.info("Ricevuta richiesta della lista di tutti i prodotti");
		
		return productService.findAllProducts();
		
	}
	
//	@PostMapping("/update")
//	public Response<?> updateCustomer(@RequestBody String body){
//		log.info("\n\n\n\nbody: " + body + "\n\n\n"+"update\n\n\n");
//		int conta = 0;
//		int[] arr = new int[body.length()];
//		for (int i = 0; i < body.length(); i++) {
//			if (body.charAt(i) == '"') {
//				arr[conta] = i;
//				conta++;
//			}
//		}
//		String id = body.substring(arr[2] + 1, arr[3]);
//		String name = body.substring(arr[6] + 1, arr[7]);
//		if(name.equals("")) {
//			name=productService.findCustomerById(Integer.parseInt(id)).getResult().getName();
//		}
//		String surname = body.substring(arr[10] + 1, arr[11]);
//		if(surname.equals("")) {
//			surname=customerService.findCustomerById(Integer.parseInt(id)).getResult().getSurname();
//		}
//		String email = body.substring(arr[14] + 1, arr[15]);
//		if(email.equals("")) {
//			email=customerService.findCustomerById(Integer.parseInt(id)).getResult().getEmail();
//		}
//		
//		return customerService.updateUser(Integer.parseInt(id), name, surname, email);
//	
//	}
	

	
}
