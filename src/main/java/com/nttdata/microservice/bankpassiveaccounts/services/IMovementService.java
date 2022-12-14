package com.nttdata.microservice.bankpassiveaccounts.services;

import java.time.LocalDate;

import com.nttdata.microservice.bankpassiveaccounts.collections.MovementsCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMovementService {
	
	public Mono<MovementsCollection> saveDeposit(MovementsCollection collection);
	public Mono<MovementsCollection> saveWithdrawal(MovementsCollection collection);
	public Mono<MovementsCollection> saveTransferWithSameAccount(MovementsCollection collection);
	public Mono<MovementsCollection> saveTransferThirdAccount(MovementsCollection collection);
	public Mono<MovementsCollection> saveDepositWithDebitCard(MovementsCollection collection);
	public Mono<MovementsCollection> saveWithdrawalWithDebitCard(MovementsCollection collection);
	
	//public Mono<Long> countMovementsByAccountNumberAndCreatedAt(String accountNumber, LocalDate start, LocalDate end);
	public Flux<MovementsCollection> getByAccountNumber(String accountNumber);
	
	//public Mono<Double> getMaxAverageByAccountNumber(String personCode);
	
	
	//public Mono<MovementsCollection> save(MovementsCollection collection) throws Exception;
	//public Mono<Long> countByAccountCodeAndMonth(String code) throws Exception;
	//public Flux<MovementsCollection> getByAccountCode(String code) throws Exception;

}
