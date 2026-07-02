package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.CustomerEntity;
import com.v2nhung.bank.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity toEntity(CustomerDto dto);

    CustomerDto toDto(CustomerEntity entity);
}
