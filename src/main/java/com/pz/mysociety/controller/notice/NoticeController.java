package com.pz.mysociety.controller.notice;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.ComplainVO;
import com.pz.mysociety.model.Request.NoticeTypeVO;
import com.pz.mysociety.model.Request.SocietyNoticeVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.SocietyNoticeService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/notice")
@Validated
public class NoticeController {

    @Autowired
    private SocietyNoticeService societyNoticeService;
    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addNotice", notes = SwaggerMessage.Add_Notice_Message)
    @PostMapping("/addNotice")
    public ResponseVO addNotice(@RequestBody @Valid SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(societyNoticeRequestVO.getInitiatedBy());
        return societyNoticeService.addNotice(societyNoticeRequestVO);
    }

    @ApiOperation(value = "getAllNotices", notes = SwaggerMessage.Get_AllNotices_Message)
    @PostMapping("/getAllNotices")
    public ResponseVO getAllNotices(@Valid @RequestBody SocietyNoticeVO noticeRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(noticeRequestVO.getInitiatedBy());
        return societyNoticeService.getAllNotices(noticeRequestVO);
    }

//    @ApiOperation(value = "updateNotice", notes = SwaggerMessage.Update_Notice_Message)
//    @PostMapping("/updateNotice")
//    public ResponseVO updateNotice(@Valid @RequestBody SocietyNoticeVO noticeRequestVO) throws PZConstraintViolationException {
//        userAuthService.isAdmin(noticeRequestVO.getInitiatedBy());
//        return societyNoticeService.updateNotice(noticeRequestVO);
//    }

    @ApiOperation(value = "addNoticeType", notes = SwaggerMessage.Add_NoticeType_Message)
    @PostMapping("/addNoticeType")
    public ResponseVO insertNoticeType(@RequestBody @Valid NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(noticeTypeVO.getInitiatedBy());
        return societyNoticeService.addNoticeType(noticeTypeVO);
    }

    @ApiOperation(value = "updateNoticeType", notes = SwaggerMessage.Update_NoticeType_Message)
    @PostMapping("/updateNoticeType")
    public ResponseVO changeNoticeType(@RequestBody @Valid NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(noticeTypeVO.getInitiatedBy());
        return societyNoticeService.updateNoticeType(noticeTypeVO);
    }

    @ApiOperation(value = "getNoticeType", notes = SwaggerMessage.Get_NoticeType_Message)
    @PostMapping("/getNoticeType")
    public ResponseVO getNoticeType(@RequestBody @Valid NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(noticeTypeVO.getInitiatedBy());
        return societyNoticeService.getNoticeType(noticeTypeVO);
    }

  @ApiOperation(value = "deleteNotice", notes = SwaggerMessage.Change_AmenityFlag_Message)
    @PostMapping("/deleteNotice")
    public ResponseVO deleteNotice(@Valid @RequestBody SocietyNoticeVO noticeRequestVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(noticeRequestVO.getInitiatedBy());
        return societyNoticeService.deleteNotice(noticeRequestVO);
    }
       /*     @ApiOperation(value = "deleteNoticeType", notes = SwaggerMessage.Change_AmenityFlag_Message)
    @PostMapping("/deleteNoticeType")
    public ResponseVO dropNoticeType(@RequestBody @Valid NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(noticeTypeVO.getInitiatedBy());
        return societyNoticeService.deleteNoticeType(noticeTypeVO);
    }
    */
}
