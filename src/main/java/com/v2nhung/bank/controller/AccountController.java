package com.v2nhung.bank.controller;

import com.v2nhung.bank.service.AccountService;
import com.v2nhung.bank.util.ResultJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController extends BaseController {

    private final AccountService accountService;

    @GetMapping("/myAccount")
    public ResponseEntity<ResultJson> getAccountDetails(@RequestParam long id) {
        return setResponseEntity(accountService.getAccounts(id));
    }


}