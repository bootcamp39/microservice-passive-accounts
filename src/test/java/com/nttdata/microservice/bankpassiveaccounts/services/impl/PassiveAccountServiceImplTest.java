package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountCollection;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PassiveAccountTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.repository.IPassiveAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PassiveAccountServiceImplTest {
	
	@InjectMocks
	private PassiveAccountServiceImpl service;
	
	@Mock
	IPassiveAccountRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Test save current personal account")
	void saveCurrentPersonalAccountTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setAccountBalance(1000.0);
		
		
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456")).thenReturn(flux);
		
		Mono<Double> expected = service.getAccountBalance("123456");
		
		assertNotNull(expected);
		assertEquals(1000.0, expected.block());
		
	}
	
	
	@Test
	@DisplayName("Test If have more than one account")
	void checkIfHaveMoreThanOneAccountTest() {
		
		Mono<Long> accounts = Mono.just(1L); 
		
		when(repository.countByPersonCode("123456")).thenReturn(accounts);
		
		Mono<Boolean> expected = service.checkIfHaveMoreThanOneAccount("123456");
		
		assertNotNull(expected);
		assertEquals(true, expected.block());
		
	}
	
	@Test
	@DisplayName("Test get minimum openning amount by product")
	void getMinimumOpeningAmountTest() {
		
		Mono<Double> expected = service.getMinimumOpeningAmount(PassiveAccountTypeEnum.CURRENT_ACCOUNT.toString());
		
		assertNotNull(expected);
		assertEquals(1.0, expected.block());
		
	}
	

	@Test
	@DisplayName("Test get account balance by accountNumber")
	void getAccountBalanceTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setAccountBalance(1000.0);
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456")).thenReturn(flux);
		
		Mono<Double> expected = service.getAccountBalance("123456");
		
		assertNotNull(expected);
		assertEquals(1000.0, expected.block());
		
	}
	
	@Test
	@DisplayName("Test get day movement available by accountNumber")
	void getDayMovementAvailableTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setDayMovementAvailable(2);
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456")).thenReturn(flux);
		
		Mono<Integer> expected = service.getDayMovementAvailable("123456");
		
		assertNotNull(expected);
		assertEquals(2, expected.block());
		
	}
	
	@Test
	@DisplayName("Test get maximum transaction by accountNumber")
	void getMaximumTransactionsTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setMaximumTransactions(20);
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456")).thenReturn(flux);
		
		Mono<Integer> expected = service.getMaximumTransactions("123456");
		
		assertNotNull(expected);
		assertEquals(20, expected.block());
		
	}
	
	@Test
	@DisplayName("Test get maximum transaction without commission by accountNumber")
	void getMaximumTransactionsWithoutCommissionTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setMaximumTransactionsWithoutCommission(10);
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456")).thenReturn(flux);
		
		Mono<Integer> expected = service.getMaximumTransactionsWithoutCommission("123456");
		
		assertNotNull(expected);
		assertEquals(10, expected.block());
		
	}
	
	

}
