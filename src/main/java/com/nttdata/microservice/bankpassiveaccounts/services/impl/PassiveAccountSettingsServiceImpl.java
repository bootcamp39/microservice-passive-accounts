package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpassiveaccounts.collections.PassiveAccountSettingsCollection;
import com.nttdata.microservice.bankpassiveaccounts.repository.IPassiveAccountSettingsRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountSettingsService;

import reactor.core.publisher.Mono;

@Service
public class PassiveAccountSettingsServiceImpl implements IPassiveAccountSettingsService {
	
	@Autowired
	private IPassiveAccountSettingsRepository passiveAccountSettingsRepository;

	@Override
	public Mono<PassiveAccountSettingsCollection> getByType(String type) {
		return passiveAccountSettingsRepository.findAll().filter(x -> x.getPassiveAccountType().equals(type)).next();
	}

}
