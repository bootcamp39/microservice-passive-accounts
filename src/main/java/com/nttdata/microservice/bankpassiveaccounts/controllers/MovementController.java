package com.nttdata.microservice.bankpassiveaccounts.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.microservice.bankpassiveaccounts.collections.MovementsCollection;
import com.nttdata.microservice.bankpassiveaccounts.services.IMovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "movement")
public class MovementController {
	
	private static Logger logger = Logger.getLogger(PassiveAccountController.class);
	
	@Autowired
	private IMovementService movementService;
	
	@PostMapping(value = "/store")
	public Mono<MovementsCollection> saveMovement(@RequestBody MovementsCollection collection) throws Exception{
		logger.info("save movement");
		return movementService.save(collection);
	}
	
	@GetMapping("/findAll/{accountCode}")
	public Flux<MovementsCollection> getMovementsByAccountCode(@PathVariable("accountCode") String accountCode)
			throws Exception {
		logger.info("get movement by account code");
		return movementService.getByAccountCode(accountCode);
	}

}
