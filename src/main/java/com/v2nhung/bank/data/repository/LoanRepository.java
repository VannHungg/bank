package com.v2nhung.bank.data.repository;

import com.v2nhung.bank.data.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<LoanEntity, Long> {

    List<LoanEntity> findByCustomerIdOrderByStartDtDesc(long customerId);
}
