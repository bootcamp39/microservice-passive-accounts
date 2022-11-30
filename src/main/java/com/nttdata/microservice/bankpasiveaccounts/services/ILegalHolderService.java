package com.nttdata.microservice.bankpasiveaccounts.services;

import com.nttdata.microservice.bankpasiveaccounts.collections.LegalHolderCollection;

import reactor.core.publisher.Mono;

public interface ILegalHolderService {
	
	public Mono<LegalHolderCollection> save(LegalHolderCollection collection) throws Exception;

}
