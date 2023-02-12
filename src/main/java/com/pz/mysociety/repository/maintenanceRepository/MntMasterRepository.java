package com.pz.mysociety.repository.maintenanceRepository;

import com.pz.mysociety.entity.maintenanceEntity.MntMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MntMasterRepository extends JpaRepository<MntMasterEntity, Integer>, JpaSpecificationExecutor<MntMasterEntity> {
    MntMasterEntity findByName(String name);
}
