package it.gestionalejaclsg.jac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gestionalejaclsg.jac.dto.Response;

import it.gestionalejaclsg.jac.service.ProductHasInvoiceService;

@Controller
@RequestMapping("/rest/producthasinvoice")
public class ProductHasInvoiceRestController {
	
	@Autowired
	private ProductHasInvoiceService productHasInvoiceService;
	
	@PostMapping("/findByIdInvoice")
	public Response<?> findByIdInvoice(@RequestBody String body){
		
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
				
		}
		String termine = body.substring(arr[2] + 1, arr[3]);
		
		int id=Integer.parseInt(termine);
		return productHasInvoiceService.findProductHasInvoiceById(id);
	}

}
