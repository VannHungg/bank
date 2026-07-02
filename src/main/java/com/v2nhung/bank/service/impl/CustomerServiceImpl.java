package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.CustomerEntity;
import com.v2nhung.bank.data.mapper.CustomerMapper;
import com.v2nhung.bank.data.repository.CustomerRepository;
import com.v2nhung.bank.dto.CustomerDto;
import com.v2nhung.bank.service.CustomerService;
import com.v2nhung.bank.util.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerDto register(CustomerDto customerDto) {
        try {
            customerDto.setPwd(passwordEncoder.encode(customerDto.getPwd()));
            CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
            return customerMapper.toDto(customerRepository.save(customerEntity));
        }
        catch (Exception e) {
            log.error("Register customer email: {} got error: {}",
                    customerDto.getEmail(), e.getMessage(), e);
            throw new BusinessException("Register customer email got error: " + e.getMessage(), e);
        }
    }

    @Override
    public CustomerDto getUserAfterLogin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email;

            if (authentication != null) {
                email = authentication.getName();
            }
            else {
                throw new BusinessException("User not authenticated");
            }

            Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
            if (customer.isEmpty()) {
                throw new BusinessException("User not found");
            }

            return customerMapper.toDto(customer.get());
        } catch (Exception e) {
            log.error("Has error when getUserAfterLogin: {}", e.getMessage(), e);
            throw new RuntimeException("Has error when getUserAfterLogin: " + e.getMessage(), e);
        }
    }
}
