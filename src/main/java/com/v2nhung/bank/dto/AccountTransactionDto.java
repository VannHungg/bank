package com.v2nhung.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransactionDto {
    private String transactionId;
    private Long accountNumber;
    private Long customerId;
    private Date transactionDt;
    private String transactionSummary;
    private String transactionType;
    private Integer transactionAmt;
    private Integer closingBalance;
}
