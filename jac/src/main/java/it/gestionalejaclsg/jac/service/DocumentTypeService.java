package it.gestionalejaclsg.jac.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionalejaclsg.jac.dao.DocumentTypeRepository;
import it.gestionalejaclsg.jac.dto.DocumentTypeDTO;
import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.DocumentType;

@Service
public class DocumentTypeService {
	
	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	
	final static String error = "Nessuna payCondition trovata.";

	// create

	public Response<DocumentType> createDocType(DocumentType payCondition) {

		Response<DocumentType> response = new Response<DocumentType>();

		try {
			this.documentTypeRepository.save(payCondition);

			response.setResult(payCondition);
			response.setResultTest(true);

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("DocumentType non creata");

		}

		return response;

	}

	// delete
	public Response<String> deleteMeasureUnitById(int id) {

		Response<String> response = new Response<String>();

		try {
			this.documentTypeRepository.deleteById(id);

			response.setResult("payCondition eliminata.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("payCondition non eliminata correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<DocumentTypeDTO>> findAlDocTypes() {

		Response<List<DocumentTypeDTO>> response = new Response<List<DocumentTypeDTO>>();

		List<DocumentTypeDTO> result = new ArrayList<>();

		try {

			Iterator<DocumentType> iterator = this.documentTypeRepository.findAll().iterator();

			while (iterator.hasNext()) {

				DocumentType payCondition = iterator.next();
				result.add(DocumentTypeDTO.build(payCondition));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}
//	public Response<DocumentTypeDTO> findLastMeasure() {
//
//		Response<List<DocumentTypeDTO>> response = new Response<List<DocumentTypeDTO>>();
//	
//		int last=0;
//		try {
//
//			Iterator<DocumentType> iterator = this.documentTypeRepository.findAll().iterator();
//
//			while (iterator.hasNext()) {
//
//				DocumentType payCondition = iterator.next();
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
	public Response<DocumentTypeDTO> findDocTypeById(int id) {

		Response<DocumentTypeDTO> response = new Response<DocumentTypeDTO>();

		try {

			DocumentType payCondition = this.documentTypeRepository.findById(id).get();

			response.setResult(DocumentTypeDTO.build(payCondition));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	
//	 update payCondition
	public Response<DocumentTypeDTO> payCondition(DocumentType pc) {

		Response<DocumentTypeDTO> response = new Response<DocumentTypeDTO>();

		try {

			this.documentTypeRepository.save(pc);

			response.setResult(DocumentTypeDTO.build(pc));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}


}
