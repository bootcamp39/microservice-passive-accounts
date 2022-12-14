package com.nttdata.microservice.bankpassiveaccounts.services;

import java.util.Date;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPassiveAccountService {
	//public Flux<PassiveAccountCollection> getAll() throws Exception;
	//public Flux<PassiveAccountCollection> getByPersonCode(String personCode);
	//public Mono<PassiveAccountCollection> getByAccountCode(String accountCode);
	
	//SAVE ACCOUNTS
	public Mono<PassiveAccountCollection> saveCurrentPersonalAccount(PassiveAccountCollection collection);
	public Mono<PassiveAccountCollection> saveSavingPersonalAccount(PassiveAccountCollection collection);
	public Mono<PassiveAccountCollection> saveFixTermPersonalAccount(PassiveAccountCollection collection);
	public Mono<PassiveAccountCollection> saveCurrentEnterpriseAccount(PassiveAccountCollection collection);
	
	public Mono<PassiveAccountCollection> saveVipPersonalAccount(String accountNumber, Double minimumAverageAmount);
	public Mono<PassiveAccountCollection> savePymeEnterpriseAccount(String accountNumber);
	
	//VALIDATORS
	public Mono<Boolean> checkIfHaveMoreThanOneAccount(String personCode);
	public Mono<Boolean> checkIfPassiveAccountExists(String accountNumber);
	public Mono<Boolean> checkIfHaveAverageAmount(String accountNumber, Double minimumAverageAmount);
	
	//UPDATE
	public Mono<PassiveAccountCollection> updateAccountBalance(String accountNumber, Double newAccountBalance);
	
	//CAMPOS
	public Mono<Double> getMinimumOpeningAmount(String accountType);
	public Mono<Integer> getMaximumTransactions(String accountNumber);
	public Mono<Integer> getMaximumTransactionsWithoutCommission(String accountNumber);
	
	
	public Mono<Double> getMaintenanceCommission(String accountNumber);
	public Mono<Double> getTransactionCommission(String accountNumber);
	public Mono<Double> getAccountBalance(String accountNumber);
	
	public Flux<PassiveAccountCollection> getPassiveAccountsWithChargeCommissionPending(Date chargeCommissionDate );
	public Mono<PassiveAccountCollection> updateChargeCommissionDate(String accountNumber, Date chargeCommissionDate);
	
	//public Mono<PassiveAccountCollection> getSavingAccountWithMinimumAverageAmount(String personCode, Double minimumAverageAmount);
	
	public Mono<PassiveAccountCollection> updateDebitCardNumber(String accountNumber, String debitCardNumber); 
	
	//public Mono<PassiveAccountCollection> save(PassiveAccountCollection collection) throws Exception;
	
	//public Mono<PassiveAccountCollection> update(PassiveAccountCollection updatedCollection, String code) throws Exception;
	//public Mono<Void> delete(PassiveAccountCollection collection) throws Exception;
}
