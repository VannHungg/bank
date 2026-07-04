package com.v2nhung.bank.service.impl;

import com.v2nhung.bank.data.entity.NoticeEntity;
import com.v2nhung.bank.data.mapper.NoticeMapper;
import com.v2nhung.bank.data.repository.NoticeRepository;
import com.v2nhung.bank.dto.NoticeDto;
import com.v2nhung.bank.service.NoticeService;
import com.v2nhung.bank.util.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    @Override
    public List<NoticeDto> getNotices(Date from, Date to) {
        try {
            List<NoticeEntity> noticeEntities;
            if (from != null && to != null) {
                noticeEntities = noticeRepository.findByDateRange(from, to);
            } else {
                noticeEntities = noticeRepository.findAllActiveNotices();
            }

            return noticeMapper.toDto(noticeEntities);
        } catch (Exception e) {
            log.error("Has error when getNotices: {}", e.getMessage(), e);
            throw new BusinessException("Has error when getNotices: " + e.getMessage(), e);
        }
    }
}
