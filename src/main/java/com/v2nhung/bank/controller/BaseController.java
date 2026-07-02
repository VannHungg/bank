package com.v2nhung.bank.controller;

import com.v2nhung.bank.util.ResultJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<ResultJson> setResponseEntity(Object data) {
        return ResponseEntity.ok(new ResultJson(HttpStatus.OK.value(), null, data));
    }
}
