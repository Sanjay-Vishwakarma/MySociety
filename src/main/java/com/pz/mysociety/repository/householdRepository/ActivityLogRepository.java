package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.ActivityLogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ActivityLogRepository  extends JpaRepository<ActivityLogEntity ,Integer>{

    List<ActivityLogEntity> findByUnitIdAndUserId( int unitId, int UserId, Pageable pageable);

    @Query(nativeQuery = true ,value = "SELECT a.unit_id, a.user_id, a.activity_type, a.description, a.timestamp, a.id  FROM acivity_log a WHERE a.unit_id =?1 And a.user_id=?2 ORDER BY a.id DESC LIMIT 10")
    List<ActivityLogEntity> getTopActivityLog( int unitId, int UserId);

    int countByUserIdAndUnitId(int userId, int unitId);
}
