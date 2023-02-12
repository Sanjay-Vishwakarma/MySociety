package com.pz.mysociety.validation;

import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO;
import com.pz.mysociety.model.RequestVO;
import com.pz.mysociety.model.ResponseVO;
import org.owasp.esapi.ValidationErrorList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;

@Component
@Validated
public class UserInputValidator implements InputValidator{


    public void adminSignupInputValidation(UserVO userVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(!Functions.nonNullString(userVO.getRole())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Role", "UserService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getName())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Name", "UserService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getEmail())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Email", "UserService", ValidationMessages.Email_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getPassword())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Password", "UserService", ValidationMessages.Password_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if(Functions.nonNullString(userVO.getRole()) && !userVO.getRole().equals(UserRoles.SOCIETY)){


            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Role", "UserService", ValidationMessages.Role_Not_Permitted, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);


        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void userSignupInputValidation(UserVO userVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(!Functions.nonNullString(userVO.getRole())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userSignupInputValidation", "Role", "UserService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getName())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userSignupInputValidation", "Name", "UserService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getMobileNumber())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userSignupInputValidation", "Mobile", "UserService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getRole()) && !userVO.getRole().equals(UserRoles.USER)){


            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "Role", "UserService", ValidationMessages.Role_Not_Permitted, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);


        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void userOTPInputValidation(UserVO userVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(userVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userOTPInputValidation", "Id", "UserService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(userVO.getValidateOtp() == 0){
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userOTPInputValidation", "ValidateOTP", "UserService", ValidationMessages.OTP_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(userVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER)){
            if(!Functions.nonNullString(userVO.getDeviceToken())){
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userOTPInputValidation", "Token", "UserService", ValidationMessages.Device_Token_Message, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void userIdValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(userVO.getId() == 0){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userIdValidation", "Id", "UserService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);

        }

    }

    public void adminSigninInputValidation(UserVO userVO) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(!Functions.nonNullString(userVO.getEmail())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSigninInputValidation", "Email", "UserService", ValidationMessages.Email_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getPassword())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSigninInputValidation", "Password", "UserService", ValidationMessages.Password_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void adminEmailInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(!Functions.nonNullString(userVO.getEmail())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminForgetInputValidation", "Email", "UserService", ValidationMessages.Email_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void userSignInInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(!Functions.nonNullString(userVO.getMobileNumber())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","userSignInInputValidation", "Mobile", "UserService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void adminChangePasswordInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(userVO.getId() == 0){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSigninInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getPassword())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSigninInputValidation", "Password", "UserService", ValidationMessages.Password_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getNewPassword())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSigninInputValidation", "New Password", "UserService", ValidationMessages.New_Password_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateUserProfileInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();


        if(userVO.getId() == 0){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if(!Functions.nonNullString(userVO.getName())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Name", "UserService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getEmail())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Email", "UserService", ValidationMessages.Email_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void updateAdminProfileInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(userVO.getId() == 0){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if(!Functions.nonNullString(userVO.getName())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Name", "UserService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(!Functions.nonNullString(userVO.getMobileNumber())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Mobile", "UserService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addProfileInputValidation(UserVO userVO) throws PZConstraintViolationException {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(userVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if(!Functions.nonNullString(userVO.getPhoto())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserProfileInputValidation", "Profile", "UserService", ValidationMessages.Photo_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }

    public void updateUserAboutInputValidation(UserVO userVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(userVO.getId() == 0){
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserAboutInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }
        if(!Functions.nonNullString(userVO.getType())){

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","updateUserAboutInputValidation", "Type", "UserService", ValidationMessages.Type_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);

        }


        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void addProfessionInputValidation(ProfessionMasterVO professionMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if(professionMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            if(professionMasterVO.getId() == 0){
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator","addProfessionInputValidation", "Id", "UserService", ValidationMessages.ID_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }else {

            if (professionMasterVO.getUserId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (professionMasterVO.getSocietyId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Id", "UserService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if(professionMasterVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER)) {
                if (professionMasterVO.getUnitId() == 0) {
                    PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Id", "UserService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                    pzConstraints.add(pzConstraint);
                }
            }

            if (!Functions.nonNullString(professionMasterVO.getProfession())) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Profession", "UserService", ValidationMessages.User_Profession_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(professionMasterVO.getName())) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Name", "UserService", ValidationMessages.Name_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }

            if (!Functions.nonNullString(professionMasterVO.getMobileNumber())) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "addProfessionInputValidation", "Mobile", "UserService", ValidationMessages.Mobile_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void getProfessionInputValidation(ProfessionMasterVO professionMasterVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();

        if (professionMasterVO.getUserId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "getProfessionInputValidation", "Id", "UserService", ValidationMessages.User_Id_Requires, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if (professionMasterVO.getSocietyId() == 0) {
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "getProfessionInputValidation", "Id", "UserService", ValidationMessages.Society_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
        }

        if(professionMasterVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER)){
            if (professionMasterVO.getUnitId() == 0) {
                PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "getProfessionInputValidation", "Id", "UserService", ValidationMessages.Unit_Id_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
                pzConstraints.add(pzConstraint);
            }
        }

        if(pzConstraints.size() > 0){
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }
    }

    public void reGenerateInputValidation(UserVO userVO) {
        ArrayList<PZConstraint> pzConstraints = new ArrayList<>();

        if(!Functions.nonNullString(userVO.getToken())){
            PZConstraint pzConstraint = new PZConstraint("UserInputValidator", "reGenerateInputValidation", "Id", "UserService", ValidationMessages.Token_Message, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Mandatory_Parameter_Missing, pzConstraints);
        }

    }
}
