package it.gestionalejaclsg.jac.controller;

import java.text.SimpleDateFormat;
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
	
//	@Autowired
//	private ProductHasInvoiceService productHasInvoiceService;
//	
	@Autowired
	private ProductService productService;
	
	//private static final double iva=0.22;
	
	@PostMapping("/search")
	public Response<?> searchInvoices(@RequestBody String body){
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
		
		return this.invoiceService.findInvoiceByTerm(termine);
	}

	
	@PostMapping("/create")
	public Response<?> createInvoice(@RequestBody String body) {
		
		Invoice invoice=new Invoice();
		//ProductHasInvoice phi=new ProductHasInvoice();

		//body: {"custId":"1","payCondition":"123","docType":"123","sale":"123","idItemsString":"123;123;123;123;","qntItemsString":"123;123;123;123;"}

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		//double iva=0.22;
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
	
		String customerId = body.substring(arr[2] + 1, arr[3]);
		String paymentCondition = body.substring(arr[6] + 1, arr[7]);
		String docType = body.substring(arr[10] + 1, arr[11]);
		String sconto;
		if((body.substring(arr[14]+1,arr[15])).equals("")) {
			sconto="0";
			
		}else {
			sconto=body.substring(arr[14]+1,arr[15]);
		}
		String idAricles=body.substring(arr[18] + 1, arr[19]);
		String articlesQuantity=body.substring(arr[22] + 1, arr[23]);
		String siva=body.substring(arr[26] + 1, arr[27]);
		double iva=Double.parseDouble(siva)/100;
		
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");	
		
		Calendar calndr = Calendar.getInstance();

		
		invoice.setDateTime(sdf.format(calndr.getTime()).toString());
				
		invoice.setCustomer_id(Integer.parseInt(customerId));
		invoice.setIdCustomer(customerId);
		
		
		invoice.setCondizionePagamento(paymentCondition);
		invoice.setTipoDocumento(docType);
		invoice.setSconto(sconto);
		invoice.setFields(idAricles);
		invoice.setQuantita(articlesQuantity);
		
		
		invoice.setIva(iva+"");
	
		
		
		
		//CREAZIONE CODICE
		/**questo blocco è ok**/
		
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
		
		log.info("data "+codeInt);
		
		invoice.setCodeInvoice(codeInt+""); //es cod 20211-20212-20213
		
		//FINE CREAZIONE CODICE 
		
		log.info("\n\n inizio splits \n\n");
		String[] arrArt=idAricles.split(";");
		String[] arrQntArt=articlesQuantity.split(";");
		
		log.info("articolo 1: "+arrArt[0]);
		
		
		//ciclo somma prezzi stonks
		log.info("\n\n inizio ciclo somma \n\n");
		double sommaPrices=0;
		double valoreSconto=0;
		double prezzoArticolo=0;
		double prezzoScontato=0;
		
		//phi.setInvoiceId(invoiceService.findLastInvoice().getResult().getId()+1+"");//recupera l'ultimo id invoice e fa +1
		//cicla per fare la somma dei prezzi tentendo conto VERAMENTE degli sconti applicati ai prodotti
		for(int i=0; i<arrArt.length; i++) {
			//recupera il prezzo dall'id moltiplicandolo per la quantita
			prezzoArticolo=Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getPrice())*Integer.parseInt(arrQntArt[i]);
			
			//phi.setProductId(arrArt[i]);
			//phi.setQuantity(arrQntArt[i]+"");
			
			
			
			//controlla se lo sconto è 0 per evitare problemi di calcolo
			if(Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd())==0) {
				//se è 0 moltiplica per 1 che se non ricordo male in 5 elementare mi hanno detto che ogni numero moltiplicato per uno dà il numero stesso come risultato
				prezzoScontato=prezzoArticolo*1;
			}else {	
				//altrimenti calcola lo sconto
				valoreSconto=Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd());
				prezzoScontato=prezzoArticolo-(prezzoArticolo*(valoreSconto/100));
			}
			
			
			sommaPrices+=prezzoScontato;
		}
		
		int totMerci=0;
		for(int j=0;j<arrQntArt.length;j++) {
			totMerci+=Integer.parseInt(arrQntArt[j]);
		}
		String totMercis=totMerci+"";
		invoice.setSconto(sconto+""); //prezzo degli articoli scontati
		invoice.setTotalPrice(sommaPrices+"");//prezzo totale dei prodotti senza sconti ne iva
		
		if(iva!=0)
			invoice.setIvaPrice((sommaPrices+(sommaPrices*iva))+"");//prezzo totale dei prodotti con aggiunta di iva
		else
			invoice.setIvaPrice(sommaPrices+"");
			
		
		if(iva!=0)
			invoice.setImponibile((sommaPrices*iva)+"");//calcolo dell'iva
		else
			invoice.setImponibile(sommaPrices+"");//calcolo dell'iva

			
		invoice.setTotaleMerci(totMercis);//numero prodotti totali
//		if(flag==false) {
//			invoice.setTotalToPay(sommaPrices+(sommaPrices*iva)+"");
//		}else {
		double saldo=sommaPrices*Double.parseDouble(sconto)/100;
		invoice.setImportoSconto(saldo+"");
		invoice.setTotalToPay((sommaPrices-saldo)+(sommaPrices*iva)+"");
