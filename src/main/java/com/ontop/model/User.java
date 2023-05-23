package com.ontop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"USER\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "firstName must not be null")
	private String firstName;
	@NotNull(message = "lastName must not be null")
	private String lastName;
	@NotNull(message = "routingNumber must not be null")
	private String routingNumber;
	@NotNull(message = "nationalID imust not be null")
	private String nationalID;
	@NotNull(message = "accountNumber must not be null")
	private String accountNumber;
	
    public User(long id, String firstName, String lastName, String routingNamber, String nationalID, String accountNumber) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.routingNumber = routingNamber;
    	this.nationalID = nationalID;
    	this.accountNumber = accountNumber;
    }
}
