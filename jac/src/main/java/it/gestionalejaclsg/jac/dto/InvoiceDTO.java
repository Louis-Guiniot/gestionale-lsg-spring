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
	
	private int customer_id;
	
	private String dateTime;
	
    String fields;
    
    private String sconto;
    
    private String iva;
    
    private String ivaPrice;
    
    private String totalToPay;
    
    private String totaleServizi;
    
    private String imponibile;
    
    private String tipoDocumento;
    
    private String condizionePagamento;
    
	public static InvoiceDTO build(Invoice invoice) {

		InvoiceDTO result = new InvoiceDTO();
		BeanUtils.copyProperties(invoice, result);

		return result;
	}
}
