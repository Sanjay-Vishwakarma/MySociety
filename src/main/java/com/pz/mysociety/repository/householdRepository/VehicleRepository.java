package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.VehicleEntity;
import com.pz.mysociety.model.VO.HouseHoldVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {
    VehicleEntity findByUnitIdAndVehicleNumber(int unitId, String vehicleNumber);

    @Query("SELECT new com.pz.mysociety.model.VO.HouseHoldVO(v.id, v.name, v.vehicleNumber, v.type, v.isActive, v.photo) FROM Vehicle v WHERE v.unitId = ?1 AND v.isActive = ?2")
    List<HouseHoldVO> getVehicleDetail(int unitId, boolean b);
}
