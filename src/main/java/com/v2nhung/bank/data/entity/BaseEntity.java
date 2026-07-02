package com.v2nhung.bank.data.entity;

import jakarta.persistence.Column;

import java.util.Date;

public class BaseEntity {

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "update_dt")
    private Date updateDt;
}
