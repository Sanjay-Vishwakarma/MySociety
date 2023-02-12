package com.pz.mysociety.repository.productServiceRepository;

import com.pz.mysociety.entity.productServiceEntity.ProductServiceMasterEntity;
import com.pz.mysociety.model.Request.productServiceRequest.ProductServiceMasterVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface ProductServiceMasterRepository extends JpaRepository<ProductServiceMasterEntity, Integer> , JpaSpecificationExecutor<ProductServiceMasterEntity> {
    ProductServiceMasterEntity findByUnitIdAndTitle(int unitId, String title);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.productServiceRequest.ProductServiceMasterVO(pm.id,pm.productCategoryId, pm.productSubCategoryId, pm.title, pm.description, pm.purchaseYear, pm.type, pm.price, pm.originalPrice, pm.mobileNumber, pm.address, pm.brand, pm.productCondition, pm.productUrl, pm.isNegotiable, pm.isCall)  FROM ProductMaster pm WHERE pm.societyId IN ?1 AND pm.productCategoryId = ?2")
    List<ProductServiceMasterVO> getProductBySocietyId(List<Integer> societyId, int productCategoryId);

    int countBySocietyIdInAndProductCategoryId(List<Integer> societyId, int productCategoryId);
//    int countBySocietyIdIn(List<Integer> societyId);
}
