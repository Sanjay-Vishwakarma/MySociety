package com.pz.mysociety.controller.guard;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.GuardDocumentMasterVO;
import com.pz.mysociety.model.Request.GuardHistoryVO;
import com.pz.mysociety.model.Request.GuardVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.GuardService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/guard")
public class GuardController {


    @Autowired
    private GuardService guardService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addGuard", notes = SwaggerMessage.Add_Guard_Message)
    @PostMapping("/addGuard")
    public ResponseVO addGuard(@RequestBody @Valid GuardVO guardVO) throws PZConstraintViolationException {
        userAuthService.isSociety(guardVO.getInitiatedBy());
        return guardService.addGuard(guardVO);
    }

//    @ApiOperation(value = "updateGuard", notes = SwaggerMessage.Update_Guard_Message)
//    @PostMapping("/updateGuard")
//    public ResponseVO updateGuard(@RequestBody @Valid GuardVO guardVO) throws PZConstraintViolationException {
//        userAuthService.isSociety(guardVO.getInitiatedBy());
//        return guardService.updateGuard(guardVO);
//    }

    @ApiOperation(value = "login", notes = SwaggerMessage.Guard_Login_Message)
    @PostMapping("/login")
    public ResponseVO guardLogin(@RequestBody @Valid GuardVO guardVO) throws PZConstraintViolationException {
        userAuthService.isGuard(guardVO.getInitiatedBy());
        return guardService.guardLogin(guardVO);
    }

    @ApiOperation(value = "resendOtp", notes = SwaggerMessage.Resend_User_Otp_Message)
    @PostMapping("/resendOtp")
    public ResponseVO resendOtp(@Valid @RequestBody GuardVO guardVO) throws PZConstraintViolationException{
        userAuthService.isGuard(guardVO.getInitiatedBy());
        return guardService.resendOtp(guardVO);
    }

    @ApiOperation(value = "verifyOtp", notes = SwaggerMessage.Verify_Otp_Message)
    @PostMapping("/verifyOtp")
    public ResponseVO verifyOtp(@Valid @RequestBody GuardVO guardVO) throws PZConstraintViolationException {
        userAuthService.isGuard(guardVO.getInitiatedBy());
        return guardService.verifyOtp(guardVO);
    }

    @ApiOperation(value = "guardLogout", notes = SwaggerMessage.Guard_Logout_Message)
    @PostMapping("/guardLogout")
    public ResponseVO guardLogout(@Valid @RequestBody GuardVO guardVO) throws PZConstraintViolationException {
        userAuthService.isGuard(guardVO.getInitiatedBy());
        return guardService.guardLogout(guardVO);
    }

    @ApiOperation(value = "getGuardList", notes = SwaggerMessage.Get_Guard_Message)
    @PostMapping("/getGuardList")
    public ResponseVO getGuardList(@RequestBody @Valid GuardVO guardVO) throws PZConstraintViolationException {
        userAuthService.isSociety(guardVO.getInitiatedBy());
        return guardService.getGuardList(guardVO);
    }

    @ApiOperation(value = "guardAttendance", notes = SwaggerMessage.Get_Guard_Attendance_Message)
    @PostMapping("/getGuardAttendance")
    public ResponseVO getGuardAttendance(@RequestBody @Valid GuardHistoryVO guardHistoryVO) throws PZConstraintViolationException {
        userAuthService.isSociety(guardHistoryVO.getInitiatedBy());
        return guardService.getGuardAttendance(guardHistoryVO);
    }

//    @ApiOperation(value="addGuardDocument",notes=SwaggerMessage.Get_Helper_Document )
//    @PostMapping("/addGuardDocument")
//    public ResponseVO addGuardDocument(@RequestBody @Valid GuardDocumentMasterVO guardDocumentMasterVO)throws PZConstraintViolationException{
//        userAuthService.isSociety(guardDocumentMasterVO.getInitiatedBy());
//        return guardService.addGuardDocument(guardDocumentMasterVO);
//    }


}