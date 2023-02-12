package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.UserStatus;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.SocietyRequestVO.ParkingSlotVO;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class SocietyInputValidator {


    public void addSocietyInputValidation(SocietyVO societyVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(societyVO.getSocietyName())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Name", "SocietyService", ValidationMessages.Society_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getRegistrationNumber())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Registration", "SocietyService", ValidationMessages.Registration_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getBillingEmail())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Billing Number", "SocietyService", ValidationMessages.Bill_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getAddress())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Address", "SocietyService", ValidationMessages.Address_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getSocietyBlock())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Block", "SocietyService", ValidationMessages.Society_Block_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getState())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "State", "SocietyService", ValidationMessages.State_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getCity())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "City", "SocietyService", ValidationMessages.City_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getPincode())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyInputValidation", "Pincode", "SocietyService", ValidationMessages.Pincode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getPhoto())) {
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addSocietyInputValidation", "Photo", "SocietyService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getSocietyDocumentInputValidation(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "societyIdInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (societyDocumentVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "societyIdInputValidation", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if(!Functions.nonNullString(societyDocumentVO.getSocietyRole())){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!societyDocumentVO.getSocietyRole().equalsIgnoreCase(UserRoles.SECRETARY)){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void societyIdInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "societyIdInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void linkSocietyInputValidation(SocietyMappingVO societyMappingVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyMappingVO.getUser().getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "linkSocietyInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if(!Functions.nonNullString(societyMappingVO.getSocietyRole())){
//            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","linkSocietyInputValidation", "Society Role", "SocietyService", ValidationMessages.Society_Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addAreaInputValidation(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (areaMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addAreaInputValidation", "Society", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (areaMasterVO.getAreaTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addAreaInputValidation", "Area Type", "SocietyService", ValidationMessages.Area_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(areaMasterVO.getAreaName())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addAreaInputValidation", "Area Name", "SocietyService", ValidationMessages.Area_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getAreaInputValidation(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (areaMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getAreaInputValidation", "Society", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getAreaSearchInputValidation(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (areaMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getAreaSearchInputValidation", "Society", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(areaMasterVO.getAreaType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getAreaSearchInputValidation", "Area Type", "SocietyService", ValidationMessages.Area_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateAreaInputValidation(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (areaMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateAreaInputValidation", "Id", "SocietyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(areaMasterVO.getAreaName())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateAreaInputValidation", "Area Name", "SocietyService", ValidationMessages.Area_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addUnitListInputValidation(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitListInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitMasterVO.getAreaId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitListInputValidation", "Id", "SocietyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitMasterVO.getAreaTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitListInputValidation", "Id", "SocietyService", ValidationMessages.Area_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(unitMasterVO.getUnit())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitListInputValidation", "Unit", "SocietyService", ValidationMessages.Unit_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getUnitListInputValidation(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUnitListInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitMasterVO.getAreaId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUnitListInputValidation", "Id", "SocietyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (!Functions.nonNullString(unitMasterVO.getAreaType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUnitListInputValidation", "Area Type", "SocietyService", ValidationMessages.Area_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateUnitInputValidation(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUnitInputValidation", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(unitMasterVO.getUnit())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUnitInputValidation", "Unit", "SocietyService", ValidationMessages.Unit_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addUnitInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getAreaId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Id", "SocietyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(ownerMasterVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(ownerMasterVO.getOccupancyStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitInputValidation", "Status", "SocietyService", ValidationMessages.Occupancy_Status_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void userDashboardInputValidate(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "userDashboardInputValidate", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "userDashboardInputValidate", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateOwnerStatusInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateOwnerStatusInputValidation", "Id", "SocietyService", ValidationMessages.Owner_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(ownerMasterVO.getUserStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateOwnerStatusInputValidation", "Status", "SocietyService", ValidationMessages.Status_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUserStatus().equalsIgnoreCase(UserStatus.USER_REJECTED) && !Functions.nonNullString(ownerMasterVO.getMessage())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateOwnerStatusInputValidation", "Message", "SocietyService", ValidationMessages.Remark_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void applicationMessageInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "applicationMessageInputValidation", "Id", "SocietyService", ValidationMessages.Owner_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(ownerMasterVO.getMessage())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "applicationMessageInputValidation", "Message", "SocietyService", ValidationMessages.Message_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getUserDocumentInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserDocumentInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserDocumentInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addEmergencyNumberInputValidation(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (emergencyNumberVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addEmergencyNumberInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addEmergencyNumberInputValidation", "Name", "SocietyService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addEmergencyNumberInputValidation", "Mobile", "SocietyService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addEmergencyNumberInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void emergencyIdInputValidation(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (emergencyNumberVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "emergencyIdInputValidation", "Id", "SocietyService", ValidationMessages.Emergency_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void emergencyUpdateInputValidation(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (emergencyNumberVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "emergencyIdInputValidation", "Id", "SocietyService", ValidationMessages.Emergency_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getName())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "emergencyUpdateInputValidation", "Name", "SocietyService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getMobileNumber())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "emergencyUpdateInputValidation", "Mobile", "SocietyService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(emergencyNumberVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "emergencyUpdateInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addTypeInputValidation(TypeVO typeVO) throws PZConstraintViolationException {
        if (!Functions.nonNullString(typeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateTypeInputValidation(TypeVO typeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (typeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Id", "SocietyService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(typeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addSocietyDocumentInputValidation(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyDocumentInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (societyDocumentVO.getSocietyUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyDocumentInputValidation", "Id", "SocietyService", ValidationMessages.Society_User_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if(!Functions.nonNullString(societyDocumentVO.getSocietyRole())){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

//        if(!societyDocumentVO.getSocietyRole().equalsIgnoreCase(UserRoles.SECRETARY)){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (societyDocumentVO.getDocumentTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyDocumentInputValidation", "Type", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyDocumentVO.getDocument())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyDocumentInputValidation", "Type", "SocietyService", ValidationMessages.Document_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateSocietyDocumentInputValidation(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentInputValidation", "Id", "SocietyService", ValidationMessages.Document_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

//        if(!Functions.nonNullString(societyDocumentVO.getSocietyRole())){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//
//        if(!societyDocumentVO.getSocietyRole().equalsIgnoreCase(UserRoles.SECRETARY)){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyDocumentInputValidation", "Role", "SocietyService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

        if (!Functions.nonNullString(societyDocumentVO.getDocument())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentInputValidation", "Type", "SocietyService", ValidationMessages.Document_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addUnitDocumentTypeInputValidation(UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitDocumentTypeVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitDocumentTypeVO.getSocietyUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Society_User_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(unitDocumentTypeVO.getResidentType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Type", "SocietyService", ValidationMessages.Resident_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitDocumentTypeVO.getDocumentTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateUnitDocumentTypeInputValidation(UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitDocumentTypeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(unitDocumentTypeVO.getResidentType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Type", "SocietyService", ValidationMessages.Resident_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitDocumentTypeVO.getDocumentTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addUnitDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateUserUnitDocumentInputValidation(UnitDocumentMasterVO documentMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (documentMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUserUnitDocumentInputValidation", "Id", "SocietyService", ValidationMessages.Document_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(documentMasterVO.getDocument())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUserUnitDocumentInputValidation", "Type", "SocietyService", ValidationMessages.Document_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getUserStatusInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserStatusInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getSocietyUserListInputValidation(SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(societyUserMasterVO.getAdminStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getSocietyUserListInputValidation", "Status", "SocietyService", ValidationMessages.Status_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateSocietyUserStatusInputValidation(SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyUserMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyUserStatusInputValidation", "Id", "SocietyService", ValidationMessages.Owner_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyUserMasterVO.getAdminStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyUserStatusInputValidation", "Status", "SocietyService", ValidationMessages.Status_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (societyUserMasterVO.getAdminStatus().equalsIgnoreCase(UserStatus.USER_REJECTED)) {
            if (!Functions.nonNullString(societyUserMasterVO.getMessage())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyUserStatusInputValidation", "Message", "SocietyService", ValidationMessages.Message_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void allMemberDetailInputValidation(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitMasterVO.getAreaId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "allMemberDetailInputValidation", "Id", "SocietyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateSocietyInputValidation(SocietyVO societyVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyVO.getSocietyName())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "Name", "SocietyService", ValidationMessages.Society_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getAddress())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "Address", "SocietyService", ValidationMessages.Address_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getSocietyBlock())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "Block", "SocietyService", ValidationMessages.Society_Block_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getState())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "State", "SocietyService", ValidationMessages.State_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getCity())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "City", "SocietyService", ValidationMessages.City_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if (!Functions.nonNullString(societyVO.getPincode())) {

            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyInputValidation", "Pincode", "SocietyService", ValidationMessages.Pincode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateSocietyDocumentStatusInputValidation(SocietyDocumentVO societyDocumentVO) {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentStatusInputValidation", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyDocumentVO.getDocumentStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentStatusInputValidation", "Status", "SocietyService", ValidationMessages.Document_Status_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (societyDocumentVO.getDocumentStatus().equalsIgnoreCase(UserStatus.DOC_REJECTED)) {
            if (!Functions.nonNullString(societyDocumentVO.getRemark())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentStatusInputValidation", "Remark", "SocietyService", ValidationMessages.Remark_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateUnitDocumentStatusInputValidation(UnitDocumentMasterVO unitDocumentMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitDocumentMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUnitDocumentStatusInputValidation", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(unitDocumentMasterVO.getStatus())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUnitDocumentStatusInputValidation", "Status", "SocietyService", ValidationMessages.Document_Status_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (unitDocumentMasterVO.getStatus().equalsIgnoreCase(UserStatus.DOC_REJECTED)) {
            if (!Functions.nonNullString(unitDocumentMasterVO.getRemark())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateUnitDocumentStatusInputValidation", "Remark", "SocietyService", ValidationMessages.Remark_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void changeProviderInputValidation(MobileProviderVO mobileProviderVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (mobileProviderVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "changeProviderInputValidation", "ID", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getSocietyInputValidation(SocietyVO societyVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (!Functions.nonNullString(societyVO.getCity())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getSocietyInputValidation", "City", "SocietyService", ValidationMessages.City_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyVO.getSocietyName())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getSocietyInputValidation", "Name", "SocietyService", ValidationMessages.Society_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getUpdateMemberRole(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if (ownerMasterVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUpdateMemberRole", "Id", "SocietyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (!Functions.nonNullString(ownerMasterVO.getSocietyRole())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUpdateMemberRole", "Role", "SocietyService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void societyIdAndUserIdInputValidation(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserDocumentInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserDocumentInputValidation", "Society Id", "SocietyService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }


        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getMemberRoleInputValidation(UnitMasterVO unitMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (unitMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getMemberRoleInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void getSocietyAdminInputValidation(SocietyVO societyVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getSocietyAdminInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }


    }

    public void addSocietyDocumentTypeInputValidation(SocietyDocumentTypeVO societyDocumentTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentTypeVO.getDocumentTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateSocietyDocumentTypeInputValidation(SocietyDocumentTypeVO societyDocumentTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyDocumentTypeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Document_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (societyDocumentTypeVO.getDocumentTypeId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateSocietyDocumentTypeInputValidation", "Id", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getUserUnitStatusInputValidation(OwnerMasterVO ownerMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (ownerMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserUnitStatusInputValidation", "Id", "SocietyService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (ownerMasterVO.getUnitId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "getUserUnitStatusInputValidation", "Id", "SocietyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateAreaTypeInputValidation(AreaTypeVO areaTypeVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if (areaTypeVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Id", "SocietyService", ValidationMessages.Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(areaTypeVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "updateTypeInputValidation", "Type", "SocietyService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void societyReviewInputValidation(SocietyReviewVO societyReviewVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();
        if (societyReviewVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            if (societyReviewVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyReview", "societyId", "societyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (societyReviewVO.getOwnerId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyReview", "OwnerId", "societyService", ValidationMessages.Owner_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
//        if(Functions.nonNullString(societyReviewVO.getDescription())){
//            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator","addSocietyReview","Description","societyService",ValidationMessages.Description_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }

            if (societyReviewVO.getRating() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyReview", "Rating", "societyService", ValidationMessages.Rating_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing,pzConstraints);
            }
        }
    }

    public void addsocietyParkingInputValidation(ParkingSlotVO parkingSlotVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();
      // if (parkingSlotVO.getAction().equalsIgnoreCase(Status.ADD)) {
            if (parkingSlotVO.getUnitId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addParkingSlot", "unitId", "societyService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (parkingSlotVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidation", "addParkingSlot", "SocietyId", "societyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
            if (parkingSlotVO.getAreaId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidation", "addParkingSlot", "areaId", "sociteyService", ValidationMessages.Area_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(parkingSlotVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyInputValidation", "addParkingSlot", "name", "societyService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }
        }
       // }
            public static void updatesocietyParkingInputValidation(ParkingSlotVO parkingSlotVO) throws PZConstraintViolationException{
                ArrayList<PZConstraint> pzConstraints=new ArrayList<>();

                if(parkingSlotVO.getId()==0){
                    PZConstraint pzConstraint = new PZConstraint("SocietyInputValidation","updateParkingSlot","id","societyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }

                if(pzConstraints.size()>0){
                    throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing,pzConstraints);
                }
            }

           public static void getsocietyParkingInputValidation(ParkingSlotVO parkingSlotVO) throws PZConstraintViolationException{
               ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

               if(parkingSlotVO.getSocietyId()==0) {
                   PZConstraint pzConstraint = new PZConstraint("SocietyInputValidation", "getParkingSlot", "SocietyId", "societyService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                   pzConstraints.add(pzConstraint);
               }

                   if(pzConstraints.size()>0){
                       throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing,pzConstraints);

                   }

           }

    public void addSocietyTemplateInputValidation(SocietyTemplateVO societyTemplateVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyTemplateVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyTemplateInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyTemplateVO.getTemplateType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyTemplateInputValidation", "Id", "SocietyService", ValidationMessages.Document_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getSocietyTemplateInputValidation(SocietyTemplateVO societyTemplateVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyTemplateVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyTemplateInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
}





