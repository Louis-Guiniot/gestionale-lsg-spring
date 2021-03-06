package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.controller.ProductRestController;
import it.gestionalejaclsg.jac.dao.ProductRepository;
import it.gestionalejaclsg.jac.dto.ProductDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Product;



@Service

public class ProductService {
	
	private static Logger log = LoggerFactory.getLogger(ProductService.class);

	
	@Autowired
	private ProductRepository productRepository;
	
	final static String error = "Nessun prodotto trovato.";

	// create
	
	public Response<Product> createProduct(Product product) {

		Response<Product> response = new Response<Product>();

		try {
			this.productRepository.save(product); 

			response.setResult(product);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("product non creato");

		}

		return response;

	}

	// delete
	public Response<String> deleteProductById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.productRepository.deleteById(id);

			response.setResult("product eliminato.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("product non eliminato correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<ProductDTO>> findAllProducts() {

		Response<List<ProductDTO>> response = new Response<List<ProductDTO>>();

		List<ProductDTO> result = new ArrayList<>();

		try {

			Iterator<Product> iterator = this.productRepository.findAll().iterator();

			while (iterator.hasNext()) {

				Product product = iterator.next();
				result.add(ProductDTO.build(product));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	// findLast
			public Response<ProductDTO> findLastProduct() {

				
				List<ProductDTO> list=this.findAllProducts().getResult();
				
				ProductDTO lastProd=list.get(list.size()-1);
				int last=lastProd.getId();
						
				return findProductById(last);

			}

	//find product by id
	public Response<ProductDTO> findProductById(int id) {

		Response<ProductDTO> response = new Response<ProductDTO>();

		try {

			Product product = this.productRepository.findById(id).get();

			response.setResult(ProductDTO.build(product));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}
		
		log.info("responose "+response);

		return response;

	}

	//update product
	public Response<ProductDTO> updateProduct(int id,String description,  String measureUnit, String name, String price, String sconto) {

		Response<ProductDTO> response = new Response<ProductDTO>();

		try {
			Product product = this.productRepository.findById(id).get();

			if (name != null)
				product.setName(name);
			
			

			if (description != null)
				product.setDescription(description);

			if (price != null)
				product.setPrice(price);

			if (measureUnit != null)
				product.setMeasureUnit(measureUnit);
			if (sconto != null)
				product.setScontoProd(sconto);

			
			
			this.productRepository.save(product);
			
			response.setResult(ProductDTO.build(product));
//			response.setResultTest(true);

		} catch (Exception e) {
			
			response.setError(error);
			
		}	

		return response;
	}
	

}
