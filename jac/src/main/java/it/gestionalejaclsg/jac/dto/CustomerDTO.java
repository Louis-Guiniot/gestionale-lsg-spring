package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.Customer;

import lombok.Data;

@Data

public class CustomerDTO {
	
	private int id;
	
	private String name;
	
	private String email;
	
	private String ragioneSociale;
	
	private String partitaIva;
	
	private String sede;
	
	private String residenza;
	
	public static CustomerDTO build(Customer customer) {

		CustomerDTO result = new CustomerDTO();
		BeanUtils.copyProperties(customer, result);

		return result;
	}
	

}
