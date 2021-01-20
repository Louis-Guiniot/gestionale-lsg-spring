package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.gestionalejaclsg.jac.dao.InvoiceRepository;
import it.gestionalejaclsg.jac.dto.InvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Invoice;

public class InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;
	
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

	// find invoice by id
	public Response<InvoiceDTO> findInvoiceById(int id) {

		Response<InvoiceDTO> response = new Response<InvoiceDTO>();

		try {

			Invoice invoice = this.invoiceRepository.findById(id).get();

			response.setResult(InvoiceDTO.build(invoice));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	// update invoice
	public Response<InvoiceDTO> updateUser(int id, String code, String totalPrice, String idCustomer, HashMap<Integer, String> fields) {

		Response<InvoiceDTO> response = new Response<InvoiceDTO>();

		try {
			Invoice invoice = this.invoiceRepository.findById(id).get();

			if (code != null)
				invoice.setCodeInvoice(code);
			
			if (totalPrice != null)
				invoice.setTotalPrice(totalPrice);
			
			if (idCustomer != null)
				invoice.setIdCustomer(idCustomer);
			
			if (fields != null)
				invoice.setFields(fields);

			this.invoiceRepository.save(invoice);

			response.setResult(InvoiceDTO.build(invoice));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}
}
