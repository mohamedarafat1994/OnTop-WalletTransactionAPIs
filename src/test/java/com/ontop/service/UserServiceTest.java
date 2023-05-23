package com.ontop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ontop.model.Transaction;
import com.ontop.model.User;
import com.ontop.repos.UserRepo;

class UserServiceTest {

	@InjectMocks
	UserService mock;
	
	@Mock
	UserRepo repoMock;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllUsers() {
		List<User> userList = new ArrayList<User>();
		userList.add(new User());
		userList.add(new User());
		when(repoMock.findAll()).thenReturn(userList);
		List<User> list = mock.getllAll();
		assertEquals(2,list.size());
		verify(repoMock,times(1)).findAll();
	}
	
	@Test
	void testCreateUser() {
		User user = new User((long) 1,"","","","","");//,new ArrayList<Transaction>());
		when(repoMock.save(Mockito.any(User.class))).thenReturn(user);
		Long userId = mock.createUser(user);
		assertEquals(1,userId);
		verify(repoMock,times(1)).save(user);
	}

	@Test
	void testGetUserByUserId() {
		User user = new User((long) 1,"","","","","");//,new ArrayList<Transaction>());
		repoMock.save(user);
		Optional<User> userOptional = Optional.of(user);
		when(repoMock.findById(Mockito.any(Long.class))).thenReturn(userOptional);
		user = mock.getUser(user.getId().intValue());
		assertEquals(user.getId(),userOptional.get().getId());
		verify(repoMock,times(1)).findById(user.getId());
	}
	
}
