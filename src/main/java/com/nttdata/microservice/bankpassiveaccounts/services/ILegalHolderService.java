package com.nttdata.microservice.bankpassiveaccounts.services;

import com.nttdata.microservice.bankpassiveaccounts.collections.LegalHolderCollection;

import reactor.core.publisher.Mono;

public interface ILegalHolderService {
	
	public Mono<LegalHolderCollection> save(LegalHolderCollection collection) throws Exception;

}
