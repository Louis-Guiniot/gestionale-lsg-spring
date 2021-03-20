package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.BodyInvoiceRepository;
import it.gestionalejaclsg.jac.dto.BodyInvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.BodyInvoice;

@Service
public class BodyInvoiceService {
private static Logger log = LoggerFactory.getLogger(BodyInvoiceService.class);
	
	@Autowired
	private BodyInvoiceRepository invoiceRepository;
	
	final static String error = "Nessuna invoice trovata.";

	// create

	public Response<BodyInvoice> createBodyInvoice(BodyInvoice invoice) {

		Response<BodyInvoice> response = new Response<BodyInvoice>();

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
	public Response<String> deleteBodyInvoiceById(int id) {

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
	public Response<List<BodyInvoiceDTO>> findAllBodyInvoices() {

		Response<List<BodyInvoiceDTO>> response = new Response<List<BodyInvoiceDTO>>();

		List<BodyInvoiceDTO> result = new ArrayList<>();

		try {

			Iterator<BodyInvoice> iterator = this.invoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {

				BodyInvoice invoice = iterator.next();
				result.add(BodyInvoiceDTO.build(invoice));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	// findLast
		public Response<BodyInvoiceDTO> findLastBodyInvoice() {

			Response<List<BodyInvoiceDTO>> response = new Response<List<BodyInvoiceDTO>>();
		
			int last=0;
			try {

				Iterator<BodyInvoice> iterator = this.invoiceRepository.findAll().iterator();

				while (iterator.hasNext()) {

					BodyInvoice invoice = iterator.next();
					
					if(!iterator.hasNext()) {						
						last=invoice.getId();
					}

				}
				


			} catch (Exception e) {

				response.setError(error);

			}
			
			log.info("passaggio prima del return\nid last: "+last+"\n\n\n\n\n");
			return findBodyInvoiceById(last);

		}

	// find invoice by id
	public Response<BodyInvoiceDTO> findBodyInvoiceById(int id) {
		log.info("findo invoice by id");
		Response<BodyInvoiceDTO> response = new Response<BodyInvoiceDTO>();
		
		if(id!=0) {
			try {

				BodyInvoice invoice = this.invoiceRepository.findById(id).get();

				response.setResult(BodyInvoiceDTO.build(invoice));
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError(error);

			}

			return response;
		}else {
			log.info("siamo nell'else");
			Response<BodyInvoiceDTO>response2= new Response<BodyInvoiceDTO>();
			BodyInvoice invoice2 = new BodyInvoice();
			response2.setResult(BodyInvoiceDTO.build(invoice2));
			return response2;
		}
		}

	// update invoice
//	public Response<BodyInvoiceDTO> updateBodyInvoice(int id, String code, String totalPrice, String idCustomer, String fields) {
//
//		Response<BodyInvoiceDTO> response = new Response<BodyInvoiceDTO>();
//
//		try {
//			BodyInvoice invoice = this.invoiceRepository.findById(id).get();
//
//			if (code != null)
//				invoice.setCodeBodyInvoice(code);
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
//			response.setResult(BodyInvoiceDTO.build(invoice));
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
	
	
	
	
	
	
	public Response<BodyInvoiceDTO> updateBodyInvoice(BodyInvoice invoice) {

		Response<BodyInvoiceDTO> response = new Response<BodyInvoiceDTO>();

		try {

			this.invoiceRepository.save(invoice);

			response.setResult(BodyInvoiceDTO.build(invoice));
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
}
