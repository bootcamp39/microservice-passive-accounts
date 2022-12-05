package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PassiveAccountTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.repository.IPassiveAccountRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountSettingsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PassiveAccountServiceImpl implements IPassiveAccountService{
	
	@Autowired
	private IPassiveAccountRepository passiveAccountRepository;
	
	@Autowired
	private IPassiveAccountSettingsService passiveAccountSettingsService;

	@Override
	public Flux<PassiveAccountCollection> getAll() throws Exception {
		// TODO Auto-generated method stub
		return passiveAccountRepository.findAll();
	}

	@Override
	public Flux<PassiveAccountCollection> getByPersonCode(String code) throws Exception {
		// TODO Auto-generated method stub
		Flux<PassiveAccountCollection> col = passiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(code));
		
		return col;
	}
	
	@Override
	public Mono<PassiveAccountCollection> getByAccountCode(String accountCode) throws Exception {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> col = passiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(accountCode)).next();
		return col;
	}
	
	private Mono<Long> countAccountsByPersonCode(String personCode) {
		return passiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(personCode)).count();
	}
	
	private Mono<PassiveAccountCollection> saveAccountForNaturalPerson(PassiveAccountCollection collection) throws Exception{
		
		return this.countAccountsByPersonCode(collection.getPersonCode())
		.flatMap(quantityAccounts -> 
		{
			if(quantityAccounts==0) {
				return passiveAccountSettingsService.getByType(collection.getAccountType())
						.flatMap(settings -> {
							if(collection.getAccountAmount() >= settings.getMinimunOpeningAmount()) {
								collection.setAccountNumber(UUID.randomUUID().toString());
								collection.setCreatedAt(new Date());
								if(collection.getAccountType().equals(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString())) {
									collection.setCommission(0.00); //0 de comisión
								}else if(collection.getAccountType().equals(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())) {
									collection.setAvailableMovementsPerMonthAvailable(-1); // -1 movimientos ilimitados
								}else if(collection.getAccountType().equals(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString())) {
									collection.setCommission(0.00); //0 de comisión
									collection.setAvailableMovementsPerMonthAvailable(1); // 1 movimiento: deposito o retiro por mes
								}
								return passiveAccountRepository.save(collection);	
							}else {
								return Mono.error(new Exception("The account amount must be greater than or equal to the minimum opening amount"));
							}
							
							
						}
						);
				
			}else {
				return Mono.error(new Exception("The natural person already has an account created"));	
			}
		});
	}
	
	private Mono<PassiveAccountCollection> saveAccountForLegalPerson(PassiveAccountCollection collection) throws Exception{
		
		if(collection.getAccountType().equals(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString())
				|| collection.getAccountType().equals(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString())) {
			throw new Exception("The legal person cannot have a saving account or fix term account");
		}else if(collection.getAccountType().equals(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())) {
			collection.setAccountNumber(UUID.randomUUID().toString());
			collection.setCreatedAt(new Date());
			collection.setCommission(0.00); //0 de comisión
			collection.setAvailableMovementsPerMonthAvailable(1); // 1 movimiento: deposito o retiro por mes
			return passiveAccountRepository.save(collection);
		}else{
			throw new Exception("Invalid account type!");	
		}
	}
	

	@Override
	public Mono<PassiveAccountCollection> save(PassiveAccountCollection collection) throws Exception {
		
		if(collection.getPersonType().equals(PersonTypeEnum.NATURAL_PERSON.toString())) {
			return saveAccountForNaturalPerson(collection);
		}else if(collection.getPersonType().equals(PersonTypeEnum.LEGAL_PERSON.toString())) {
			return saveAccountForLegalPerson(collection);
		}else {
			throw new Exception("Invalid person type!");
		}
	}

	@Override
	public Mono<PassiveAccountCollection> update(PassiveAccountCollection updatedCollection, String accountCode)
			throws Exception {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> result = this.getByAccountCode(accountCode);
		PassiveAccountCollection resultNew = result.block();
		resultNew.setWithdrawalAmountPerMonthAvailable(updatedCollection.getWithdrawalAmountPerMonthAvailable());
		resultNew.setDepositAmountPerMonthAvailable(updatedCollection.getDepositAmountPerMonthAvailable());
		resultNew.setDayMovementAvailable(updatedCollection.getDayMovementAvailable());
		
		return passiveAccountRepository.save(resultNew);
	}

	@Override
	public Mono<Void> delete(PassiveAccountCollection collection) throws Exception {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> result = passiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(collection.getAccountNumber())).next();
		PassiveAccountCollection resultNew = result.block();
		
		return passiveAccountRepository.delete(resultNew);
	}

	
	
}
