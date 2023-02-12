package com.pz.mysociety.repository.noticeRepository;

import com.pz.mysociety.entity.noticeEntity.SocietyNoticeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocietyNoticeRepository  extends JpaRepository<SocietyNoticeEntity, Integer>, JpaSpecificationExecutor<SocietyNoticeEntity> {

    @Query("SELECT n FROM Notice n WHERE n.id = ?1")
    SocietyNoticeEntity getNoiceById(int complainId);

    List<SocietyNoticeEntity> findBySocietyIdOrderByIdDesc(int societyId, Pageable paging);

    SocietyNoticeEntity findByIdAndSocietyId(int id, int societyId);

    int countBySocietyId(int societyId);

    SocietyNoticeEntity findByAttachment(String attachment);
}
