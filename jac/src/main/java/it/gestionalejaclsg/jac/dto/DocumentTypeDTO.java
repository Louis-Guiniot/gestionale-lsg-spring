package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.DocumentType;

import lombok.Data;

@Data

public class DocumentTypeDTO {
	
	private int id;
	
	private String tipo;
	
	public static DocumentTypeDTO build(DocumentType documentType) {

		DocumentTypeDTO result = new DocumentTypeDTO();
		BeanUtils.copyProperties(documentType, result);

		return result;
	}
}
