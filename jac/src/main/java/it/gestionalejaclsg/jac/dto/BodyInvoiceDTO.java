package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.BodyInvoice;

import lombok.Data;

@Data
public class BodyInvoiceDTO {

	private int id;
	
	private String idInvoice;
	
	private String fields;
	
	private int iva;
	
	private String imponibile;
    
    private String quantita;
    
    private String importoSconto;
    
	public static BodyInvoiceDTO build(BodyInvoice bodyinvoice) {

		BodyInvoiceDTO result = new BodyInvoiceDTO();
		BeanUtils.copyProperties(bodyinvoice, result);

		return result;
	}
}
