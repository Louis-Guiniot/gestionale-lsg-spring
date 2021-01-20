package it.gestionalejaclsg.jac.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.gestionalejaclsg.jac.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	
}
