package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.ProductHasInvoiceRepository;
import it.gestionalejaclsg.jac.dto.ProductHasInvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.ProductHasInvoice;

@Service
public class ProductHasInvoiceService {
	
	@Autowired
	private ProductHasInvoiceRepository productHasInvoiceRepository;
	
	final static String error = "Nessuna phi trovata.";
	
	public Response<ProductHasInvoice> createProductHasInvoice(ProductHasInvoice phi) {

		Response<ProductHasInvoice> response = new Response<ProductHasInvoice>();

		try {
			this.productHasInvoiceRepository.save(phi);

			response.setResult(phi);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("ProductHasInvoice non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteProductHasInvoiceById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.productHasInvoiceRepository.deleteById(id);

			response.setResult("phi eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("phi non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<ProductHasInvoiceDTO>> findAllProductHasInvoices() {

		Response<List<ProductHasInvoiceDTO>> response = new Response<List<ProductHasInvoiceDTO>>();

		List<ProductHasInvoiceDTO> result = new ArrayList<>();

		try {

			Iterator<ProductHasInvoice> iterator = this.productHasInvoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {

				ProductHasInvoice phi = iterator.next();
				result.add(ProductHasInvoiceDTO.build(phi));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
//	public Response<ProductHasInvoiceDTO> findLastMeasure() {
//
//		Response<List<ProductHasInvoiceDTO>> response = new Response<List<ProductHasInvoiceDTO>>();
//	
//		int last=0;
//		try {
//
//			Iterator<ProductHasInvoice> iterator = this.productHasInvoiceRepository.findAll().iterator();
//
//			while (iterator.hasNext()) {
//
//				ProductHasInvoice phi = iterator.next();
//				
//				if(!iterator.hasNext()) {						
//					last=phi.getId();
//				}
//
//			}
//			
//
//
//		} catch (Exception e) {
//
//			response.setError(error);
//
//		}
//		
//		return findMeasureUnitById(last);
//
//	}
//	
	

	// find phi by id
	public Response<ProductHasInvoiceDTO> findProductHasInvoiceById(int id) {

		Response<ProductHasInvoiceDTO> response = new Response<ProductHasInvoiceDTO>();

		try {

			ProductHasInvoice phi = this.productHasInvoiceRepository.findById(id).get();

			response.setResult(ProductHasInvoiceDTO.build(phi));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	
//	 update phi
	public Response<ProductHasInvoiceDTO> updateProductHasInvoice(ProductHasInvoice pc) {

		Response<ProductHasInvoiceDTO> response = new Response<ProductHasInvoiceDTO>();

		try {

			this.productHasInvoiceRepository.save(pc);

			response.setResult(ProductHasInvoiceDTO.build(pc));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}


}
