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
	
	final static String error = "Nessun customer trovato.";
	
	// create
	
		public Response<Customer> createCustomer(Customer customer) {

			Response<Customer> response = new Response<Customer>();

			try {
				this.customerRepository.save(customer); 

				response.setResult(customer);
				response.setResultTest(true);

			} catch (Exception e) {
				e.getStackTrace();
				response.setError("customer non creato");

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
				response.setError("customer non eliminato correttamente.");
			}
			return response;
		}

		// findAll
		public Response<List<CustomerDTO>> findAllCustomers() {

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

				response.setError(error);

			}

			return response;

		}

		//find customer by id
		public Response<CustomerDTO> findCustomerById(int id) {

			Response<CustomerDTO> response = new Response<CustomerDTO>();

			try {

				Customer customer = this.customerRepository.findById(id).get();

				response.setResult(CustomerDTO.build(customer));
				response.setResultTest(true);

			} catch (Exception e) {

				response.setError(error);

			}

			return response;

		}

		//update customer
		public Response<CustomerDTO> updateUser(int id, String ragioneSociale, String partitaIva, String email, String sede, String residenza) {

			Response<CustomerDTO> response = new Response<CustomerDTO>();
			try {
				Customer customer = this.customerRepository.findById(id).get();

				if (ragioneSociale != null)
					customer.setRagioneSociale(ragioneSociale);
				
				if (partitaIva != null)
					customer.setPartitaIva(partitaIva);

				if (email != null)
					customer.setEmail(email);	
				
				if (sede != null)
					customer.setSede(sede);
				
				if (residenza != null)
					customer.setResidenza(residenza);
				
				
				this.customerRepository.save(customer);
				
				response.setResult(CustomerDTO.build(customer));
				response.setResultTest(true);

			} catch (Exception e) {
				
				response.setError(error);
				
			}	

			return response;
		}
		


}
