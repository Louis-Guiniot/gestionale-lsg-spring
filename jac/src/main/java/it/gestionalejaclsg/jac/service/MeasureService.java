package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.MeasureRepository;
import it.gestionalejaclsg.jac.dto.MeasureDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.MeasureUnit;

@Service

public class MeasureService {

	@Autowired
	MeasureRepository measureRepository;
	
	final static String error = "Nessuna measureUnit trovata.";

	// create

	public Response<MeasureUnit> createMeasureUnit(MeasureUnit measureUnit) {

		Response<MeasureUnit> response = new Response<MeasureUnit>();

		try {
			this.measureRepository.save(measureUnit);

			response.setResult(measureUnit);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("MeasureUnit non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteMeasureUnitById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.measureRepository.deleteById(id);

			response.setResult("measureUnit eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("measureUnit non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<MeasureDTO>> findAllMeasureUnits() {

		Response<List<MeasureDTO>> response = new Response<List<MeasureDTO>>();

		List<MeasureDTO> result = new ArrayList<>();

		try {

			Iterator<MeasureUnit> iterator = this.measureRepository.findAll().iterator();

			while (iterator.hasNext()) {

				MeasureUnit measureUnit = iterator.next();
				result.add(MeasureDTO.build(measureUnit));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	
	

	// find measureUnit by id
	public Response<MeasureDTO> findMeasureUnitById(int id) {

		Response<MeasureDTO> response = new Response<MeasureDTO>();

		try {

			MeasureUnit measureUnit = this.measureRepository.findById(id).get();

			response.setResult(MeasureDTO.build(measureUnit));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	public int findMeasureIdByMeasureUnit(String unit) {

		int id=0;
		
		try {
			
			MeasureUnit m=this.measureRepository.findByType(unit);
			
			if(m.getType().equals(unit)) {
				id=m.getId();
			}
			
		} catch (Exception e) {

			e.getStackTrace();
		}

		return id;

	}
	
//	 update measureUnit
	public Response<MeasureDTO> updateMeasureUnit(int id, String type) {

		Response<MeasureDTO> response = new Response<MeasureDTO>();

		try {
			MeasureUnit measureUnit = this.measureRepository.findById(id).get();

			if (type != null)
				measureUnit.setType(type);

			this.measureRepository.save(measureUnit);

			response.setResult(MeasureDTO.build(measureUnit));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}
}
