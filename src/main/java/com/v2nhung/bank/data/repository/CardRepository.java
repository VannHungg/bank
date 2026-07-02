package com.v2nhung.bank.data.repository;

import com.v2nhung.bank.data.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findByCustomerId(long customerId);
}
