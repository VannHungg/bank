package com.v2nhung.bank.service;

import com.v2nhung.bank.dto.AccountTransactionDto;

public interface AccountTransactionService {

    AccountTransactionDto findById(Long id);
}
