package it.gestionalejaclsg.jac.dao;

	import org.springframework.data.repository.CrudRepository;
	import org.springframework.stereotype.Repository;

	import it.gestionalejaclsg.jac.entity.TailInvoice;

	@Repository
	public interface TailInvoiceRepository extends CrudRepository<TailInvoice, Integer>{

	}


