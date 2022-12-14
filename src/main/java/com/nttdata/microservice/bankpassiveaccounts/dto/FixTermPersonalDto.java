package com.nttdata.microservice.bankpassiveaccounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixTermPersonalDto {
	
	private String accountNumber;
	private String personCode;
	private Integer dayMovementAvailable;

}
