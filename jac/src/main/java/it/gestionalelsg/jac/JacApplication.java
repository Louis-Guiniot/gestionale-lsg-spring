package it.gestionalelsg.jac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
"it.gestionalejaclsg.jac.service", 
"it.gestionalejaclsg.jac.entity",
"it.gestionalejaclsg.jac.dto",
"it.gestionalejaclsg.jac.dao",
"it.gestionalejaclsg.jac.controller",
"it.gestionalejaclsg.jac.configuration",
})
public class JacApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacApplication.class, args);
	}

}
