package com.pz.mysociety.repository.complainRepository;

import com.pz.mysociety.entity.complainEntity.ComplainChatRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ComplainChatRecordRepository  extends JpaRepository<ComplainChatRecordEntity, Integer>{

   List<ComplainChatRecordEntity> findByComplainId(int complainId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE ComplainHistory ch SET ch.isRead = ?1 WHERE ch.complainId = ?2 AND ch.role = ?3")
    void changeAdminFlag(boolean flag, int complainId, String role);

    ComplainChatRecordEntity findByAttachment(String fileName);

}
