package com.pz.mysociety.repository.productServiceRepository;

import com.pz.mysociety.entity.productServiceEntity.ProductServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Repository
public interface ProductServiceCategoryRepository extends JpaRepository<ProductServiceCategoryEntity, Integer>, JpaSpecificationExecutor<ProductServiceCategoryEntity> {
    ProductServiceCategoryEntity findByName(String name);
}
