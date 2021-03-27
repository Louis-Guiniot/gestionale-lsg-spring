package it.gestionalejaclsg.jac.dto;

import javax.persistence.Column;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.BodyInvoice;

import lombok.Data;

@Data
public class BodyInvoiceDTO {

	private int id;

	private int idInvoice;

	private String codiceArticolo;
	
	private String descrizione;
	
	private String unitamisura;
	
	private String quantita;

	private String importoUnitario;

	private String scontoFormula;

	private String prezzoNetto;		

	private String importoSconto;
	
	private String imponibile;

	private String iva;
	
	private String imposta;

	private String totaleRighe;
	
	private String totaleMerce;

	private String totaleServizi;

	public static BodyInvoiceDTO build(BodyInvoice bodyinvoice) {

		BodyInvoiceDTO result = new BodyInvoiceDTO();
		BeanUtils.copyProperties(bodyinvoice, result);

		return result;
	}
}
