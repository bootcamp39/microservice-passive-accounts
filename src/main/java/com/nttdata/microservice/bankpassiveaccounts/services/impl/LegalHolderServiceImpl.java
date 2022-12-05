package com.nttdata.microservice.bankpassiveaccounts.services.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpassiveaccounts.collections.LegalHolderCollection;
import com.nttdata.microservice.bankpassiveaccounts.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankpassiveaccounts.repository.ILegalHolderRepository;
import com.nttdata.microservice.bankpassiveaccounts.services.ILegalHolderService;
import com.nttdata.microservice.bankpassiveaccounts.services.IPassiveAccountService;

import reactor.core.publisher.Mono;

@Service
public class LegalHolderServiceImpl implements ILegalHolderService {
	
	@Autowired
	private ILegalHolderRepository legalHolderRepository;
	
	@Autowired
	private IPassiveAccountService pasiveAccountService;

	@Override
	public Mono<LegalHolderCollection> save(LegalHolderCollection collection) throws Exception {
		
		return pasiveAccountService.getByAccountCode(collection.getPassiveAccountCode())
		.flatMap(value -> {
			if(value.getPersonType().equals(PersonTypeEnum.LEGAL_PERSON.toString())) {
				
				collection.setLegalHolderCode(UUID.randomUUID().toString());
				collection.setCreatedAt(new Date());
				return legalHolderRepository.save(collection);
			}else {
				return Mono.empty();
			}
		});
		
	}

}
