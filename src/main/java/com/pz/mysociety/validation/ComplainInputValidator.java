package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.ComplainVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ComplainInputValidator {
    public void addComplainInputValidator(ComplainVO complainVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(complainVO.getSocietyId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "Id", "ComplainService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(complainVO.getUserId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "Id", "ComplainService", ValidationMessages.UserId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(complainVO.getUnitId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "Id", "ComplainService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(!Functions.nonNullString(complainVO.getComplainTitle())) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "Title", "ComplainService", ValidationMessages.Complain_Title_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(!Functions.nonNullString(complainVO.getComplainContent())) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "Content", "ComplainService", ValidationMessages.ComplainContent_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void updateComplainInputValidator(ComplainVO complainVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(complainVO.getComplainId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","updateComplainInputValidator", "complainId", "ComplainService", ValidationMessages.ComplainId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(!Functions.nonNullString(complainVO.getComplainStatus())) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","updateComplainInputValidator", "ComplainStatus", "ComplainService", ValidationMessages.Complain_Status_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }   public void addComplainRecordInputValidator(ComplainVO complainVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(complainVO.getComplainId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplainRecord", "complainId", "ComplainService", ValidationMessages.ComplainId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(!Functions.nonNullString(complainVO.getRole())) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplainRecord", "role", "ComplainService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(Functions.nonNullString(complainVO.getRole()) && complainVO.getRole().equalsIgnoreCase(UserRoles.SOCIETY) && complainVO.getAdminId()==0){
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplainRecord", "complainContent", "ComplainService", ValidationMessages.Valid_Data_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(Functions.nonNullString(complainVO.getRole()) && complainVO.getRole().equalsIgnoreCase(UserRoles.USER) && complainVO.getUserId()==0){
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplainRecord", "complainContent", "ComplainService", ValidationMessages.Valid_Data_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(!Functions.nonNullString(complainVO.getContent()) && !Functions.nonNullString(complainVO.getAttachment())) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplainRecord", "complainContent", "ComplainService", ValidationMessages.Blank_Complain_Content, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
    public void getSocietyComplainInputValidator(ComplainVO complainVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(complainVO.getInitiatedBy().equals(UserRoles.USER)){
        if(complainVO.getUserId()== 0 && complainVO.getUnitId()==0 ) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "userId", "ComplainService", ValidationMessages.UserId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }}
        if(complainVO.getInitiatedBy().equals(UserRoles.SOCIETY)){
        if(complainVO.getSocietyId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","addComplain", "societyId", "ComplainService", ValidationMessages.SocietyId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }}
        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
    public void complainFlagChangeInputValidator(ComplainVO complainVO) throws PZConstraintViolationException{
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(complainVO.getComplainId()== 0) {
            PZConstraint pzConstraint = new PZConstraint("ComplainInputValidator","complainFlagChangeInputValidator", "complainId", "ComplainService", ValidationMessages.ComplainId_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }
}
