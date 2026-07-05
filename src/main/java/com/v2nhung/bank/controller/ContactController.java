package com.v2nhung.bank.controller;

import com.v2nhung.bank.dto.ContactDto;
import com.v2nhung.bank.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<ContactDto> saveContact (@RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(contactService.saveContact(contactDto));
    }

}