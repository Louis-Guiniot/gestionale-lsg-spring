package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.gestionalejaclsg.jac.dao.InvoiceRepository;
import it.gestionalejaclsg.jac.dto.InvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Invoice;

@Service
public class InvoiceService {
	
	private static Logger log = LoggerFactory.getLogger(InvoiceService.class);
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	final static String error = "Nessuna invoice trovata.";

	// create

	public Response<Invoice> createInvoice(Invoice invoice) {

		Response<Invoice> response = new Response<Invoice>();

		try {
			this.invoiceRepository.save(invoice);

			response.setResult(invoice);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("invoice non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteInvoiceById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.invoiceRepository.deleteById(id);

			response.setResult("invoice eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("invoice non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<InvoiceDTO>> findAllInvoices() {

		Response<List<InvoiceDTO>> response = new Response<List<InvoiceDTO>>();

		List<InvoiceDTO> result = new ArrayList<>();

		try {

			Iterator<Invoice> iterator = this.invoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {

				Invoice invoice = iterator.next();
				result.add(InvoiceDTO.build(invoice));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	// findLast
		public Response<InvoiceDTO> findLastInvoice() {

			Response<List<InvoiceDTO>> response = new Response<List<InvoiceDTO>>();
		
			int last=0;
			try {

				Iterator<Invoice> iterator = this.invoiceRepository.findAll().iterator();

				while (iterator.hasNext()) {

					Invoice invoice = iterator.next();
					
					if(!iterator.hasNext()) {						
						last=invoice.getId();
					}

				}
				


			} catch (Exception e) {

				response.setError(error);

			}
			
			log.info("passaggio prima del return\nid last: "+last+"\n\n\n\n\n");
			return findInvoiceById(last);

		}

	// find invoice by id
	public Response<InvoiceDTO> findInvoiceById(int id) {
		log.info("findo invoice by id");
		Response<InvoiceDTO> response = new Response<InvoiceDTO>();
		
		if(id!=0) {
			try {

				Invoice invoice = this.invoiceRepository.findById(id).get();

				response.setResult(InvoiceDTO.build(invoice));
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError(error);

			}

			log.info("response    "+response);
			return response;
		}else {
			log.info("siamo nell'else");
			Response<InvoiceDTO>response2= new Response<InvoiceDTO>();
			Invoice invoice2 = new Invoice();
			invoice2.setCodeInvoice("null");
			response2.setResult(InvoiceDTO.build(invoice2));
			return response2;
		}
		}

	// update invoice
//	public Response<InvoiceDTO> updateInvoice(int id, String code, String totalPrice, String idCustomer, String fields) {
//
//		Response<InvoiceDTO> response = new Response<InvoiceDTO>();
//
//		try {
//			Invoice invoice = this.invoiceRepository.findById(id).get();
//
//			if (code != null)
//				invoice.setCodeInvoice(code);
//			
//			if (totalPrice != null)
//				invoice.setTotalPrice(totalPrice);
//			
//			if (idCustomer != null)
//				invoice.setIdCustomer(idCustomer);
//			
//			if (fields != null)
//				invoice.setFields(fields);
//
//			this.invoiceRepository.save(invoice);
//
//			response.setResult(InvoiceDTO.build(invoice));
//			response.setResultTest(true);
//
//		} catch (Exception e) {
//
//			response.setError(error);
//
//		}
//
//		return response;
//	}
	
	
	
	
	
	
	public Response<InvoiceDTO> updateInvoice(Invoice invoice) {

		Response<InvoiceDTO> response = new Response<InvoiceDTO>();

		try {

			this.invoiceRepository.save(invoice);

			response.setResult(InvoiceDTO.build(invoice));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}

	
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	         Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	//louis la carogna
	public Response<List<InvoiceDTO>> findInvoiceByTerm(String termine) {
		Response<List<InvoiceDTO>> response = new Response<List<InvoiceDTO>>();

		List<InvoiceDTO> result = new ArrayList<>();
		boolean isNumeric=false;
		if(InvoiceService.isNumeric(termine)) {
			isNumeric=true;
		}
		
		try {

			Iterator<Invoice> iterator = this.invoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {
				
			

				Invoice invoice = iterator.next();
				
				if((invoice.getCodeInvoice().equals(termine)||invoice.getCondizionePagamento().equals(termine)||invoice.getDateTime().equals(termine)||invoice.getFields().equals(termine)||invoice.getIdCustomer().equals(termine)||invoice.getImponibile().equals(termine)||invoice.getImportoSconto().equals(termine)||invoice.getIva().equals(termine)||invoice.getIvaPrice().equals(termine)||invoice.getQuantita().equals(termine)||invoice.getSconto().equals(termine)||invoice.getTipoDocumento().equals(termine)||invoice.getTotaleMerci().equals(termine)||invoice.getTotaleServizi().equals(termine)||invoice.getTotalPrice().equals(termine)||invoice.getTotalToPay().equals(termine))&&isNumeric==false) {
					log.info("\n\n\nparametro trovato!\n\n\n");
					result.add(InvoiceDTO.build(invoice));
				}else {
					if(isNumeric==true&&(invoice.getId()==(Integer.parseInt(termine))||invoice.getCustomer_id()==(Integer.parseInt(termine)))) {
					log.info("\n\n\nparametro trovato!\n\n\n");
					result.add(InvoiceDTO.build(invoice));
					}else {
						log.info("\n\n\nEH VOLEVI!\n\n\n");
					}
				}

			}

			response.setResult(result);
			
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}
		log.info("\n\nResponse: "+result+"\n\n");
		return response;
	}
}
