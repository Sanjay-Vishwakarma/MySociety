package com.pz.mysociety.controller.Helper;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.HelperService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/helper")
public class HelperController {

    @Autowired
    HelperService helperService;
    @Autowired
    UserAuthService userAuthService;

    @ApiOperation(value = "addHelper", notes = SwaggerMessage.AddHelper_Message)
    @PostMapping("/addHelper")
    public ResponseVO addHelper(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrGuard(helperRequestVO.getInitiatedBy());
        return helperService.insertDailyHelp(helperRequestVO);
    }
    @ApiOperation(value = "addHelperDocument", notes = SwaggerMessage.Add_Helper_Document)
    @PostMapping("/addHelperDocument")
    public ResponseVO addHelperDocument(@RequestBody @Valid HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.addHelperDocument(helperDocRequestVO);
    }
    @ApiOperation(value = "getHelperByType", notes = SwaggerMessage.GetHelper_Type_Message)
    @PostMapping("/getHelperByType")
    public ResponseVO dailyHelperType(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(helperRequestVO.getInitiatedBy());
        return helperService.getHelperByType(helperRequestVO);
    }

    @ApiOperation(value = "getHelper", notes = SwaggerMessage.GetHelper_Message)
    @PostMapping("/getHelper")
    public ResponseVO getHelper(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(helperRequestVO.getInitiatedBy());
        return helperService.getHelper(helperRequestVO);
    }

//    @ApiOperation(value = "updateHelper", notes = SwaggerMessage.updateHelper_Message)
//    @PostMapping("/updateHelper")
//    public ResponseVO updateHelper(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
//        userAuthService.isSocietyOrUser(helperRequestVO.getInitiatedBy());
//        return helperService.updateHelper(helperRequestVO);
//    }
    @ApiOperation(value = "addHelperType", notes = SwaggerMessage.AddHelper_Type_Message)
    @PostMapping("/addHelperType")
    public ResponseVO insertDailyHelperType(@RequestBody @Valid HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(helperTypeRequestVO.getInitiatedBy());
        return helperService.addDailyHelperType(helperTypeRequestVO);
    }

    @ApiOperation(value = "getHelperType", notes = SwaggerMessage.Helper_Type_Message)
    @PostMapping("/getHelperType")
    public ResponseVO fetchDailyHelperType(@RequestBody @Valid HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(helperTypeRequestVO.getInitiatedBy());
        return helperService.getDailyHelperType(helperTypeRequestVO);
    }

    @ApiOperation(value = "updateHelperType", notes = SwaggerMessage.updateHelper_Type_Message)
    @PostMapping("/updateHelperType")
    public ResponseVO changeDailyHelperType(@RequestBody @Valid HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
         userAuthService.isSuperAdmin(helperTypeRequestVO.getInitiatedBy());
        return helperService.updateDailyHelperType(helperTypeRequestVO);
    }

    @ApiOperation(value = "addServiceType", notes = SwaggerMessage.Add_Service_Type_Message)
    @PostMapping("/addServiceType")
    public ResponseVO addServiceType(@RequestBody @Valid ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(serviceTypeVO.getInitiatedBy());
        return helperService.addServiceType(serviceTypeVO);
    }

    @ApiOperation(value = "getServiceType", notes = SwaggerMessage.Get_Type_Message)
    @PostMapping("/getServiceType")
    public ResponseVO getServiceType(@RequestBody @Valid ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(serviceTypeVO.getInitiatedBy());
        return helperService.getServiceType(serviceTypeVO);
    }

    @ApiOperation(value = "updateServiceType", notes = SwaggerMessage.Update_Service_Type_Message)
    @PostMapping("/updateServiceType")
    public ResponseVO updateServiceType(@RequestBody @Valid ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(serviceTypeVO.getInitiatedBy());
        return helperService.updateServiceType(serviceTypeVO);
    }

    @ApiOperation(value = "getServiceHelperType", notes = SwaggerMessage.Get_Type_Message)
    @PostMapping("/getServiceHelperType")
    public ResponseVO getServiceHelperType(@RequestBody @Valid ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(serviceTypeVO.getInitiatedBy());
        return helperService.getServiceHelperType(serviceTypeVO);
    }
    //Document

