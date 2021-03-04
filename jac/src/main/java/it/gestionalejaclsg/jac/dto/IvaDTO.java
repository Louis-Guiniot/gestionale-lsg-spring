package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.Iva;
import lombok.Data;

@Data

public class IvaDTO {
private int id;
	
	private String percentualeIva;
	
	public static IvaDTO build(Iva iva) {

		IvaDTO result = new IvaDTO();
		BeanUtils.copyProperties(iva, result);

		return result;
	}
}
