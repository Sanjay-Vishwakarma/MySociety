package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperTypeEntity;
import com.pz.mysociety.model.Request.HelperTypeVO;
import com.pz.mysociety.model.Request.TypeVO;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface HelperTypeRepository extends JpaRepository<HelperTypeEntity, Integer>, JpaSpecificationExecutor<HelperTypeEntity> {
    HelperTypeEntity findByType(String type);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(ht.id, ht.serviceTypeId, ht.type, ht.iconImage, ht.isFullTime, ht.isActive, ht.timestamp) FROM HelperType ht WHERE ht.serviceTypeId=?1 AND ht.isActive=?2")
    List<HelperListVO> findByServiceTypeIdAndIsActive(int serviceTypeId, boolean isActive, Pageable paging);

    List<HelperTypeEntity> findByServiceTypeIdAndIsActive(int serviceTypeId, boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.HelperTypeVO(ht.id, ht.type, ht.iconImage, ht.isFullTime, ht.isActive)FROM HelperType ht WHERE ht.serviceTypeId = ?1 AND ht.isActive = ?2")
    List<HelperTypeVO> getHelperType(int serviceTypeId, boolean isActive, Pageable paging);

    HelperTypeEntity findById(int helperTypeId);

    int countByServiceTypeIdAndIsActive(int serviceTypeId, boolean isActive);

    int countByServiceTypeId(int serviceTypeId);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.HelperTypeVO(ht.id, ht.type, ht.iconImage, ht.isFullTime, ht.isActive)FROM HelperType ht WHERE ht.serviceTypeId IN ?1 AND ht.isActive = ?2")
    List<HelperTypeVO> getHelperTypeList(List<Integer> serviceTypeId, boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(ht.id, ht.type) FROM HelperType ht WHERE ht.type LIKE ?1% AND ht.isActive = ?2 AND ht.serviceTypeId = ?3")
    List<TypeVO> getSearchHelperType(String type, boolean isActive, int serviceTypeId, Pageable pageable);

    @Query(value = "SELECT ht.type FROM HelperType ht WHERE ht.id = ?1")
    String getTypeById(int helperTypeId);


}
