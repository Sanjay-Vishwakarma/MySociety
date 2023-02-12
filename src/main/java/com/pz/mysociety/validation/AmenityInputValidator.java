package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.AmenityTypeVO;
import com.pz.mysociety.model.Request.AmenityVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AmenityInputValidator {
    public void addAmenityInputValidator(AmenityVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenity", "userId", "AmenityService", ValidationMessages.UserId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (amenityVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenity", "societyId", "AmenityService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(amenityVO.getUnitId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "unitId", "AmenityService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(amenityVO.getAmenityTypeId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "amenityTypeId", "AmenityService", ValidationMessages.AmenityTypeId_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(amenityVO.getBookingDate()==null) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "Date", "AmenityService", ValidationMessages.Booking_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(amenityVO.getStartDate()==null) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "start Date", "AmenityService", ValidationMessages.Start_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(amenityVO.getEndDate()==null) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "end Date", "AmenityService", ValidationMessages.End_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.isEndDateGreatThanStartDate(amenityVO.getStartDate(), amenityVO.getEndDate())){
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","addAmenity", "Time", "AmenityService", ValidationMessages.End_Date_Time_Error, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(amenityVO.getAreaId()==0){
            PZConstraint pzConstraint=new PZConstraint("AmenityInputValidator", "addAmenity","areaId","AmenityService",ValidationMessages.Area_Id_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void amenityInputValidator(AmenityVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (amenityVO.getAmenityId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "Amenity", "amenityId", "AmenityService", ValidationMessages.AmenityId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void changeAmenityFlagInputValidator(AmenityVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (amenityVO.getAmenityId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("changeAmenityFlagInputValidator", "Amenity", "amenityId", "AmenityService", ValidationMessages.AmenityId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (!Functions.nonNullString(amenityVO.getRole())) {
            PZConstraint pzConstraint = new PZConstraint("changeAmenityFlagInputValidator", "Amenity", "role", "AmenityService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void addAmenityReocrdInputValidator(AmenityVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getAmenityId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenityReocrdInputValidator", "amenityId", "AmenityService", ValidationMessages.AmenityId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (!Functions.nonNullString(amenityVO.getRole())) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenityReocrdInputValidator", "role", "AmenityService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (!Functions.nonNullString(amenityVO.getContent()) && !Functions.nonNullString(amenityVO.getAttachment())) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenityReocrdInputValidator", "amenityComtent", "AmenityService", ValidationMessages.Amenity_Content_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void addAmenityTypeInputValidator(AmenityTypeVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getSocietyId() == 0 || !Functions.nonNullString(amenityVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "addAmenityTypeInputValidator", "IdAndType", "AmenityService", ValidationMessages.SocietyId_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void getSocietyAmenityTypeInputValidator(AmenityTypeVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "getSocietyAmenityTypeInputValidator", "amenityId", "AmenityService", ValidationMessages.SocietyId_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }  public void updateAmenityTypeInputValidator(AmenityTypeVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getId() == 0 && !Functions.nonNullString(amenityVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "updateAmenityTypeInputValidator", "amenityId", "AmenityService", ValidationMessages.AmenityTypeId_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void deleteAmenityTypeInputValidator(AmenityTypeVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (amenityVO.getId()==0) {
            PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator", "deleteAmenityTypeInputValidator", "amenityTypeId", "AmenityService", ValidationMessages.AmenityTypeId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void getAmenityListInputValidator(AmenityVO amenityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(amenityVO.getInitiatedBy().equals(UserRoles.USER)){
            if(amenityVO.getUserId()== 0 && amenityVO.getUnitId()==0 ) {
                PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","getAmenityListInputValidator", "userId", "ComplainService", ValidationMessages.UserIdAndFlatId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }}
        if(amenityVO.getInitiatedBy().equals(UserRoles.SOCIETY)){
            if(amenityVO.getSocietyId()== 0) {
                PZConstraint pzConstraint = new PZConstraint("AmenityInputValidator","getAmenityListInputValidator", "societyId", "ComplainService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }}
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

}
