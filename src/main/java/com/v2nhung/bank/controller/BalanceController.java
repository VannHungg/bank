package com.v2nhung.bank.controller;

import com.v2nhung.bank.service.AccountTransactionService;
import com.v2nhung.bank.util.ResultJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceController extends BaseController {

    private final AccountTransactionService accountTransactionService;

    @GetMapping("/myBalance")
    public ResponseEntity<ResultJson> getBalanceDetails(@RequestParam(name = "customerId", required = false) Long customerId) {
        return setResponseEntity(accountTransactionService.findById(customerId));
    }

}