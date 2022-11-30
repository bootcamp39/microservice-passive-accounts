package com.nttdata.microservice.bankpasiveaccounts.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpasiveaccounts.collections.PasiveAccountCollection;
import com.nttdata.microservice.bankpasiveaccounts.collections.enums.PassiveAccountTypeEnum;
import com.nttdata.microservice.bankpasiveaccounts.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankpasiveaccounts.repository.IPasiveAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PasiveAccountServiceImpl implements IPasiveAccountService{
	
	@Autowired
	private IPasiveAccountRepository pasiveAccountRepository;

	@Override
	public Flux<PasiveAccountCollection> getAll() throws Exception {
		// TODO Auto-generated method stub
		return pasiveAccountRepository.findAll();
	}

	@Override
	public Flux<PasiveAccountCollection> getByPersonCode(String code) throws Exception {
		// TODO Auto-generated method stub
		Flux<PasiveAccountCollection> col = pasiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(code));
		
		return col;
	}
	
	@Override
	public Mono<PasiveAccountCollection> getByAccountCode(String accountCode) throws Exception {
		// TODO Auto-generated method stub
		Mono<PasiveAccountCollection> col = pasiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(accountCode)).next();
		return col;
	}

	@Override
	public Mono<PasiveAccountCollection> save(PasiveAccountCollection collection) throws Exception{
		
		
		if(collection.getPersonType().equals(PersonTypeEnum.NATURAL_PERSON.toString())) {
			
			//obtiene cantidad de cuentas, solo puede tener una cuenta
			Mono<PasiveAccountCollection> pasiveAccount = 
					this.countAccountsByPersonCode(collection.getPersonCode())
			.flatMap(value -> 
			{
				if(value==0) {
					
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
					return pasiveAccountRepository.save(collection);
					
				}else {
					return Mono.empty();	
				}
				
				
			});
			
			if(pasiveAccount.equals(Mono.empty())) {
				throw new Exception("Un cliente personal solo puede tener una cuenta "); 
			}
			
			return pasiveAccount;
			
		}else if(collection.getPersonType().equals(PersonTypeEnum.LEGAL_PERSON.toString())) {
			
			if(collection.getAccountType().equals(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString())
					|| collection.getAccountType().equals(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString())) {
				
				throw new Exception("El cliente de tipo empresa no puede tener una cuenta de ahorros o de plazo fijo");
				
			}else if(collection.getAccountType().equals(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())) {
				
				collection.setCommission(0.00); //0 de comisión
				collection.setAvailableMovementsPerMonthAvailable(1); // 1 movimiento: deposito o retiro por mes
				
				return pasiveAccountRepository.save(collection);
				
			}else{
				throw new Exception("Tipo de cuenta no encontrado");	
			}
			
		}else {
			throw new Exception("Tipo de persona no encontrado");
		}
		
	}

	@Override
	public Mono<PasiveAccountCollection> update(PasiveAccountCollection updatedCollection, String accountCode)
			throws Exception {
		// TODO Auto-generated method stub
		Mono<PasiveAccountCollection> result = this.getByAccountCode(accountCode);
		PasiveAccountCollection resultNew = result.block();
		resultNew.setWithdrawalAmountPerMonthAvailable(updatedCollection.getWithdrawalAmountPerMonthAvailable());
		resultNew.setDepositAmountPerMonthAvailable(updatedCollection.getDepositAmountPerMonthAvailable());
		resultNew.setDayMovementAvailable(updatedCollection.getDayMovementAvailable());
		
		return pasiveAccountRepository.save(resultNew);
	}

	@Override
	public Mono<Void> delete(PasiveAccountCollection collection) throws Exception {
		// TODO Auto-generated method stub
		Mono<PasiveAccountCollection> result = pasiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(collection.getAccountNumber())).next();
		PasiveAccountCollection resultNew = result.block();
		
		return pasiveAccountRepository.delete(resultNew);
	}

	@Override
	public Mono<Long> countAccountsByPersonCode(String personCode) {
		return pasiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(personCode)).count();
	}
	
	/*@Override
	public Mono<Long> countCurrencyAccountsByPersonCode(String personCode) {
		return pasiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(personCode) 
				&& x.getAccountType().equals(PassiveAccountTypeEnum.CURRENT_ACCOUNT)).count();
	}*/

}
