package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.entity.amenityEntity.AmenityMasterEntity;
import com.pz.mysociety.entity.amenityEntity.AmenityTypeEntity;
import com.pz.mysociety.model.Request.AmenityTypeVO;
import com.pz.mysociety.model.Request.AmenityVO;
import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.repository.amenityRepository.AmenityMasterRepository;
import com.pz.mysociety.repository.amenityRepository.AmenityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmenityValidationService {
    @Autowired
    private AmenityMasterRepository amenityMasterRepository;

    @Autowired
    private AmenityTypeRepository amenityTypeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SocietyService societyService;

    public void isUserMappingExist(int userId) throws PZConstraintViolationException {
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        userService.isUserExist(userVO);
    }
    public void isSocietyExist(AmenityTypeVO amenityTypeVO) throws PZConstraintViolationException {
        societyService.isSocietyExist(amenityTypeVO);
    }
    public void isAmenityIdExist(AmenityVO amenityVO) throws PZConstraintViolationException {

        AmenityMasterEntity amenityMasterEntity=amenityMasterRepository.getAmenityById(amenityVO.getAmenityId());
        if(amenityMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("AmenityValidationService","isAmenityIdExist", "Amenity ID", "AmenityValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isAmenityTypeIdExist(AmenityTypeVO amenityTypeVO) throws PZConstraintViolationException {

        AmenityTypeEntity amenityMasterEntity=amenityTypeRepository.getAmenityTypeById(amenityTypeVO.getId());
        if(amenityMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("AmenityValidationService","isAmenityTypeIdExist", "AmenityType ID", "AmenityValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isAmenityTypeIdExist(AmenityVO amenityVO) throws PZConstraintViolationException {

        AmenityTypeEntity amenityMasterEntity=amenityTypeRepository.getAmenityTypeById(amenityVO.getAmenityTypeId());
        if(amenityMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("AmenityValidationService","isAmenityTypeIdExist", "AmenityType ID", "AmenityValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }

    public void isAmenityBooked(AmenityVO amenityVO) throws PZConstraintViolationException {
        int count = amenityMasterRepository.checkAmenityBook(Status.APPROVED, amenityVO.getAmenityTypeId(), Functions.getFormatDate(amenityVO.getStartDate()), Functions.getFormatDate(amenityVO.getEndDate()));
        if(count != 0) throw new PZConstraintViolationException(ValidationMessages.Amenity_Time_Slot_Error);
    }
}
