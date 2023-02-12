package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.Types;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.MntMasterVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntFileInvoiceVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MntMasterInputValidation {

    public void addMntTypeInputValidation(MntMasterVO mntMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(!Functions.nonNullString(mntMasterVO.getAction())){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntTypeInputValidation", "Action", "MntMasterService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

        if(!mntMasterVO.getAction().equalsIgnoreCase(Status.UPDATE) && !mntMasterVO.getAction().equalsIgnoreCase(Status.ADD)){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntTypeInputValidation", "Action", "MntMasterService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

        if(mntMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            if(mntMasterVO.getId() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (!Functions.nonNullString(mntMasterVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addMntTypeInputValidation", "Name", "MntMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addSocietyMntTypeInputValidation(SocietyMntMappingVO societyMntMappingVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(!Functions.nonNullString(societyMntMappingVO.getAction())){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Action", "MntMasterService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

        if (!societyMntMappingVO.getAction().equalsIgnoreCase(Status.UPDATE) && !societyMntMappingVO.getAction().equalsIgnoreCase(Status.ADD)){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Action", "MntMasterService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

        if(societyMntMappingVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            if(societyMntMappingVO.getId() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {
            if(societyMntMappingVO.getSocietyId() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(societyMntMappingVO.getMntTypeId() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.Mnt_Type_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(societyMntMappingVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addSocietyMntTypeInputValidation", "Name", "MntMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyMntMappingVO.getColumnType())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Action", "MntMasterService", ValidationMessages.Column_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }

            if(!societyMntMappingVO.getColumnType().equalsIgnoreCase(Types.MASTER) && !societyMntMappingVO.getColumnType().equalsIgnoreCase(Types.FEE)){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Column", "MntMasterService", ValidationMessages.Column_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(societyMntMappingVO.getColumnNumber())) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addSocietyMntTypeInputValidation", "Number", "MntMasterService", ValidationMessages.Column_Number_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(societyMntMappingVO.getColumnType().equalsIgnoreCase(Types.FEE)){
                if (!Functions.nonNullString(societyMntMappingVO.getCalculationLogic())) {
                    PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addSocietyMntTypeInputValidation", "Logic", "MntMasterService", ValidationMessages.Calculation_Logic_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }

                if(societyMntMappingVO.getTotalAmount() == 0){
                    PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Amount", "MntMasterService", ValidationMessages.Total_Amount_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }
            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getSocietyMntTypeInputValidation(SocietyMntMappingVO societyMntMappingVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyMntMappingVO.getSocietyId() == 0){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(societyMntMappingVO.getColumnType()) && !societyMntMappingVO.getColumnType().equalsIgnoreCase(Types.MASTER) && !societyMntMappingVO.getColumnType().equalsIgnoreCase(Types.FEE)){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addSocietyMntTypeInputValidation", "Column", "MntMasterService", ValidationMessages.Column_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addMntInvoiceInputValidation(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyInvoiceVO.getSocietyId() == 0){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(societyInvoiceVO.getType())){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Type", "MntMasterService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(!Functions.nonNullString(societyInvoiceVO.getFile())){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "File", "MntMasterService", ValidationMessages.File_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getMntInvoiceInputValidation(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyInvoiceVO.getSocietyId() == 0){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","getMntInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void generateInvoiceInputValidation(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyInvoiceVO.getInvoiceId() == null){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","generateInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Invoice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void deleteInvoiceInputValidation(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyInvoiceVO.getInvoiceId() == null){
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","deleteInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Invoice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addInvoiceInputValidation(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(societyInvoiceVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            if(societyInvoiceVO.getId() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
            if (societyInvoiceVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (societyInvoiceVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (societyInvoiceVO.getUnitId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addInvoiceInputValidation", "Id", "MntMasterService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (societyInvoiceVO.getInvoice() == null) {
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation", "addInvoiceInputValidation", "Invoice", "MntMasterService", ValidationMessages.Invoice_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getName())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Name", "MntMasterService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getMobileNumber())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Mobile", "MntMasterService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getMonth())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Month", "MntMasterService", ValidationMessages.Month_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getYear())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Year", "MntMasterService", ValidationMessages.Year_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getArea())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Area", "MntMasterService", ValidationMessages.Area_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(!Functions.nonNullString(societyInvoiceVO.getUnit())){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Unit", "MntMasterService", ValidationMessages.Unit_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(societyInvoiceVO.getTotalAmount() == 0){
                PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","addMntInvoiceInputValidation", "Total Amount", "MntMasterService", ValidationMessages.Total_Amount_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void deleteSocietyMntTypeInputValidation(SocietyMntMappingVO societyMntMappingVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();
        if (societyMntMappingVO.getId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("MntMasterInputValidation","deleteSocietyMntTypeInputValidation", "Id", "MntMasterService", ValidationMessages.ID_Required,PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }


    public void addSocietyTemplateInputValidation(SocietyTemplateVO societyTemplateVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (societyTemplateVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyTemplateInputValidation", "Id", "SocietyService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (!Functions.nonNullString(societyTemplateVO.getTemplateType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyInputValidator", "addSocietyTemplateInputValidation", "Type", "SocietyService", ValidationMessages.Template_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
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
