package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Invoice;
import it.gestionalejaclsg.jac.service.InvoiceService;

@RestController
@RequestMapping("/rest/invoice")
public class InvoiceRestController {
	private static Logger log = LoggerFactory.getLogger(InvoiceRestController.class);
	
	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/create")
	public Response<?> createInvoice(@RequestBody String body){
		
		
		
		//
		
		
		log.info("\n\n\n\nbody: "+body+"\n\n\n");
		
		Invoice invoice= new Invoice();
		
		
		return null;
		//return this.invoiceService.createInvoice(invoice);
	}
}
