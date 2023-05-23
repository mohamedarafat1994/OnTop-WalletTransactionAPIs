package com.ontop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ontop.model.Transaction;
import com.ontop.model.User;
import com.ontop.model.Wallet;
import com.ontop.repos.WalletRepo;

class WalletServiceTest {

	@InjectMocks
	WalletService mock;
	
	@Mock
	WalletRepo repoMock;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateWallet() {
		User user = new User((long)1,"","","","","");//, new ArrayList<Transaction>());
		Wallet w = new Wallet((long)1,0,user);//,new HashSet<Transaction>());
		when(repoMock.save(Mockito.any(Wallet.class))).thenReturn(w);
		mock.createWallet(w);
		assertEquals(1,w.getId().intValue());
		verify(repoMock,times(1)).save(w);
	}

	
	@Test
	void testGetUpdateUserWalletBalance() {
		User user = new User((long)1,"","","","","");//,new ArrayList<Transaction>());
		Wallet w = new Wallet((long)1,0,user);//,new HashSet<Transaction>());
		repoMock.save(w);
		when(repoMock.save(Mockito.any(Wallet.class))).thenReturn(w);
		mock.updateUserWalletBalance(w, 2);
		assertEquals(w.getBalance(), 2);
		verify(repoMock,times(2)).save(w);
	}
	
	@Test
	void getUserWallet() {
		User user = new User((long)1,"","","","","");//,new ArrayList<Transaction>());
		Wallet w = new Wallet((long)1,1000,user);//,new HashSet<Transaction>());
		repoMock.save(w);
		when(repoMock.save(Mockito.any(Wallet.class))).thenReturn(w);
		mock.getUserWallet(user.getId().intValue());
		verify(repoMock,times(1)).findByUserId(user.getId().intValue());
	}
	
}
