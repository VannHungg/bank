package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.ContactEntity;
import com.v2nhung.bank.data.mapper.ContactMapper;
import com.v2nhung.bank.data.repository.ContactRepository;
import com.v2nhung.bank.dto.ContactDto;
import com.v2nhung.bank.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    private final String PREFIX_CONTACT_ID = "SR";

    @Override
    @Transactional
    public ContactDto saveContact(ContactDto contactDto) {
        try {
            contactDto.setContactId(generateContactId());
            ContactEntity entity = contactMapper.toEntity(contactDto);
            ContactEntity result = contactRepository.save(entity);

            return contactMapper.toDto(result);
        }
        catch (Exception e) {
            log.error("Has error when saveContact: {}", e.getMessage(), e);
            throw new RuntimeException("Has error when saveContact: " + e.getMessage(), e);
        }
    }

    private String generateContactId() {
        return PREFIX_CONTACT_ID + UUID.randomUUID().toString().replace("-", "");
    }
}
