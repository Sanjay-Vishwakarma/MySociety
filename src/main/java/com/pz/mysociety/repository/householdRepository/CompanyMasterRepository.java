package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.CompanyMasterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMasterRepository extends JpaRepository<CompanyMasterEntity, Integer> , JpaSpecificationExecutor<CompanyMasterEntity> {
    CompanyMasterEntity findByName(String name);

    List<CompanyMasterEntity> findByCompanyTypeIdAndIsActive(int id, boolean isActive, Pageable pageable);

    int countByCompanyTypeIdAndIsActive(int companyTypeId, boolean isActive);

    CompanyMasterEntity findByIdAndIsActive(int id, boolean isActive);

    CompanyMasterEntity findById(int id);




}
