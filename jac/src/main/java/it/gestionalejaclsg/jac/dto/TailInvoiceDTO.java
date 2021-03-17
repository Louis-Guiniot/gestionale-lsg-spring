package it.gestionalejaclsg.jac.dto;


import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.TailInvoice;

import lombok.Data;

@Data

public class TailInvoiceDTO {
	private int id;
	
	private int idInvoice;
	
	private String totalPrice;
	
	private String ivaPrice;
	
	private String totalToPay;
	
	private String totaleServizi;
    
	public static TailInvoiceDTO build(TailInvoice tailinvoice) {

		TailInvoiceDTO result = new TailInvoiceDTO();
		BeanUtils.copyProperties(tailinvoice, result);

		return result;
	}
}
