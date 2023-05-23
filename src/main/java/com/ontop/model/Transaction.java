package com.ontop.model;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "amount")
	private double amount;
	
	@CreatedDate
	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
	}
	
	@Column(name = "user_id")
	private Long user_id;
	
	@Column(name = "wallet_id")
	private Long wallet_id;
	
	public Transaction(Long wallet_id,Long user_id, double amount) {
		this.user_id = user_id;
		this.wallet_id = wallet_id;
		this.amount = amount;
	}
}
