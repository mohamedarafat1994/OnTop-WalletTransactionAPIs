package com.ontop.controller;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ontop.entity.WalletBalanceResponse;
import com.ontop.entity.WalletExecuteTransactionRequest;
import com.ontop.entity.WalletExecuteTransactionResponse;
import com.ontop.exception.NegativeBalanceNotAllowedException;
import com.ontop.exception.UserNotFoundException;
import com.ontop.model.Transaction;
import com.ontop.model.User;
import com.ontop.model.Wallet;
import com.ontop.service.TransactionService;
import com.ontop.service.UserService;
import com.ontop.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallets")
public class TransactionController {

	@Value("${NegativeBalanceResponseCode}")
	private String NegativeBalanceResponseCode;

	@Value("${UserNotFoundResponseCode}")
	private String UserNotFoundResponseCode;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/balance")
	public ResponseEntity<WalletBalanceResponse> getAllUsers(@RequestParam Integer user_id){
		Wallet wallet = walletService.getUserWallet(user_id);
		if(wallet != null) {
			WalletBalanceResponse response = new WalletBalanceResponse(walletService.getUserWalletBalance(wallet),user_id);
			return new ResponseEntity<WalletBalanceResponse>(response, HttpStatus.OK);
		}
		throw new UserNotFoundException();
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		return new ResponseEntity<Object>(Collections.singletonMap("user_id",userService.createUser(user)),HttpStatus.CREATED);
	}
	
	@PostMapping("/transactions")
	public ResponseEntity<WalletExecuteTransactionResponse> createTransaction(@Valid @RequestBody WalletExecuteTransactionRequest body){
		String responseMessage = transactionService.executeTransaction(body.getUser_id(), body.getAmount());
		if(responseMessage.equals(NegativeBalanceResponseCode)) {
			throw new NegativeBalanceNotAllowedException();
		}else if(responseMessage.equals(UserNotFoundResponseCode)) {
			throw new UserNotFoundException();
		}else {
			WalletExecuteTransactionResponse response = new WalletExecuteTransactionResponse(responseMessage,body.getAmount(),body.getUser_id(),"Successfull");
			return new ResponseEntity<WalletExecuteTransactionResponse>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(required = false) String date, @RequestParam(required = false) Double amount, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) throws ParseException{
		Pageable paging = PageRequest.of(page, size);
		if(date != null && amount != null) {// filter by amount & date
			return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactionSortedDescByAmountAndDate(paging,amount,date),HttpStatus.OK);
		}else if(date != null && amount == null) {// filter by date only
			return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactionSortedDescByDate(paging,date),HttpStatus.OK);
		}else if(date == null && amount != null) {// filter by amount only
			return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactionSortedDescByAmount(paging,amount),HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactionSortedDesc(paging),HttpStatus.OK);
		}
	}
}
