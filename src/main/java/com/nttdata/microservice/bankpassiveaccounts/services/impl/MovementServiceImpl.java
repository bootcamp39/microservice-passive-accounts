package com.nttdata.microservice.bankpassiveaccounts.services.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpassiveaccounts.collections.MovementsCollection;
import com.nttdata.microservice.bankpassiveaccounts.repository.IMovementRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.IMovementService;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements IMovementService{

	@Autowired
	private IMovementRepository repository;
	
	//@Autowired
	//private IPassiveAccountService pasiveAccountService;

	/*@Override
	public Mono<MovementsCollection> save(MovementsCollection collection) throws Exception {
		
		
		return pasiveAccountService.getByAccountCode(collection.getPassiveAccountCode())
		.flatMap(value -> {
			
			if(value.getAccountType().equals(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString())) {
				try {
					return this.countByAccountCodeAndMonth(collection.getPassiveAccountCode())
					.flatMap(x -> {
						if(x <= value.getAvailableMovementsPerMonthAvailable() ) {
							return movementRepository.save(collection);
						}
						return Mono.empty();
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(value.getAccountType().equals(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())) {
				return movementRepository.save(collection);
			}else if(value.getAccountType().equals(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString())) {
				try {
					return this.countByAccountCodeAndMonth(collection.getPassiveAccountCode())
					.flatMap(x -> {
						if(x <= value.getAvailableMovementsPerMonthAvailable() ) {
							return movementRepository.save(collection);
						}
						return Mono.empty();
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return Mono.empty();
		});
		
		
	}*/

	/*@Override
	public Mono<Long> countByAccountCodeAndMonth(String code) throws Exception {
				
		return movementRepository.findAll().filter(x -> x.getPassiveAccountCode().equals(code)).count();
	}*/

	/*@Override
	public Flux<MovementsCollection> getByAccountCode(String code) throws Exception {
		return movementRepository.findAll().filter(x -> x.getPassiveAccountCode().equals(code));
	}*/

	@Override
	public Mono<MovementsCollection> saveDeposit(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovementsCollection> saveWithdrawal(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovementsCollection> saveTransferWithSameAccount(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovementsCollection> saveTransferThirdAccount(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovementsCollection> saveDepositWithDebitCard(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovementsCollection> saveWithdrawalWithDebitCard(MovementsCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Mono<Long> countMovementsByAccountNumberAndCreatedAt(String accountNumber) {
		
			    
		return repository.countByAccountNumberAndCreatedAtBetween(accountNumber, start, end)
				.filter( m -> m.getCreatedAt().toInstant()
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate().isBefore(start.toLocalDate())
					      
		      && m.getCreatedAt().toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDate().isAfter(end.toLocalDate())
		      
						)
				;
	}*/

	@Override
	public Flux<MovementsCollection> getByAccountNumber(String accountNumber) {
		return repository.findByAccountNumber(accountNumber);
	}

	
	
	
}
