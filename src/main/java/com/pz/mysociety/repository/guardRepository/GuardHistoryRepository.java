package com.pz.mysociety.repository.guardRepository;

import com.pz.mysociety.entity.guardEntity.GuardHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardHistoryRepository extends JpaRepository<GuardHistoryEntity, Integer> {

    List<GuardHistoryEntity> findByGuardIdAndInTimeGreaterThanAndInTimeLessThan(int guardId, String fromDate, String toDate);
}
