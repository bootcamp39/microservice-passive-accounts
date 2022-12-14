package com.nttdata.microservice.bankpassiveaccounts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountEnterpriseDto {

	private String accountNumber;
	private String personCode;
	private Double maintenanceCommission;
	private Integer maximumTransactionsWithoutCommission;
	private List<String> holders;
	private List<String> signatories;
}
