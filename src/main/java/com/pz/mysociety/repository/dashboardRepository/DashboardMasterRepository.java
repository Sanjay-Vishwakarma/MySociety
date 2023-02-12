package com.pz.mysociety.repository.dashboardRepository;

import com.pz.mysociety.entity.dashboardEntity.DashboardMasterEntity;
import com.pz.mysociety.model.VO.UserDashboardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardMasterRepository extends JpaRepository<DashboardMasterEntity, Integer> , JpaSpecificationExecutor<DashboardMasterEntity> {
    DashboardMasterEntity findByLabelIdAndLabelType(int labelId, String labelType);

    List<DashboardMasterEntity> findByIsActive(boolean isActive);

    List<DashboardMasterEntity> findByIsActiveOrderByPriorityOrder(boolean isActive);

    DashboardMasterEntity findByPriorityOrder(int priorityOrder);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserDashboardVO(dm.id, dm.labelId, dm.labelType, dm.cardName, dm.cardSize, dm.priorityOrder, dm.isActive) FROM DashboardMaster dm ORDER BY dm.priorityOrder")
    List<UserDashboardVO> getAllDashboard();

}
