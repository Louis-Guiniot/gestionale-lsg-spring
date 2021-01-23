package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.ProductRepository;
import it.gestionalejaclsg.jac.dto.ProductDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Product;



@Service

public class ProductService {
	
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

		return response;

	}

	//update product
	public Response<ProductDTO> updateProduct(int id, String code, String name, String description, String price, String measureUnit) {

		Response<ProductDTO> response = new Response<ProductDTO>();

		try {
			Product product = this.productRepository.findById(id).get();

			if (name != null)
				product.setName(name);
			
			if (code != null)
				product.setCodeProduct(code);

			if (description != null)
				product.setDescription(description);

			if (price != null)
				product.setPrice(price);

			if (measureUnit != null)
				product.setMeasureUnit(measureUnit);

			
			
			this.productRepository.save(product);
			
			response.setResult(ProductDTO.build(product));
			response.setResultTest(true);

		} catch (Exception e) {
			
			response.setError(error);
			
		}	

		return response;
	}
	

}
