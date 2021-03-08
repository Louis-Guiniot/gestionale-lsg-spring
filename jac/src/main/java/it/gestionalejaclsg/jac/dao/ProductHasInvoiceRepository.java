package it.gestionalejaclsg.jac.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.gestionalejaclsg.jac.entity.ProductHasInvoice;
@Repository
public interface ProductHasInvoiceRepository extends CrudRepository<ProductHasInvoice, Integer>{
	
	public ProductHasInvoice findByInvoiceId(int id);
	public ProductHasInvoice findByQuantity(int id);
	public ProductHasInvoice findByProductId(int id);
	

}
