package it.gestionalejaclsg.jac.dto;



import org.springframework.beans.BeanUtils;


import it.gestionalejaclsg.jac.entity.Product;
import lombok.Data;

@Data

public class ProductDTO {
	
	private int id;
	
	private String codeProduct;
	
	private String name;
	
	private String description;
	
	private String price;
	
	private String measureUnit;
	
	private int measureUnit_id;
	
	private String img;
	
	private String scontoProd;
	
	private int iva;
	
	public static ProductDTO build(Product product) {

		ProductDTO result = new ProductDTO();
		BeanUtils.copyProperties(product, result);

		return result;
	}
	

}
