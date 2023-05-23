package com.ontop.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ontop.model.Transaction;
import com.ontop.model.User;
import com.ontop.model.Wallet;
import com.ontop.repos.TransactionRepo;

@Service
public class TransactionService {
	
	Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Value("${NegativeBalanceResponseCode}")
	private String NegativeBalanceResponseCode;

	@Value("${UserNotFoundResponseCode}")
	private String UserNotFoundResponseCode;
	
	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	public String executeTransaction(Integer user_id, Double amount) {
		User user = userService.getUser(user_id);
		if (user != null) {
			Wallet wallet = walletService.getUserWallet(user_id);
			double newAmountAfterFees = 0.0;
			double balanceAfterFees = 0.0;
			if(wallet != null) {
				double balance = walletService.getUserWalletBalance(wallet);
				if(amount > 0) {
					newAmountAfterFees = amount * 0.9;
				}else if (amount < 0){
					newAmountAfterFees = amount * 1.1;
				}else {//zero
					return NegativeBalanceResponseCode;
				}
				balanceAfterFees = balance + newAmountAfterFees;
				if(balanceAfterFees >= 0 ) {
					walletService.updateUserWalletBalance(wallet,balanceAfterFees);
					Transaction t = new Transaction();
					t.setUser_id(user.getId());
					t.setWallet_id(wallet.getId());
					t.setAmount(amount);
					Transaction createdTransaction = transactionRepo.save(t);
					return createdTransaction.getId().toString();
				}else {
					return NegativeBalanceResponseCode;
				}
			}{//create wallet
				if(amount > 0 ) {
					if(amount > 0) {
						newAmountAfterFees = amount * 0.9;
					}else if (amount < 0){
						newAmountAfterFees = amount * 1.1;
					}else {//zero
						return NegativeBalanceResponseCode;
					}
					if(newAmountAfterFees >= 0 ) {
						Wallet newWallet = new Wallet(newAmountAfterFees,user);
						Wallet createdWallet = walletService.createWallet(newWallet);
						Transaction t = new Transaction();
						t.setUser_id(user.getId());
						t.setWallet_id(createdWallet.getId());
						t.setAmount(amount);
						Transaction createdTransaction = transactionRepo.save(t);
						String transactionID = createdTransaction.getId().toString();
						return transactionID;
					}else {
						return NegativeBalanceResponseCode;
					}
				}else {
					return NegativeBalanceResponseCode;
				}
			}
		}
		return UserNotFoundResponseCode;
	}

	public List<Transaction> getAllTransactionSortedDesc(Pageable p){
		return transactionRepo.findByOrderByCreatedDateDesc(p);
	}
	
	public List<Transaction> getAllTransactionSortedDescByAmount(Pageable p,Double amount){
		return transactionRepo.findByAmountByOrderByCreatedDateDesc(amount,p);
	}

	public List<Transaction> getAllTransactionSortedDescByAmountAndDate(Pageable paging, Double amount,
			String date) throws ParseException {
		return transactionRepo.findByAmountAndCreatedDateByOrderByCreatedDateDesc(amount,convertStringToDate(date),paging);
	}

	public List<Transaction> getAllTransactionSortedDescByDate(Pageable paging, String date) {
		return transactionRepo.findByCreatedDateByOrderByCreatedDateDesc(convertStringToDate(date),paging);
	}
	
	public Date convertStringToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = new Date();
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
}
