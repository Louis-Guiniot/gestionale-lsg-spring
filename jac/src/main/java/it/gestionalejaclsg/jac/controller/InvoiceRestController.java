package it.gestionalejaclsg.jac.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Invoice;
import it.gestionalejaclsg.jac.service.InvoiceService;
import it.gestionalejaclsg.jac.service.ProductService;

@RestController
@RequestMapping("/rest/invoice")
public class InvoiceRestController {
	private static Logger log = LoggerFactory.getLogger(InvoiceRestController.class);

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ProductService productService;

	@SuppressWarnings("deprecation")
	@PostMapping("/create")
	public Response<?> createInvoice(@RequestBody String body) {
		
		Invoice invoice=new Invoice();

		//body: {"custId":"1","payCondition":"payment","docType":"doc","sale":"","articles":"1","taxable":"tax"}

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		boolean flag=true;
		String customerId = body.substring(arr[2] + 1, arr[3]);
		String paymentCondition = body.substring(arr[6] + 1, arr[7]);
		String docType = body.substring(arr[10] + 1, arr[11]);
		String sconto;
		if((body.substring(arr[14]+1,arr[15])).equals("")) {
			sconto="0";
			flag=false;
		}else {
			sconto=body.substring(arr[14]+1,arr[15]);
			
		}
		String aricles=body.substring(arr[18] + 1, arr[19]);
		String taxable=body.substring(arr[22] + 1, arr[23]);
		Calendar calndr = Calendar.getInstance();
		
		invoice.setDateTime(calndr.getTime().toString());
		
		
		invoice.setCustomer_id(Integer.parseInt(customerId));
		invoice.setIdCustomer(customerId);
		
		
		invoice.setCondizionePagamento(paymentCondition);
		invoice.setTipoDocumento(docType);
		invoice.setSconto(sconto);
		invoice.setFields(aricles);
		invoice.setImponibile(taxable);
		log.info("\n\n inizio quantita \n\n");
		int conta2 = 0; //è la quantita
		int[] arr2 = new int[aricles.length()];
		for (int i = 0; i < aricles.length(); i++) {
			if (body.charAt(i) == ';') {
				arr2[conta2] = i;
				conta2++;
			}
		}
		log.info("\n\n dine qnt \n\n");
		
		invoice.setIva(22+"");
		invoice.setQuantita(conta2+"");
		log.info("\n\n codice fattura \n\n");
		String codeStr=invoiceService.findLastInvoice().getResult().getCodeInvoice().substring(4,5)+1 ; //20211 - .substring(4,5))+1
		log.info("\n\n CODICE STR "+codeStr+"\n\n");
		int codeInt=Integer.parseInt(codeStr);
		invoice.setCodeInvoice(calndr.getTime().getYear()+""+codeInt); //es cod 20211-20212-20213
		log.info("\n\n inizio splits \n\n");
		String[] arrArt=aricles.split(";");
		
		//ciclo somma prezzi stonks
		log.info("\n\n inizio ciclo somma \n\n");
		double sommaPrices=0;
		for(int i=0; i<arrArt.length; i++) {
			sommaPrices=sommaPrices+Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getPrice()) ;
		}
		invoice.setTotalPrice(sommaPrices+"");
		invoice.setIvaPrice((sommaPrices*0.22)+"");
		invoice.setImponibile((sommaPrices*0.22)+"");
		invoice.setTotaleMerci((sommaPrices+sommaPrices*0.22)+"");
		if(flag==false) {
			invoice.setTotalToPay(sommaPrices+(sommaPrices*0.22)+"");
		}else {
			double saldo=Double.parseDouble(sconto)/100*sommaPrices;
			invoice.setImportoSconto(saldo+"");
			invoice.setTotalToPay((sommaPrices-saldo)+(sommaPrices*0.22)+"");
		}
		invoice.setQuantita(arrArt.length+"");
		
		
		return this.invoiceService.createInvoice(invoice);
		
	}
	
	
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllInvoices() {

		log.info("Ricevuta richiesta della lista di tutti i prodotti");
		
		return invoiceService.findAllInvoices();
		
	}
	
	@GetMapping(path = "/findLast")
	public Response<?> findLastInvoice() {

		log.info("\n\n\nRicevuta richiesta della lista di tutti i prodotti\n\n\n");
		
		return invoiceService.findLastInvoice();
		
	}
	
	
	
	
	
	
}
