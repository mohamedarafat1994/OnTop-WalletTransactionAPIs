package com.ontop.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletListTransactionsResponse {

	private String transactionID;
	private double transactionAmount;
	private int    transactionUserID;
	private Date   transactionDate;
}
