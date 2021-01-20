package it.gestionalejaclsg.jac.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.gestionalejaclsg.jac.entity.MeasureUnit;

@Repository
public interface Measure extends CrudRepository<MeasureUnit, Integer>{

}
