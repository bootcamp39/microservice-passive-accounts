package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PassiveAccountTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.facade.IPassiveAccountFacade;
import com.nttdata.microservice.bankpassiveaccounts.repository.IPassiveAccountRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.IMovementService;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PassiveAccountServiceImpl implements IPassiveAccountService{
	
	@Autowired
	private IPassiveAccountRepository repository;
	
	@Autowired
	private IPassiveAccountFacade facade;
	
	@Autowired
	private IMovementService movementService;
	
	/*@Override
	public Flux<PassiveAccountCollection> getAll() throws Exception {
		// TODO Auto-generated method stub
		return passiveAccountRepository.findAll();
	}*/

	/*@Override
	public Flux<PassiveAccountCollection> getByPersonCode(String code) throws Exception {
		// TODO Auto-generated method stub
		Flux<PassiveAccountCollection> col = passiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(code));
		
		return col;
	}*/
	
	/*@Override
	public Mono<PassiveAccountCollection> getByAccountCode(String accountCode) {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> col = passiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(accountCode)).next();
		return col;
	}*/
	
	/*private Mono<Long> countAccountsByPersonCode(String personCode) {
		return passiveAccountRepository.findAll().filter(x -> x.getPersonCode().equals(personCode)).count();
	}*/
	
	/*private Mono<PassiveAccountCollection> saveAccountForNaturalPerson(PassiveAccountCollection collection) throws Exception{
		
		return passiveAccountRepository.countByPersonCode(collection.getPersonCode())
		.flatMap(quantityAccounts -> 
		{
			if(quantityAccounts==0) {
				return passiveAccountSettingsService.getByType(collection.getAccountType())
						.flatMap(settings -> {
							if(collection.getAccountAmount() >= settings.getMinimumOpeningAmount()) {
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
	}*/
	
	/*private Mono<PassiveAccountCollection> saveAccountForLegalPerson(PassiveAccountCollection collection) throws Exception{
		
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
	}*/
	

	/*@Override
	public Mono<PassiveAccountCollection> save(PassiveAccountCollection collection) throws Exception {
		
		if(collection.getPersonType().equals(PersonTypeEnum.NATURAL_PERSON.toString())) {
			return saveAccountForNaturalPerson(collection);
		}else if(collection.getPersonType().equals(PersonTypeEnum.LEGAL_PERSON.toString())) {
			return saveAccountForLegalPerson(collection);
		}else {
			throw new Exception("Invalid person type!");
		}
	}*/

	@Override
	public Mono<PassiveAccountCollection> saveCurrentPersonalAccount(PassiveAccountCollection collection)
			{
		
		return getMinimumOpeningAmount(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())
				.flatMap( x -> {
					
					//SETTING DEFAULT VALUES
					collection.setAccountType(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString());
					collection.setPersonType(PersonTypeEnum.PERSONAL.toString());
					collection.setAccountNumber(UUID.randomUUID().toString());
					collection.setCreatedAt(new Date());
					
					//VALIDATE MINIMUM OPENING AMOUNT
					if(collection.getMinimumOpenningAmount() < x) {
						return Mono.error(Exception::new);
					}
					
					return checkIfHaveMoreThanOneAccount(collection.getPersonCode()).flatMap(y -> {
						
						//CHECK IF HAVE MORE THAN ONE ACCOUNT
						if(y == true) {
							return Mono.error(Exception::new);
						}
						
						return facade.checkIfHaveDebt(collection.getPersonCode()).flatMap(z -> {
							
							//CHECK IF HAVE DEBT
							if(z == true) {
								return Mono.error(Exception::new);	
							}
							
							//SAVE ACCOUNT
							return repository.save(collection);
						});
						
						
					});
						
				});
		
	}

	@Override
	public Mono<PassiveAccountCollection> saveSavingPersonalAccount(PassiveAccountCollection collection)
			{
		
		return getMinimumOpeningAmount(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString())
				.flatMap( x -> {
					
					//SETTING DEFAULT VALUES
					collection.setAccountType(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString());
					collection.setPersonType(PersonTypeEnum.PERSONAL.toString());
					collection.setAccountNumber(UUID.randomUUID().toString());
					collection.setCreatedAt(new Date());
					
					//VALIDATE MINIMUM OPENING AMOUNT
					if(collection.getMinimumOpenningAmount() < x) {
						return Mono.error(Exception::new);
					}
					
					return checkIfHaveMoreThanOneAccount(collection.getPersonCode()).flatMap(y -> {
						
						//CHECK IF HAVE MORE THAN ONE ACCOUNT
						if(y == true) {
							return Mono.error(Exception::new);
						}
						
						return facade.checkIfHaveDebt(collection.getPersonCode()).flatMap(z -> {
							
							//CHECK IF HAVE DEBT
							if(z == true) {
								return Mono.error(Exception::new);	
							}
							
							//SAVE ACCOUNT
							return repository.save(collection);
						});
					});
						
				});
	}

	@Override
	public Mono<PassiveAccountCollection> saveFixTermPersonalAccount(PassiveAccountCollection collection)
			{
		return getMinimumOpeningAmount(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString())
				.flatMap( x -> {
					
					//SETTING DEFAULT VALUES
					collection.setAccountType(PassiveAccountTypeEnum.FIX_TERM_ACCOUNT.toString());
					collection.setPersonType(PersonTypeEnum.PERSONAL.toString());
					collection.setAccountNumber(UUID.randomUUID().toString());
					collection.setCreatedAt(new Date());
					
					//VALIDATE MINIMUM OPENING AMOUNT
					if(collection.getMinimumOpenningAmount() < x) {
						return Mono.error(Exception::new);
					}
					
					return checkIfHaveMoreThanOneAccount(collection.getPersonCode()).flatMap(y -> {
						
						//CHECK IF HAVE MORE THAN ONE ACCOUNT
						if(y == true) {
							return Mono.error(Exception::new);
						}
						
						return facade.checkIfHaveDebt(collection.getPersonCode()).flatMap(z -> {
							
							//CHECK IF HAVE DEBT
							if(z == true) {
								return Mono.error(Exception::new);	
							}
							
							//SAVE ACCOUNT
							return repository.save(collection);
						});
					});
						
				});
	}

	@Override
	public Mono<PassiveAccountCollection> saveCurrentEnterpriseAccount(PassiveAccountCollection collection)
			{
		return getMinimumOpeningAmount(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString())
				.flatMap( x -> {
					
					//SETTING DEFAULT VALUES
					collection.setAccountType(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString());
					collection.setPersonType(PersonTypeEnum.ENTERPRISE.toString());
					collection.setAccountNumber(UUID.randomUUID().toString());
					collection.setCreatedAt(new Date());
					
					//VALIDATE MINIMUM OPENING AMOUNT
					if(collection.getMinimumOpenningAmount() < x) {
						return Mono.error(Exception::new);
					}
					
					return facade.checkIfHaveDebt(collection.getPersonCode()).flatMap(z -> {
						
						//CHECK IF HAVE DEBT
						if(z == true) {
							return Mono.error(Exception::new);	
						}
						
						//SAVE ACCOUNT
						return repository.save(collection);
					});
						
				});
	}

	@Override
	public Mono<PassiveAccountCollection> saveVipPersonalAccount(String numberAccount, Double minimumAverageAmount){
		
		//GET SAVING ACCOUNT
		return repository.findByAccountNumber(numberAccount)
				.next()
				.flatMap(account -> {
					//VALIDATE MINIMUM OPENING AMOUNT
					return checkIfHaveAverageAmount(numberAccount, minimumAverageAmount)
							.flatMap(result -> {
								if(result == false) {
									return Mono.error(RuntimeException::new);
								}
								//CHECK IF HAVE CREDIT CARD
								return facade.checkIfCreditCard(account.getPersonCode())
										.flatMap(a -> {
										
										if(a == false) {
											return Mono.error(RuntimeException::new);	
										}
										
										//CHECK IF HAVE DEBT
										return facade.checkIfHaveDebt(account.getPersonCode()).flatMap(z -> {
											if(z == true) {
												return Mono.error(Exception::new);	
											}
											
											//SETTING DEFAULT VALUES
											account.setAccountType(PassiveAccountTypeEnum.VIP.toString());
											account.setUpdatedAt(new Date());
											account.setMinimumAverageAmount(minimumAverageAmount);
											
											//SAVE ACCOUNT
											return repository.save(account);
											
										});
									
								});
							});
				});
		
	}

	@Override
	public Mono<PassiveAccountCollection> savePymeEnterpriseAccount(String accountNumber)
			 {
		//GET SAVING ACCOUNT
		return repository.findByAccountNumber(accountNumber)
				.next()
				.flatMap(account -> {

					//CHECK IF HAVE CREDIT CARD
					return facade.checkIfCreditCard(account.getPersonCode())
							.flatMap(a -> {
							
							if(a == false) {
								return Mono.error(RuntimeException::new);	
							}
							
							//CHECK IF HAVE DEBT
							return facade.checkIfHaveDebt(account.getPersonCode()).flatMap(z -> {
								if(z == true) {
									return Mono.error(Exception::new);	
								}
								
								//SETTING DEFAULT VALUES
								account.setAccountType(PassiveAccountTypeEnum.PYME.toString());
								account.setUpdatedAt(new Date());
								
								//SAVE ACCOUNT
								return repository.save(account);
								
							});
						
					});
				});
	}

	@Override
	public Mono<Boolean> checkIfHaveMoreThanOneAccount(String personCode) {
		return repository.countByPersonCode(personCode).flatMap(x -> {
			if(x > 0) {
				return Mono.just(true);
			}
			return Mono.just(false);
		});
	}

	@Override
	public Mono<Double> getMinimumOpeningAmount(String accountType) {
		
		switch(PassiveAccountTypeEnum.valueOf(accountType)){
		case CURRENT_ACCOUNT :
			return Mono.just(0.0);
		case FIX_TERM_ACCOUNT :
			return Mono.just(0.0);
		case SAVING_ACCOUNT :
			return Mono.just(0.0);
		case VIP :
			return Mono.just(0.0);
		case PYME :
			return Mono.just(0.0);	
		default:
			return Mono.just(0.0);
		} 
	}

	@Override
	public Mono<Integer> getMaximumTransactions(String accountNumber) {
		return repository.findByAccountNumber(accountNumber).next().flatMap(x -> {
			return Mono.just(x.getMaximumTransactions());
		});
	}

	@Override
	public Mono<Integer> getMaximumTransactionsWithoutCommission(String accountNumber) {
		return repository.findByAccountNumber(accountNumber).next().flatMap(x -> {
			return Mono.just(x.getMaximumTransactionsWithoutCommission());
		});
	}
	
	//@Override
	//public Mono<Double> getMaxAverageByAccountNumber(String personCode) {
		
		//return null;
		
		/*return repository.getByPersonCode(personCode)
				.next()
				.flatMap(x -> {
					return repository.countByAccountNumber(x.getAccountNumber())
							.flatMap(count -> {
								return repository.findByAccountNumber(x.getAccountNumber())
										.map(y -> y.getAmount())
										.reduce(0.0, (x1, x2) -> x1 + x2)
										.map(sum -> sum / count);
							});
					})
				.sort((a,b) -> a.compareTo(b))
				.next();*/
	//}
	

	/*@Override
	public Mono<PassiveAccountCollection> getSavingAccountWithMinimumAverageAmount(String personCode, Double minimumAverageAmount) {
		return repository.findByPersonCode(personCode)
				.filter(x -> x.getAccountType().equals(PassiveAccountTypeEnum.SAVING_ACCOUNT.toString()))
				.next()
		.flatMap( x -> {
			return Mono.just(x);
		});
	}*/

	/*@Override
	public Mono<Double> getMaintenanceCommission(String accountNumber) {
		return repository.findByAccountNumber(accountNumber)
				.map( x -> 
					
					movementService.getByAccountNumber(x.getAccountNumber())
					.map(y -> y.getAmount())
					.reduce(0.0, (x1, x2) -> x1 + x2)
					//.map(z -> z.doubleValue())
					.flatMap( z -> {
						return Mono.just(z);
					});
					
					//return x.getAccountAmount();
				);
			
				.next().flatMap(x -> {
			return movementService.getByAccountNumber(x.getAccountNumber())
					.map( y -> y.getAmount())
					.reduce(0.0, (x1, x2) -> x1 + x2)
					.flatMap(z -> {
						return movementService.countMovementsByAccountNumber(accountNumber)
								.flatMap(a -> {
									Double average = z / a;
									return Mono.just(average);
								});
					});
			//return Mono.just(x.getMaintenanceCommission());
		});
	}*/
	
	@Override
	public Mono<Double> getTransactionCommission(String accountNumber) {
		return repository.findByAccountNumber(accountNumber).next().flatMap(x -> {
			return Mono.just(x.getTransactionCommission());
		});
	}

	@Override
	public Flux<PassiveAccountCollection> getPassiveAccountsWithChargeCommissionPending(Date chargeCommissionDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<PassiveAccountCollection> updateChargeCommissionDate(String accountNumber, Date chargeCommissionDate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Mono<Boolean> checkIfPassiveAccountExists(String accountNumber) {
		return repository.findByAccountNumber(accountNumber)
				.switchIfEmpty(x -> Mono.just(false))
				.count().flatMap(x -> {
			if(x > 0) {
				return Mono.just(true);
			}
			return Mono.just(false);
		});
	}

	@Override
	public Mono<PassiveAccountCollection> updateAccountBalance(String accountNumber, Double newAccountBalance) {
		return repository.findByAccountNumber(accountNumber).next().flatMap(x -> {
			x.setAccountBalance(newAccountBalance);
			return repository.save(x);
		});
	}

	@Override
	public Mono<Double> getAccountBalance(String accountNumber) {
		return repository.findByAccountNumber(accountNumber).next().flatMap(x -> {
			return Mono.just(x.getAccountBalance());
		});
	}

	@Override
	public Mono<PassiveAccountCollection> updateDebitCardNumber(String accountNumber, String debitCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Flux<PassiveAccountCollection> getByPersonCode(String personCode) {
		return repository.findByPersonCode(personCode);
	}*/

	@Override
	public Mono<Boolean> checkIfHaveAverageAmount(String accountNumber, Double minimumAverageAmount) {
		
		// SET START DATA AND END DATE
		LocalDate local = LocalDate.now();
	    local = local.minusMonths(1);
	    
	    LocalDate start = LocalDate.of(local.getYear(), local.getMonth(), 1);
	    LocalDate end = LocalDate.of(local.getYear(), local.getMonth(),30);

	    // GET SAVING ACCOUNT
		/*return repository.findByAccountNumber(accountNumber)
				.next()
				.flatMap(account -> {*/
					
					//GET MOVEMENTS SUM AMOUNT A MONTH AGO
					return movementService.getByAccountNumber(accountNumber)
							.filter( mo ->  
								 mo.getCreatedAt().toInstant()
								.atZone(ZoneId.systemDefault())
								.toLocalDate().isBefore(start)
								
								&& mo.getCreatedAt().toInstant()
								.atZone(ZoneId.systemDefault())
								.toLocalDate().isAfter(end)
							)
							.map(movement -> movement.getAmount())
							.reduce(0.0, (x1, x2) -> x1 + x2)
							.flatMap(suma -> {
								
								//GET MOVEMENTS COUNT A MONTH AGO
								return movementService.getByAccountNumber(accountNumber)
										.filter( m -> m.getCreatedAt().toInstant()
												.atZone(ZoneId.systemDefault())
												.toLocalDate().isBefore(start)
												
												&& m.getCreatedAt().toInstant()
												.atZone(ZoneId.systemDefault())
												.toLocalDate().isAfter(end)
												)
										.count()
										.flatMap(cantidad -> {
											
											// CHECK IF MINIMUM AVERAGE AMOUNT IS GREATHER THAM CALCULATED AVERAGE AMOUNT 
											if( (suma/cantidad) > minimumAverageAmount) {
												return Mono.just(true);	
											}
											return Mono.just(false);
										});
								
							});
							
				//});
	}

	@Override
	public Mono<Double> getMaintenanceCommission(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Mono<PassiveAccountCollection> update(PassiveAccountCollection updatedCollection, String accountCode)
			throws Exception {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> result = this.getByAccountCode(accountCode);
		PassiveAccountCollection resultNew = result.block();
		resultNew.setWithdrawalAmountPerMonthAvailable(updatedCollection.getWithdrawalAmountPerMonthAvailable());
		resultNew.setDepositAmountPerMonthAvailable(updatedCollection.getDepositAmountPerMonthAvailable());
		resultNew.setDayMovementAvailable(updatedCollection.getDayMovementAvailable());
		
		return passiveAccountRepository.save(resultNew);
	}*/

	/*@Override
	public Mono<Void> delete(PassiveAccountCollection collection) throws Exception {
		// TODO Auto-generated method stub
		Mono<PassiveAccountCollection> result = passiveAccountRepository.findAll().filter(x -> x.getAccountNumber().equals(collection.getAccountNumber())).next();
		PassiveAccountCollection resultNew = result.block();
		
		return passiveAccountRepository.delete(resultNew);
	}*/

	
	
}
