package com.nttdata.microservice.bankpasiveaccounts.services;

import com.nttdata.microservice.bankpasiveaccounts.collections.PasiveAccountCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPasiveAccountService {
	public Flux<PasiveAccountCollection> getAll() throws Exception;
	public Flux<PasiveAccountCollection> getByPersonCode(String code) throws Exception;
	public Mono<PasiveAccountCollection> getByAccountCode(String accountCode) throws Exception;
	
	public Mono<Long> countAccountsByPersonCode(String personCode);
	
	public Mono<PasiveAccountCollection> save(PasiveAccountCollection collection) throws Exception;
	public Mono<PasiveAccountCollection> update(PasiveAccountCollection updatedCollection, String code) throws Exception;
	public Mono<Void> delete(PasiveAccountCollection collection) throws Exception;
}
