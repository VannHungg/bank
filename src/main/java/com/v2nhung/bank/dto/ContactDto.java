package com.v2nhung.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto extends BaseDto {
    private Long id;
    private String contactId;
    private String contactName;
    private String contactEmail;
    private String subject;
    private String message;
}