    @ApiOperation(value = "addHelperPhoneNumber", notes = SwaggerMessage.Add_Helper_PhoneNumber)
    @PostMapping("/addHelperPhoneNumber")
    public ResponseVO addHelperPhoneNumber(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(helperRequestVO.getInitiatedBy());
        return helperService.addHelperPhoneNumber(helperRequestVO);
    }
    @ApiOperation(value = "getHelperList", notes = SwaggerMessage.GetFrequentVisitor)
    @PostMapping("/getHelperList")
    public ResponseVO getHelperList(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(helperRequestVO.getInitiatedBy());
        return helperService.getHelperList(helperRequestVO);
    }
    @ApiOperation(value = "addHelperLogHistory", notes = SwaggerMessage.Add_Helper_LogHistory)
    @PostMapping("/addHelperLogHistory")
    public ResponseVO addHelperLogHistory(@RequestBody @Valid HelperHistoryLogVO helperHistoryLogRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(helperHistoryLogRequestVO.getInitiatedBy());
        return helperService.addHelperLogHistory(helperHistoryLogRequestVO);
    }
    @ApiOperation(value = "addVisitorLogHistory", notes = SwaggerMessage.Add_Visitor_HistoryLog_Message)
    @PostMapping("/addVisitorLogHistory")
    public ResponseVO addVisitorHistoryLog(@RequestBody @Valid HelperHistoryLogVO visitorHistoryLogRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(visitorHistoryLogRequestVO.getInitiatedBy());
        return helperService.addVisitorHistoryLog(visitorHistoryLogRequestVO);
    }
    @ApiOperation(value = "addWaitingVisitorHistoryLog", notes = SwaggerMessage.Add_Visitor_HistoryLog_Message)
    @PostMapping("/addWaitingVisitorHistoryLog")
    public ResponseVO addWaitingVisitorHistoryLog(@RequestBody @Valid HelperHistoryLogVO visitorHistoryLogRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(visitorHistoryLogRequestVO.getInitiatedBy());
        return helperService.addWaitingVisitorHistoryLog(visitorHistoryLogRequestVO);
    }
    @ApiOperation(value = "addVisitor", notes = SwaggerMessage.Add_Visitor_Message)
    @PostMapping("/addVisitor")
    public ResponseVO addVisitor(@RequestBody @Valid VisitorVO visitorRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(visitorRequestVO.getInitiatedBy());
        return helperService.addVisitor(visitorRequestVO);
    }
    @ApiOperation(value = "getVisitor", notes = SwaggerMessage.GetVisitor)
    @PostMapping("/getVisitor")
    public ResponseVO getVisitor(@RequestBody @Valid HelperVO visitorRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(visitorRequestVO.getInitiatedBy());
        return helperService.getVisitor(visitorRequestVO);
    }
    @ApiOperation(value = "getByPassCode", notes = SwaggerMessage.Get_ByPassCode_Message)
    @PostMapping("/getByPassCode")
    public ResponseVO getByPassCode(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(helperRequestVO.getInitiatedBy());
        return helperService.getByPassCode(helperRequestVO);
    }
    @ApiOperation(value = "insideHelperList", notes = SwaggerMessage.Get_InsideHelper_Message)
    @PostMapping("/insideHelperList")
    public ResponseVO insideHelperList(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(helperRequestVO.getInitiatedBy());
        return helperService.insideHelperList(helperRequestVO);
    }
    @ApiOperation(value = "getAttendance", notes = SwaggerMessage.Get_Attendance)
    @PostMapping("/getAttendance")
    public ResponseVO getAttendance(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(helperRequestVO.getInitiatedBy());
        return helperService.getAttendance(helperRequestVO);
    }
    @ApiOperation(value = "checkVisitorPhoneNumber", notes = SwaggerMessage.Add_Helper_PhoneNumber)
    @PostMapping("/checkVisitorPhoneNumber")
    public ResponseVO checkVisitorPhoneNumber(@RequestBody @Valid HelperVO visitorRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(visitorRequestVO.getInitiatedBy());
        return helperService.checkVisitorPhoneNumber(visitorRequestVO);
    }
    @ApiOperation(value = "getWaitingList", notes = SwaggerMessage.Get_WaitingList)
    @PostMapping("/getWaitingList")
    public ResponseVO getWaitingList(@RequestBody @Valid HelperVO visitorRequestVO) throws PZConstraintViolationException {
        userAuthService.isGuard(visitorRequestVO.getInitiatedBy());
        return helperService.getWaitingList(visitorRequestVO);
    }
    @ApiOperation(value = "getVisitorType", notes = SwaggerMessage.Get_Visitor_Type)
    @PostMapping("/getVisitorType")
    public ResponseVO getVisitorType(@RequestBody @Valid ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        userAuthService.isGuard(serviceTypeVO.getInitiatedBy());
        return helperService.getVisitorType(serviceTypeVO);
    }

