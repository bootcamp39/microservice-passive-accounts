package com.nttdata.microservice.bankpasiveaccounts.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservice.bankpasiveaccounts.collections.LegalHolderCollection;
import com.nttdata.microservice.bankpasiveaccounts.collections.enums.PersonTypeEnum;
import com.nttdata.microservice.bankpasiveaccounts.repository.ILegalHolderRepository;

import reactor.core.publisher.Mono;

@Service
public class LegalHolderServiceImpl implements ILegalHolderService {
	
	@Autowired
	private ILegalHolderRepository legalHolderRepository;
	
	@Autowired
	private IPasiveAccountService pasiveAccountService;

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
