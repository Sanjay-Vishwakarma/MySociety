package com.pz.mysociety.repository.noticeRepository;

import com.pz.mysociety.entity.noticeEntity.SocietyNoticeTypeEntity;
import com.pz.mysociety.model.Request.TypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeTypeRepository extends JpaRepository<SocietyNoticeTypeEntity, Integer>, JpaSpecificationExecutor<SocietyNoticeTypeEntity> {

    SocietyNoticeTypeEntity findByType(String type);

    @Query("SELECT s FROM societyNoticeType s WHERE s.id = ?1")
    SocietyNoticeTypeEntity getsocietyNoticeTypeById(int id);

    List<SocietyNoticeTypeEntity> findByIsActive(boolean isActive, Pageable paging);

    int countByIsActive(boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(s.id, s.type) FROM societyNoticeType s WHERE s.type LIKE ?1% AND s.isActive = ?2")
    List<TypeVO> getSearchNoticeType(String type, boolean isActive, Pageable pageable);
}
