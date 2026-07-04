package com.v2nhung.bank.service;

import com.v2nhung.bank.dto.NoticeDto;

import java.util.Date;
import java.util.List;

public interface NoticeService {

    List<NoticeDto> getNotices(Date from, Date to);
}
