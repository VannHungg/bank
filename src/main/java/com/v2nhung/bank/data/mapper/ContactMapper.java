package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.ContactEntity;
import com.v2nhung.bank.dto.ContactDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactDto toDto(ContactEntity entity);

    ContactEntity toEntity(ContactDto dto);
}
