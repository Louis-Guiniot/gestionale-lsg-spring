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
	
	@Column(name = "total_price")
	private String totalPrice;
	
	@Column(name="ivaPrice")
	private String ivaPrice;
	
	@Column(name="totalToPay")
	private String totalToPay;
	
	@Column(name="totale_servizi")
	private String totaleServizi;
	
}
