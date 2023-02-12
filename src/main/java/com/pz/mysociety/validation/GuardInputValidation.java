package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.GuardDocumentMasterVO;
import com.pz.mysociety.model.Request.GuardHistoryVO;
import com.pz.mysociety.model.Request.GuardVO;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

@Component
@Validated
public class GuardInputValidation {

    public void addGuardInputValidation(GuardVO guardVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (guardVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "updateGuardInputValidation", "Id", "GuardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        } else {

            if (guardVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Id", "GuardService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(guardVO.getRole())) {

                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Role", "GuardService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(guardVO.getName())) {

                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Name", "GuardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(guardVO.getMobileNumber())) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Mobile", "GuardService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(String.valueOf(guardVO.getGuardDocumentList()))){
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation","addGuardDocumentList","GuardDocumentList","GuardService",ValidationMessages.Mandatory_DOcument_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }


            if (!Functions.nonNullString(guardVO.getPhoto())) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Photo", "GuardService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (Functions.nonNullString(guardVO.getRole()) && !guardVO.getRole().equals(UserRoles.GUARD)) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "addGuardInputValidation", "Role", "GuardService", ValidationMessages.Role_Not_Permitted, PZConstraintExceptionEnum.INVALID_REQUEST);
                pzConstraints.add(pzConstraint);

            }
        }

            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }
  //  }
    public void guardLoginInputValidation(GuardVO guardVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardLoginInputValidation", "Id", "GuardService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (guardVO.getGuardPin() == 0) {
            if (!Functions.nonNullString(guardVO.getMobileNumber())) {
                PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardLoginInputValidation", "Mobile", "GuardService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void guardIdInputValidation(GuardVO guardVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardVO.getId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardIdInputValidation", "Id", "GuardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);

        }

    }

    public void verifyInputValidation(GuardVO guardVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "verifyInputValidation", "Id", "GuardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (guardVO.getValidateOtp() == 0) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "verifyInputValidation", "ValidateOTP", "GuardService", ValidationMessages.OTP_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getGuardListInputValidation(GuardVO guardVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("getGuardListInputValidation", "guardIdInputValidation", "Id", "GuardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);

        }

    }

//    public void updateGuardInputValidation(GuardVO guardVO) {
//       ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
//
//        if(guardVO.getId() == 0){
//            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation","updateGuardInputValidation", "Id", "GuardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!Functions.nonNullString(guardVO.getName())){
//            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation","updateGuardInputValidation", "Name", "GuardService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!Functions.nonNullString(guardVO.getMobileNumber())){
//            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation","updateGuardInputValidation", "Mobile", "GuardService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(pzConstraints.size() > 0){
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//
//    }

    public void guardAttendanceInputValidation(GuardHistoryVO guardHistoryVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guardHistoryVO.getGuardId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardAttendanceInputValidation", "Id", "GuardService", ValidationMessages.Guard_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(guardHistoryVO.getYear())) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardAttendanceInputValidation", "Year", "GuardService", ValidationMessages.Year_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(guardHistoryVO.getMonth())) {
            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardAttendanceInputValidation", "Month", "GuardService", ValidationMessages.Month_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
}

//    public void guardDocumentInputValidation(GuardDocumentMasterVO guardDocumentMasterVO)throws PZConstraintViolationException{
//        ArrayList<PZConstraint>pzConstraints=new ArrayList<>();
//
//        if(guardDocumentMasterVO.getGuardId()==0){
//            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation","guardDocumentInputValidation","GuardId","GuardService",ValidationMessages.ID_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(guardDocumentMasterVO.getId()==0){
//            PZConstraint pzConstraint=new PZConstraint("GuardInputValidation","guardDocumentInputValidation","Id","GuardService",ValidationMessages.ID_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!Functions.nonNullString(guardDocumentMasterVO.getDocumentTypeId())){
//
//            PZConstraint pzConstraint=new PZConstraint("GuardInputValidation","guardDocumentInputValidation","DocumentTypeId","GuardService",ValidationMessages.ID_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//        if (!Functions.nonNullString(guardDocumentMasterVO.getDocument())) {
//
//            PZConstraint pzConstraint = new PZConstraint("GuardInputValidation", "guardDocumentInputValidation", "Document", "GuardService", ValidationMessages.Document_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//
//        }
//
//        if (pzConstraints.size() > 0) {
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//
//
//    }
//}
