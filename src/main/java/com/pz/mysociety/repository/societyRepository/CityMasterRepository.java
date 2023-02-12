package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.CityMasterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface CityMasterRepository extends JpaRepository<CityMasterEntity, Integer> {

    @Query(value = "SELECT c.city FROM City c WHERE c.city LIKE ?1%")
    List<String> getCityByName(String cityName, Pageable searchPageable);

    @Query(value = "SELECT c.city FROM City c")
    List<String> getCity(Pageable pageable);

}
