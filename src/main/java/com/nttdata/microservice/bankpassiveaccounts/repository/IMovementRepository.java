package com.nttdata.microservice.bankpassiveaccounts.repository;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.microservice.bankpassiveaccounts.collections.MovementsCollection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IMovementRepository extends ReactiveCrudRepository<MovementsCollection, ObjectId>{

	public Flux<MovementsCollection> findByAccountNumber(String accountNumber);
}
