package it.gestionalejaclsg.jac.controller;


import java.util.Random;

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
	public Response<?> createInvoice(@RequestBody String body) {

		//

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		String lista = body.substring(arr[2] + 1, arr[3]);
		String price = body.substring(arr[6] + 1, arr[7]);
		String customerId = body.substring(arr[10] + 1, arr[11]);
		price = price.substring(0, price.length() - 1);
		int conta2 = 0;
		int[] array = new int[price.length()];
		for (int i = 0; i < price.length(); i++) {
			if (price.charAt(i) == ';') {
				array[conta2] = i;
				conta2++;
			}
		}
		log.info("CONTA 2 VALE: " + conta2);
		String[] pricesArrayString = price.split(";", conta2 + 1);

		log.info("inizio ciclo di stampa prezzi:");
		for (int i = 0; i < pricesArrayString.length; i++) {
			log.info(pricesArrayString[i]);
		}

		log.info("ciclo di conversione");
		int[] pricesArrayInt = new int[pricesArrayString.length];
		for (int i = 0; i < pricesArrayString.length; i++) {
			pricesArrayInt[i] = Integer.parseInt(pricesArrayString[i]);
		}

		log.info("ciclo di somma");
		int finalPrice = 0;
		for (int i = 0; i < pricesArrayInt.length; i++) {
			finalPrice += pricesArrayInt[i];
		}
		
		
		//generazione codice casuale
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    //fine coidce casuale
	    
	    
		log.info("final price: " + finalPrice);
		log.info("creazione invoice");

		
		String str = "";
		Invoice invoice = new Invoice();
		
		invoice.setTotalPrice(str + finalPrice);
		invoice.setFields(lista);
		invoice.setIdCustomer(customerId);
		invoice.setCodeInvoice(generatedString);
		
		return this.invoiceService.createInvoice(invoice);
	}
}
