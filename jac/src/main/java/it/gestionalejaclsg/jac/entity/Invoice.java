package it.gestionalejaclsg.jac.entity;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

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
	
	//bisogna capire se funzia --> se no usare codice e/o id
	@Column(name = "product_fields")
    HashMap<Integer, String> fields = new HashMap<>();
}
