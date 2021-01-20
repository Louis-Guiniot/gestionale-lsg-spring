package it.gestionalejaclsg.jac.dto;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import lombok.Data;
@EntityScan("it.gestionalejaclsg.jac.dto")
@Data
public class Response<T> {
	
	private T result;
	
	private boolean resultTest;
	
	private String error;
	
}
