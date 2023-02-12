package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.Types;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HouseholdInputValidator {

    public void addGuestInputValidation(GuestVO guestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (guestVO.getUnitId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(guestVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(guestVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Mobile", "HouseholdService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!guestVO.getIsFrequent()) {

            if (guestVO.getDate() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Date", "HouseholdService", ValidationMessages.Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (guestVO.getStartTime() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Time", "HouseholdService", ValidationMessages.Start_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (guestVO.getValidFor() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestInputValidation", "Time", "HouseholdService", ValidationMessages.End_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        } else {

            if (guestVO.getStartDate() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestFrequentInputValidation", "Date", "HouseholdService", ValidationMessages.Start_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (guestVO.getEndDate() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addGuestFrequentInputValidation", "Date", "HouseholdService", ValidationMessages.End_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

//    public void addGuestFrequentInputValidation(GuestVO guestVO) throws PZConstraintViolationException {
//        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
//
//        if(guestVO.getUnitId() == 0){
//
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator","addGuestFrequentInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//
//        }
//        if(!Functions.nonNullString(guestVO.getName())){
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator","addGuestFrequentInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!Functions.nonNullString(guestVO.getMobileNumber())){
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator","addGuestFrequentInputValidation", "Mobile", "HouseholdService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//
//        if(guestVO.getStartDate() == null){
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator","addGuestFrequentInputValidation", "Date", "HouseholdService", ValidationMessages.Start_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(guestVO.getEndDate() == null){
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator","addGuestFrequentInputValidation", "Date", "HouseholdService", ValidationMessages.End_Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(pzConstraints.size() > 0){
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//
//    }

    public void addVehicleInputValidation(VehicleVO vehicleVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (vehicleVO.getUnitId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addVehicleInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(vehicleVO.getName())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addVehicleInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(vehicleVO.getVehicleNumber())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addVehicleInputValidation", "Number", "HouseholdService", ValidationMessages.Vehicle_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(vehicleVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addVehicleInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateVehicleInputValidation(VehicleVO vehicleVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (vehicleVO.getId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateVehicleInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(vehicleVO.getName())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateVehicleInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(vehicleVO.getVehicleNumber())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateVehicleInputValidation", "Number", "HouseholdService", ValidationMessages.Vehicle_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(vehicleVO.getType())) {

            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateVehicleInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addFrequentEntryInputValidation(FrequentEntryVO frequentRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (frequentRequestVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (frequentRequestVO.getStartTime() == null) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Time", "HouseholdService", ValidationMessages.Start_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (frequentRequestVO.getValidFor() == null) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Time", "HouseholdService", ValidationMessages.End_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(frequentRequestVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(frequentRequestVO.getIsFrequent()){

            if (!Functions.nonNullString(frequentRequestVO.getDays())) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Days", "HouseholdService", ValidationMessages.Days_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(frequentRequestVO.getValidDays())) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Days", "HouseholdService", ValidationMessages.Valid_Days_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {
            if (frequentRequestVO.getDate() == null) {
                PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Date", "HouseholdService", ValidationMessages.Date_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if (frequentRequestVO.getType().equalsIgnoreCase(Types.CAB) && !Functions.nonNullString(frequentRequestVO.getVehicleNumber())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentEntryInputValidation", "Number", "HouseholdService", ValidationMessages.Vehicle_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

//    public void addFrequentInputValidation(FrequentEntryVO frequentRequestVO) throws PZConstraintViolationException {
//        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
//
//        if (frequentRequestVO.getUnitId() == 0) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//
//        if (frequentRequestVO.getStartTime() == null) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Time", "HouseholdService", ValidationMessages.Start_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if (frequentRequestVO.getValidFor() == null) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Time", "HouseholdService", ValidationMessages.End_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if (!Functions.nonNullString(frequentRequestVO.getType())) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if (frequentRequestVO.getType().equalsIgnoreCase(Types.CAB) && !Functions.nonNullString(frequentRequestVO.getVehicleNumber())) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFrequentInputValidation", "Number", "HouseholdService", ValidationMessages.Vehicle_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if (pzConstraints.size() > 0) {
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//    }

    public void addFamilyMemberInputValidation(FamilyMemberVO familyMemberVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (familyMemberVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getParentId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Id", "HouseholdService", ValidationMessages.Parent_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getName() == null) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Time", "HouseholdService", ValidationMessages.Start_Time_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(familyMemberVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getType().equalsIgnoreCase(Types.ADULT) && !Functions.nonNullString(familyMemberVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Mobile", "HouseholdService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getType().equalsIgnoreCase(Types.ADULT) && !Functions.nonNullString(familyMemberVO.getPhoto())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Photo", "HouseholdService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getType().equalsIgnoreCase(Types.KID) && !Functions.nonNullString(familyMemberVO.getMonitor())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addFamilyMemberInputValidation", "Monitor", "HouseholdService", ValidationMessages.Monitor_Message_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void linkHelperInputValidation(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperMappingVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getAreaId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getHelperId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getHelperDetailInputValidation(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperMappingVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getHelperDetailInputValidation", "Id", "HouseholdService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getHelperId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getHelperDetailInputValidation", "Id", "HouseholdService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addReviewInputValidation(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperMappingVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addReviewInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getRating() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addReviewInputValidation", "Id", "HouseholdService", ValidationMessages.Rating_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(helperMappingVO.getReview())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addReviewInputValidation", "Review", "HouseholdService", ValidationMessages.Review_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void unLinkHelperInputValidation(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperMappingVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "unLinkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(helperMappingVO.getRemark())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "unLinkHelperInputValidation", "Remark", "HouseholdService", ValidationMessages.Remark_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getReviewInputValidation(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (helperMappingVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (helperMappingVO.getHelperId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "linkHelperInputValidation", "Id", "HouseholdService", ValidationMessages.Helper_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addCompanyInputValidation(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(companyMasterVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addCompanyInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (companyMasterVO.getCompanyTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addCompanyInputValidation", "Id", "HouseholdService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(companyMasterVO.getCompanyLogo())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addCompanyInputValidation", "Logo", "HouseholdService", ValidationMessages.Company_Logo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getCompanyInputValidation(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (companyMasterVO.getCompanyTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getCompanyInputValidation", "Id", "HouseholdService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateCompanyInputValidation(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (companyMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateCompanyInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(companyMasterVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateCompanyInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (companyMasterVO.getCompanyTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateCompanyInputValidation", "Id", "HouseholdService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if (!Functions.nonNullString(companyMasterVO.getCompanyLogo())) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addCompanyInputValidation", "Logo", "HouseholdService", ValidationMessages.Company_Logo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addTypeInputValidation(TypeVO typeVO) throws PZConstraintViolationException {
        if (!Functions.nonNullString(typeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "addTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateTypeInputValidation(TypeVO typeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (typeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateTypeInputValidation", "Id", "SocietyService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(typeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getHouseHoldTypeInputValidation(HouseholdVO householdVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (householdVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getHouseHoldTypeInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(householdVO.getHouseholdType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getHouseHoldTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }


    public void getActivityLogInputValidation(ActivityLogVO activityLogVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (activityLogVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getActivityLogInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (activityLogVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "getActivityLogInputValidation", "Id", "SocietyService", ValidationMessages.UserId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateFamilyMemberInputValidation(FamilyMemberVO familyMemberVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (familyMemberVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(familyMemberVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Name", "HouseholdService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(familyMemberVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Type", "HouseholdService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (familyMemberVO.getType().equalsIgnoreCase(Types.ADULT) && !Functions.nonNullString(familyMemberVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Mobile", "HouseholdService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if (familyMemberVO.getType().equalsIgnoreCase(Types.ADULT) && !Functions.nonNullString(familyMemberVO.getPhoto())) {
//            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Photo", "HouseholdService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (familyMemberVO.getType().equalsIgnoreCase(Types.KID) && !Functions.nonNullString(familyMemberVO.getMonitor())) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "updateFamilyMemberInputValidation", "Monitor", "HouseholdService", ValidationMessages.Monitor_Message_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void deleteFamilyMemberInputValidation(FamilyMemberVO familyMemberVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (familyMemberVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("HouseholdInputValidator", "deleteFamilyMemberInputValidation", "Id", "HouseholdService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
}
