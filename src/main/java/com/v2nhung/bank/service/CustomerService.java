package com.v2nhung.bank.service;

import com.v2nhung.bank.dto.CustomerDto;

public interface CustomerService {
    CustomerDto register(CustomerDto customerDto);

    CustomerDto getUserAfterLogin();
}
