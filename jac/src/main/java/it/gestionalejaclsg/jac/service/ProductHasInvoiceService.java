package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.controller.PayConditionRestController;
import it.gestionalejaclsg.jac.dao.ProductHasInvoiceRepository;
import it.gestionalejaclsg.jac.dto.ProductHasInvoiceDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
import jdk.internal.org.jline.utils.Log;

@Service
public class ProductHasInvoiceService {
	
	@Autowired
	private ProductHasInvoiceRepository productHasInvoiceRepository;
	
	final static String error = "Nessuna phi trovata.";
	
	private static Logger log = LoggerFactory.getLogger(PayConditionRestController.class);
	
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
	// find phi by id invoice
    public Response<ProductHasInvoiceDTO> findProductHasInvoiceById(int id) {

        Response<ProductHasInvoiceDTO> response = new Response<ProductHasInvoiceDTO>();

        try {

            ProductHasInvoice phi = this.productHasInvoiceRepository.findByInvoiceId(id);

            response.setResult(ProductHasInvoiceDTO.build(phi));
            response.setResultTest(true);

        } catch (Exception e) {

            response.setError(error);

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
	
	
	public Response<String> deleteProductHasInvoiceByPhi(ProductHasInvoice phic){
		Response<String> response = new Response<String>();
		
		//Response<List<ProductHasInvoiceDTO>> phiDTIO=findAllProductHasInvoices();
		
		
		boolean flag=false;//se lo trova
		List<ProductHasInvoiceDTO> result = new ArrayList<>();

		try {

			Iterator<ProductHasInvoice> iterator = this.productHasInvoiceRepository.findAll().iterator();

			while (iterator.hasNext()) {
				
				

				ProductHasInvoice phi = iterator.next();
				if(phic.getInvoiceId().equals(phi.getInvoiceId())&&phic.getProductId().equals(phic.getProductId())&&phic.getQuantity().equals(phic.getQuantity())&&flag==false) {
					flag=true;
					this.productHasInvoiceRepository.delete(phic);
				}

			}

			response.setResult("eliminato");
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

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
				log.info("1");

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		log.info("retunr ok");
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
