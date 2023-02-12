package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.DocumentMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


public interface DocumentMasterRepository extends JpaRepository<DocumentMasterEntity, Integer>, JpaSpecificationExecutor<DocumentMasterEntity> {

    DocumentMasterEntity findByType(String type);

    List<DocumentMasterEntity> findByIsActive(boolean isActive);

    int countByIsActive(boolean isActive);

    DocumentMasterEntity findById(int documentTypeId);
}
