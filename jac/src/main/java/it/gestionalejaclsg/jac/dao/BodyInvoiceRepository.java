package it.gestionalejaclsg.jac.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.gestionalejaclsg.jac.entity.BodyInvoice;

@Repository
public interface BodyInvoiceRepository extends CrudRepository<BodyInvoice, Integer>{

}
