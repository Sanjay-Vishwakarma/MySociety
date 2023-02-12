package com.pz.mysociety.repository.dashboardRepository;

import com.pz.mysociety.entity.dashboardEntity.FeatureCategoryEntity;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface FeatureCategoryRepository extends JpaRepository<FeatureCategoryEntity, Integer> , JpaSpecificationExecutor<FeatureCategoryEntity>{

    FeatureCategoryEntity findByName(String name);

    List<FeatureCategoryEntity> findByIsActive(boolean isActive, Pageable pageable);

    int countByIsActive(boolean isActive);

    FeatureCategoryEntity findByNameAndIsActive(String name, boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO(fc.id, fc.name, fc.redirect, fc.isActive) FROM FeatureCategory fc")
    List<FeatureCategoryVO> getFeatureCategory(Pageable pageable);
}
