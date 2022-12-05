package com.nttdata.microservice.bankpassiveaccounts.services;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountSettingsCollection;

import reactor.core.publisher.Mono;

public interface IPassiveAccountSettingsService {
	
	public Mono<PassiveAccountSettingsCollection> getByType(String type);

}
