package com.nttdata.microservice.bankpassiveaccounts.collections;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "debit_cards")
public class DebitCardCollection {
	
	@Id
	private ObjectId id;
	private String debitCardNumber;
	private String personCode;
	private String mainAccountNumber;

}
