package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperDocumentTypeEntity;
import com.pz.mysociety.model.VO.HelperDocumentListVO;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelperDocumentTypeRepository extends JpaRepository<HelperDocumentTypeEntity, Integer> , JpaSpecificationExecutor<HelperDocumentTypeEntity> {

    HelperDocumentTypeEntity findBySocietyIdAndServiceTypeIdAndHelperTypeIdAndDocumentTypeIdAndIsActive(int societyId, int serviceTypeId, int helperTypeId, int documentTypeId, boolean isActive );

    int countBySocietyIdAndIsActive(int societyId, boolean isActive);

    int countBySocietyId(int societyId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperDocumentListVO( hd.id,hd.societyId, hd.helperTypeId, hd.serviceTypeId, hd.documentTypeId, hd.isActive, hd.isMandatory ,ht.type, ht.iconImage, st.type ) FROM HelperDocumentType hd join  HelperType ht on ht.id=hd.helperTypeId join ServiceType st on st.id= hd.serviceTypeId WHERE hd.societyId = ?1 ")
    List<HelperDocumentListVO> getDocumentMapping(int societyId,Pageable pageable);

    List<HelperDocumentTypeEntity> findBySocietyIdAndHelperTypeIdAndIsMandatory(int societyId, int helperTypeId , boolean isMandatory);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperDocumentListVO( hd.id,hd.societyId, hd.helperTypeId, hd.serviceTypeId, hd.documentTypeId, hd.isActive, hd.isMandatory ,ht.type, ht.iconImage, st.type ) FROM HelperDocumentType hd join  HelperType ht on ht.id=hd.helperTypeId join ServiceType st on st.id= hd.serviceTypeId WHERE hd.societyId = ?1 and hd.helperTypeId = ?2 and hd.isActive = ?3 ")
    List<HelperDocumentListVO> getDocumentByHelperTypeIdAndSocietyId(int societyId,int helperTypeId,boolean isActive, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperDocumentListVO( hd.id,hd.societyId, hd.helperTypeId, hd.serviceTypeId, hd.documentTypeId, hd.isActive, hd.isMandatory ,ht.type, ht.iconImage, st.type ) FROM HelperDocumentType hd join  HelperType ht on ht.id=hd.helperTypeId join ServiceType st on st.id= hd.serviceTypeId WHERE hd.societyId = ?1 and hd.helperTypeId = ?2 ")
    List<HelperDocumentListVO> getDocumentByHelperTypeIdAndSocietyId(int societyId,int helperTypeId, Pageable pageable);



}
