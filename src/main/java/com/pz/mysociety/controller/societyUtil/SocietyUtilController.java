package com.pz.mysociety.controller.societyUtil;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.ProfessionUtilMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.UserAuthService;
import com.pz.mysociety.service.societyUtilService.SocietyUtilService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/util")
@Validated
public class SocietyUtilController {

    @Autowired
    private SocietyUtilService societyUtilService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addTypeLanguage", notes = SwaggerMessage.Add_Language)
    @PostMapping("/addTypeLanguage")
    public ResponseVO addTypeLanguage(@Valid @RequestBody LanguageTypeVO languageTypeVO) {
        userAuthService.isSuperAdmin(languageTypeVO.getInitiatedBy());
        return societyUtilService.addTypeLanguage(languageTypeVO);
    }

    @ApiOperation(value = "getTypeLanguage", notes = SwaggerMessage.Get_Language_List)
    @PostMapping("/getTypeLanguage")
    public ResponseVO getTypeLanguage(@Valid @RequestBody LanguageTypeVO languageTypeVO){
        userAuthService.isSuperAdmin(languageTypeVO.getInitiatedBy());
        return societyUtilService.getTypeLanguage(languageTypeVO);
    }



    @ApiOperation(value = "addProfessionMaster", notes = SwaggerMessage.Add_profession_Master)
    @PostMapping("/addProfessionMaster")
    public ResponseVO addProfessionMaster(@RequestBody @Valid ProfessionUtilMasterVO professionUtilMasterVO) {
        userAuthService.isSuperAdmin(professionUtilMasterVO.getInitiatedBy());
        return societyUtilService.addProfessionMaster(professionUtilMasterVO);

    }


    @ApiOperation(value = "getProfessionMaster", notes = SwaggerMessage.Get_profession_Master)
    @PostMapping("/getProfessionMaster")
    public ResponseVO getProfessionMaster(@RequestBody @Valid ProfessionUtilMasterVO professionUtilMasterVO) {
        userAuthService.isAllUser(professionUtilMasterVO.getInitiatedBy());
        return societyUtilService.getProfessionMaster(professionUtilMasterVO);

    }


}





