package com.v2nhung.bank.data.repository;

import com.v2nhung.bank.data.entity.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Long> {
    AuthoritiesEntity findByName(String name);
}
