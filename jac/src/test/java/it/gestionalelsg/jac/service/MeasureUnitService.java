package it.gestionalelsg.jac.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.gestionalejaclsg.jac.service.MeasureService;

@SpringBootTest
public class MeasureUnitService {
	
	@Autowired
	MeasureService measureUnitService;
	
	@Test
	public void findMeasureUnitIdByDescription() {
		String um="N";
		
		int id=this.measureUnitService.findMeasureIdByMeasureUnit(um);
		assertEquals(1,id);
	}

}
