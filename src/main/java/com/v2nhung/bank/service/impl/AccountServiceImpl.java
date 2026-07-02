package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.AccountEntity;
import com.v2nhung.bank.data.mapper.AccountMapper;
import com.v2nhung.bank.data.repository.AccountRepository;
import com.v2nhung.bank.dto.AccountDto;
import com.v2nhung.bank.service.AccountService;
import com.v2nhung.bank.util.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccounts(Long id) {
        try {
            AccountEntity account =  accountRepository.findById(id).orElse(null);
            return accountMapper.toDto(account);
        }
        catch (Exception e) {
            log.error("Has error when get accounts: {}", e.getMessage(), e);
            throw new BusinessException("Has error when get accounts: " + e.getMessage(), e);
        }
    }
}
