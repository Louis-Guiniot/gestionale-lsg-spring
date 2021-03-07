package it.gestionalejaclsg.jac.dto;



import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
import lombok.Data;

@Data
public class ProductHasInvoiceDTO {
	

	
	private String product_id;
	
	private String invoice_id;

	private String quantity;
	
	public static ProductHasInvoiceDTO build(ProductHasInvoice pas) {

		ProductHasInvoiceDTO result = new ProductHasInvoiceDTO();
		BeanUtils.copyProperties(pas, result);

		return result;
	}
	
}
