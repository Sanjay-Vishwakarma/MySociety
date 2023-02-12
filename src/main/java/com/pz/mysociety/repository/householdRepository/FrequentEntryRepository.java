package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.FrequentEntryEntity;
import com.pz.mysociety.model.Request.FrequentEntryVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequentEntryRepository extends JpaRepository<FrequentEntryEntity, Integer> {

    List<FrequentEntryEntity> findByUnitId(int unitId);

}
