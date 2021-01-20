package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.Customer;

import lombok.Data;

@Data

public class CustomerDTO {
	
	private int id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	public static CustomerDTO build(Customer customer) {

		CustomerDTO result = new CustomerDTO();
		BeanUtils.copyProperties(customer, result);

		return result;
	}
	

}
