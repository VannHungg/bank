package com.v2nhung.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long customerId;
    private String name;
    private String email;
    private String pwd;
    private String mobileNumber;
    private String role;
}
