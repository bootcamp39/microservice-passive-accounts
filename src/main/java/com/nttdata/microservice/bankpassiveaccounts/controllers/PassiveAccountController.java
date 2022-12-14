package com.nttdata.microservice.bankpassiveaccounts.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;
import com.nttdata.microservice.bankpassiveaccounts.dto.CurrentAccountEnterpriseDto;
import com.nttdata.microservice.bankpassiveaccounts.dto.CurrentAccountPersonalDto;
import com.nttdata.microservice.bankpassiveaccounts.dto.FixTermPersonalDto;
import com.nttdata.microservice.bankpassiveaccounts.dto.SavingAccountPersonalDto;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "passive-accounts")
public class PassiveAccountController {
	
	private static Logger logger = Logger.getLogger(PassiveAccountController.class);

	@Autowired
	private IPassiveAccountService pasiveAccountService;
	
	/*@GetMapping("/list-passive-accounts")
	public Flux<PassiveAccountCollection> getAllPasiveAccounts() throws Exception {
		logger.info("get all pasive accounts");
		return pasiveAccountService.getAll();
	}*/

	/*@GetMapping("/find/{personCode}")
	public Mono<PassiveAccountCollection> getAccountByPersonCode(@PathVariable("personCode") String personCode)
			throws Exception {
		logger.info("get account by personCode");
		return pasiveAccountService.getByAccountCode(personCode);
	}*/
	
	@PostMapping(value = "/saveCurrentPersonalAccount")
	public Mono<PassiveAccountCollection> saveCurrentPersonalAccount(@RequestBody CurrentAccountPersonalDto dto) throws Exception{
		logger.info("save passive account");
		PassiveAccountCollection passiveAccountCollection = new PassiveAccountCollection();
		passiveAccountCollection.setAccountNumber(dto.getAccountNumber());
		passiveAccountCollection.setPersonCode(dto.getPersonCode());
		passiveAccountCollection.setMaintenanceCommission(dto.getMaintenanceCommission());
		passiveAccountCollection.setMaximumTransactionsWithoutCommission(dto.getMaximumTransactionsWithoutCommission());
		return pasiveAccountService.saveCurrentPersonalAccount(passiveAccountCollection);
	}
	
	@PostMapping(value = "/saveSavingPersonalAccount")
	public Mono<PassiveAccountCollection> saveSavingPersonalAccount(@RequestBody SavingAccountPersonalDto dto) throws Exception{
		logger.info("save passive account");
		PassiveAccountCollection passiveAccountCollection = new PassiveAccountCollection();
		passiveAccountCollection.setAccountNumber(dto.getAccountNumber());
		passiveAccountCollection.setPersonCode(dto.getPersonCode());
		passiveAccountCollection.setMaximumTransactions(dto.getMaximumTransactions());
		return pasiveAccountService.saveSavingPersonalAccount(passiveAccountCollection);
	}
	
	@PostMapping(value = "/saveFixTermPersonalAccount")
	public Mono<PassiveAccountCollection> saveFixTermPersonalAccount(@RequestBody FixTermPersonalDto dto) throws Exception{
		logger.info("save passive account");
		PassiveAccountCollection passiveAccountCollection = new PassiveAccountCollection();
		passiveAccountCollection.setAccountNumber(dto.getAccountNumber());
		passiveAccountCollection.setPersonCode(dto.getPersonCode());
		passiveAccountCollection.setDayMovementAvailable(dto.getDayMovementAvailable());
		return pasiveAccountService.saveFixTermPersonalAccount(passiveAccountCollection);
	}
	
	@PostMapping(value = "/saveCurrentEnterpriseAccount")
	public Mono<PassiveAccountCollection> saveCurrentEnterpriseAccount(@RequestBody CurrentAccountEnterpriseDto dto) throws Exception{
		logger.info("save passive account");
		PassiveAccountCollection passiveAccountCollection = new PassiveAccountCollection();
		passiveAccountCollection.setAccountNumber(dto.getAccountNumber());
		passiveAccountCollection.setPersonCode(dto.getPersonCode());
		passiveAccountCollection.setMaintenanceCommission(dto.getMaintenanceCommission());
		passiveAccountCollection.setMaximumTransactionsWithoutCommission(dto.getMaximumTransactionsWithoutCommission());
		
		return pasiveAccountService.saveCurrentEnterpriseAccount(passiveAccountCollection);
	}
	
	@PostMapping(value = "/saveVipPersonalAccount")
	public Mono<PassiveAccountCollection> saveVipPersonalAccount(@PathVariable("accountNumber") String accountNumber, @PathVariable("minimumAverageAmount") Double minimumAverageAccount) throws Exception{
		logger.info("save passive account");
		return pasiveAccountService.saveVipPersonalAccount(accountNumber, minimumAverageAccount);
	}
	
	@PostMapping(value = "/savePymeEnterpriseAccount")
	public Mono<PassiveAccountCollection> savePymeEnterpriseAccount(@PathVariable("accountNumber") String accountNumber) throws Exception{
		logger.info("save passive account");
		return pasiveAccountService.savePymeEnterpriseAccount(accountNumber);
	}
	
	
	@GetMapping("/find/{accountNumber}")
	public Mono<Double> getAccountBalance(@PathVariable("accountNumber") String accountNumber)
			throws Exception {
		logger.info("get account balance by account number");
		return pasiveAccountService.getAccountBalance(accountNumber);
	}
	
	
	/*@PutMapping(value = "/update/{personCode}")
	public Mono<PassiveAccountCollection> updatePasiveAccount(@RequestParam("personCode") String personCode,
			@RequestBody PassiveAccountCollection updateCollection) throws Exception {

		Mono<PassiveAccountCollection> result = pasiveAccountService.getByAccountCode(personCode);
		PassiveAccountCollection resultCollection = result.block();

		Mono<PassiveAccountCollection> resultUpdated = null;

		try {
			if (resultCollection != null) {
				resultUpdated = pasiveAccountService.update(resultCollection, personCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultUpdated;
	}*/
	
	/*@DeleteMapping(value = "/delete")
	public Mono<Void> deletePasiveAccount(@RequestBody PassiveAccountCollection collection) throws Exception{
		return pasiveAccountService.delete(collection);
	}*/

}