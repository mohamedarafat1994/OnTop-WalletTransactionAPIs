package com.ontop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ontop.model.User;
import com.ontop.repos.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	

	public List<User> getllAll(){
		return (List<User>) userRepo.findAll();
	}
	
	public Long createUser(User user) {
		return userRepo.save(user).getId();
	}
	
	public User getUser(int userId) {
		Optional<User> user = userRepo.findById((long) userId);
		if(user.isEmpty())
			return null;
		return user.get();
	}
}
