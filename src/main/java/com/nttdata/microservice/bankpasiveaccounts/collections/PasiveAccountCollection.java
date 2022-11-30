package com.nttdata.microservice.bankpasiveaccounts.collections;

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
@Document(value = "pasive_accounts")
public class PasiveAccountCollection {
	
	@Id
	private ObjectId id;
	
	private String passiveAccountCode;
	private String accountType;
	private String personType;
	private String accountNumber;
	private String personCode;
	private String state;
	
	private Double accountAmount;
	private Double commission;
	private Double withdrawalAmountPerMonthAvailable;
	private Double depositAmountPerMonthAvailable;
	private Integer dayMovementAvailable;
	private Integer availableMovementsPerMonthAvailable;
	
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
}
