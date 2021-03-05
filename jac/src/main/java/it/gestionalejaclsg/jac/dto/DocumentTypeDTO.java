package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.DocumentType;
import it.gestionalejaclsg.jac.entity.PayCondition;
import lombok.Data;

@Data

public class DocumentTypeDTO {
	
	private int id;
	
	private String tipoDocumento;
	
	public static DocumentTypeDTO build(DocumentType documentType) {

		DocumentTypeDTO result = new DocumentTypeDTO();
		BeanUtils.copyProperties(documentType, result);

		return result;
	}
}
