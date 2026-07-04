package com.v2nhung.bank.data.mapper;

import com.v2nhung.bank.data.entity.NoticeEntity;
import com.v2nhung.bank.dto.NoticeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeDto toDto(NoticeEntity entity);

    List<NoticeDto> toDto(List<NoticeEntity> entities);
}
