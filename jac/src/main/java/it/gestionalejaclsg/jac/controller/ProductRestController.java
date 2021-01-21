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
import it.gestionalejaclsg.jac.service.ProductService;


@RestController
@RequestMapping("/rest/product")

public class ProductRestController {
	private static Logger log = LoggerFactory.getLogger(ProductRestController.class);
	
	
	@Autowired
	ProductService productService;
	
	
	@PostMapping("/create")
	public Response<?> createProduct(@RequestBody String body) {

		/*
		 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ + json
		 * per body ++ + {"phoneNumber":"9876543210",
		 * "name":"gianni","surname":"andimor"}+
		 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 */

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
		String number = body.substring(arr[2] + 1, arr[3]);
		String name = body.substring(arr[6] + 1, arr[7]);
		String surname = body.substring(arr[10] + 1, arr[11]);

		log.info("number: " + number + "\nname: " + name + "\n" + "surname: " + surname);
		
//		product.setCodeProduct(codeProduct);
//		product.setName(name);
//		product.setDescription(description);
//		product.setPrice(price);
//		product.setMeasureUnit(measureUnit);
		return productService.createProduct(product);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllQuestions() {

		log.info("Ricevuta richiesta della lista di tutti i prodotti");
		
		return productService.findAllProducts();
		
	}
	

	
}
