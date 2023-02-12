package com.pz.mysociety.repository.dashboardRepository;

import com.pz.mysociety.entity.dashboardEntity.SubCategoryEntity;
import com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> , JpaSpecificationExecutor<SubCategoryEntity> {
    SubCategoryEntity findByName(String name);

    List<SubCategoryEntity> findByIsActive(boolean isActive, Pageable pageable);

    int countByIsActive(boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO(st.id, st.featureId, fc.name, st.name, st.redirect, st.isActive) FROM SubCategory st JOIN FeatureCategory fc ON st.featureId = fc.id WHERE st.isActive = ?1")
    List<SubCategoryVO> getByIsActive(boolean isActive, Pageable pageable);

    List<SubCategoryEntity> findByFeatureIdAndIsActive(int featureId, boolean isActive);
    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO(st.id, st.featureId, fc.name, st.name, st.redirect, st.isActive) FROM SubCategory st JOIN FeatureCategory fc ON st.featureId = fc.id")
    List<SubCategoryVO> getAllSubCategory(Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO(st.id, st.featureId,st.name) FROM SubCategory st WHERE st.featureId = ?1 AND st.isActive = ?2")
    List<SubCategoryVO> getByFeatureIdAndIsActive(int featureId, boolean isActive);
}
