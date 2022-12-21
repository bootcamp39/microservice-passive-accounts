package com.nttdata.microservice.bankpassiveaccounts.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.microservice.bankpassiveaccounts.collections.WalletCollection;

@Repository
public interface IWalletRepository extends ReactiveCrudRepository<WalletCollection, ObjectId>{

}
