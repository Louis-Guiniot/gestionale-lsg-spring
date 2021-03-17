package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.TailInvoiceRepository;
import it.gestionalejaclsg.jac.dto.TailInvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.TailInvoice;

@Service
public class TailInvoiceService {
private static Logger log = LoggerFactory.getLogger(TailInvoiceService.class);
	
	@Autowired
	private TailInvoiceRepository tailinvoiceRepository;
	
	final static String error = "Nessuna tailinvoice trovata.";

	// create

	public Response<TailInvoice> createTailInvoice(TailInvoice tailinvoice) {

		Response<TailInvoice> response = new Response<TailInvoice>();

		try {
			this.tailinvoiceRepository.save(tailinvoice);

			response.setResult(tailinvoice);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("tailinvoice non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteTailInvoiceById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.tailinvoiceRepository.deleteById(id);

			response.setResult("tailinvoice eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("tailinvoice non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<TailInvoiceDTO>> findAllTailInvoices() {

		Response<List<TailInvoiceDTO>> response = new Response<List<TailInvoiceDTO>>();

		List<TailInvoiceDTO> result = new ArrayList<>();

		try {

			Iterator<TailInvoice> iterator = this.tailinvoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {

				TailInvoice tailinvoice = iterator.next();
				result.add(TailInvoiceDTO.build(tailinvoice));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	// findLast
		public Response<TailInvoiceDTO> findLastTailInvoice() {

			Response<List<TailInvoiceDTO>> response = new Response<List<TailInvoiceDTO>>();
		
			int last=0;
			try {

				Iterator<TailInvoice> iterator = this.tailinvoiceRepository.findAll().iterator();

				while (iterator.hasNext()) {

					TailInvoice tailinvoice = iterator.next();
					
					if(!iterator.hasNext()) {						
						last=tailinvoice.getId();
					}

				}
				


			} catch (Exception e) {

				response.setError(error);

			}
			
			log.info("passaggio prima del return\nid last: "+last+"\n\n\n\n\n");
			return findTailInvoiceById(last);

		}

	// find invoice by id
	public Response<TailInvoiceDTO> findTailInvoiceById(int id) {
		log.info("findo invoice by id");
		Response<TailInvoiceDTO> response = new Response<TailInvoiceDTO>();
		
		if(id!=0) {
			try {

				TailInvoice tailinvoice = this.tailinvoiceRepository.findById(id).get();

				response.setResult(TailInvoiceDTO.build(tailinvoice));
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError(error);

			}

			return response;
		}else {
			log.info("siamo nell'else");
			Response<TailInvoiceDTO>response2= new Response<TailInvoiceDTO>();
			TailInvoice tailinvoice2 = new TailInvoice();
			response2.setResult(TailInvoiceDTO.build(tailinvoice2));
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
	
	
	
	
	
	
	public Response<TailInvoiceDTO> updateTailInvoice(TailInvoice tailinvoice) {

		Response<TailInvoiceDTO> response = new Response<TailInvoiceDTO>();

		try {

			this.tailinvoiceRepository.save(tailinvoice);

			response.setResult(TailInvoiceDTO.build(tailinvoice));
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
