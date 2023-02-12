package com.pz.mysociety.repository.dashboardRepository;

import com.pz.mysociety.entity.dashboardEntity.FeatureTypeEntity;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface FeatureTypeRepository extends JpaRepository<FeatureTypeEntity, Integer>, JpaSpecificationExecutor<FeatureTypeEntity> {

    List<FeatureTypeEntity> findByIsActive(boolean isActive, Pageable pageable);

    int countByIsActive(boolean isActive);

    FeatureTypeEntity findByName(String name);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO(ft.name, ft.description, ft.iconUrl, ft.redirect, ft.isFavorite, ft.isNew) FROM FeatureType ft WHERE ft.dashboardId = ?1 AND ft.isActive = ?2 ORDER BY ft.priorityOrder")
    List<FeatureTypeVO> getByDashboardIdAndIsDashboardAndIsActive(int featureId, boolean isActive);

//    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO(ft.id, fc.name, sc.name, ft.name, ft.description, ft.iconUrl, ft.redirect, ft.isFavorite, ft.isNew, ft.isActive) FROM FeatureType ft JOIN FeatureCategory fc ON ft.featureId = fc.id LEFT JOIN SubCategory sc ON ft.subCategoryId = sc.id WHERE ft.isActive = ?1")
//    List<FeatureTypeVO> getByIsActive(boolean isActive, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO(ft.id, ft.name, ft.description, ft.iconUrl, ft.redirect, ft.isNew) FROM FeatureType ft WHERE ft.subCategoryId = ?1 AND ft.isActive = ?2")
    List<FeatureTypeVO> getBySubCategoryIdAndIsActive(int subIdId, boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO(ft.id, ft.featureId, fc.name, ft.subCategoryId, sc.name, ft.name, ft.description, ft.iconUrl, ft.redirect, ft.isFavorite, ft.isNew, ft.isActive) FROM FeatureType ft JOIN FeatureCategory fc ON ft.featureId = fc.id LEFT JOIN SubCategory sc ON ft.subCategoryId = sc.id WHERE ft.isActive = true OR ft.isActive = false")
    List<FeatureTypeVO> getAllFeatureType(Pageable pageable);

    List<FeatureTypeEntity> findByDashboardId(int dashboardId);

    FeatureTypeEntity findByDashboardIdAndPriorityOrder(int dashboardId, int priorityOrder);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO(ft.id, ft.name, ft.priorityOrder) FROM FeatureType ft WHERE ft.dashboardId = ?1 AND ft.isActive = ?2 ORDER BY ft.priorityOrder")
    List<FeatureTypeVO> getByDashboardIdAndIsActive(int dashboardId, boolean isActive);
}
