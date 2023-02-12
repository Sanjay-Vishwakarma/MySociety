package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.MobileConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileConfigurationRepository extends JpaRepository<MobileConfigurationEntity, Integer> {

    MobileConfigurationEntity findByIsActive(boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.entity.societyEntity.MobileConfigurationEntity(mp.id, mp.providerName, mp.isActive) FROM MobileProvider mp")
    List<MobileConfigurationEntity> getProvider();

    MobileConfigurationEntity findByProviderName(String provider);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE MobileProvider mp SET mp.isActive = ?1")
    void getActiveProvider(boolean isActive);
}
