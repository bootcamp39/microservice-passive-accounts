package com.nttdata.microservice.bankpassiveaccounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountPersonalDto {

	private String accountNumber;
	private String personCode;
	private Double maintenanceCommission;
	private Integer maximumTransactionsWithoutCommission;
	
}
