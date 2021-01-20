package it.gestionalejaclsg.jac.dto;

import java.util.HashMap;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.Invoice;
import lombok.Data;

@Data

public class InvoiceDTO {
	
	private int id;
	
	private String codeInvoice;
	
	private String totalPrice;
	
	private String idCustomer;
	
    HashMap<Integer, String> fields = new HashMap<>();
	
	public static InvoiceDTO build(Invoice invoice) {

		InvoiceDTO result = new InvoiceDTO();
		BeanUtils.copyProperties(invoice, result);

		return result;
	}
}
