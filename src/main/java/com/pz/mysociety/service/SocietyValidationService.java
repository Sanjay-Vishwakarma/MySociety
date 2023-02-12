package com.pz.mysociety.service;


import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.societyEntity.*;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.repository.societyRepository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocietyValidationService {

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private AreaMasterRepository areaMasterRepository;

    @Autowired
    private OwnerMasterRepository ownerMasterRepository;

    @Autowired
    private UnitMasterRepository unitMasterRepository;

    @Autowired
    private EmergencyNumberRepository emergencyNumberRepository;

    @Autowired
    private UserService userService;

    public void isSocietyExist(SocietyVO societyVO) throws PZConstraintViolationException {

        SocietyEntity checkSociety = societyRepository.findBySocietyNameAndPincode(societyVO.getSocietyName(), societyVO.getPincode());

        if(checkSociety != null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isSocietyExist", "Society", "SocietyService", ValidationMessages.Society_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Society_Present_Error, pzConstraints);
        }
    }

    public List<SocietyEntity> isCityExist(SocietyVO societyVO, Pageable pageable) throws PZConstraintViolationException {
        List<SocietyEntity> checkSocietyCity = societyRepository.findByCity(societyVO.getCity(), pageable);

        if(checkSocietyCity == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isCityExist", "City", "SocietyService", Messages.Society_List_Not_Found, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(Messages.Society_List_Not_Found, pzConstraints);
        }
        return checkSocietyCity;
    }

    public void isSocietyValid(int societyId) throws PZConstraintViolationException {
        SocietyEntity checkSocietyValid = societyRepository.findSocietyById(societyId);

        if(checkSocietyValid == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isSocietyValid", "Society", "SocietyService", ValidationMessages.Society_Not_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Society_Not_Present_Error, pzConstraints);
        }
    }

    public void isUserExist(int userId) throws PZConstraintViolationException {
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        userService.isUserExist(userVO);
    }

    public void isAreaExist(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        AreaMasterEntity areaMasterEntity = areaMasterRepository.findBySocietyIdAndAreaTypeIdAndAreaName(areaMasterVO.getSocietyId(), areaMasterVO.getAreaTypeId(), areaMasterVO.getAreaName());

        if(areaMasterEntity != null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isAreaExist", "Area", "SocietyService", ValidationMessages.Area_Exist_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Area_Exist_Error, pzConstraints);
        }
    }

    public void isOwnerExist(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {

        OwnerMasterEntity ownerMasterEntity = ownerMasterRepository.findByUserIdAndUnitId(ownerMasterVO.getUserId(), ownerMasterVO.getUnitId());

        if(ownerMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isOwnerExist", "Owner", "SocietyService", Messages.No_Request_Exist, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(Messages.No_Request_Exist, pzConstraints);
        }
    }

    public void isEmergencyNumberLinked(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {

        EmergencyNumberEntity checkMobileNumber = emergencyNumberRepository.findBySocietyIdAndMobileNumber(emergencyNumberVO.getSocietyId(), emergencyNumberVO.getMobileNumber());

        if(checkMobileNumber != null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isEmergencyNumberLinked", "Mobile", "SocietyService", ValidationMessages.Mobile_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mobile_Present_Error, pzConstraints);
        }
    }
    public void isSocietyExistById(SocietyVO societyVO) throws PZConstraintViolationException {

        SocietyEntity checkSociety = societyRepository.findSocietyById(societyVO.getId());

        if(checkSociety == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isSocietyExist", "Society", "SocietyService", ValidationMessages.Society_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Society_Present_Error, pzConstraints);
        }
    }

    public void isUnitExist(OwnerMasterVO ownerMasterVO) {
        UnitMasterEntity unitMasterEntity = unitMasterRepository.getOne(ownerMasterVO.getUnitId());
    }

    public void isSocietyIdAndUnitIdValid(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        List<OwnerMasterEntity> ownerMasterEntity = ownerMasterRepository.findBySocietyIdAndUnitId(ownerMasterVO.getSocietyId(), ownerMasterVO.getUnitId());

        if(ownerMasterEntity.isEmpty()){
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService","isSocietyIdAndUnitIdValid", "Society", "SocietyService", ValidationMessages.Owner_Not_Found, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Society_Present_Error, pzConstraints);
        }
    }

 /*   public void isSocietyExist(SocietyNoticeVO societyNoticeVO) throws PZConstraintViolationException {

        SocietyEntity checkSociety = societyRepository.findSocietyById(societyNoticeVO.getSocietyId());

        if (checkSociety == null) {
            PZConstraint pzConstraint = new PZConstraint("SocietyValidationService", "isSocietyExist", "Society", "SocietyService", ValidationMessages.Society_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Society_Present_Error, pzConstraints);
        }
    }*/
}
