package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bodyinvoice")
@Data
public class BodyInvoice {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_Invoice")
	private int idInvoice;
	
	@Column(name = "product_fields")
	private String fields;
	
	@Column(name="iva")
	private String iva;
	
	@Column(name="imponibile")
	private String imponibile;
	
	@Column(name="quantita")
	private String quantita;
	
	@Column(name="importo_sconto")
	private String importoSconto;

	@Column(name="totale_merci")
	private String totaleMerci;

}
