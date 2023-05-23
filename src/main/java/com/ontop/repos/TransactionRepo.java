package com.ontop.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import com.ontop.model.Transaction;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction, Long>{

	List<Transaction> findByOrderByCreatedDateDesc(Pageable p);

	@Query("SELECT t FROM Transaction t WHERE t.amount=?1 ORDER BY t.createdDate DESC")
	List<Transaction> findByAmountByOrderByCreatedDateDesc(Double amount, Pageable p);

	@Query("SELECT t FROM Transaction t WHERE t.amount=?1 and t.createdDate = ?2 ORDER BY t.createdDate DESC")
	List<Transaction> findByAmountAndCreatedDateByOrderByCreatedDateDesc(Double amount, Date date,Pageable paging);

	@Query("SELECT t FROM Transaction t WHERE t.createdDate = ?1 ORDER BY t.createdDate DESC")
	List<Transaction> findByCreatedDateByOrderByCreatedDateDesc(Date convertStringToDate, Pageable paging);

}
