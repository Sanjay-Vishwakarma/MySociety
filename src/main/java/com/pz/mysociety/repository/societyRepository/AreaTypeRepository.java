package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.AreaTypeEntity;
import com.pz.mysociety.model.Request.TypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaTypeRepository extends JpaRepository<AreaTypeEntity, Integer> , JpaSpecificationExecutor<AreaTypeEntity> {
    AreaTypeEntity findByType(String type);

    List<AreaTypeEntity> findByIsActive(boolean isActive, Pageable pageable);


    int countByIsActive(boolean isActive);


    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(art.id, art.type) FROM AreaType art WHERE art.type LIKE ?1% AND art.isActive = ?2")
    List<TypeVO> getSearchAreaType(String type, boolean isActive, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.TypeVO(art.id, art.type) FROM AreaType art WHERE art.isActive = ?1")
    List<TypeVO> getActiveAreaType(boolean isActive, Pageable searchPageable);
}
