package it.gestionalejaclsg.jac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionalejaclsg.jac.dto.Response;
import it.gestionalejaclsg.jac.entity.Iva;
import it.gestionalejaclsg.jac.service.IvaService;

@RestController
@RequestMapping("/rest/iva")
public class IvaRestController {
	private static Logger log = LoggerFactory.getLogger(IvaRestController.class);
	
	@Autowired
	private IvaService ivaService;
	
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllIvas() {

		log.info("\n\n\nfind all ivas");
		
		return ivaService.findAllIvas();
		
	}
	
	@PostMapping(path="/create")
	public Response<?> createIva(@RequestBody String body) {
		
		Iva iva=new Iva();

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
		int conta = 0;
		int[] arr = new int[body.length()+1];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		int measureId = this.ivaService.findLastIva().getResult().getId()+1;
		String tipoIva = body.substring(arr[2] + 1, arr[3]);
		String info= body.substring(arr[6] + 1, arr[7]);
		log.info("\n\ntipoIva: "+tipoIva+"\n\n");

		iva.setId(measureId);
		iva.setPercentualeIva(tipoIva);
		iva.setInfo(info);
		
		return ivaService.createIva(iva);
		
	}
	

	@PostMapping(path="/delete")
	public Response<?> deleteMeasureById(@RequestBody String body){
	
		
		int conta = 0;
		int[] arr = new int[body.length()];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		
		String id = body.substring(arr[2] + 1, arr[3]);
		
		return ivaService.deleteIvaById(Integer.parseInt(id));
	}
	
	@PostMapping(path ="/update")
	public Response<?> updateIva(@RequestBody String body) {
		
		Iva iva=new Iva();

		log.info("\n\n\n\nbody: " + body + "\n\n\n");
		
		int conta = 0;
		int[] arr = new int[body.length()+1];
		for (int i = 0; i < body.length(); i++) {
			if (body.charAt(i) == '"') {
				arr[conta] = i;
				conta++;
			}
		}
		log.info("\n\n inizio substrings \n\n");
		
		int measureId = this.ivaService.findLastIva().getResult().getId()+1;
		String tipoIva = body.substring(arr[2] + 1, arr[3]);
		log.info("\n\ntipoIva: "+tipoIva+"\n\n");

		iva.setId(measureId);
		iva.setPercentualeIva(tipoIva);
		
		return ivaService.updateIva(iva);
	}
	
	
	
	

}
