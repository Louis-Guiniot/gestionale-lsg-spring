package it.gestionalejaclsg.jac.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dao.ProductRepository;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.BodyInvoice;
import it.gestionalejaclsg.jac.entity.Invoice;
import it.gestionalejaclsg.jac.entity.Product;
import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
import it.gestionalejaclsg.jac.entity.TailInvoice;
import it.gestionalejaclsg.jac.service.BodyInvoiceService;
import it.gestionalejaclsg.jac.service.InvoiceService;
import it.gestionalejaclsg.jac.service.ProductHasInvoiceService;
import it.gestionalejaclsg.jac.service.ProductService;
import it.gestionalejaclsg.jac.service.TailInvoiceService;

@RestController
@RequestMapping("/rest/invoice")
public class InvoiceRestController {
	private static Logger log = LoggerFactory.getLogger(InvoiceRestController.class);

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private BodyInvoiceService bodyInvoiceService;

	@Autowired
	private TailInvoiceService tailInvoiceService;

	@Autowired
	private ProductHasInvoiceService productHasInvoiceService;

	@Autowired
	private ProductRepository productRepository;



	@PostMapping("/create")
	public void createInvoice(@RequestBody String body) {

		Invoice invoice = new Invoice();
		ProductHasInvoice phi = new ProductHasInvoice();
		
		
		/**HEAD**/
		
		
		
		
		// body:
		// {"custId":"1","payCondition":"123","docType":"123","sale":"123","idItemsString":"123;123;123;123;","qntItemsString":"123;123;123;123;"}

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		// double iva=0.22;
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
		if ((body.substring(arr[14] + 1, arr[15])).equals("")) {
			sconto = "0";

		} else {
			sconto = body.substring(arr[14] + 1, arr[15]);
		}
		String idAricles = body.substring(arr[18] + 1, arr[19]);
		String articlesQuantity = body.substring(arr[22] + 1, arr[23]);
		String siva = body.substring(arr[26] + 1, arr[27]);
		double iva = Double.parseDouble(siva) / 100;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");

		Calendar calndr = Calendar.getInstance();

		// head ivoice

		invoice.setDateTime(sdf.format(calndr.getTime()).toString());

		invoice.setCustomer_id(Integer.parseInt(customerId));
		
		
		invoice.setListino("1");

		
		invoice.setCondizionePagamento(paymentCondition);
		invoice.setTipoDocumento(docType);

		// CREAZIONE CODICE
		/** questo blocco è ok **/

		log.info("\n\n codice fattura \n\n");
		String codeStr = "";
		if (invoiceService.findLastInvoice().getResult().getCodeInvoice().equals("null")) {
			codeStr = Calendar.YEAR + "1";
			log.info("calendar: " + Calendar.YEAR);
		} else {
			codeStr = invoiceService.findLastInvoice().getResult().getCodeInvoice(); // 20211 - .substring(4,5))+1
		}
		log.info("\n\n CODICE STR " + codeStr + "\n\n");
		int codeInt = Integer.parseInt(codeStr) + 1;

		invoice.setCodeInvoice(codeInt + ""); // es cod 20211-20212-20213

		// FINE CREAZIONE CODICE

		
		productHasInvoiceService.createProductHasInvoice(phi);

		invoiceService.createInvoice(invoice);
		
		//BODY
		
		
		
		
		Product p;
		String [] arrProdotti=idAricles.split(";");
		String [] arrProdQnt=articlesQuantity.split(";");
		
		BodyInvoice bInv = null;
		
		for(int i=0; i<arrProdotti.length;i++) {
			p= this.productRepository.findById(Integer.parseInt(arrProdotti[i])).get();
			
			bInv.setCodiceArticolo(p.getCodeProduct());
			bInv.setDescrizione(p.getDescription());
			bInv.setIdInvoice(invoiceService.findLastInvoice().getResult().getId()+1);
			bInv.setUnitamisura(p.getMeasureUnit());
			bInv.setQuantita(arrProdQnt[i]);
			bInv.setImportoUnitario(Double.parseDouble(p.getPrice())*p.getIva()+"");
			bInv.setScontoFormula(p.getScontoProd());
			bInv.setPrezzoNetto((Double.parseDouble(p.getPrice())-(Double.parseDouble(p.getPrice())*(Double.parseDouble(p.getScontoProd())/100))+""));
			bInv.setImportoSconto((Double.parseDouble(p.getScontoProd())*Double.parseDouble(arrProdQnt[i])*Double.parseDouble(p.getPrice())/100)+"");
			bInv.setImponibile((Double.parseDouble(p.getPrice())-(Double.parseDouble(p.getPrice())*(Double.parseDouble(p.getScontoProd())/100))*Double.parseDouble(arrProdQnt[i]))+"");
			bInv.setIva(p.getIva()+"");
			bInv.setImposta(p.getIva()/100*(Double.parseDouble(p.getPrice())-(Double.parseDouble(p.getPrice())*(Double.parseDouble(p.getScontoProd())/100))*Double.parseDouble(arrProdQnt[i]))+"");
			bInv.setTotaleRighe(Double.parseDouble(bInv.getImponibile())+Double.parseDouble(bInv.getImposta())+"");
			bodyInvoiceService.createBodyInvoice(bInv);
		}
		
		//TAIL
		
		TailInvoice tInv=null;
		
		
		
		
		
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

	@PostMapping(path = "/delete")
	public Response<?> deleteInvoiceById(@RequestBody String body) {

		log.info("\n\n\n\nbody: " + body + "\n\n\n" + "eliminazione\n\n\n");
		int pPartenza = 0;
		int pArrivo = 0;

		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == ':') {
				pPartenza = i + 1;
			}
			if (body.charAt(i) == '}') {
				pArrivo = i;
			}
		}
		String id = body.substring(pPartenza, pArrivo);

		return invoiceService.deleteInvoiceById(Integer.parseInt(id));
	}



	@PostMapping(path = "/findInvoiceById")
	public Response<?> findInvoiceById(String body) {
		return invoiceService.findInvoiceById(Integer.parseInt(body));
	}

}
