package it.gestionalejaclsg.jac.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customer")
@Data


public class Customer {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "ragione_sociale")
	private String ragioneSociale;

	@Column(name = "partita_iva")
	private String partitaIva;
	
	@Column(name = "sede")
	private String sede;
	
	@Column(name = "residenza")
	private String residenza;
	
}
