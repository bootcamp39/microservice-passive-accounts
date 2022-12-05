package com.nttdata.microservice.bankpassiveaccounts.controllers;

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

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "passive-accounts")
public class PassiveAccountController {
	
	private static Logger logger = Logger.getLogger(PassiveAccountController.class);

	@Autowired
	private IPassiveAccountService pasiveAccountService;
	
	@GetMapping("/list-passive-accounts")
	public Flux<PassiveAccountCollection> getAllPasiveAccounts() throws Exception {
		logger.info("get all pasive accounts");
		return pasiveAccountService.getAll();
	}

	@GetMapping("/find/{personCode}")
	public Mono<PassiveAccountCollection> getAccountByPersonCode(@PathVariable("personCode") String personCode)
			throws Exception {
		logger.info("get account by personCode");
		return pasiveAccountService.getByAccountCode(personCode);
	}
	
	@PostMapping(value = "/store")
	public Mono<PassiveAccountCollection> savePasiveAccount(@RequestBody PassiveAccountCollection collection) throws Exception{
		logger.info("save passive account");
		return pasiveAccountService.save(collection);
	}
	
	@PutMapping(value = "/update/{personCode}")
	public Mono<PassiveAccountCollection> updatePasiveAccount(@RequestParam("personCode") String personCode,
			@RequestBody PassiveAccountCollection updateCollection) throws Exception {

		Mono<PassiveAccountCollection> result = pasiveAccountService.getByAccountCode(personCode);
		PassiveAccountCollection resultCollection = result.block();

		Mono<PassiveAccountCollection> resultUpdated = null;

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
	public Mono<Void> deletePasiveAccount(@RequestBody PassiveAccountCollection collection) throws Exception{
		return pasiveAccountService.delete(collection);
	}

}
