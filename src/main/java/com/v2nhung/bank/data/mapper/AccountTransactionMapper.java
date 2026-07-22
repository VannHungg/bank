package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.AccountTransactionEntity;
import com.v2nhung.bank.dto.AccountTransactionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {

    AccountTransactionDto toDto(AccountTransactionEntity entity);

    List<AccountTransactionDto> toDto(List<AccountTransactionEntity> entity);
}
