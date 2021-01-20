package it.gestionalejaclsg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = "it.gestionalejaclsg.jac.entity")
//@EnableCrudRepositories(basePackages = {"it.gestionalejaclsg.jac.dao"})
public class JacApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacApplication.class, args);
	}

}
