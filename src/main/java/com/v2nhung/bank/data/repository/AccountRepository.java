package com.v2nhung.bank.data.repository;

import com.v2nhung.bank.data.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByCustomerId(long customerId);
}
