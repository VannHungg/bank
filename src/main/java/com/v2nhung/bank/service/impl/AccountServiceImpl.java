package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.AccountEntity;
import com.v2nhung.bank.data.entity.CustomerEntity;
import com.v2nhung.bank.data.mapper.AccountMapper;
import com.v2nhung.bank.data.repository.AccountRepository;
import com.v2nhung.bank.data.repository.CustomerRepository;
import com.v2nhung.bank.dto.AccountDto;
import com.v2nhung.bank.service.AccountService;
import com.v2nhung.bank.util.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccounts(String email) {
        try {
            Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
            if (customer.isEmpty()) {
                return null;
            }

            Long customerId = customer.get().getCustomerId();
            Optional<AccountEntity> account =  accountRepository.findById(customerId);
            return account.map(accountMapper::toDto).orElse(null);
        }
        catch (Exception e) {
            log.error("Has error when get accounts: {}", e.getMessage(), e);
            throw new BusinessException("Has error when get accounts: " + e.getMessage(), e);
        }
    }
}
