package com.v2nhung.bank.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private String msg;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
