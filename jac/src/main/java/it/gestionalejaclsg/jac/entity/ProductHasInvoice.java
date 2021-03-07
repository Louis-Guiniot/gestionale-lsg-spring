package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "product_has_invoice")
@Data

public class ProductHasInvoice {
	

	@Column(name = "product_id")
	private String product_id;

	@Column(name = "invoice_id")
	private String invoice_id;
	
	@Column(name="quantity")
	private String quantity;

}
