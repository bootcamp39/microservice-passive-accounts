package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.microservice.bankpassiveaccounts.collections.WalletCollection;
import com.nttdata.microservice.bankpassiveaccounts.repository.IWalletRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.IWalletService;

import reactor.core.publisher.Mono;

public class WalletServiceImpl implements  IWalletService{
	
	@Autowired
	private IWalletRepository repository;

	@Override
	public Mono<WalletCollection> save(WalletCollection collection) {
		return repository.save(collection);
	}

}
