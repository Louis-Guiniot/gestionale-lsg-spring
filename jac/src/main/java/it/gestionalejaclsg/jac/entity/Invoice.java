package it.gestionalejaclsg.jac.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	

import lombok.Data;

@Entity
@Table(name = "invoice")
@Data


public class Invoice {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	private String codeInvoice;
	
	@Column(name = "total_price")
	private String totalPrice;
	
	@Column(name = "id_customer")
	private String idCustomer;
	
	@Column(name="customer_id")
	private int customer_id;
	
	//bisogna capire se funzia --> se no usare codice e/o id
	@Column(name = "product_fields")
    private String fields;
	
	@Column(name="dateTime")
	private String dateTime;
	
	@Column(name="sconto")
	private String sconto;
	
	@Column(name="iva")
	private String iva;
	
	@Column(name="ivaPrice")
	private String ivaPrice;
	
	@Column(name="totalToPay")
	private String totalToPay;
	
	@Column(name="totale_servizi")
	private String totaleServizi;
	
	//Per imponibile o nella dizione estesa base imponibile, 
	//s'intende l'importo sul quale, teoricamente, 
	//potrà essere calcolata e applicata un'imposta o un contributo.
	@Column(name="imponibile")
	private String imponibile;
	
	@Column(name="tipo_documento")
	private String tipoDocumento;
	
	@Column(name="condizione_pagamento")
	private String condizionePagamento;
}
