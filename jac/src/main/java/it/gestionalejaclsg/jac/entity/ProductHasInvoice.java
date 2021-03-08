package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "product_has_invoice")
@Data

public class ProductHasInvoice {
	
	@Id
	@Column(name = "product_id")
	private String productId;
	
	
	@Column(name = "invoice_id")
	private String invoiceId;
	
	@Column(name="quantity")
	private String quantity;

}
