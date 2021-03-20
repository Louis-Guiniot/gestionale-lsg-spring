package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "product")
@Data


public class Product {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	private String codeProduct;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private String price;
	
	
	
	//migrata da measure unit
	@Column(name = "measure_unit")
	private String measureUnit;
	
	@Column(name = "measure_unit_id")
	private int measureUnit_id;

	@Column(name="sconto_prod")
	private String scontoProd;
	
	@Column(name="iva")
	private int iva;


	
}
