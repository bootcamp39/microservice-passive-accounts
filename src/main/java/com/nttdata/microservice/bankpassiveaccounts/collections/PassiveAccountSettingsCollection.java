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
@Document(value = "passive_accounts_settings")
public class PassiveAccountSettingsCollection {
	
	@Id
	private ObjectId id;
	
	private String passiveAccountType;
	private Double minimunOpeningAmount;
	private int maximunTransationsWithoutCommission;
	private boolean mustHaveCreditCard;
	
	private String state;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;

}
