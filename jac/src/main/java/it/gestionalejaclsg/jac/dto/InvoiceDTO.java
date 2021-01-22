package it.gestionalejaclsg.jac.dto;



import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.Invoice;
import lombok.Data;

@Data

public class InvoiceDTO {
	
	private int id;
	
	private String codeInvoice;
	
	private String totalPrice;
	
	private String idCustomer;
	
	private String dateTime;
	
    String fields;
    
    private String sconto;
    
    private String iva;
    
    private String ivaPrice;
    
    private String totalToPay;
    
	public static InvoiceDTO build(Invoice invoice) {

		InvoiceDTO result = new InvoiceDTO();
		BeanUtils.copyProperties(invoice, result);

		return result;
	}
}
