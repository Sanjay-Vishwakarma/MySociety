package com.pz.mysociety.repository.guardRepository;

import com.pz.mysociety.entity.guardEntity.GuardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardRepository extends JpaRepository<GuardEntity, Integer>, JpaSpecificationExecutor<GuardEntity> {
    GuardEntity findBySocietyIdAndMobileNumber(int societyId, String mobileNumber);

    GuardEntity findBySocietyIdAndGuardPin(int societyId, int pin);

    @Query(value = "SELECT new com.pz.mysociety.entity.guardEntity.GuardEntity(g.id, g.societyId, g.name, g.email, g.mobileNumber, g.role, g.photo) FROM Guard g WHERE g.societyId = ?1 AND g.guardPin = ?2")
    GuardEntity getGuardDetail(int societyId, int guardPin);

    List<GuardEntity>  findBySocietyId(int societyId, Pageable pageable);

    int countBySocietyId(int societyId);

    List<GuardEntity> findBySocietyIdAndIsActive(int societyId, boolean isActive, Pageable paging);

    int countBySocietyIdAndIsActive(int societyId, boolean isActive);
GuardEntity findById(int id);
}