//		}
		
		int manodopera=63;
		invoice.setTotaleServizi((((sommaPrices-saldo)+(sommaPrices*iva))+manodopera)+"");//prezzo totale con sconti, iva e manodopera
		
		//productHasInvoiceService.createProductHasInvoice(phi);
		
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
		
		//ProductHasInvoice phi=new ProductHasInvoice();
		//{"idS":"18","custId":"","payCondition":"","docType":"","sale":"","idItemsString":"","qntItemsString":"","iva":"0.10"}

		
		String idS = body.substring(arr[2] + 1, arr[3]);
		String customerId = body.substring(arr[6] + 1, arr[7]);
		String payCondition = body.substring(arr[10] + 1, arr[11]);
		String docType = body.substring(arr[14] + 1, arr[15]);
		String sale = body.substring(arr[18] + 1, arr[19]);
		
		String idArticles = body.substring(arr[22] + 1, arr[23]);
		String articlesQuantity= body.substring(arr[26] + 1, arr[27]);
		
		String siva=body.substring(arr[29] + 1, arr[30]);
		double iva=Double.parseDouble(siva)/100;
		
	
		
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

		if(idArticles=="") {
			
			
			idArticles=invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getFields();
			invoiceUpd.setFields(idArticles);
			//phi
		//	phi.setInvoiceId(invoiceService.findLastInvoice().getResult().getId()+1+"");//recupera l'ultimo id invoice e fa +1
			
			//phi.setProductId(idArticles);
			//phi.setQuantity(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotaleMerci()+"");
			//phi
			sale=invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getSconto();
			invoiceUpd.setSconto(sale);
			invoiceUpd.setTotalPrice(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotalPrice());
			invoiceUpd.setIvaPrice(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getIvaPrice());
			invoiceUpd.setTotaleMerci(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotaleMerci());
			invoiceUpd.setTotalToPay(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotalToPay());
			invoiceUpd.setTotaleServizi(invoiceService.findInvoiceById(Integer.parseInt(idS)).getResult().getTotaleServizi());
		}else {
			log.info("\n\n inizio splits \n\n");
			String[] arrArt=idArticles.split(";");
			String[] arrQntArt=articlesQuantity.split(";");
			

			//ciclo somma prezzi stonks
			log.info("\n\n inizio ciclo somma \n\n");
			double sommaPrices=0;
			double valoreSconto=0;
			double prezzoArticolo=0;
			double prezzoScontato=0;
			//phi.setInvoiceId(invoiceService.findLastInvoice().getResult().getId()+1+"");//recupera l'ultimo id invoice e fa +1
			//cicla per fare la somma dei prezzi tentendo conto VERAMENTE degli sconti applicati ai prodotti
			for(int i=0; i<arrArt.length; i++) {
				//recupera il prezzo dall'id moltiplicandolo per la quantita
				prezzoArticolo=Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getPrice())*Integer.parseInt(arrQntArt[i]);
				
				//product has invoice
				//phi.setProductId(arrArt[i]);
				//phi.setQuantity(arrQntArt[i]+"");
				
				
				//controlla se lo sconto è 0 per evitare problemi di calcolo
				if(Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd())==0) {
					//se è 0 moltiplica per 1 che se non ricordo male in 5 elementare mi hanno detto che ogni numero moltiplicato per uno dà il numero stesso come risultato
					prezzoScontato=prezzoArticolo*1;
				}else {	
					//altrimenti calcola lo sconto
					valoreSconto=Double.parseDouble(productService.findProductById(Integer.parseInt(arrArt[i])).getResult().getScontoProd());
					prezzoScontato=prezzoArticolo-(prezzoArticolo*(valoreSconto/100));
				}
				
				
				sommaPrices+=prezzoScontato;
			}
			
			int totMerci=0;
			for(int j=0;j<arrQntArt.length;j++) {
				totMerci+=Integer.parseInt(arrQntArt[j]);
			}
			String totMercis=totMerci+"";
			invoiceUpd.setSconto(sale+""); //prezzo degli articoli scontati
			invoiceUpd.setTotalPrice(sommaPrices+"");//prezzo totale dei prodotti senza sconti ne iva
			
			if(iva!=0)
				invoiceUpd.setIvaPrice((sommaPrices+(sommaPrices*iva))+"");//prezzo totale dei prodotti con aggiunta di iva
			else
				invoiceUpd.setIvaPrice(sommaPrices+"");
				
			
			if(iva!=0)
				invoiceUpd.setImponibile((sommaPrices*iva)+"");//calcolo dell'iva
			else
				invoiceUpd.setImponibile(sommaPrices+"");//calcolo dell'iva

				
			invoiceUpd.setTotaleMerci(totMercis);//numero prodotti totali
//			if(flag==false) {
//				invoice.setTotalToPay(sommaPrices+(sommaPrices*iva)+"");
//			}else {
			double saldo=sommaPrices*Double.parseDouble(sale)/100;
			invoiceUpd.setImportoSconto(saldo+"");
			invoiceUpd.setTotalToPay((sommaPrices-saldo)+(sommaPrices*iva)+"");
//			}
			
			int manodopera=63;
			invoiceUpd.setTotaleServizi((((sommaPrices-saldo)+(sommaPrices*iva))+manodopera)+"");//prezzo totale con sconti, iva e manodopera
		}
		
		
		
		
		
		
		return this.invoiceService.updateInvoice(invoiceUpd);
	}
	
	
	 @PostMapping(path="/findInvoiceById")
	 public Response<?> findInvoiceById(@RequestBody String body){
		 log.info("body    "+body);
		 return invoiceService.findInvoiceById(Integer.parseInt(body));
	 }
	
}
