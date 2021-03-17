package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tailinvoice")
@Data
public class TailInvoice {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_Invoice")
	private int idInvoice;
	
	@Column(name="totale_merce")
	private String totaleMerce;
	
	@Column(name="totale_servizi")
	private String totaleServizi;
	
	@Column(name="ulteriore_sconto")
	private String ulterioreSconto;
	
	@Column(name="importo_sconto_coda")
	private String importoScontoCoda;
	
	@Column(name="totale_sconti")
	private String totaleSconti;
	
	@Column(name="imponibile")
	private String imponibile;
	
	@Column(name = "totale_imposte")
	private String totaleImposte;
	
	@Column(name="totalToPay")
	private String totalToPay;
	

	
}
