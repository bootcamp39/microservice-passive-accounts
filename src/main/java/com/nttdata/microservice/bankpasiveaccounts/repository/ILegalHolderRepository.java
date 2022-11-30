package com.nttdata.microservice.bankpasiveaccounts.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.microservice.bankpasiveaccounts.collections.LegalHolderCollection;

@Repository
public interface ILegalHolderRepository extends ReactiveCrudRepository<LegalHolderCollection, ObjectId>{

}
