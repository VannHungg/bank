package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.AccountTransactionEntity;
import com.v2nhung.bank.data.mapper.AccountTransactionMapper;
import com.v2nhung.bank.data.repository.AccountTransactionsRepository;
import com.v2nhung.bank.dto.AccountTransactionDto;
import com.v2nhung.bank.service.AccountTransactionService;
import com.v2nhung.bank.util.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final AccountTransactionMapper accountTransactionMapper;

    @Override
    @Transactional(readOnly = true)
    public AccountTransactionDto findById(Long id) {
        try {
            AccountTransactionEntity account =  accountTransactionsRepository.findById(id).orElse(null);
            return accountTransactionMapper.toDto(account);
        }
        catch (Exception e) {
            log.error("Has error when get account transactions: {}", e.getMessage(), e);
            throw new BusinessException("Has error when get account transaction: " + e.getMessage(), e);
        }
    }
}
