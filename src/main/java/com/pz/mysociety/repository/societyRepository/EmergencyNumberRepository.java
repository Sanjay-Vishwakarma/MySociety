package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.EmergencyNumberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface EmergencyNumberRepository extends JpaRepository<EmergencyNumberEntity, Integer>, JpaSpecificationExecutor<EmergencyNumberEntity>{
    List<EmergencyNumberEntity> findBySocietyId(int societyId);

    EmergencyNumberEntity findByMobileNumber(String mobileNumber);

    EmergencyNumberEntity findByEmail(String email);

    EmergencyNumberEntity findBySocietyIdAndMobileNumber(int societyId, String mobileNumber);

    List<EmergencyNumberEntity> findBySocietyIdAndIsActive(int societyId, boolean isActive, Pageable pageable);

    int countBySocietyIdAndIsActive(int societyId, boolean isActive);
}
