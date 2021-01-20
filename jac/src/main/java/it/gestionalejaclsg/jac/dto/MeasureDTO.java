package it.gestionalejaclsg.jac.dto;

import org.springframework.beans.BeanUtils;

import it.gestionalejaclsg.jac.entity.MeasureUnit;
import lombok.Data;

@Data

public class MeasureDTO {

		private int id;
		
		private String type;
		
		public static MeasureDTO build(MeasureUnit measure) {

			MeasureDTO result = new MeasureDTO();
			BeanUtils.copyProperties(measure, result);

			return result;
		}
		
}
