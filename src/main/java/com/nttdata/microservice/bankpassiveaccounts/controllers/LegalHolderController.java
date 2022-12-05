package com.nttdata.microservice.bankpassiveaccounts.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.microservice.bankpassiveaccounts.collections.LegalHolderCollection;
import com.nttdata.microservice.bankpassiveaccounts.services.ILegalHolderService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "legal-holder")
public class LegalHolderController {
	
	private static Logger logger = Logger.getLogger(PassiveAccountController.class);
	
	@Autowired
	private ILegalHolderService legalHolderService;

	@PostMapping(value = "/store")
	public Mono<LegalHolderCollection> saveLegalHolder(@RequestBody LegalHolderCollection collection) throws Exception{
		logger.info("save legal holder");
		return legalHolderService.save(collection);
	}
}
