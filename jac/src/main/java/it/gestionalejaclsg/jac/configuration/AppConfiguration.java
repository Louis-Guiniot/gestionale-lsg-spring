package it.gestionalejaclsg.jac.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)


public class AppConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers().permitAll()//end point senza controllo di nessun tipo, quindi accessibile ad ogni client
		.anyRequest().permitAll()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/product/**").allowedMethods("GET", "POST");
		registry.addMapping("/rest/invoice/**").allowedMethods("GET", "POST");
		registry.addMapping("/rest/customer/**").allowedMethods("GET", "POST");
		registry.addMapping("/rest/measure/**").allowedMethods("GET", "POST");
		registry.addMapping("/rest/iva/**").allowedMethods("GET", "POST");
		//registry.addMapping("/rest/user/**").allowedMethods("GET", "POST").allowedOrigins("localhost:4200/**");
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

}