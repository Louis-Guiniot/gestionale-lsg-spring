package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
import it.gestionalejaclsg.jac.service.InvoiceService;
import it.gestionalejaclsg.jac.service.ProductHasInvoiceService;

@Controller
@RequestMapping("/rest/producthasinvoice")
public class ProductHasInvoiceRestController {
	
	@Autowired
	private ProductHasInvoiceService productHasInvoiceService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	private static Logger log = LoggerFactory.getLogger(ProductHasInvoiceRestController.class);
	
	@PostMapping("/findByIdInvoice")
	public Response<?> findByIdInvoice(@RequestBody String body){
		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
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
	
	
	@PostMapping("/delete")
	public Response<?> deletePhiByPhi(String body){
		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
				
		}
		
		ProductHasInvoice phi=new ProductHasInvoice();
		
		String productId = body.substring(arr[2] + 1, arr[3]);
		String quantity = body.substring(arr[6] + 1, arr[7]);
	
		
		phi.setInvoiceId(invoiceService.findLastInvoice().getResult().getId()+1+"");
		phi.setProductId(productId);
		phi.setQuantity(quantity);
		
		return productHasInvoiceService.deleteProductHasInvoiceByPhi(phi);
		
	}
	
	@PostMapping("/create")
	public Response<?> createPhi(String body){
		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
				
		}
		
		ProductHasInvoice phi=new ProductHasInvoice();
		
		String productId = body.substring(arr[2] + 1, arr[3]);
		String quantity = body.substring(arr[6] + 1, arr[7]);
		
		phi.setInvoiceId(invoiceService.findLastInvoice().getResult().getId()+1+"");
		phi.setProductId(productId);
		phi.setQuantity(quantity);
		
		return productHasInvoiceService.createProductHasInvoice(phi);
	
		
	}
}
