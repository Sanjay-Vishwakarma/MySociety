package com.pz.mysociety.repository.societyRepository;


import com.pz.mysociety.entity.societyEntity.UnitDocumentMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDocumentMasterRepository extends JpaRepository<UnitDocumentMasterEntity, Integer> {

    List<UnitDocumentMasterEntity> findByOwnerId(int ownerId);


    @Query(value = "SELECT udm FROM UnitDocumentMaster udm JOIN OwnerMaster om ON udm.ownerId = om.id WHERE udm.userId = ?1 AND om.unitId = ?2")
    List<UnitDocumentMasterEntity> getUserIdAndUnitId(int userId, int unitId);

    List<UnitDocumentMasterEntity> findBySocietyIdAndUserId(int societyId, int userId);
}
