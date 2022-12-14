package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import static org.junit.jupiter.api.Assertions.*;
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
	@DisplayName("get account balance of the accountNumber")
	void getAccountBalanceTest() {

		PassiveAccountCollection account = new PassiveAccountCollection();
		account.setAccountNumber("123456");
		account.setAccountBalance(1000.0);
		PassiveAccountCollection[] lista = {account};
		Flux<PassiveAccountCollection> flux = Flux.fromArray(lista);
		
		when(repository.findByAccountNumber("123456"))
		.thenReturn(flux);
		
		Mono<Double> expected = service.getAccountBalance("123456");
		
		assertNotNull(expected);
		//assertEquals(1000.0, expected);
		
	}

}
