package it.gestionalejaclsg.jac.dto;



import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
import lombok.Data;

@Data
public class ProductHasInvoiceDTO {
	

	
	private String productId;
	
	private String invoiceId;

	private String quantity;
	
	public static ProductHasInvoiceDTO build(ProductHasInvoice pas) {

		ProductHasInvoiceDTO result = new ProductHasInvoiceDTO();
		BeanUtils.copyProperties(pas, result);

		return result;
	}
	
}
