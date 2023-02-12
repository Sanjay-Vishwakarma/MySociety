package com.pz.mysociety.service;

import com.pz.mysociety.common.config.securityConfiguration.JwtUtil;
import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.mail.*;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.entity.userEntity.UserProfessionMasterEntity;
import com.pz.mysociety.entity.userEntity.UserEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.ComplainListVO;
import com.pz.mysociety.model.VO.UserListVO;
import com.pz.mysociety.repository.userRepository.UserProfessionMasterRepository;
import com.pz.mysociety.repository.userRepository.UserRepository;
import com.pz.mysociety.validation.UserInputValidator;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private UserProfessionMasterRepository userProfessionMasterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MailService mailService;

    @Autowired
    SMSService smsService;

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseVO adminSignUp(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.adminSignupInputValidation(userVO);
        userValidationService.isEmailExist(userVO);
        if(Functions.nonNullString(userVO.getMobileNumber())) {
            userValidationService.isMobileExist(userVO);
        }

        UserEntity userEntity = modelMapper.map(userVO, UserEntity.class);

        UserEntity userEntityDB;

        if (Functions.nonNullString(userVO.getInitiatedBy()) && userVO.getInitiatedBy().equals(UserRoles.SUPER_ADMIN)) {

            userEntity.setUserStatus(UserStatus.USER_VERIFIED);
            userEntityDB = userRepository.save(userEntity);


        } else {

            String secretKey = GenerateKey.generateKey(32);
            int otp = 100000 + random.nextInt(899999);

            userEntity.setKey(secretKey);
            userEntity.setValidateOtp(otp);
            userEntity.setOtpRequestedTime(new Date());
            userEntity.setOtpAttempt(5);
            userEntity.setUserStatus(UserStatus.USER_CREATED);
            userEntityDB = userRepository.save(userEntity);

//            Send OTP to Email
            Map<String,Object> model = mailService.getDefaultModel();
            model.put(MailPlaceHolder.OTP.name(), otp);
            String body= mailService.getHtmlBodyUsingThymeleafTemplate(model, MailTemplateName.MAIL_USER_SIGNUP_OTP);
            mailService.sendMail(userEntity.getEmail(), EmailSubject.EMAIL_OTP ,body,null,null,null,null);

        }

        UserVO responseUserVO = new UserVO();
        responseUserVO.setId(userEntityDB.getId());
        responseVO.setUser(responseUserVO);
        responseVO.setSuccessResponse(Messages.Success_User_Added);
        return responseVO;

    }

    public ResponseVO userSignUp(UserVO userVO) throws PZConstraintViolationException {

        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.userSignupInputValidation(userVO);
        userValidationService.isMobileExist(userVO);
        if(Functions.nonNullString(userVO.getEmail())) {
            userValidationService.isEmailExist(userVO);
        }

        UserEntity userEntity = modelMapper.map(userVO, UserEntity.class);


        String secretKey = GenerateKey.generateKey(32);
        int otp = 100000 + random.nextInt(899999);

        userEntity.setKey(secretKey);
        userEntity.setValidateOtp(otp);
        userEntity.setOtpRequestedTime(new Date());
        userEntity.setOtpAttempt(5);
        userEntity.setIsActive(true);
        userEntity.setUserStatus(UserStatus.USER_CREATED);
        UserEntity userEntityDB = userRepository.save(userEntity);

        // Send OTP on Mobile

        Map<String,Object> model = smsService.getDefaultModel();
        model.put(MailPlaceHolder.OTP.name(),otp);

        String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);

        smsService.sendSMS(userEntity.getMobileNumber(),body);

        UserVO responseUserVO = new UserVO();
        responseUserVO.setId(userEntityDB.getId());
        responseVO.setUser(responseUserVO);
        responseVO.setSuccessResponse(Messages.Success_User_Added);
        return responseVO;

    }

    public ResponseVO checkSignUpOtp(UserVO userVO) throws PZConstraintViolationException {

        ResponseVO responseVO = new ResponseVO();
        userInputValidator.userOTPInputValidation(userVO);
        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (!userValidationService.isOtpValid(userEntity)) {
            responseVO.setFailResponse(Messages.Otp_Expired);
            return responseVO;
        }

        if (userEntity.getValidateOtp() == userVO.getValidateOtp()) {

            userEntity.setValidateOtp(0);
            userEntity.setOtpAttempt(0);
            userEntity.setOtpRequestedTime(null);
            userEntity.setDeviceToken(userVO.getDeviceToken());
            userEntity.setUserStatus(UserStatus.USER_VERIFIED);
            userRepository.save(userEntity);
            responseVO.setSuccessResponse(Messages.Otp_Verified_Successfully);
            return responseVO;
        } else {
            responseVO.setFailResponse(Messages.Otp_Invalid);
            return responseVO;
        }


    }

    public ResponseVO resendSocietySignupOtp(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.userIdValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userEntity.getOtpAttempt() == 0) {
            userRepository.deleteById(userEntity.getId());
            responseVO.setFailResponse(Messages.Otp_Signup_Attempt);
            return responseVO;
        }

        int otp = 100000 + random.nextInt(899999);

        userEntity.setOtpAttempt(userEntity.getOtpAttempt() - 1);
        userEntity.setValidateOtp(otp);
        userEntity.setOtpRequestedTime(new Date());
        userRepository.save(userEntity);

