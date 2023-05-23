package com.ontop.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ontop.model.Wallet;

@Repository
public interface WalletRepo extends CrudRepository<Wallet, Long>{

	Wallet findByUserId(Integer user_id);

}
