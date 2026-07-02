package com.v2nhung.bank.repository;

import com.v2nhung.bank.data.entity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsRepository extends JpaRepository<AccountTransactionEntity, Long> {

    List<AccountTransactionEntity> findByCustomerIdOrderByTransactionDtDesc(long customerId);
}
