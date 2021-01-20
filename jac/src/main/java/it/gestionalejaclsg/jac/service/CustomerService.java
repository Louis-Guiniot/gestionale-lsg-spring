package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.CustomerRepository;
import it.gestionalejaclsg.jac.dto.CustomerDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Customer;

@Service

public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	// create
	
		public Response<Customer> createCustomer(Customer customer) {

			Response<Customer> response = new Response<Customer>();

			try {
				this.customerRepository.save(customer); 

				response.setResult(customer);
				response.setResultTest(true);

			} catch (Exception e) {
				e.getStackTrace();
				response.setError("Candidato non creato");

			}

			return response;

		}

		// delete
		public Response<String> deleteCustomerById(int id) {

			Response<String> response = new Response<String>();

			try {
				this.customerRepository.deleteById(id);

				response.setResult("customer eliminato.");
				response.setResultTest(true);

			} catch (Exception e) {
				response.setError("Customer non eliminato correttamente.");
			}
			return response;
		}

		// findAll
		public Response<List<CustomerDTO>> findAllUsers() {

			Response<List<CustomerDTO>> response = new Response<List<CustomerDTO>>();

			List<CustomerDTO> result = new ArrayList<>();

			try {

				Iterator<Customer> iterator = this.customerRepository.findAll().iterator();

				while (iterator.hasNext()) {

					Customer customer = iterator.next();
					result.add(CustomerDTO.build(customer));

				}

				response.setResult(result);
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError("Nessun elemento trovato.");

			}

			return response;

		}

		//find customer by id
		public Response<CustomerDTO> findUserById(int id) {

			Response<CustomerDTO> response = new Response<CustomerDTO>();

			try {

				Customer customer = this.customerRepository.findById(id).get();

				response.setResult(CustomerDTO.build(customer));
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError("Nessun elemento trovato.");

			}

			return response;

		}

		//update customer
		public Response<CustomerDTO> updateUser(int id, String name, String surname, String email) {

			Response<CustomerDTO> response = new Response<CustomerDTO>();

			try {
				Customer customer = this.customerRepository.findById(id).get();

				if (name != null)
					customer.setName(name);
				
				if (surname != null)
					customer.setSurname(surname);

				if (email != null)
					customer.setEmail(email);				
				
				
				this.customerRepository.save(customer);
				
				response.setResult(CustomerDTO.build(customer));
				response.setResultTest(true);

			} catch (Exception e) {
				
				response.setError("Nessun elemento trovato.");
				
			}	

			return response;
		}
		


}
