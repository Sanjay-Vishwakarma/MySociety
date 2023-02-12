package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.SocietyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface SocietyRepository extends JpaRepository<SocietyEntity, Integer>, JpaSpecificationExecutor<SocietyEntity> {
    SocietyEntity findBySocietyNameAndPincode(String societyName, String pincode);

    List<SocietyEntity> findByCity(String city, Pageable pageable);

    @Query(value = "SELECT s FROM Society s WHERE s.id = ?1")
    SocietyEntity findSocietyById(int id);

    int countByCity(String city);

    int countBySocietyNameAndPincodeAndCity(String societyName, String pincode, String city);

    List<SocietyEntity> findBySocietyNameAndPincodeAndCity(String societyName, String pincode, String city, Pageable pageable);

    @Query(value = "SELECT s FROM Society s WHERE s.city = ?1 AND s.societyName LIKE ?2%")
    List<SocietyEntity> getSociety(String city, String societyName, Pageable pageable);

    @Query(value = "SELECT COUNT(s) FROM Society s WHERE s.city = ?1 AND s.societyName LIKE ?2%")
    int getCountCityAndSocietyName(String city, String societyName);

    @Query(value = "SELECT s FROM Society s WHERE s.societyName LIKE ?1% AND s.city = ?2")
    List<SocietyEntity> getSocietyNameSearch(String societyName,String city, Pageable pageable);

    @Query(value = "SELECT s FROM Society s WHERE s.city = ?1 AND s.pincode LIKE ?2%")
    List<SocietyEntity> getPincodeSearch(String city, String pincode, Pageable pageable);

    @Query(value = "SELECT COUNT(s) FROM Society s WHERE s.societyName LIKE ?1% AND s.city = ?2")
    int getSocietyNameAndCityCount(String societyName, String city);

    @Query(value = "SELECT COUNT(s) FROM Society s WHERE s.city = ?1 AND s.pincode LIKE ?2%")
    int getCityAndPincodeCount(String societyName, String pincode);

    @Query(value = "SELECT s.id FROM Society s WHERE s.pincode = ?1")
    List<Integer> getSocietyIdByPinCode(String pincode);

    @Query(value = "SELECT s.id FROM Society s WHERE s.pincode = ?1 AND s.pincode LIKE ?2%")
    List<Integer> getSocietyIdByPinCodeLike(String pincode, String lastCharRemoved);
}
