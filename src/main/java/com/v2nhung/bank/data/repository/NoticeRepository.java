package com.v2nhung.bank.data.repository;

import com.v2nhung.bank.data.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Query(value = "select n from NoticeEntity n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<NoticeEntity> findAllActiveNotices();
}
