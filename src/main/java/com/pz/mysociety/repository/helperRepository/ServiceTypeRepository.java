package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.ServiceTypeEntity;
import com.pz.mysociety.model.Request.TypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity, Integer> {
    ServiceTypeEntity findByType(String type);

    List<ServiceTypeEntity> findByIsSocietyUser(boolean isSocietyUser, Pageable paging);

    List<ServiceTypeEntity> findByIsUserAndIsActive(boolean isUser, boolean isActive, Pageable paging);

    List<ServiceTypeEntity> findByIsGuardAndIsActive(boolean isGuard, boolean isActive, Pageable paging);

    List<ServiceTypeEntity> findByIsActive(boolean isActive, Pageable paging);

    int countByIsActive(boolean isActive);

    int countByIsUser(boolean isUser);

    int countByIsGuard(boolean isGuard);

    @Query(value = "SELECT s.id FROM ServiceType s WHERE s.isGuard = ?1 and s.isActive = ?2 and isVisitor = ?3")
    List<Integer> getHelperTypeList(boolean a, boolean b, boolean c);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(s.id, s.type) FROM ServiceType s WHERE s.type LIKE ?1% AND s.isActive = ?2")
    List<TypeVO> getSearchServiceType(String type, boolean isActive, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(s.id, s.type) FROM ServiceType s WHERE s.isActive = ?1")
    List<TypeVO> getActiveServiceType(boolean isActive, Pageable searchPageable);

}
