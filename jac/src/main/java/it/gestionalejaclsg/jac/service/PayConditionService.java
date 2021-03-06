package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.PayConditionRepository;

import it.gestionalejaclsg.jac.dto.PayConditionDTO;
import it.gestionalejaclsg.jac.dto.Response;

import it.gestionalejaclsg.jac.entity.PayCondition;


@Service
public class PayConditionService {
	
	@Autowired
	private PayConditionRepository payConditionRepository;
	
	final static String error = "Nessuna payCondition trovata.";

	// create

	public Response<PayCondition> createPaymentCondition(PayCondition payCondition) {

		Response<PayCondition> response = new Response<PayCondition>();

		try {
			this.payConditionRepository.save(payCondition);

			response.setResult(payCondition);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("PayCondition non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteMeasureUnitById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.payConditionRepository.deleteById(id);

			response.setResult("payCondition eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("payCondition non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<PayConditionDTO>> findAllPaymentConditions() {

		Response<List<PayConditionDTO>> response = new Response<List<PayConditionDTO>>();

		List<PayConditionDTO> result = new ArrayList<>();

		try {

			Iterator<PayCondition> iterator = this.payConditionRepository.findAll().iterator();

			while (iterator.hasNext()) {

				PayCondition payCondition = iterator.next();
				result.add(PayConditionDTO.build(payCondition));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
//	public Response<PayConditionDTO> findLastMeasure() {
//
//		Response<List<PayConditionDTO>> response = new Response<List<PayConditionDTO>>();
//	
//		int last=0;
//		try {
//
//			Iterator<PayCondition> iterator = this.payConditionRepository.findAll().iterator();
//
//			while (iterator.hasNext()) {
//
//				PayCondition payCondition = iterator.next();
//				
//				if(!iterator.hasNext()) {						
//					last=payCondition.getId();
//				}
//
//			}
//			
//
//
//		} catch (Exception e) {
//
//			response.setError(error);
//
//		}
//		
//		return findMeasureUnitById(last);
//
//	}
//	
	

	// find payCondition by id
	public Response<PayConditionDTO> findPaymentConditionById(int id) {

		Response<PayConditionDTO> response = new Response<PayConditionDTO>();

		try {

			PayCondition payCondition = this.payConditionRepository.findById(id).get();

			response.setResult(PayConditionDTO.build(payCondition));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	
//	 update payCondition
	public Response<PayConditionDTO> payCondition(PayCondition pc) {

		Response<PayConditionDTO> response = new Response<PayConditionDTO>();

		try {

			this.payConditionRepository.save(pc);

			response.setResult(PayConditionDTO.build(pc));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}


}
