package com.nttdata.microservice.bankpassiveaccounts.collections;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "passive_accounts")
public class PassiveAccountCollection {
	
	@Id
	private ObjectId id;
	
	//private String passiveAccountCode;
	private String accountType;
	private String personType;
	private String accountNumber;
	private String personCode;
	
	
	private Double accountAmount;
	private Double commission;
	private Double withdrawalAmountPerMonthAvailable;
	private Double depositAmountPerMonthAvailable;
	private Integer dayMovementAvailable;
	private Integer availableMovementsPerMonthAvailable;
	
	private String state;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
}