//            Send OTP to Email
        Map<String,Object> model = mailService.getDefaultModel();
        model.put(MailPlaceHolder.OTP.name(), otp);
        String body= mailService.getHtmlBodyUsingThymeleafTemplate(model, MailTemplateName.MAIL_USER_SIGNUP_OTP);
        mailService.sendMail(userEntity.getEmail(), EmailSubject.EMAIL_OTP ,body,null,null,null,null);

        responseVO.setSuccessResponse(Messages.Otp_Send_Attempt);
        return responseVO;
    }

    public ResponseVO resendUserSignupOtp(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.userIdValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userEntity.getOtpAttempt() == 0) {
            userRepository.deleteById(userEntity.getId());
            responseVO.setFailResponse(Messages.Otp_Signup_Attempt);
            return responseVO;
        }

        int otp = 100000 + random.nextInt(899999);

        // Send OTP on Mobile

        Map<String,Object> model = smsService.getDefaultModel();
        model.put(MailPlaceHolder.OTP.name(),otp);

        String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);

        smsService.sendSMS(userEntity.getMobileNumber(),body);

        userEntity.setOtpAttempt(userEntity.getOtpAttempt() - 1);
        userEntity.setValidateOtp(otp);
        userEntity.setOtpRequestedTime(new Date());
        userRepository.save(userEntity);
        responseVO.setSuccessResponse(Messages.Otp_Send_Attempt);
        return responseVO;
    }

