package com.ontop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ontop.model.Wallet;
import com.ontop.repos.WalletRepo;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepo walletRepo;

	public Wallet getUserWallet(Integer user_id) {
		Wallet wallet = walletRepo.findByUserId(user_id);
		if(wallet == null)
			return null;
		return wallet;
	}
	
	public double getUserWalletBalance(Wallet wallet) {
		return wallet.getBalance();
	}

	public void updateUserWalletBalance(Wallet wallet, double newBalance) {
		wallet.setBalance(newBalance);
		walletRepo.save(wallet);
	}
	
	public Wallet createWallet(Wallet wallet) {
		return walletRepo.save(wallet);
	}
	
}
