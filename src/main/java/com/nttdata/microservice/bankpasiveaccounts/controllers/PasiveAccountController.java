package com.nttdata.microservice.bankpasiveaccounts.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.microservice.bankpasiveaccounts.collections.PasiveAccountCollection;
import com.nttdata.microservice.bankpasiveaccounts.services.IPasiveAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "passive-accounts")
public class PasiveAccountController {
	
	private static Logger logger = Logger.getLogger(PasiveAccountController.class);

	@Autowired
	private IPasiveAccountService pasiveAccountService;
	
	@GetMapping("/list-passive-accounts")
	public Flux<PasiveAccountCollection> getAllPasiveAccounts() throws Exception {
		logger.info("get all pasive accounts");
		return pasiveAccountService.getAll();
	}

	@GetMapping("/find/{personCode}")
	public Mono<PasiveAccountCollection> getAccountByPersonCode(@PathVariable("personCode") String personCode)
			throws Exception {
		logger.info("get account by personCode");
		return pasiveAccountService.getByAccountCode(personCode);
	}
	
	@PostMapping(value = "/store")
	public Mono<PasiveAccountCollection> savePasiveAccount(@RequestBody PasiveAccountCollection collection) throws Exception{
		logger.info("save passive account");
		return pasiveAccountService.save(collection);
	}
	
	@PutMapping(value = "/update/{personCode}")
	public Mono<PasiveAccountCollection> updatePasiveAccount(@RequestParam("personCode") String personCode,
			@RequestBody PasiveAccountCollection updateCollection) throws Exception {

		Mono<PasiveAccountCollection> result = pasiveAccountService.getByAccountCode(personCode);
		PasiveAccountCollection resultCollection = result.block();

		Mono<PasiveAccountCollection> resultUpdated = null;

		try {
			if (resultCollection != null) {
				resultUpdated = pasiveAccountService.update(resultCollection, personCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultUpdated;
	}
	
	@DeleteMapping(value = "/delete")
	public Mono<Void> deletePasiveAccount(@RequestBody PasiveAccountCollection collection) throws Exception{
		return pasiveAccountService.delete(collection);
	}

}
