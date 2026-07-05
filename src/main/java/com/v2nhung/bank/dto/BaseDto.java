package com.v2nhung.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseDto {

    protected Date createdDt;
    protected Date updatedDt;
    protected String createdBy;
    protected String updatedBy;
}
