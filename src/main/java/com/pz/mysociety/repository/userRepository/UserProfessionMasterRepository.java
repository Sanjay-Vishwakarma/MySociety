package com.pz.mysociety.repository.userRepository;

import com.pz.mysociety.entity.userEntity.UserProfessionMasterEntity;
import com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfessionMasterRepository extends JpaRepository<UserProfessionMasterEntity, Integer> {

    UserProfessionMasterEntity findByUnitIdAndMobileNumberAndProfession(int unitId, String mobileNumber, String profession);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO(p.id, p.profession, p.name, p.mobileNumber) FROM ProfessionMaster p WHERE p.societyId = ?1 AND p.unitId != ?2")
    List<ProfessionMasterVO> getBySocietyId(int societyId, int unitId);

    UserProfessionMasterEntity findBySocietyIdAndMobileNumberAndProfession(int societyId, String mobileNumber, String profession);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO(p.id, p.profession, p.name, p.mobileNumber) FROM ProfessionMaster p WHERE p.userId = ?1 AND p.unitId = ?2")
    List<ProfessionMasterVO> getUserIdAndUnitId(int userId, int unitId);
}
