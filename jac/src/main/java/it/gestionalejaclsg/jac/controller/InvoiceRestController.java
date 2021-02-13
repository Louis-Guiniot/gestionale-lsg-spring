package it.gestionalejaclsg.jac.controller;

import java.util.Calendar;


import org.json.simple.parser.ParseException;
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
		//String sconto;
//		if((body.substring(arr[14]+1,arr[15])).equals("")) {
//			sconto="0";
//			flag=false;
//		}else {
//			sconto=body.substring(arr[14]+1,arr[15]);
//			
//		}
		String aricles=body.substring(arr[18] + 1, arr[19]);
		//String taxable=body.substring(arr[22] + 1, arr[23]);
		Calendar calndr = Calendar.getInstance();
		
		invoice.setDateTime(calndr.getTime().toString());
		
		
		invoice.setCustomer_id(Integer.parseInt(customerId));
		invoice.setIdCustomer(customerId);
		
		
		invoice.setCondizionePagamento(paymentCondition);
		invoice.setTipoDocumento(docType);
		//invoice.setSconto(sconto);
		invoice.setFields(aricles);
		//invoice.setImponibile(taxable);
		log.info("\n\n inizio quantita \n\n");
		int conta2 = 0; //è la quantita
		int[] arr2 = new int[aricles.length()];
		for (int i = 0; i < aricles.length(); i++) {
			if (body.charAt(i) == ';') {
				arr2[conta2] = i;
				conta2++;
			}
		}
		log.info("\n\n fine qnt \n\n");
		
		invoice.setIva(22+"");
		invoice.setQuantita(conta2+"");
		
		
		
		//CREAZIONE CODICE
		
		log.info("\n\n codice fattura \n\n");
		String codeStr= "";
		if(invoiceService.findLastInvoice().getResult().getCodeInvoice().equals("null")) {
			codeStr=Calendar.YEAR+"1";
			log.info("calendar: "+Calendar.YEAR);
		}else {
			codeStr=invoiceService.findLastInvoice().getResult().getCodeInvoice() ; //20211 - .substring(4,5))+1
		}
		log.info("\n\n CODICE STR "+codeStr+"\n\n");
		int codeInt=Integer.parseInt(codeStr)+1;
		
		invoice.setCodeInvoice(codeInt+""); //es cod 20211-20212-20213
		log.info("\n\n inizio splits \n\n");
		String[] arrArt=aricles.split(";");
		
		log.info("articolo 1: "+arrArt[0]);
		
		
		//ciclo somma prezzi stonks
		log.info("\n\n inizio ciclo somma \n\n");
		double sommaPrices=0;
		for(int i=0; i<arrArt.length; i++) {
			sommaPrices=sommaPrices+Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getPrice()) ;
		}
		double sommaSconti=0;
		for(int i=0; i<arrArt.length; i++) {
			sommaSconti=sommaSconti+Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd()) ;
		}
		if(sommaSconti>50) {
			sommaSconti=50;
		}
		invoice.setSconto((sommaPrices*sommaSconti/100)+"");
		invoice.setTotalPrice(sommaPrices+"");
		invoice.setIvaPrice((sommaPrices*0.22)+"");
		invoice.setImponibile((sommaPrices*0.22)+"");
		invoice.setTotaleMerci((sommaPrices+sommaPrices*0.22)+"");
		if(flag==false) {
			invoice.setTotalToPay(sommaPrices+(sommaPrices*0.22)+"");
		}else {
			double saldo=(sommaSconti)/100*sommaPrices;
			invoice.setImportoSconto(saldo+"");
			invoice.setTotalToPay((sommaPrices-saldo)+(sommaPrices*0.22)+"");
		}
		invoice.setQuantita(arrArt.length+"");
		int manodopera=10;
		invoice.setTotaleServizi(((sommaPrices+sommaPrices*0.22)+manodopera)+"");
		
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
	@PostMapping(path="/delete")
	public Response<?> deleteInvoiceById(@RequestBody String body){
	
		
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
		
		return invoiceService.deleteInvoiceById(Integer.parseInt(id));
	}
	
	@PostMapping(path="/update")
	public Response<?> updateInvoice(@RequestBody String body) throws ParseException{
		log.info("\n\n\n\n\n\n\n update invoice: \n"+body+"\n\n\n\n\n\n");
		
		Invoice invoiceUpd = new Invoice();
		
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
		String idS = body.substring(arr[2] + 1, arr[3]);
		String customerId = body.substring(arr[6] + 1, arr[7]);
		String payCondition = body.substring(arr[10] + 1, arr[11]);
		String docType = body.substring(arr[14] + 1, arr[15]);
		String sale = body.substring(arr[18] + 1, arr[19]);
		String articles = body.substring(arr[22] + 1, arr[23]);
		String taxable = body.substring(arr[26] + 1, arr[27]);
		String quantity = body.substring(arr[30] + 1, arr[31]);
		String saleImport = body.substring(arr[34] + 1, arr[35]);
	
		
		invoiceUpd.setId(Integer.parseInt(idS));
	
		int codInv=(Integer.parseInt(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getCodeInvoice()));
		invoiceUpd.setCodeInvoice(codInv+"");
		
		if(customerId=="") {
			int idcustomer= Integer.parseInt(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getIdCustomer());
			invoiceUpd.setIdCustomer(idcustomer+"");
			invoiceUpd.setCustomer_id(Integer.parseInt(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getIdCustomer()));
		}else {
			invoiceUpd.setIdCustomer(customerId+"");
			invoiceUpd.setCustomer_id(Integer.parseInt(customerId));
		}
		
		
		invoiceUpd.setDateTime(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getDateTime());
	
		
		if(payCondition=="") {
			invoiceUpd.setCondizionePagamento(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getCondizionePagamento());
		}else {
			invoiceUpd.setCondizionePagamento(payCondition);
		}
		
		if(docType=="") {
			invoiceUpd.setTipoDocumento(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTipoDocumento());
		}else {
			invoiceUpd.setTipoDocumento(docType);
		}

		if(articles=="") {
			articles=invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getFields();
			invoiceUpd.setFields(articles);
			sale=invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getSconto();
			invoiceUpd.setSconto(sale);
			invoiceUpd.setTotalPrice(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotalPrice());
			invoiceUpd.setIvaPrice(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getIvaPrice());
			invoiceUpd.setTotaleMerci(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotaleMerci());
			invoiceUpd.setTotalToPay(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotalToPay());
			invoiceUpd.setTotaleServizi(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotaleServizi());
		}else {
			invoiceUpd.setFields(articles);
			String[] arrArt=articles.split(";");
			double sommaPrices=0;
			for(int i=0; i<arrArt.length; i++) {
				sommaPrices=sommaPrices+Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getPrice()) ;
			}
			double sommaSconti=0;
			for(int i=0; i<arrArt.length; i++) {
				sommaSconti=sommaSconti+Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd()) ;
			}
			if(sommaSconti>50) {
				sommaSconti=50;
			}

			invoiceUpd.setTotalPrice(sommaPrices+"");
			invoiceUpd.setIvaPrice((sommaPrices*0.22)+"");
			invoiceUpd.setImponibile((sommaPrices*0.22)+"");
			invoiceUpd.setTotaleMerci((sommaPrices+sommaPrices*0.22)+"");
			if(flag==false) {
				invoiceUpd.setTotalToPay(sommaPrices+(sommaPrices*0.22)+"");
			}else {
				double saldo=(sommaSconti)/100*sommaPrices;
				invoiceUpd.setImportoSconto(saldo+"");
				invoiceUpd.setTotalToPay((sommaPrices-saldo)+(sommaPrices*0.22)+"");
			}
			invoiceUpd.setQuantita(arrArt.length+"");
			int manodopera=10;
			invoiceUpd.setTotaleServizi(((sommaPrices+sommaPrices*0.22)+manodopera)+"");
			invoiceUpd.setSconto((sommaPrices*sommaSconti/100)+"");
		}
		
		//calcolo quantita
		int qnt = 0; //è la quantita
		int[] art = new int[articles.length()];
		for (int i = 0; i < articles.length(); i++) {
			if (body.charAt(i) == ';') {
				art[qnt] = i;
				qnt++;
			}
		}
		
		if(taxable=="") {
			invoiceUpd.setImponibile(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getImponibile());
		}else {
			invoiceUpd.setImponibile(taxable);
		}

		if(quantity=="") {
			invoiceUpd.setQuantita(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getQuantita());
		}else {
			invoiceUpd.setQuantita(quantity);
		}
		
		if(saleImport=="") {
			invoiceUpd.setImportoSconto(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getImportoSconto());
		}else {
			invoiceUpd.setImportoSconto(saleImport);
		}

		invoiceUpd.setIva(22+"");
		//ciclo somma prezzi stonks
				log.info("\n\n inizio service \n\n");
				
		
		
		
		return this.invoiceService.updateInvoice(invoiceUpd);
	}
}
