package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.PayCondition;
import lombok.Data;

@Data

public class PayConditionDTO {
	
	private int id;
	
	private String condizione;
	
	public static PayConditionDTO build(PayCondition payCondition) {

		PayConditionDTO result = new PayConditionDTO();
		BeanUtils.copyProperties(payCondition, result);

		return result;
	}
}
