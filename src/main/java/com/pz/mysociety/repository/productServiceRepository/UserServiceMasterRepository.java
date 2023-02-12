package com.pz.mysociety.repository.productServiceRepository;

import com.pz.mysociety.entity.productServiceEntity.UserServiceMasterEntity;
import com.pz.mysociety.model.Request.productServiceRequest.UserServiceMasterVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceMasterRepository extends JpaRepository<UserServiceMasterEntity, Integer> , JpaSpecificationExecutor<UserServiceMasterEntity> {

    UserServiceMasterEntity findByUnitIdAndTitle(int unitId, String title);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.productServiceRequest.UserServiceMasterVO(sm.id, sm.serviceCategoryId, sm.serviceSubCategoryId, sm.title, sm.description, sm.mobileNumber, sm.servicePrice, sm.serviceType, sm.serviceDay, sm.serviceProvide, sm.serviceRange, sm.serviceLocation, sm.whatsappUrl, sm.isCall, sm.serviceUrl, sm.isUnitVisible)  FROM ServiceMaster sm WHERE sm.societyId IN ?1")
    List<UserServiceMasterVO> getServiceBySocietyId(List<Integer> societyId);

    int countBySocietyIdIn(List<Integer> societyId);
}
