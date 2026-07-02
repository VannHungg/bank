package com.v2nhung.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private long customerId;
    private long accountNumber;
    private String accountType;
    private String branchAddress;
}
