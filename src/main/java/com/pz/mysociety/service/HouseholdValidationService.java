package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.complainEntity.OwnerMasterEntity;
import com.pz.mysociety.entity.householdEntity.CompanyMasterEntity;
import com.pz.mysociety.model.Request.HelperMappingVO;
import com.pz.mysociety.model.Request.OwnerMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.repository.householdRepository.CompanyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HouseholdValidationService {

    @Autowired
    private SocietyService societyService;


    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    public void isUnitValid(int unitId) {
        OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
        ownerMasterVO.setUnitId(unitId);
        societyService.isUnitExist(ownerMasterVO);
    }

    public void isSocietyAndUnit(int societyId , int unitId) throws PZConstraintViolationException {
        OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
        ownerMasterVO.setUnitId(unitId);
        ownerMasterVO.setSocietyId(societyId);
        societyService.isSocietyIdAndUnitIdValid(ownerMasterVO);
    }

    public void isCompanyValid(int companyId) {
        CompanyMasterEntity companyMasterEntity = companyMasterRepository.getOne(companyId);

    }
    public  void isUnitansUserMapping(int userId, int unitId) throws PZConstraintViolationException {
        OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
        ownerMasterVO.setUnitId(unitId);
        ownerMasterVO.setUserId(userId);
        ResponseVO userMapping= societyService.isUserMappingExist(ownerMasterVO);
        if (userMapping.getOwner() == null) {
            PZConstraint pzConstraint = new PZConstraint("houseHoldValidationService", "isUnitansUserMapping", "ActivityLog", "ActivityLogValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }

}
