package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.VisitorHistoryLogVO;
import com.pz.mysociety.model.Request.VisitorVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VisitorInputValidator {



    public void addVisitorInputValidator(VisitorVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (visitorVO.getUnitIdList().size()<0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (!Functions.nonNullString(visitorVO.getVisitorType())) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "VisitorType", "HelperService", ValidationMessages.Visitor_type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
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
    public void checkVisitorInputValidator(VisitorVO visitorVO) throws PZConstraintViolationException {
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
    public void getWaitingListInputValidator(VisitorVO visitorVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorVO.getSocietyId() == 0) {

            PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "checkVisitorInputValidator", "Id", "HelperService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void addVisitorHistoryLogInputValidator(VisitorHistoryLogVO visitorHistoryVo) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (visitorHistoryVo.getAction().equals("in")) {
            if (visitorHistoryVo.getSocietyId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "visitorService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (visitorHistoryVo.getVisitorId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "visitorId", "visitorService", ValidationMessages.PassCode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (visitorHistoryVo.getUnitIdList().size()<=0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "unitId", "visitorService", ValidationMessages.PassCode_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getAction())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Action", "visitorService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getVisitorType())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Visitor Type", "visitorService", ValidationMessages.VisitorType_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        } else {
            if (visitorHistoryVo.getVisitorId() == 0) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Id", "HelperService", ValidationMessages.Visitor_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getAction())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Action", "HelperService", ValidationMessages.Action_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
            if (!Functions.nonNullString(visitorHistoryVo.getOutTime())) {

                PZConstraint pzConstraint = new PZConstraint("HelperInputValidation", "addHelperByGuardInputValidator", "Out Time", "HelperService", ValidationMessages.OutTime_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);

            }
        }

        if (pzConstraints.size() > 0) {
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
}
