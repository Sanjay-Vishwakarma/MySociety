package com.pz.mysociety.repository.dashboardRepository;


import com.pz.mysociety.entity.dashboardEntity.CardMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CardMasterRepository extends JpaRepository<CardMasterEntity, Integer> , JpaSpecificationExecutor<CardMasterEntity> {

    CardMasterEntity findByCardName(String name);





}
