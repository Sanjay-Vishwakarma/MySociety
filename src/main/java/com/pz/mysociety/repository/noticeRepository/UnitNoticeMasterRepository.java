package com.pz.mysociety.repository.noticeRepository;

import com.pz.mysociety.entity.noticeEntity.UnitNoticeMasterEntity;
import com.pz.mysociety.model.Request.SocietyNoticeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitNoticeMasterRepository extends JpaRepository<UnitNoticeMasterEntity, Integer> {

    @Query(value = "SELECT new com.pz.mysociety.model.Request.SocietyNoticeVO(n.id, n.noticeTitle, n.noticeContent, n.noticeType, n.startDate, n.endDate, n.attachment, n.timestamp) FROM UnitNotice un JOIN Notice n ON un.noticeId = n.id WHERE un.unitId = ?1 ORDER BY un.id DESC")
    List<SocietyNoticeVO> getByUnitId(int unitId);

    @Query(value = "SELECT un.unitId FROM UnitNotice un  WHERE un.noticeId = ?1")
    List<Integer> getUnitByNoticeId(int id);
}
