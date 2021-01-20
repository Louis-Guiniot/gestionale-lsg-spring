package it.gestionalejaclsg.jac.dto;



import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import it.gestionalejaclsg.jac.entity.Product;
import lombok.Data;

@Data
@EntityScan("it.gestionalejaclsg.jac.dto")
public class ProductDTO {
	
	private int id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private String price;
	
	private String measureUnit;
	
	public static ProductDTO build(Product product) {

		ProductDTO result = new ProductDTO();
		BeanUtils.copyProperties(product, result);

		return result;
	}
	

}
