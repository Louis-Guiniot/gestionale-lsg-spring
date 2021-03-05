package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.IvaRepository;
import it.gestionalejaclsg.jac.dto.IvaDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Iva;

@Service
public class IvaService {
	
	@Autowired
	private IvaRepository ivaRepository;
	
	final static String error = "Nessuna payCondition trovata.";

	// create

	public Response<Iva> createIva(Iva payCondition) {

		Response<Iva> response = new Response<Iva>();

		try {
			this.ivaRepository.save(payCondition);

			response.setResult(payCondition);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("Iva non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteIvaById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.ivaRepository.deleteById(id);

			response.setResult("payCondition eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("payCondition non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<IvaDTO>> findAllIvas() {

		Response<List<IvaDTO>> response = new Response<List<IvaDTO>>();

		List<IvaDTO> result = new ArrayList<>();

		try {

			Iterator<Iva> iterator = this.ivaRepository.findAll().iterator();

			while (iterator.hasNext()) {

				Iva payCondition = iterator.next();
				result.add(IvaDTO.build(payCondition));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
	public Response<IvaDTO> findLastIva() {

		Response<List<IvaDTO>> response = new Response<List<IvaDTO>>();
	
		int last=0;
		try {

			Iterator<Iva> iterator = this.ivaRepository.findAll().iterator();

			while (iterator.hasNext()) {

				Iva iva= iterator.next();
				
				if(!iterator.hasNext()) {						
					last=iva.getId();
				}

			}
			


		} catch (Exception e) {

			response.setError(error);

		}
		
		return findIvaById(last);

	}
	
//	public Response<IvaDTO> findLastMeasure() {
//
//		Response<List<IvaDTO>> response = new Response<List<IvaDTO>>();
//	
//		int last=0;
//		try {
//
//			Iterator<Iva> iterator = this.ivaRepository.findAll().iterator();
//
//			while (iterator.hasNext()) {
//
//				Iva payCondition = iterator.next();
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
	public Response<IvaDTO> findIvaById(int id) {

		Response<IvaDTO> response = new Response<IvaDTO>();

		try {

			Iva payCondition = this.ivaRepository.findById(id).get();

			response.setResult(IvaDTO.build(payCondition));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	
//	 update payCondition
	public Response<IvaDTO> updateIva(Iva pc) {

		Response<IvaDTO> response = new Response<IvaDTO>();

		try {

			this.ivaRepository.save(pc);

			response.setResult(IvaDTO.build(pc));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}


}
