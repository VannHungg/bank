package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.AccountTransactionEntity;
import com.v2nhung.bank.dto.AccountTransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {

    AccountTransactionDto toDto(AccountTransactionEntity entity);
}
