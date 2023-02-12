package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.userEntity.UserEntity;
import com.pz.mysociety.model.Request.AmenityVO;
import com.pz.mysociety.model.Request.ComplainVO;
import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.repository.societyRepository.UnitMasterRepository;
import com.pz.mysociety.repository.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    @Value( "${otp.duration}")
    private long otpDuration;

    @Value( "${login.attempt.duration}")
    private long loginAttemptDuration;

    public void isEmailExist(UserVO userVO) throws PZConstraintViolationException{

        UserEntity user= userRepository.findByEmailAndRole(userVO.getEmail(), userVO.getRole());

        if(user != null){
            PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateEmail", "Email", "UserService", ValidationMessages.Email_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.Email_Present_Error, pzConstraints);
        }


    }

    public void isMobileExist(UserVO userVO) throws PZConstraintViolationException{


            UserEntity user= userRepository.findByMobileNumberAndRole(userVO.getMobileNumber(), userVO.getRole());

            if(user != null){
                PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateMobile", "Mobile", "UserService", ValidationMessages.Mobile_Present_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
                List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
                pzConstraints.add(pzConstraint);

                throw new PZConstraintViolationException(ValidationMessages.Mobile_Present_Error, pzConstraints);
            }
    }


    public boolean isOtpValid(UserEntity userEntity){

        if(userEntity.getValidateOtp() == 0){
            return false;
        }

        long otpTimeInMills = userEntity.getOtpRequestedTime().getTime();
        if(otpTimeInMills + otpDuration < System.currentTimeMillis()){
            return false;
        }


        return true;
    }

    public boolean isLoginValid(UserEntity userEntity){

        if(userEntity.getFailAttempt() != null) {
            long attemptTimeInMills = userEntity.getFailAttempt().getTime();
            if (attemptTimeInMills + loginAttemptDuration > System.currentTimeMillis()) {
                return false;
            }else {
                userEntity.setFailAttempt(null);
                userRepository.save(userEntity);
            }
        }

        return true;
    }

    public UserEntity isUserExist(UserVO userVO) throws PZConstraintViolationException {

        UserEntity user = userRepository.findUserById(userVO.getId());

        if(user == null){
            PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateUser", "User", "UserService", ValidationMessages.User_Exist_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Exist_Error, pzConstraints);
        }

        return user;
    }
    public UserEntity isUserComplainMapping(ComplainVO complainVO) throws PZConstraintViolationException {
        UserEntity user = userRepository.findUserById(complainVO.getUserId());

        if(user == null){
            PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateUser", "User", "UserService", ValidationMessages.User_Exist_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Exist_Error, pzConstraints);
        }

        return user;
    }
    public UserEntity isAdminComplainMapping(ComplainVO complainVO) throws PZConstraintViolationException {

        UserEntity user = userRepository.findUserByIdAndRole(complainVO.getUserId(), "Society");

        if(user == null){
            PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateUser", "User", "UserService", ValidationMessages.User_Exist_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Exist_Error, pzConstraints);
        }

        return user;
    }
    public UserEntity isUserAmenityMapping(AmenityVO amenityVO) throws PZConstraintViolationException {

        UserEntity user = userRepository.findUserById(amenityVO.getUserId());

        if(user == null){
            PZConstraint pzConstraint = new PZConstraint("UserValidationService","validateUser", "User", "UserService", ValidationMessages.User_Exist_Error, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Exist_Error, pzConstraints);
        }

        return user;
    }
//    public UserEntity isEmailValid(UserVO userVO) throws PZConstraintViolationException {
//
//        UserEntity user = userRepository.findByEmail(userVO.getEmail());
//
//        if(user == null){
//            PZConstraint pzConstraint = new PZConstraint("UserValidationService","isEmailValid", "Email", "UserService", Messages.Fail_Attempt, PZConstraintExceptionEnum.INVALID_PARAMETER_ENTERED);
//            List<PZConstraint>  pzConstraints = new ArrayList<PZConstraint>();
//            pzConstraints.add(pzConstraint);
//
//            throw new PZConstraintViolationException(Messages.Fail_Attempt, pzConstraints);
//        }
//
//        return  user;
//    }


}