//    @Autowired
//    private JwtUtil jwtUtil;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    public ResponseVO adminSignIn(UserVO userVO) throws Exception {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.adminSigninInputValidation(userVO);
        UserEntity userEntity = userRepository.findByEmailAndPasswordAndRole(userVO.getEmail(), userVO.getPassword(), userVO.getInitiatedBy());


        if (userEntity != null) {

            UserVO userVO1 = new UserVO();

            userVO1.setId(userEntity.getId());
            userVO1.setName(userEntity.getName());
            userVO1.setEmail(userEntity.getEmail());
            userVO1.setMobileNumber(userEntity.getMobileNumber());
            userVO1.setRole(userEntity.getRole());
            userVO1.setUserStatus(userEntity.getUserStatus());

//            String token = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRole(), userEntity.getKey());


            if (userEntity.getRole().equalsIgnoreCase(UserRoles.SOCIETY) && userEntity.getUserStatus().equalsIgnoreCase(UserStatus.USER_VERIFIED)) {
                SocietyMappingVO societyMappingVO = new SocietyMappingVO();
                societyMappingVO.setUser(userVO1);
                responseVO = societyService.getSocietyUserMapping(societyMappingVO);
                responseVO.setIsVerified(true);
            }

//            userEntity.setToken(token);
            userRepository.save(userEntity);
//            responseVO.setToken(token);
            responseVO.setUser(userVO1);
            responseVO.setSuccessResponse(Messages.Singin_Success);
        } else {
            responseVO.setFailResponse(Messages.Fail_Attempt);
        }

        return responseVO;

    }

    public void userIdValidation(UserVO userVO) throws PZConstraintViolationException {
        userInputValidator.userIdValidation(userVO);
    }

    public void isUserExist(UserVO userVO) throws PZConstraintViolationException {
        userValidationService.isUserExist(userVO);
    }

    public ResponseVO adminForgetPassword(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.adminEmailInputValidation(userVO);
        UserEntity userEntity = userRepository.findByEmailAndRole(userVO.getEmail(), userVO.getInitiatedBy());

        if (userEntity != null) {
            String randomPassword = RandomString.make(8);

//            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(randomPassword);
            String sha256hex = DigestUtils.sha256Hex(randomPassword);

            userEntity.setPassword(sha256hex);
            userRepository.save(userEntity);

//            Send Email Forget Password
            Map<String,Object> model = mailService.getDefaultModel();
            model.put(MailPlaceHolder.OTP.name(), randomPassword);
            String body= mailService.getHtmlBodyUsingThymeleafTemplate(model, MailTemplateName.MAIL_USER_SIGNUP_OTP);
            mailService.sendMail(userEntity.getEmail(), EmailSubject.EMAIL_OTP ,body,null,null,null,null);

            responseVO.setSuccessResponse(Messages.Forget_Email_Success_Message);
        } else {
            responseVO.setFailResponse(Messages.Fail_Attempt);
        }
        return responseVO;
    }

    public ResponseVO adminChangePassword(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.adminChangePasswordInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userEntity.getPassword().equals(userVO.getPassword())) {
            userEntity.setPassword(userVO.getNewPassword());
            userRepository.save(userEntity);
            responseVO.setSuccessResponse(Messages.Change_Password_Successfully);
        } else {
            responseVO.setFailResponse(Messages.Fail_Attempt);
        }
        return responseVO;
    }

    public ResponseVO userLogin(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.userSignInInputValidation(userVO);
        UserEntity userEntity = userRepository.findByMobileNumberAndRole(userVO.getMobileNumber(), userVO.getInitiatedBy());

        if (userEntity != null) {

            if (!userValidationService.isLoginValid(userEntity)) {
                responseVO.setFailResponse(Messages.Login_Attempt_Duration);
                return responseVO;
            }

//            String token = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRole(), userEntity.getKey());
            int otp = 100000 + random.nextInt(899999);

//            userEntity.setToken(token);
            userEntity.setValidateOtp(otp);
            userEntity.setOtpRequestedTime(new Date());
            userEntity.setOtpAttempt(5);
            userRepository.save(userEntity);

            // Send OTP on Mobile

        Map<String,Object> model = smsService.getDefaultModel();
        model.put(MailPlaceHolder.OTP.name(),otp);

        String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);

        smsService.sendSMS(userEntity.getMobileNumber(),body);

            UserVO userVO1 = new UserVO();
            userVO1.setId(userEntity.getId());
            userVO1.setUserStatus(userEntity.getUserStatus());
            responseVO.setUser(userVO1);
            responseVO.setSuccessResponse(Messages.Singin_Success);
        } else {
            responseVO.setFailResponse(Messages.Fail_Attempt);
        }
        return responseVO;

    }

    public ResponseVO resendUserLoginOtp(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        userInputValidator.userIdValidation(userVO);
        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userEntity.getOtpAttempt() == 0) {
            userEntity.setFailAttempt(new Date());
            userEntity.setValidateOtp(0);
            userEntity.setOtpRequestedTime(null);
            userRepository.save(userEntity);
            responseVO.setFailResponse(Messages.Otp_Login_Attempt);
            return responseVO;
        }

        int otp = 100000 + random.nextInt(899999);

        userEntity.setOtpAttempt(userEntity.getOtpAttempt() - 1);
        userEntity.setValidateOtp(otp);
        userEntity.setOtpRequestedTime(new Date());
        userRepository.save(userEntity);

