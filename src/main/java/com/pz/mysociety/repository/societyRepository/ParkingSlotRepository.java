package com.pz.mysociety.repository.societyRepository;


import com.pz.mysociety.entity.societyEntity.ParkingSlotEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository <ParkingSlotEntity,Integer>, JpaSpecificationExecutor <ParkingSlotEntity>{

    ParkingSlotEntity findBySocietyIdAndAreaIdAndUnitId(int SocietyId, int areaId, int unitId);
    ParkingSlotEntity findByAreaId(int areaId);
    ParkingSlotEntity findByName(String name);
    ParkingSlotEntity findByUnitIdAndName(int unitId, String name);
   // ParkingSlotEntity findByUnitIdAndIsOccupied(int unitId, boolean IsOccupied);
   //List<ParkingSlotEntity> findByUnitId(int unitId);

}
