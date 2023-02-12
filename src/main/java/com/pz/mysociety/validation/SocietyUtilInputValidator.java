package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.ProfessionUtilMasterVO;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

@Component
@Validated
public class SocietyUtilInputValidator {

    public void addTypeLanguageInputValidation(LanguageTypeVO languageTypeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(languageTypeVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            if(languageTypeVO.getId() == 0){
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator","addTypeLanguageInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

        }else {

            if (languageTypeVO.getServiceId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addTypeLanguageInputValidation", "Id", "DashboardService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(languageTypeVO.getType())) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addTypeLanguageInputValidation", "Type", "DashboardService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(languageTypeVO.getEng())) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addTypeLanguageInputValidation", "ENG", "DashboardService", ValidationMessages.Language_English_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(languageTypeVO.getLanguage())) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addTypeLanguageInputValidation", "Language", "DashboardService", ValidationMessages.Language_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(languageTypeVO.getServiceName())) {
                PZConstraint pzConstraint = new PZConstraint("DashboardInputValidator", "addTypeLanguageInputValidation", "Name", "DashboardService", ValidationMessages.Service_Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void addProfessionMaster(ProfessionUtilMasterVO professionUtilMasterVO)throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (professionUtilMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {

            if (professionUtilMasterVO.getId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyUtilInputValidator", "addProfessionMaster", "Id", "SocietyUtilService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);

                pzConstraints.add(pzConstraint);
            }
        } else {

            if (!Functions.nonNullString(professionUtilMasterVO.getProfession())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyUtilInputValidator", "getProfessionMaster", "Name", "SocietyUtilService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
}

