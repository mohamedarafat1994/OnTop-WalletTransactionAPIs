package com.ontop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletExecuteTransactionResponse {
	private String wallet_transaction_id;
	private double amount;
	private int user_id;
	private String status;
}
