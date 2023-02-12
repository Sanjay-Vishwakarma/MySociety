package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.FamilyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMemberEntity, Integer> {
    FamilyMemberEntity findByUnitIdAndMobileNumber(int unitId, String mobileNumber);

    @Query("SELECT new com.pz.mysociety.entity.householdEntity.FamilyMemberEntity(fm.id, fm.name, fm.mobileNumber, fm.type, fm.monitor, fm.photo) FROM FamilyMember fm WHERE fm.unitId = ?1")
    List<FamilyMemberEntity> getFamilyMember(int unitId);

    FamilyMemberEntity findByUnitIdAndMobileNumberAndIsActive(int id, String mobileNumber, boolean isActive);
}
