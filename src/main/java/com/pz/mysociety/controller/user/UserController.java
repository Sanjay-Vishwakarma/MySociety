package com.pz.mysociety.controller.user;


import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.PushNotificationRequest;
import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.model.Request.userRequestVO.ProfessionMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.service.UserAuthService;
import com.pz.mysociety.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private PushNotificationService pushNotificationService;

//    @PostMapping("/notification")
//    public void sendTokenNotification(@RequestBody PushNotificationRequest request) {
//        pushNotificationService.sendPushNotificationToToken(request.getTitle(), request.getMessage(), request.getToken());
//    }

    @ApiOperation(value = "adminSignUp", notes = SwaggerMessage.Admin_SignUp_Message)
    @PostMapping("/adminSignUp")
    public ResponseVO adminSignUp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException{
        //Check for Role
        userAuthService.isAdmin(userVO.getInitiatedBy());
        return userService.adminSignUp(userVO);
    }

    @ApiOperation(value = "userSignUp", notes = SwaggerMessage.User_SignUp_Message)
    @PostMapping("/signup")
    public ResponseVO userSignUp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException{
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.userSignUp(userVO);
    }

    @ApiOperation(value = "verifySignUpOtp", notes = SwaggerMessage.Verify_Otp_Message)
    @PostMapping("/verifySignupOtp")
    public ResponseVO verifySignUpOtp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException{
        userAuthService.isSocietyOrUser(userVO.getInitiatedBy());
        return userService.checkSignUpOtp(userVO);
    }

    @ApiOperation(value = "resendSocietySignUpOtp", notes = SwaggerMessage.Resend_User_Otp_Message)
    @PostMapping("/resendSocietySignupOtp")
    public ResponseVO resendSocietySignupOtp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isSociety(userVO.getInitiatedBy());
        return userService.resendSocietySignupOtp(userVO);
    }

    @ApiOperation(value = "resendUserSignUpOtp", notes = SwaggerMessage.Resend_User_Otp_Message)
    @PostMapping("/resendUserSignupOtp")
    public ResponseVO userResendSignupOtp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException{
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.resendUserSignupOtp(userVO);
    }

    @ApiOperation(value = "changePassword", notes = SwaggerMessage.Change_Password_Message)
    @PostMapping("/changePassword")
    public ResponseVO adminChangePassword(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(userVO.getInitiatedBy());
        return userService.adminChangePassword(userVO);
    }

    @ApiOperation(value = "adminForgetPassword", notes = SwaggerMessage.Forget_Password_Message)
    @PostMapping("/adminForgetPassword")
    public ResponseVO adminForgetPassword(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(userVO.getInitiatedBy());
        return userService.adminForgetPassword(userVO);
    }

    @ApiOperation(value = "adminSignIn", notes = SwaggerMessage.Admin_SignIn_Message)
    @PostMapping("/adminSignin")
    public ResponseVO adminSingIn(@Valid @RequestBody UserVO userVO) throws Exception {
        userAuthService.isAdmin(userVO.getInitiatedBy());
        return userService.adminSignIn(userVO);
    }

    @ApiOperation(value = "login", notes = SwaggerMessage.User_Login_Message)
    @PostMapping("/login")
    public ResponseVO userLogin(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.userLogin(userVO);
    }

    @ApiOperation(value = "resendUserLoginOtp", notes = SwaggerMessage.Resend_User_Otp_Message)
    @PostMapping("/resendUserLoginOtp")
    public ResponseVO userResendLoginOtp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException{
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.resendUserLoginOtp(userVO);
    }

    @ApiOperation(value = "verifyLoginOtp", notes = SwaggerMessage.Verify_Otp_Message)
    @PostMapping("/verifyLoginOtp")
    public ResponseVO validatedLoginOtp(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.checkLoginOtp(userVO);
    }

    @ApiOperation(value = "updateUserProfile", notes = SwaggerMessage.User_Update_Profile_Message)
    @PostMapping("/updateUserProfile")
    public ResponseVO updateUserProfile(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.updateUserProfile(userVO);
    }

    @ApiOperation(value = "updateAdminProfile", notes = SwaggerMessage.Admin_Update_Profile_Message)
    @PostMapping("/updateAdminProfile")
    public ResponseVO updateAdminProfile(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(userVO.getInitiatedBy());
        return userService.updateAdminProfile(userVO);
    }

    @ApiOperation(value = "addUserProfile", notes = SwaggerMessage.Add_User_Profile_Message)
    @PostMapping("/addUserProfile")
    public ResponseVO userProfile(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(userVO.getInitiatedBy());
        return userService.addProfile(userVO);
    }


    @ApiOperation(value = "getUserAbout", notes = SwaggerMessage.Get_User_About_Message)
    @PostMapping("/getUserAbout")
    public ResponseVO getUserAbout(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.getUserAbout(userVO);
    }

    @ApiOperation(value = "updateUserAbout", notes = SwaggerMessage.Update_User_About_Message)
    @PostMapping("/updateUserAbout")
    public ResponseVO updateUserAbout(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
        userAuthService.isUser(userVO.getInitiatedBy());
        return userService.updateUserAbout(userVO);
    }

    @ApiOperation(value = "addProfession", notes = SwaggerMessage.Add_User_Profession)
    @PostMapping("/addProfession")
    public ResponseVO addProfession(@RequestBody @Valid ProfessionMasterVO professionMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(professionMasterVO.getInitiatedBy());
        return userService.addProfession(professionMasterVO);
    }

    @ApiOperation(value = "getProfession", notes = SwaggerMessage.Get_User_Profession_List)
    @PostMapping("/getProfession")
    public ResponseVO getProfession(@Valid @RequestBody ProfessionMasterVO professionMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(professionMasterVO.getInitiatedBy());
        return userService.getProfession(professionMasterVO);
    }

//    @ApiOperation(value = "reGenerate", notes = SwaggerMessage.Get_User_Profession_List)
//    @PostMapping("/reGenerate")
//    public ResponseVO reGenerate(@Valid @RequestBody UserVO userVO) throws PZConstraintViolationException {
//        userAuthService.isAllUser(userVO.getInitiatedBy());
//        return userService.reGenerate(userVO);
//    }

}