    @ApiOperation(value = "recentActivity", notes = SwaggerMessage.Recent_Activity_Message)
    @PostMapping("/recentActivity")
    public ResponseVO recentActivity(@Valid @RequestBody ActivityVO activityVO) throws PZConstraintViolationException {
        userAuthService.isUser(activityVO.getInitiatedBy());
        return helperService.recentActivity(activityVO);
    }
    @ApiOperation(value = "getUnitDetails", notes = SwaggerMessage.Get_UnitDetails)
    @PostMapping("/getUnitDetails")
    public ResponseVO getUnitDetails(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrGuard(helperRequestVO.getInitiatedBy());
        return helperService.getUnitDetails(helperRequestVO);
    }
    @ApiOperation(value = "getInOutHistory", notes = SwaggerMessage.Get_InOutHistory)
    @PostMapping("/getInOutHistory")
    public ResponseVO getInOutHistory(@RequestBody @Valid HelperVO helperRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrGuard(helperRequestVO.getInitiatedBy());
        return helperService.getInOutHistory(helperRequestVO);
    }
    @ApiOperation(value = "addHelperTypeDocument", notes = SwaggerMessage.Add_Helper_Document)
    @PostMapping("/addHelperTypeDocument")
    public ResponseVO addHelperTypeDocument(@RequestBody @Valid HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.addHelperTypeDocument(helperDocRequestVO);
    }
    @ApiOperation(value = "updateHelperTypeDocument", notes = SwaggerMessage.Update_Helper_Document)
    @PostMapping("/updateHelperTypeDocument")
    public ResponseVO updateHelperDocument(@RequestBody @Valid HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.updateHelperTypeDocument(helperDocRequestVO);
    }
    @ApiOperation(value = "getHelperTypeDocument", notes = SwaggerMessage.Get_Helper_Document)
    @PostMapping("/getHelperTypeDocument")
    public ResponseVO getHelperTypeDocument(@RequestBody @Valid HelperVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.getHelperTypeDocument(helperDocRequestVO);
    }
    @ApiOperation(value = "getHelperMandatoryDocuments", notes = SwaggerMessage.Get_Helper_Document_HelperTypeId)
    @PostMapping("/getHelperMandatoryDocuments")
    public ResponseVO getHelperMandatoryDocuments(@RequestBody @Valid HelperDocumentVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrGuard(helperDocRequestVO.getInitiatedBy());
        return helperService.getHelperMandatoryDocuments(helperDocRequestVO);
    }
    @ApiOperation(value = "getVisitorDashBoard", notes = SwaggerMessage.GetVisitorDashBoard)
    @PostMapping("/getVisitorDashBoard")
    public ResponseVO getVisitorDashBoard(@RequestBody @Valid HelperHistoryLogVO helperDocRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.getVisitorDashBoard(helperDocRequestVO);
    }
    @ApiOperation(value = "getHelperDocument", notes = SwaggerMessage.Helper_Document)
    @PostMapping("/getHelperDocument")
    public ResponseVO getHelperDocument (@RequestBody @Valid HelperDocumentVO helperDocRequestVO)throws PZConstraintViolationException {
        userAuthService.isSociety(helperDocRequestVO.getInitiatedBy());
        return helperService.getHeplerDocument(helperDocRequestVO);
    }

    @ApiOperation(value = "recentVisitor", notes = SwaggerMessage.Get_Recent_Visitor)
    @PostMapping("/recentVisitor")
    public ResponseVO recentVisitor(@RequestBody @Valid HelperVO helperVO){
        userAuthService.isSociety(helperVO.getInitiatedBy());
        return helperService.recentVisitor(helperVO);
    }


}
