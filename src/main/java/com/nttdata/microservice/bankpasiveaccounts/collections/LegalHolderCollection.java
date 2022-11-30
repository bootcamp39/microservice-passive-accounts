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
@Document(value = "legal_holders")
public class LegalHolderCollection {
	
	@Id
	private ObjectId id;
	private String passiveAccountCode;
	
	private String legalHolderCode;
	private String legalHolderType;
	private String names;
	
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;

}
