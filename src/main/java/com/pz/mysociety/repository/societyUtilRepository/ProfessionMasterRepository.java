package com.pz.mysociety.repository.societyUtilRepository;

import com.pz.mysociety.entity.societyUtilEntity.ProfessionMasterEntity;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "Hunter")
public interface ProfessionMasterRepository  extends JpaRepository<ProfessionMasterEntity,Integer> ,JpaSpecificationExecutor<ProfessionMasterEntity> {

ProfessionMasterEntity findByProfession(String name);


}
