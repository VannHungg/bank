package com.v2nhung.bank.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultJson {
    private Integer status;
    private Object error;
    private Object result;

    public ResultJson(Object result) {
        this.result = result;
    }
}
