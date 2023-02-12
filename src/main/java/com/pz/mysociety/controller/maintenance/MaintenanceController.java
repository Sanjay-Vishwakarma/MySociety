package com.pz.mysociety.controller.maintenance;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntFileInvoiceVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.MntMasterVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.UserAuthService;
import com.pz.mysociety.service.mntService.MntMasterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mnt")
public class MaintenanceController {

    @Autowired
    private MntMasterService mntMasterService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addMntType", notes = SwaggerMessage.Add_Mnt_Type)
    @PostMapping("/addMntType")
    public ResponseVO addMntType(@Valid @RequestBody MntMasterVO mntMasterVO) {
        userAuthService.isSuperAdmin(mntMasterVO.getInitiatedBy());
        return mntMasterService.addMntType(mntMasterVO);
    }

    @ApiOperation(value = "getMntType", notes = SwaggerMessage.Get_Mnt_Type_List)
    @PostMapping("/getMntType")
    public ResponseVO getMntType(@Valid @RequestBody MntMasterVO mntMasterVO){
        userAuthService.isAdmin(mntMasterVO.getInitiatedBy());
        return mntMasterService.getMntType(mntMasterVO);
    }

    @ApiOperation(value = "addSocietyMntType", notes = SwaggerMessage.Add_Society_Mnt_Type)
    @PostMapping("/addSocietyMntType")
    public ResponseVO addSocietyMntType(@Valid @RequestBody SocietyMntMappingVO societyMntMappingVO) {
        userAuthService.isSociety(societyMntMappingVO.getInitiatedBy());
        return mntMasterService.addSocietyMntType(societyMntMappingVO);
    }

    @ApiOperation(value = "getSocietyMntType", notes = SwaggerMessage.Get_Society_Mnt_Type_List)
    @PostMapping("/getSocietyMntType")
    public ResponseVO getSocietyMntType(@Valid @RequestBody SocietyMntMappingVO societyMntMappingVO){
        userAuthService.isSociety(societyMntMappingVO.getInitiatedBy());
        return mntMasterService.getSocietyMntType(societyMntMappingVO);
    }

    @ApiOperation(value = "addMntInvoice", notes = SwaggerMessage.Add_Mnt_Invoice)
    @PostMapping("/addMntInvoice")
    public ResponseVO addMntInvoice(@Valid @RequestBody SocietyMntFileInvoiceVO societyInvoiceVO) throws Exception {
        userAuthService.isSociety(societyInvoiceVO.getInitiatedBy());
        return mntMasterService.addMntInvoice(societyInvoiceVO);
    }

    @ApiOperation(value = "getMntInvoice", notes = SwaggerMessage.Get_Mnt_Invoice)
    @PostMapping("/getMntInvoice")
    public ResponseVO getMntInvoice(@Valid @RequestBody SocietyMntFileInvoiceVO societyInvoiceVO){
        userAuthService.isSociety(societyInvoiceVO.getInitiatedBy());
        return mntMasterService.getMntInvoice(societyInvoiceVO);
    }

    @ApiOperation(value = "deleteSocietyMntType",notes= SwaggerMessage.Del_Mnt_Type)
    @PostMapping("/deleteSocietyMntType")
    public ResponseVO deleteSocietyMntType(@RequestBody SocietyMntMappingVO societyMntMappingVO){
        userAuthService.isSociety(societyMntMappingVO.getInitiatedBy());
        return mntMasterService.deleteSocietyMntType(societyMntMappingVO);
    }


    @ApiOperation(value = "addInvoice", notes = SwaggerMessage.Add_Invoice)
    @PostMapping("/addInvoice")
    public ResponseVO addInvoice(@Valid @RequestBody SocietyMntFileInvoiceVO societyInvoiceVO) {
        userAuthService.isSociety(societyInvoiceVO.getInitiatedBy());
        return mntMasterService.addInvoice(societyInvoiceVO);
    }

    @ApiOperation(value = "deleteInvoice", notes = SwaggerMessage.Delete_Invoice)
    @PostMapping("/deleteInvoice")
    public ResponseVO deleteInvoice(@Valid @RequestBody SocietyMntFileInvoiceVO societyInvoiceVO){
        userAuthService.isSociety(societyInvoiceVO.getInitiatedBy());
        return mntMasterService.deleteInvoice(societyInvoiceVO);
    }

    @ApiOperation(value = "generateInvoice", notes = SwaggerMessage.Update_Invoice_Status)
    @PostMapping("/generateInvoice")
    public ResponseVO generateInvoice(@Valid @RequestBody SocietyMntFileInvoiceVO societyInvoiceVO){
        userAuthService.isSociety(societyInvoiceVO.getInitiatedBy());
        return mntMasterService.generateInvoice(societyInvoiceVO);
    }

    @ApiOperation(value = "addSocietyTemplate", notes = SwaggerMessage.Add_Society_Template)
    @PostMapping("/addSocietyTemplate")
    public ResponseVO addSocietyTemplate(@Valid @RequestBody SocietyTemplateVO societyTemplateVO){
        userAuthService.isSociety(societyTemplateVO.getInitiatedBy());
        return mntMasterService.addSocietyTemplate(societyTemplateVO);
    }

    @ApiOperation(value = "getSocietyTemplate", notes = SwaggerMessage.Get_Society_Template)
    @PostMapping("/getSocietyTemplate")
    public ResponseVO getSocietyTemplate(@Valid @RequestBody SocietyTemplateVO societyTemplateVO){
        userAuthService.isSociety(societyTemplateVO.getInitiatedBy());
        return mntMasterService.getSocietyTemplate(societyTemplateVO);
    }

}
