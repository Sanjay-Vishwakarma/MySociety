package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.GuestEntity;
import com.pz.mysociety.model.VO.HouseHoldVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Integer>  {

    GuestEntity findByUnitIdAndMobileNumber(int unitId, String mobileNumber);

    @Query("SELECT new com.pz.mysociety.model.VO.HouseHoldVO(g.id, g.name, g.mobileNumber) FROM Guest g WHERE g.unitId = ?1 ORDER BY g.id DESC")
    List<HouseHoldVO> getGuestDetail(int unitId);

}
