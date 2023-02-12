package com.pz.mysociety.repository.productServiceRepository;

import com.pz.mysociety.entity.productServiceEntity.ProductServiceImageMasterEntity;
import com.pz.mysociety.model.Request.productServiceRequest.ProductServiceImageMasterVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductServiceImageMasterRepository extends JpaRepository<ProductServiceImageMasterEntity, Integer> {
    int countByProductId(int productId);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.productServiceRequest.ProductServiceImageMasterVO(pdi.id, pdi.imageUrl) FROM ProductImage pdi WHERE pdi.productId = ?1 AND pdi.type = ?2")
    List<ProductServiceImageMasterVO> getByProductId(int productId, String type);

    int countByProductIdAndType(int productId, String type);
}
