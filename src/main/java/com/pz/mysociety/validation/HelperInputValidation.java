package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HelperInputValidation {
    public void addHelperInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(helperVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            if (!Functions.nonNullString(helperVO.getHelperName()) && !Functions.nonNullString(helperVO.getHelperMobile()) /*&& !Functions.nonNullString(helperRequestVO.getPhoto())*/) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "updateHelperInputValidator", "Data", "HelperService", ValidationMessages.Valid_Data_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }
       else{

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperVO.getAreaId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "AreaId", "HelperService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperVO.getUnitId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (!Functions.nonNullString(helperVO.getHelperName())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Name", "HelperService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getPhoto())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Photo", "HelperService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperVO.getHelperTypeId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "helperTypeId", "HelperService", ValidationMessages.HelperTypeIdRequired, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getHelperMobile())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperInputValidator", "Mobile", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }}

    public void getHelperTypeInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperTypeInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperTypeInputValidator", "Type", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getHelperInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
   /*     if (helperVO.getHelperId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Id", "HelperService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getInTime())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "inTime", "HelperService", ValidationMessages.In_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }*/
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addHelperTypeInputValidator(HelperTypeVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getServiceTypeId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperTypeInputValidator", "Id", "HelperService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(helperVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperTypeInputValidator", "Type", "HelperService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getIconImage())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperTypeInputValidator", "Icon", "HelperService", ValidationMessages.Icon_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addServiceTypeInputValidation(ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(serviceTypeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addServiceTypeInputValidation", "Type", "HelperService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateServiceTypeInputValidator(ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (serviceTypeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Id", "SocietyService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(serviceTypeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getDailyHelperTypeInputValidator(HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperTypeRequestVO.getServiceTypeId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperTypeInputValidator", "Id", "HelperService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);

        }
    }

    public void HelperTypeInputValidator(HelperTypeVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(helperVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperTypeInputValidator", "Type", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateHelperTypeInputValidator(HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperTypeRequestVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "updateHelperTypeInputValidator", "Id", "HelperService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(helperTypeRequestVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "updateHelperTypeInputValidator", "Type", "HelperService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

//    public void updateHelperInputValidator(HelperVO helperRequestVO) throws PZConstraintViolationException {
//        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
//
//        if (!Functions.nonNullString(helperRequestVO.getHelperName()) && !Functions.nonNullString(helperRequestVO.getHelperMobile()) /*&& !Functions.nonNullString(helperRequestVO.getPhoto())*/) {
//
//            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "updateHelperInputValidator", "Data", "HelperService", ValidationMessages.Valid_Data_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//
//        }
//        if (pzConstraints.size() > 0) {
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//    }

    public void addMandatoryDocumentInputValidator(HelperDocumentVO helperDocumentRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperDocumentRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addMandatoryDocumentInputValidator", "SocietyID", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperDocumentRequestVO.getMandatoryDocument())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addMandatoryDocumentInputValidator", "documents", "HelperService", ValidationMessages.Mandatory_DOcument_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addPhoneNumberInputValidator(HelperVO helperRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addPhoneNumberInputValidator", "SocietyId ", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperRequestVO.getHelperMobile())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addPhoneNumberInputValidator", "Phone", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getServiceByTypeInputValidation(ServiceTypeVO serviceTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(serviceTypeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getServiceByTypeInputValidation", "Type", "HelperService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }


    }

    public void addVisitorHistoryLogInputValidator(HelperHistoryLogVO visitorHistoryVo) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorHistoryVo.getAction().equals("in")) {
            if (visitorHistoryVo.getSocietyId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "visitorService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if(visitorHistoryVo.getHelperTypeId()==0){
                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "helperTypeId", "visitorService", ValidationMessages.Helper_Type_Id, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (visitorHistoryVo.getHelperId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "helperId", "visitorService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (visitorHistoryVo.getUnitIdList() == null) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "unitId", "visitorService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(visitorHistoryVo.getCompanyName())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Company Name", "HelperService", ValidationMessages.Company_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

        } else {
            if (visitorHistoryVo.getHelperId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Visitor_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getAction())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Action", "HelperService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getLoginId())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "LoginId", "HelperService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getByPassCodeInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperVO.getPassCode()== 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "PassCode", "HelperService", ValidationMessages.PassCode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void addActivityInputValidation(ActivityVO activityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (activityVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (activityVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(activityVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(activityVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Mobile", "HouseholdService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(activityVO.getDate())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Time", "HouseholdService", ValidationMessages.Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(activityVO.getInTime())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Time", "HouseholdService", ValidationMessages.In_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (!Functions.nonNullString(activityVO.getAllowedBy())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Allowed", "SocietyService", ValidationMessages.Allowed_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (Functions.nonNullString(activityVO.getCompanyName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "CompanyName", "SocietyService", ValidationMessages.Company_Name_Length_Error, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getActivityInputValidation(ActivityVO activityVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (activityVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void addHelperHistoryLogInputValidator(HelperHistoryLogVO helperHistoryVo) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperHistoryVo.getAction().equals("in")) {
            if (helperHistoryVo.getSocietyId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (helperHistoryVo.getHelperId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }

            if (!Functions.nonNullString(helperHistoryVo.getBodyTemperature())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Body Temperature Check", "HelperService", ValidationMessages.Body_Temperature_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }


        } else {

            if (!Functions.nonNullString(helperHistoryVo.getLoginId())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "LoginId", "HelperService", ValidationMessages.OutTime_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        }

        if (!Functions.nonNullString(helperHistoryVo.getAction())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Action", "HelperService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void addVisitorInputValidator(VisitorVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (visitorVO.getUnitIdList() == null) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (visitorVO.getHelperTypeId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "helperTypeId", "HelperService", ValidationMessages.HelperTypeIdRequired, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(visitorVO.getHelperMobile())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Visitor Phone Number", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(visitorVO.getHelperName())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Name", "HelperService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(visitorVO.getBodyTemperature())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "BodyTemperature", "HelperService", ValidationMessages.Body_Temperature_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getWaitingListInputValidator(HelperVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "checkVisitorInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getVisitorInputValidator(HelperVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "checkVisitorInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void checkVisitorInputValidator(HelperVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "checkVisitorInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(visitorVO.getHelperMobile())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "checkVisitorInputValidator", "Visitor Phone Number", "HelperService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getLoginDetailsInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getLoginId())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "LoginId", "HelperService", ValidationMessages.PassCode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getInOutHistoryInputValidator(HelperVO helperVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperVO.getAreaId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "AreaId", "HelperService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperVO.getDate())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "Date", "HelperService", ValidationMessages.Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addHelperDocument(HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperDocRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "SocietyId", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getHelperId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "HelperId", "HelperService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getDocumentTypeId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "DocumentTypeId", "HelperService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(helperDocRequestVO.getDocument())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "Document", "HelperService", ValidationMessages.Document_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getHelperDocumentByHelperTypeIdInputValidator(HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperDocRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "SocietyId", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getHelperTypeId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "HelperTypeId", "HelperService", ValidationMessages.HelperTypeIdRequired, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }


    public void addHelperTypeDocument(HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (helperDocRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "SocietyId", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getHelperTypeIdList().size() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "helperTypeId", "HelperService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getServiceTypeId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "HelperId", "HelperService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getDocumentTypeId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperDocument", "DocumentTypeId", "HelperService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getHelperDocumentByHelperIdInputValidator(HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperDocRequestVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "SocietyId", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (helperDocRequestVO.getHelperId()==0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "getHelperInputValidator", "HelperId", "HelperService", ValidationMessages.HelperTypeIdRequired, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }


    public void recentVisitorInputValidation(HelperVO helperVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addActivityInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
}

