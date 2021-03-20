package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.ProductDTO;
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
	
	
	
	@PostMapping(path="/findById")
	public Response<?> findProductById(@RequestBody String body){
		
	
		
		
		log.info("\n\n\n\nbody: " + body + "\n\n\n"+"find\n\n\n");
        
        String id = body.substring(0,1);
        
        log.info("\n\n\nidFind: " + id);
		return productService.findProductById(Integer.parseInt(id));
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
		return productService.deleteProductById(Integer.parseInt(id));
	}
	
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
		String description = body.substring(arr[2] + 1, arr[3]);
		String measureUnit = body.substring(arr[6] + 1, arr[7]);
		String name = body.substring(arr[10] + 1, arr[11]);
		String price = body.substring(arr[14] + 1, arr[15]);
		String sconto = body.substring(arr[18] + 1, arr[19]);
		String iva= body.substring(arr[22] + 1, arr[23]);
		log.info("\ndescription: "+ description +"\n" + "measure unit: " + measureUnit+"\nname "+name+"\nprice: "+price);
		
		//incrementazione automatica
		ProductDTO lastProduct=productService.findLastProduct().getResult();
		String codeStringed=lastProduct.getCodeProduct();
		log.info("\n\ncodeStringed: "+codeStringed);
		//A1
		String codeNumberPartStringed=codeStringed.substring(1,2);
		
		log.info("\n\nla parte di numero del codice è: "+codeNumberPartStringed);
		
		int codeNumberPartInteger=0;
		String codeAlphaPartStringed=codeStringed.substring(0,1);
		
		log.info("\n\ncodeAlphaPartStringed: "+codeAlphaPartStringed);
		
		//se arriva a A999 il successivo sarà B1
		char c;
		if(Integer.parseInt(codeNumberPartStringed)+1<1000) {
			codeNumberPartInteger=Integer.parseInt(codeNumberPartStringed)+1;
		}else {
			codeNumberPartInteger=1;
			c=codeAlphaPartStringed.charAt(0);
			int asciiC=(int)c+1;
			codeAlphaPartStringed=Character.toString((char)asciiC);
		}
		
		
		
		String automaticCode=codeAlphaPartStringed+codeNumberPartInteger;
		
		product.setCodeProduct(automaticCode);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setMeasureUnit(measureUnit);
		log.info("measure unit: "+measureUnit);
		product.setScontoProd(sconto);
		int id=measureService.findMeasureIdByMeasureUnit(measureUnit);
		log.info("measure unit ID: "+id);
		product.setMeasureUnit_id(id);
		product.setIva(Integer.parseInt(iva));
		
		return productService.createProduct(product);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllProducts() {

		log.info("Ricevuta richiesta della lista di tutti i prodotti");
		
		return productService.findAllProducts();
		
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
		String description = body.substring(arr[6] + 1, arr[7]);
		
		if(description.equals("")) {
			description=productService.findProductById(Integer.parseInt(id)).getResult().getDescription();
		}
		
		String measureUnit = body.substring(arr[10] + 1, arr[11]);
		if(measureUnit.equals("")) {
			measureUnit=productService.findProductById(Integer.parseInt(id)).getResult().getMeasureUnit();
		}
		String name = body.substring(arr[14] + 1, arr[15]);
		if(name.equals("")) {
			name=productService.findProductById(Integer.parseInt(id)).getResult().getName();
		}
		String price= body.substring(arr[18]+1, arr[19]);
		if(price.equals("")) {
			price=productService.findProductById(Integer.parseInt(id)).getResult().getPrice();
		}
		String sconto=body.substring(arr[22]+1, arr[23]);
		if(sconto.equals("")) {
			sconto=productService.findProductById(Integer.parseInt(id)).getResult().getScontoProd();
		}
		return productService.updateProduct(Integer.parseInt(id),description, measureUnit, name,price,sconto);
	
	}
	

	
}
