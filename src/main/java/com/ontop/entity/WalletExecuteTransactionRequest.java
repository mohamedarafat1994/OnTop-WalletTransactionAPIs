package com.ontop.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletExecuteTransactionRequest {
	
	@NotNull(message = "amount and user_id must not be null")
	private Double amount;
	@NotNull(message = "amount and user_id must not be null")
	private Integer user_id;
}
