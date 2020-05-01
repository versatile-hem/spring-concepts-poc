package com.spring.poc.springbatchpoc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<TransactionEntity, Long> {

	
	@Query(nativeQuery = true, value = "select * from transaction limit 10")
	List<TransactionEntity> getAll();
	
	
}
