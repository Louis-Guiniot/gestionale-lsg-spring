package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.service.DocumentTypeService;

@RestController
@RequestMapping("/rest/doctype")
public class DocumentTypeRestController {

		private static Logger log = LoggerFactory.getLogger(DocumentTypeRestController.class);
		
		@Autowired
		private DocumentTypeService documentType;
		
		@GetMapping(path = "/findAll")
		public Response<?> findAllPayCond() {

			log.info("\n\n\nfind all payconditions");
			
			return documentType.findAlDocTypes();
			
		}

}
