package com.nttdata.microservice.bankpassiveaccounts.services;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPassiveAccountService {
	public Flux<PassiveAccountCollection> getAll() throws Exception;
	public Flux<PassiveAccountCollection> getByPersonCode(String code) throws Exception;
	public Mono<PassiveAccountCollection> getByAccountCode(String accountCode) throws Exception;
	public Mono<PassiveAccountCollection> save(PassiveAccountCollection collection) throws Exception;
	public Mono<PassiveAccountCollection> update(PassiveAccountCollection updatedCollection, String code) throws Exception;
	public Mono<Void> delete(PassiveAccountCollection collection) throws Exception;
}
