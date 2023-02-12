package com.pz.mysociety.repository.guardRepository;

import com.pz.mysociety.entity.guardEntity.GuardDocumentMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardDocumentMasterRepository extends JpaRepository<GuardDocumentMasterEntity, Integer> {


    GuardDocumentMasterEntity findBySocietyIdAndGuardIdAndDocumentTypeId(int societyId,int guardId,String documentTypeId);

    }
