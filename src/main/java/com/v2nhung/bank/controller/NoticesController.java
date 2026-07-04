package com.v2nhung.bank.controller;

import com.v2nhung.bank.service.NoticeService;
import com.v2nhung.bank.util.ResultJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class NoticesController extends BaseController {

    private final NoticeService noticeService;

    @GetMapping("/notices")
    public ResponseEntity<ResultJson> getNotices(@RequestParam(required = false) Date from,
                                                 @RequestParam(required = false) Date to) {
        return setResponseEntity(noticeService.getNotices(from, to));
    }

}