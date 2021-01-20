package it.gestionalejaclsg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "it.gestionalejaclsg.jac")
public class JacApplication {

	public static void main(String[] args) {
		SpringApplication.run(JacApplication.class, args);
	}

}
