package com.nttdata.microservice.bankpassiveaccounts.services;

import com.nttdata.microservice.bankpassiveaccounts.collections.MovementsCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMovementService {
	
	public Mono<MovementsCollection> save(MovementsCollection collection) throws Exception;
	public Mono<Long> countByAccountCodeAndMonth(String code) throws Exception;
	public Flux<MovementsCollection> getByAccountCode(String code) throws Exception;

}
