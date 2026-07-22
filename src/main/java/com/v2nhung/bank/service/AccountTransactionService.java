package com.v2nhung.bank.service;

import com.v2nhung.bank.dto.AccountTransactionDto;

import java.util.List;

public interface AccountTransactionService {

    List<AccountTransactionDto> findById(Long customerId);
}
