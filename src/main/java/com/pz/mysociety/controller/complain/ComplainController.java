package com.pz.mysociety.controller.complain;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.ComplainVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.ComplainService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/complain")
@Validated
public class ComplainController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private ComplainService complainService;

    @ApiOperation(value = "addComplain", notes = SwaggerMessage.Add_Complain_Message)
    @PostMapping("/addComplain")
    public ResponseVO addsocietyList(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isUser(complainVO.getInitiatedBy());
        return complainService.addComplain(complainVO);

    }
    @ApiOperation(value = "updateComplainStatus", notes = SwaggerMessage.Update_ComplainStatus_Message)
    @PostMapping("/updateComplainStatus")
    public ResponseVO complainStatus(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSociety(complainVO.getInitiatedBy());
        return complainService.changeComplainStatus(complainVO);
    }

    @ApiOperation(value = "addComplainRecord", notes = SwaggerMessage.Add_ComplainRecord_Message)
    @PostMapping("/addComplainRecord")
    public ResponseVO addChatHistory(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(complainVO.getInitiatedBy());
        return complainService.addComplainRecord(complainVO);
    }

    @ApiOperation(value = "changeComplainFlag", notes = SwaggerMessage.Change_Complain_Flag_Message)
    @PostMapping("/changeComplainFlag")
    public ResponseVO changeComplainFlag(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(complainVO.getInitiatedBy());
        return complainService.changeFlag(complainVO);
    }

    @ApiOperation(value = "getComplainRecord", notes = SwaggerMessage.Get_Complain_Record_Message)
    @PostMapping("/getComplainRecord")
    public ResponseVO getComplainRecord(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(complainVO.getInitiatedBy());
        return complainService.getComplainRecord(complainVO);
    }

    @ApiOperation(value = "getComplainList", notes = SwaggerMessage.Get_ComplainList_Message)
    @PostMapping("/getComplainList")
    public ResponseVO societyComplain(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(complainVO.getInitiatedBy());
        return complainService.getSocietyComplain(complainVO);
    }
    @ApiOperation(value = "getComplainDashBoard", notes = SwaggerMessage.Get_ComplainDashBoard_Message)
    @PostMapping("/getComplainDashBoard")
    public ResponseVO getComplainDashBoard(@Valid @RequestBody ComplainVO complainVO) throws PZConstraintViolationException {
        userAuthService.isSociety(complainVO.getInitiatedBy());
        return complainService.societyComplainDashBoard(complainVO);
    }
}
