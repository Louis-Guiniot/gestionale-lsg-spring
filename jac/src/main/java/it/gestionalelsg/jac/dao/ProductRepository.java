package it.gestionalelsg.jac.dao;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.gestionalejaclsg.jac.entity.Product;

@Repository

@EntityScan("it.gestionalejaclsg.jac.dao")
public interface ProductRepository extends CrudRepository<Product, Integer>{

}