//        Send OTP on Mobile

        Map<String,Object> model = smsService.getDefaultModel();
        model.put(MailPlaceHolder.OTP.name(),otp);

        String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);

        smsService.sendSMS(userEntity.getMobileNumber(),body);

        responseVO.setSuccessResponse(Messages.Otp_Send_Attempt);
        return responseVO;

    }

    public ResponseVO checkLoginOtp(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.userOTPInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (!userValidationService.isOtpValid(userEntity)) {
            responseVO.setFailResponse(Messages.Otp_Expired);
            return responseVO;
        }

        if(userEntity.getValidateOtp() == userVO.getValidateOtp()){

            ResponseVO responseVO1 = societyService.getUserMapping(userVO);

            if(responseVO1.getDashBoard().isEmpty()){
                responseVO.setCheckUnit(Messages.Not_Present);

            }else{
                responseVO.setCheckUnit(Messages.Present);
                responseVO.setDashBoard(responseVO1.getDashBoard());
            }

            userEntity.setOtpRequestedTime(null);
            userEntity.setDeviceToken(userVO.getDeviceToken());
            userEntity.setValidateOtp(0);
            userEntity.setOtpAttempt(0);
            userRepository.save(userEntity);
            responseVO.setSuccessResponse(Messages.Otp_Verified_Successfully);
        }else {
            responseVO.setFailResponse(Messages.Otp_Invalid);
        }
        return responseVO;
    }

    public ResponseVO updateUserProfile(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.updateUserProfileInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userVO.getName().equals(userEntity.getName()) && userVO.getEmail().equals(userEntity.getEmail())) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            UserEntity user = userRepository.findByEmailAndRole(userVO.getEmail(), userVO.getInitiatedBy());

            if(user != null && user.getId() != userVO.getId()){
                responseVO.setFailResponse(Messages.Email_Exist);
            }else {

                userEntity.setName(userVO.getName());
                userEntity.setEmail(userVO.getEmail());
                userRepository.save(userEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;

    }

    public ResponseVO updateAdminProfile(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.updateAdminProfileInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        if (userVO.getName().equals(userEntity.getName()) && userVO.getMobileNumber().equals(userEntity.getMobileNumber())) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            UserEntity user = userRepository.findByMobileNumberAndRole(userVO.getMobileNumber(), userVO.getInitiatedBy());

            if(user != null && user.getId() != userVO.getId()){
                responseVO.setFailResponse(Messages.Mobile_Exist);
            }else {

                UserVO userVO1 = new UserVO();

                userEntity.setName(userVO.getName());
                userEntity.setMobileNumber(userVO.getMobileNumber());
                UserEntity userEntity1 = userRepository.save(userEntity);
                userVO1.setName(userEntity1.getName());
                userVO1.setMobileNumber(userEntity1.getMobileNumber());
                responseVO.setUser(userVO1);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;
    }

    public ResponseVO addProfile(UserVO userVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.addProfileInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());

        String fileName = userEntity.getName() + "_" + userEntity.getMobileNumber() + ".png";
        FileHandlingUtil.fileUpload(userVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_USER, null);
        userEntity.setPhoto(Types.USER_IMAGES + fileName);

        userRepository.save(userEntity);
        responseVO.setSuccessResponse(Messages.Update_Successfully);
        return responseVO;

    }

    public ResponseVO getUserDetail(OwnerMasterVO ownerMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        UserVO userVO = userRepository.getUserName(ownerMasterVO.getUserId());
        responseVO.setUser(userVO);
        return responseVO;
    }
    public void isUserComplainMapping(ComplainVO complainRequestVO) throws PZConstraintViolationException {
        userValidationService.isUserComplainMapping(complainRequestVO);
    }
    public void isAdminComplainMapping(ComplainVO complainRequestVO) throws PZConstraintViolationException {
        userValidationService.isAdminComplainMapping(complainRequestVO);
    }
    public void isUserAmenityMapping(AmenityVO amenityVO) throws PZConstraintViolationException {
        userValidationService.isUserAmenityMapping(amenityVO);
    }
    public ResponseVO getUserDatai1(int userId){
        ResponseVO responseVO = new ResponseVO();
        ComplainListVO complainListVO = userRepository.getUserDetails(userId);
        responseVO.setComplainList(complainListVO);
        return responseVO;
    }

    public ResponseVO isUserMobileExist(UserVO userVO) {
        ResponseVO responseVO = new ResponseVO();
        UserEntity userEntity = userRepository.findByMobileNumber(userVO.getMobileNumber());

        if(userEntity != null){
            UserVO user = modelMapper.map(userEntity, UserVO.class);
            responseVO.setUser(user);
        }
        return responseVO;
    }

    public ResponseVO getUserListDetail(List<OwnerMasterVO> ownerMasterVOList) {
        ResponseVO responseVO = new ResponseVO();
        List<UserListVO> userVOList = new ArrayList<>();

        for(OwnerMasterVO ownerMasterVO : ownerMasterVOList){
            UserEntity userEntity = userRepository.findUserById(ownerMasterVO.getUserId());
            UserListVO userVO = modelMapper.map(userEntity, UserListVO.class);
            userVOList.add(userVO);
        }

        responseVO.setOwnerList(userVOList);
        return responseVO;
    }

    public List<String> getUserToken(List<Integer> userId) {

        List<String> userToken = new ArrayList<>();

        for (int user : userId){
            String a = userRepository.getUserToken(user, true);
            userToken.add(a);
        }
        return userToken;
    }
    public String getMobileNumber(int userId){
        String mobile = userRepository.getMobileNumber(userId, true);
        return mobile;
    }
    public String getOwnerName(String phoneNumber){
        String name = userRepository.getOwnerName(phoneNumber);
        return name;
    }
    public String getUserTokenBYUserId(int userId){
        String a = userRepository.getUserToken(userId, true);
        return a;
    }

    public ResponseVO getUserAbout(UserVO userVO) {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.userIdValidation(userVO);

        UserVO user = userRepository.getUserAboutDetail(userVO.getId());

        if(user != null){
            responseVO.setUser(user);
            responseVO.setSuccessResponse(Messages.User_About);
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }


    public ResponseVO updateUserAbout(UserVO userVO) {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.updateUserAboutInputValidation(userVO);

        UserEntity userEntity = userRepository.getOne(userVO.getId());


        if (userVO.getType().equalsIgnoreCase(Types.CALL)) {
            userEntity.setIsCall(userVO.getIsCall());

        } else if (userVO.getType().equalsIgnoreCase(Types.PROFESSION)) {
            userEntity.setUserProfession(userVO.getUserProfession());

        } else if (userVO.getType().equalsIgnoreCase(Types.SPECIALIZATION)) {
            userEntity.setUserSpecialization(userVO.getUserSpecialization());

        } else if (userVO.getType().equalsIgnoreCase(Types.INTEREST)) {
            userEntity.setUserInterest(userVO.getUserInterest());

        } else if (userVO.getType().equalsIgnoreCase(Types.ABOUT)) {
            userEntity.setUserAbout(userVO.getUserAbout());

        } else if (userVO.getType().equalsIgnoreCase(Types.WEBSITE)) {
            userEntity.setUserWebsite(userVO.getUserWebsite());

        } else if (userVO.getType().equalsIgnoreCase(Types.FACEBOOK)) {
            userEntity.setFacebookProfile(userVO.getFacebookProfile());

        } else if (userVO.getType().equalsIgnoreCase(Types.INSTAGRAM)) {
            userEntity.setInstagramProfile(userVO.getInstagramProfile());

        } else if (userVO.getType().equalsIgnoreCase(Types.TWITTER)) {
            userEntity.setTwitterProfile(userVO.getTwitterProfile());

        } else if (userVO.getType().equalsIgnoreCase(Types.LINEDIN)) {
            userEntity.setLinkedInProfile(userVO.getLinkedInProfile());
        }

        userRepository.save(userEntity);
        responseVO.setSuccessResponse(Messages.Update_Successfully);
        return responseVO;

    }

    public ResponseVO getUserList(List<Integer> societyUserId) {
        ResponseVO responseVO = new ResponseVO();
        ArrayList<UserVO> userVOs = new ArrayList<>();

        societyUserId.forEach(userId -> {
            UserVO userVO = userRepository.getUserName(userId);
            userVOs.add(userVO);
        });

        responseVO.setUserList(userVOs);
        return responseVO;
    }

    public ResponseVO addProfession(ProfessionMasterVO professionMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.addProfessionInputValidation(professionMasterVO);
        boolean isChange = false;
        UserProfessionMasterEntity userProfessionMasterEntity = new UserProfessionMasterEntity();

        if(professionMasterVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER)) {
            userProfessionMasterEntity = userProfessionMasterRepository.findByUnitIdAndMobileNumberAndProfession(professionMasterVO.getUnitId(), professionMasterVO.getMobileNumber(), professionMasterVO.getProfession());
        }else {
            userProfessionMasterEntity = userProfessionMasterRepository.findBySocietyIdAndMobileNumberAndProfession(professionMasterVO.getSocietyId(), professionMasterVO.getMobileNumber(), professionMasterVO.getProfession());
        }

        if(professionMasterVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(userProfessionMasterEntity != null){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {
                UserProfessionMasterEntity professionMaster = modelMapper.map(professionMasterVO, UserProfessionMasterEntity.class);

                userProfessionMasterRepository.save(professionMaster);
                responseVO.setSuccessResponse(Messages.Added_Success);
            }
        }else if(professionMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            UserProfessionMasterEntity professionMaster = userProfessionMasterRepository.getOne(professionMasterVO.getId());

            if (Functions.compareValue(professionMaster.getProfession(), professionMasterVO.getProfession())) {
                professionMaster.setProfession(professionMasterVO.getProfession());
                isChange = true;
            }

            if (Functions.compareValue(professionMaster.getName(), professionMasterVO.getName())) {
                professionMaster.setName(professionMasterVO.getName());
                isChange = true;
            }

            if (Functions.compareValue(professionMaster.getMobileNumber(), professionMasterVO.getMobileNumber())) {
                professionMaster.setMobileNumber(professionMasterVO.getMobileNumber());
                isChange = true;
            }

            if (isChange){
                userProfessionMasterRepository.save(professionMaster);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }else {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }

        }

        return responseVO;
    }

    public ResponseVO getProfession(ProfessionMasterVO professionMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        userInputValidator.getProfessionInputValidation(professionMasterVO);

        List<ProfessionMasterVO> professionMasterVOList = userProfessionMasterRepository.getBySocietyId(professionMasterVO.getSocietyId(), professionMasterVO.getUnitId());
        List<ProfessionMasterVO> professionMasterVOS = userProfessionMasterRepository.getUserIdAndUnitId(professionMasterVO.getUserId(), professionMasterVO.getUnitId());

        if(professionMasterVOList.isEmpty() && professionMasterVOS.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {

            List<ProfessionMasterVO> professionMasterVOS1 = new ArrayList<>();
            Map<String, List<ProfessionMasterVO>> map = new HashMap<>();

            for(ProfessionMasterVO professionMasterVO1 : professionMasterVOList){

                if(!map.containsKey(professionMasterVO1.getProfession())){
                    professionMasterVOS1.add(professionMasterVO1);
                    map.put(professionMasterVO1.getProfession(), professionMasterVOS1);
                }else {
                    List<ProfessionMasterVO> professionMaster = map.get(professionMasterVO1.getProfession());
                    professionMaster.add(professionMasterVO1);
                    map.put(professionMasterVO1.getProfession(), professionMaster);
                }
            }

            responseVO.setUserProfession(professionMasterVOS);
            responseVO.setProfession(map);
            responseVO.setSuccessResponse(Messages.Get_Profession_List);
        }
        return responseVO;
    }

    public List<Integer> getUserIdList(SocietyUserMasterVO societyUserMasterVO) {
        UserEntity userEntity = new UserEntity();
        Specification<UserEntity> specification = Specification
                .where(!Functions.nonNullString(societyUserMasterVO.getName()) ? null : SpecificationService.likeSpecification(userEntity, EntityVariable.NAME, societyUserMasterVO.getName()))
                .and(!Functions.nonNullString(societyUserMasterVO.getMobileNumber()) ? null : SpecificationService.likeSpecification(userEntity, EntityVariable.MOBILE_NUMBER, societyUserMasterVO.getMobileNumber()))
                .and(SpecificationService.equalSpecification(userEntity, EntityVariable.ROLE, UserRoles.SOCIETY));

        List<UserEntity> societyEntityPage = userRepository.findAll(specification);

        return !societyEntityPage.isEmpty() ? societyEntityPage.stream().map(UserEntity::getId).collect(Collectors.toList()) : null;
    }

//    public ResponseVO reGenerate(UserVO userVO) {
//        ResponseVO responseVO = new ResponseVO();
//        userInputValidator.reGenerateInputValidation(userVO);
//
//        String subject = jwtUtil.extractUsername(userVO.getToken());
//        String key = jwtUtil.extractKeyClaim(userVO.getToken(), "key");
//        String role = jwtUtil.extractKeyClaim(userVO.getToken(), "role");
//
//        if(!role.equalsIgnoreCase(UserRoles.SOCIETY) && !role.equalsIgnoreCase(UserRoles.USER)) {
//            responseVO.setFailResponse(Messages.Invalid_Request);
//            return responseVO;
//        }
//        UserEntity userEntity = null;
//        if(role.equalsIgnoreCase(UserRoles.USER)) {
//             userEntity = userRepository.findByMobileNumberAndRoleAndKey(subject, role, key);
//        }else if(role.equalsIgnoreCase(UserRoles.SOCIETY)){
//             userEntity = userRepository.findByEmailAndRoleAndKey(subject, role, key);
//        }
//
//        if(userEntity == null){
//            responseVO.setFailResponse(Messages.Not_Found);
//            return responseVO;
//        }
//
//        String token = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRole(), userEntity.getKey());
//        userEntity.setToken(token);
//        userRepository.save(userEntity);
//        responseVO.setToken(token);
//        responseVO.setSuccessResponse(Messages.Token_Generate_Successfully);
//        return responseVO;
//    }
}
