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
public class SocietyNoticeInputValidator {
    public void addNoticeInputValidator(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(societyNoticeRequestVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            if(societyNoticeRequestVO.getId()== 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "ID", "SocietyNoticeService", ValidationMessages.Noice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

    else {
            if (societyNoticeRequestVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator", "complainFlagChangeInputValidator", "Society ID", "SocietyNoticeService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!societyNoticeRequestVO.getNoticeFor().equalsIgnoreCase(Status.SOCIETY) && !societyNoticeRequestVO.getNoticeFor().equalsIgnoreCase(Status.UNIT)) {
                PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator", "updateNoticeInputValidator", "Notice For", "SocietyNoticeService", ValidationMessages.Notice_For_Required_Error, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(societyNoticeRequestVO.getNoticeTitle())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator", "updateNoticeInputValidator", "Notice Title", "SocietyNoticeService", ValidationMessages.Noice_Title_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
            if (!Functions.nonNullString(societyNoticeRequestVO.getNoticeContent())) {
                PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator", "updateNoticeInputValidator", "Notice Content", "SocietyNoticeService", ValidationMessages.Noice_Content_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }


            if (pzConstraints.size() > 0) {
                throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
            }

        }

    }
//    public void updateNoticeInputValidator(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
//        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

//        if(societyNoticeRequestVO.getId()== 0) {
//            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "ID", "SocietyNoticeService", ValidationMessages.Noice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//        if(societyNoticeRequestVO.getSocietyId()== 0) {
//            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "ID", "SocietyNoticeService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//        if(!Functions.nonNullString(societyNoticeRequestVO.getNoticeTitle())) {
//            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "Notice Title", "SocietyNoticeService", ValidationMessages.Noice_Title_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//        if(!Functions.nonNullString(societyNoticeRequestVO.getNoticeContent())) {
//            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "Notice Content", "SocietyNoticeService", ValidationMessages.Noice_Content_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
//            pzConstraints.add(pzConstraint);
//        }
//        if(pzConstraints.size() > 0){
//            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
//        }
//    }
    public void addNoticeTypeInputValidator(NoticeTypeVO societyNoticeTypeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(!Functions.nonNullString(societyNoticeTypeRequestVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","addNoticeTypeInputValidator", "NoticeType", "SocietyNoticeService", ValidationMessages.Noice_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void updateNoticeTypeInputValidator(NoticeTypeVO societyNoticeTypeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(!Functions.nonNullString(societyNoticeTypeRequestVO.getType())) {
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","addNoticeTypeInputValidator", "NoticeType", "SocietyNoticeService", ValidationMessages.Noice_Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(societyNoticeTypeRequestVO.getId()==0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","addNoticeTypeInputValidator", "Notice ID", "SocietyNoticeService", ValidationMessages.Noice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }  public void getAllNoticesInputValidator(SocietyNoticeVO societyNoticeVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(societyNoticeVO.getSocietyId()==0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","getAllNoticesInputValidator", "SocietyId", "SocietyNoticeService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void deleteNoticeInputValidator(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(societyNoticeRequestVO.getId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeInputValidator","updateNoticeInputValidator", "ID", "SocietyNoticeService", ValidationMessages.Noice_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

}
