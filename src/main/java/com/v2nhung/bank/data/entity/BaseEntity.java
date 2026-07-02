package com.v2nhung.bank.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_dt")
    private Date createdDt;

    @Column(name = "updated_dt")
    private Date updatedDt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    private void onCreate() {
        createdDt = new Date();
        updatedDt = new Date();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            createdBy = auth.getName();
            updatedBy = auth.getName();
        }
        else {
            createdBy = "System";
            updatedBy = "System";
        }
    }

    @PreUpdate
    private void onUpdate() {
        updatedDt = new Date();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            updatedBy = auth.getName();
        }
        else {
            updatedBy = "System";
        }
    }
}
