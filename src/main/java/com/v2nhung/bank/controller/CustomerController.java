package com.v2nhung.bank.controller;

import com.v2nhung.bank.dto.CustomerDto;
import com.v2nhung.bank.service.CustomerService;
import com.v2nhung.bank.util.ResultJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController extends BaseController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ResultJson> register(@RequestBody CustomerDto customerDto) {
        return setResponseEntity(customerService.register(customerDto));
    }

    @RequestMapping("/user")
    public ResponseEntity<ResultJson> getUserAfterLogin() {
        return setResponseEntity(customerService.getUserAfterLogin());
    }
}
