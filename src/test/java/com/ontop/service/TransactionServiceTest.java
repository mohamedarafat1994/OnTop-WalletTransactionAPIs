package com.ontop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import com.ontop.model.Transaction;
import com.ontop.model.User;
import com.ontop.model.Wallet;
import com.ontop.repos.TransactionRepo;

class TransactionServiceTest {
	
	@Mock
	TransactionService mock;
	
	@Mock
	TransactionRepo repoMock;
	
	@Mock
	UserService userServiceMock;
	
	@Mock
	WalletService walletServiceMock;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExecuteTransaction_SuccessfullResponse() {
		User user = new User((long)1,"","","","","");//,new ArrayList<Transaction>());
		Wallet wallet = new Wallet((long)1,1,user);//,new HashSet<Transaction>());
		when(userServiceMock.getUser(Mockito.anyInt())).thenReturn(user);
		when(walletServiceMock.getUserWallet(Mockito.anyInt())).thenReturn(wallet);
		doNothing().when(walletServiceMock).updateUserWalletBalance(Mockito.any(Wallet.class),Mockito.anyInt());
		TransactionService obj = Mockito.mock(TransactionService.class);
		obj.executeTransaction(1, 1.0);
		verify(obj,times(1)).executeTransaction(1,1.0);
	}

	@Test
	void testExecuteTransaction_UserNotFoundFailure() {
		User user = null;
		when(userServiceMock.getUser(Mockito.anyInt())).thenReturn(user);
		mock.executeTransaction(1, 1.0);
		verify(repoMock,times(0)).save(new Transaction());
	}
	
	@Test
	void testExecuteTransaction_NegativeBalanceFailure() {
		User user = new User((long)1,"","","","","");//,new ArrayList<Transaction>());
		when(userServiceMock.getUser(Mockito.anyInt())).thenReturn(user);
		when(walletServiceMock.getUserWallet(Mockito.anyInt())).thenReturn(null);
		TransactionService obj = Mockito.mock(TransactionService.class);
		obj.executeTransaction(1, -1.0);
		verify(obj,times(1)).executeTransaction(1,-1.0);
		verify(repoMock,times(0)).save(new Transaction());
	}

	@Test
	void testGetAllTransactionFiltered() {
		Transaction t1 = new Transaction(1L,1L,1.1);
		Transaction t2 = new Transaction(1L,1L,1.2);
		Transaction t3 = new Transaction(1L,1L,1.3);
		Transaction[] list = new Transaction[]{t1,t2,t3};
		TransactionService obj = Mockito.mock(TransactionService.class);
		doAnswer(invocation -> Arrays.asList(list)).when(obj).getAllTransactionSortedDesc(Mockito.any());
		List<Transaction> list2 = obj.getAllTransactionSortedDesc(PageRequest.of(1, 3));
		verify(obj,times(1)).getAllTransactionSortedDesc(PageRequest.of(1, 3));
		assertEquals(list2.size(),3);
	}
}
