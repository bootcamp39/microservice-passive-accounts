package com.nttdata.microservice.bankpassiveaccounts.facade.impl;

import com.nttdata.microservice.bankpassiveaccounts.facade.IPassiveAccountFacade;

import reactor.core.publisher.Mono;

public class PassiveAccountFacadeImpl implements  IPassiveAccountFacade{

	@Override
	public Mono<Boolean> checkIfHaveDebt(String personCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> checkIfCreditCard(String personCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
