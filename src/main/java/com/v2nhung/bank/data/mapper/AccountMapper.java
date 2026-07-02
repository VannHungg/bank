package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.AccountEntity;
import com.v2nhung.bank.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(AccountEntity accountEntity);
}
