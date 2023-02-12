package com.pz.mysociety.controller.society;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZApplicationException;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.SocietyRequestVO.ParkingSlotVO;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.SocietyService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/society")
@Validated
public class SocietyController {


    @Autowired
    private SocietyService societyService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "allCityList", notes = SwaggerMessage.Get_Type_Message)
    @PostMapping("/allCityList")
    public ResponseVO allCityList(@RequestBody @Valid TypeVO typeVO) throws IOException, ParseException, PZConstraintViolationException {
        userAuthService.isAllUser(typeVO.getInitiatedBy());
        return societyService.getCity(typeVO);
    }

    @ApiOperation(value = "addSociety", notes = SwaggerMessage.Add_Society_Message)
    @PostMapping("/addSociety")
    public ResponseVO addSocietyList(@RequestBody @Valid SocietyVO societyVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(societyVO.getInitiatedBy());
        return societyService.addSociety(societyVO);
    }

    @ApiOperation(value = "updateSociety", notes = SwaggerMessage.Update_Society_Message)
    @PostMapping("/updateSociety")
    public ResponseVO updateSociety(@RequestBody @Valid SocietyVO societyVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyVO.getInitiatedBy());
        return societyService.updateSociety(societyVO);
    }


    @ApiOperation(value = "getSocietyList", notes = SwaggerMessage.Get_Society_Message)
    @PostMapping("/getSocietyList")
    public ResponseVO getSocietyList(@RequestBody @Valid SocietyVO societyVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(societyVO.getInitiatedBy());
        return societyService.getSocietyList(societyVO);
    }

    @ApiOperation(value = "getSocietyAdmin", notes = SwaggerMessage.Get_Society_Admin_Message)
    @PostMapping("/getSocietyAdmin")
    public ResponseVO getSocietyAdmin(@RequestBody @Valid SocietyVO societyVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyVO.getInitiatedBy());
        return societyService.getSocietyAdmin(societyVO);
    }

    @ApiOperation(value = "linkSocietyUser", notes = SwaggerMessage.Link_Society_Message)
    @PostMapping("/linkSocietyUser")
    public ResponseVO linkSocietyUserToSociety(@RequestBody @Valid SocietyMappingVO societyMappingVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(societyMappingVO.getInitiatedBy());
        return societyService.linkSocietyUser(societyMappingVO);
    }

    @ApiOperation(value = "addArea", notes = SwaggerMessage.Add_Area_Message)
    @PostMapping("/addArea")
    public ResponseVO addArea(@RequestBody @Valid AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        userAuthService.isSociety(areaMasterVO.getInitiatedBy());
        return societyService.addArea(areaMasterVO);
    }

    @ApiOperation(value = "getAreaList", notes = SwaggerMessage.Get_Area_List_Message)
    @PostMapping("/getAreaList")
    public ResponseVO  getAreaList(@Valid @RequestBody AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(areaMasterVO.getInitiatedBy());
        return societyService.getAreaList(areaMasterVO);
    }

    @ApiOperation(value = "updateAreaDetail", notes = SwaggerMessage.Update_Area_Message)
    @PostMapping("/updateArea")
    public ResponseVO updateAreaDetail(@Valid @RequestBody AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        userAuthService.isSociety(areaMasterVO.getInitiatedBy());
        return societyService.updateAreaDetail(areaMasterVO);
    }

    @ApiOperation(value = "getAreaSearchList", notes = SwaggerMessage.Get_Area_Search_List_Message)
    @PostMapping("/getAreaSearchList")
    public ResponseVO  getAreaSearchList(@Valid @RequestBody AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(areaMasterVO.getInitiatedBy());
        return societyService.getAreaSearchList(areaMasterVO);
    }

    @ApiOperation(value = "addUnitList", notes = SwaggerMessage.Add_Unit_List_Message)
    @PostMapping("/addUnitList")
    public ResponseVO addUnitList(@Valid @RequestBody List<UnitMasterVO> unitMasterVO) throws PZApplicationException {
        userAuthService.isSociety(unitMasterVO.get(0).getInitiatedBy());
        return societyService.addUnitList(unitMasterVO);
    }

    @ApiOperation(value = "getUnitList", notes = SwaggerMessage.Get_Unit_List_Message)
    @PostMapping("/getUnitList")
    public ResponseVO getUnitList(@Valid @RequestBody UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(unitMasterVO.getInitiatedBy());
        return societyService.getUnitList(unitMasterVO);
    }

    @ApiOperation(value = "updateUnit", notes = SwaggerMessage.Update_Unit_Message)
    @PostMapping("/updateUnit")
    public ResponseVO updateUnitDetail(@Valid @RequestBody UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        userAuthService.isSociety(unitMasterVO.getInitiatedBy());
        return societyService.updateUnitDetail(unitMasterVO);
    }

    @ApiOperation(value = "getSocietyUserList", notes = SwaggerMessage.Get_Society_User_List)
    @PostMapping("/getSocietyUserList")
    public ResponseVO getSocietyUserList(@Valid @RequestBody SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyUserMasterVO.getInitiatedBy());
        return societyService.getSocietyUserList(societyUserMasterVO);
    }

    @ApiOperation(value = "updateSocietyUserStatus", notes = SwaggerMessage.Update_Society_User_Status)
    @PostMapping("/updateSocietyUserStatus")
    public  ResponseVO updateSocietyUserStatus(@Valid @RequestBody SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyUserMasterVO.getInitiatedBy());
        return societyService.updateSocietyUserStatus(societyUserMasterVO);
    }


    @ApiOperation(value = "addUnit", notes = SwaggerMessage.Add_Unit_Message)
    @PostMapping("/addUnit")
    public ResponseVO addUnit(@RequestBody @Valid OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(ownerMasterVO.getInitiatedBy());
        return societyService.addUnit(ownerMasterVO);
    }

    @ApiOperation(value = "getUserStatus", notes = SwaggerMessage.Get_User_Detail_Message)
    @PostMapping("/getUserStatus")
    public ResponseVO getUserStatus(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(ownerMasterVO.getInitiatedBy());
        return societyService.getUserStatus(ownerMasterVO);
    }


    @ApiOperation(value = "dashboardDetails", notes = SwaggerMessage.Dashboard_Message)
    @PostMapping("/dashboardDetails")
    public ResponseVO getDashboard(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(ownerMasterVO.getInitiatedBy());
        return societyService.getUserDashboard(ownerMasterVO);
    }

    @ApiOperation(value = "getOwnerList", notes = SwaggerMessage.Get_Owner_List_Message)
    @PostMapping("/getOwnerList") //getUserList
    public ResponseVO getOwnerList(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(ownerMasterVO.getInitiatedBy());
        return societyService.getOwnerList(ownerMasterVO);
    }


    @ApiOperation(value = "updateOwnerStatus", notes = SwaggerMessage.Update_Owner_Status_Message)
    @PostMapping("/updateOwnerStatus") //updateUserStatus
    public ResponseVO updateOwnerStatus(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isSociety(ownerMasterVO.getInitiatedBy());
        return societyService.updateOwnerStatus(ownerMasterVO);
    }

//    @ApiOperation(value = "applicationMessage", notes = SwaggerMessage.Application_Reject_Message)
//    @PostMapping("/applicationMessage")
//    public ResponseVO applicationMessage(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
//        userAuthService.isSociety(ownerMasterVO.getInitiatedBy());
//        return societyService.applicationMessage(ownerMasterVO);
//    }

    @ApiOperation(value = "getUserDocument", notes = SwaggerMessage.Get_User_Document_Message)
    @PostMapping("/getUserDocument")
    public ResponseVO getUserDocument(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(ownerMasterVO.getInitiatedBy());
        return societyService.getUserDocument(ownerMasterVO);
    }

    @ApiOperation(value = "updateUnitDocument", notes = SwaggerMessage.Update_User_Document_Message)
    @PostMapping("/updateUnitDocument")
    public ResponseVO updateUnitDocument(@Valid @RequestBody UnitDocumentMasterVO documentMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(documentMasterVO.getInitiatedBy());
        return societyService.updateUnitDocument(documentMasterVO);
    }

    @ApiOperation(value = "addEmergencyNumber", notes = SwaggerMessage.Add_Emergency_Number_Message)
    @PostMapping("/addEmergencyNumber")
    public ResponseVO addEmergencyNumber(@Valid @RequestBody EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        userAuthService.isSociety(emergencyNumberVO.getInitiatedBy());
        return societyService.addEmergencyNumber(emergencyNumberVO);
    }

    @ApiOperation(value = "getEmergencyNumber", notes = SwaggerMessage.Get_Emergency_Number_Message)
    @PostMapping("/getEmergencyNumber")
    public ResponseVO getEmergency(@Valid @RequestBody OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(ownerMasterVO.getInitiatedBy());
        return societyService.getEmergencyNumber(ownerMasterVO);
    }

    @ApiOperation(value = "updateEmergencyNumber", notes = SwaggerMessage.Update_Emergency_Number_Message)
    @PostMapping("/updateEmergencyNumber")
    public ResponseVO updateEmergencyNumber(@Valid @RequestBody EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        userAuthService.isSociety(emergencyNumberVO.getInitiatedBy());
        return societyService.updateEmergencyNumber(emergencyNumberVO);
    }

    @ApiOperation(value = "addAreaType", notes = SwaggerMessage.Add_Type_Message)
    @PostMapping("/addAreaType")
    public ResponseVO addAreaType(@Valid @RequestBody TypeVO typeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(typeVO.getInitiatedBy());
        return societyService.addAreaType(typeVO);
    }

    @ApiOperation(value = "getAreaType", notes = SwaggerMessage.Get_Type_Message)
    @PostMapping("/getAreaType")
    public ResponseVO getAreaType(@Valid @RequestBody AreaTypeVO areaTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(areaTypeVO.getInitiatedBy());
        return societyService.getAreaType(areaTypeVO);
    }

    @ApiOperation(value = "updateAreaType", notes = SwaggerMessage.Update_Type_Message)
    @PostMapping("/updateAreaType")
    public ResponseVO updateAreaType(@Valid @RequestBody AreaTypeVO areaTypeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(areaTypeVO.getInitiatedBy());
        return societyService.updateAreaType(areaTypeVO);
    }

    @ApiOperation(value = "addSocietyDocumentType", notes = SwaggerMessage.Add_Society_Document_Type_Message)
    @PostMapping("/addSocietyDocumentType")
    public ResponseVO addSocietyDocumentType(@Valid @RequestBody SocietyDocumentTypeVO societyDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyDocumentTypeVO.getInitiatedBy());
        return societyService.addSocietyDocumentType(societyDocumentTypeVO);
    }

    @ApiOperation(value = "getSocietyDocumentType", notes = SwaggerMessage.Get_Society_Document_Type_Message)
    @PostMapping("/getSocietyDocumentType")
    public ResponseVO getSocietyDocument(@Valid @RequestBody SocietyDocumentTypeVO societyDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isAdmin(societyDocumentTypeVO.getInitiatedBy());
        return societyService.getSocietyDocumentType(societyDocumentTypeVO);
    }

    @ApiOperation(value = "updateSocietyDocumentType", notes = SwaggerMessage.Update_Society_Document_Type_Message)
    @PostMapping("/updateSocietyDocumentType")
    public ResponseVO updateSocietyDocumentType(@Valid @RequestBody SocietyDocumentTypeVO societyDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyDocumentTypeVO.getInitiatedBy());
        return societyService.updateSocietyDocumentType(societyDocumentTypeVO);
    }

    @ApiOperation(value = "addSocietyDocument", notes = SwaggerMessage.Add_Society_Document_Message)
    @PostMapping("/addSocietyDocument")
    public ResponseVO addSocietyDocument(@Valid @RequestBody SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        userAuthService.isSociety(societyDocumentVO.getInitiatedBy());
        return societyService.addSocietyDocument(societyDocumentVO);
    }

    @ApiOperation(value = "getSocietyDocument", notes = SwaggerMessage.Get_Society_Document_Message)
    @PostMapping("/getSocietyDocument")
    public ResponseVO getSocietyDocument(@Valid @RequestBody SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        userAuthService.isSociety(societyDocumentVO.getInitiatedBy());
        return societyService.getSocietyDocument(societyDocumentVO);
    }

    @ApiOperation(value = "updateSocietyDocument", notes = SwaggerMessage.Update_User_Document_Message)
    @PostMapping("/updateSocietyDocument")
    public ResponseVO  updateSocietyDocument(@Valid @RequestBody SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        userAuthService.isSociety(societyDocumentVO.getInitiatedBy());
        return societyService.updateSocietyDocument(societyDocumentVO);
    }

    @ApiOperation(value = "updateSocietyDocumentStatus", notes = SwaggerMessage.Update_Society_Document_Status_Message)
    @PostMapping("/updateSocietyDocumentStatus")
    public ResponseVO  updateSocietyDocumentStatus(@Valid @RequestBody SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(societyDocumentVO.getInitiatedBy());
        return societyService.updateSocietyDocumentStatus(societyDocumentVO);
    }

    @ApiOperation(value = "updateUnitDocumentStatus", notes = SwaggerMessage.Update_Unit_Document_Status_Message)
    @PostMapping("/updateUnitDocumentStatus")
    public ResponseVO  updateUnitDocumentStatus(@Valid @RequestBody UnitDocumentMasterVO unitDocumentMasterVO) throws PZConstraintViolationException {
        userAuthService.isSociety(unitDocumentMasterVO.getInitiatedBy());
        return societyService.updateUnitDocumentStatus(unitDocumentMasterVO);
    }

    @ApiOperation(value = "addUnitDocumentType", notes = SwaggerMessage.Add_Unit_Document_Type_Message)
    @PostMapping("/addUnitDocumentType")
    public ResponseVO addUnitDocumentType(@Valid @RequestBody UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isSociety(unitDocumentTypeVO.getInitiatedBy());
        return societyService.addUnitDocumentType(unitDocumentTypeVO);
    }

    @ApiOperation(value = "getUnitDocumentType", notes = SwaggerMessage.Get_Unit_Document_Type_Message)
    @PostMapping("/getUnitDocumentType")
    public ResponseVO getUnitDocumentType(@Valid @RequestBody UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(unitDocumentTypeVO.getInitiatedBy());
        return societyService.getUnitDocumentType(unitDocumentTypeVO);
    }

    @ApiOperation(value = "updateUnitDocumentType", notes = SwaggerMessage.Update_Unit_Document_Type_Message)
    @PostMapping("/updateUnitDocumentType")
    public ResponseVO changeDocumentMandatory(@Valid @RequestBody UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        userAuthService.isSociety(unitDocumentTypeVO.getInitiatedBy());
        return societyService.updateUnitDocumentType(unitDocumentTypeVO);
    }

    @ApiOperation(value = "allMemberDetails", notes = SwaggerMessage.All_Member_Message)
    @PostMapping("/allMemberDetails")
    public ResponseVO allMemberDetails(@Valid @RequestBody UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(unitMasterVO.getInitiatedBy());
        return societyService.allMemberDetails(unitMasterVO);
    }

    @ApiOperation(value = "getMember", notes = SwaggerMessage.Get_Member_Message)
    @PostMapping("/getMember")
    public ResponseVO getMemberDetail(@Valid @RequestBody UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        userAuthService.isUser(unitMasterVO.getInitiatedBy());
        return societyService.getMemberDetail(unitMasterVO);
    }

    @ApiOperation(value = "getProvider", notes = SwaggerMessage.Get_Provider_Message)
    @PostMapping("/getProvider")
    public ResponseVO getProvider(@Valid @RequestBody MobileProviderVO mobileProviderVO){
        userAuthService.isSuperAdmin(mobileProviderVO.getInitiatedBy());
        return  societyService.getMessageProvider();
    }

    @ApiOperation(value = "changeProvider", notes = SwaggerMessage.Change_Provider_Message)
    @PostMapping("/changeProvider")
    public ResponseVO changeProvider(@Valid @RequestBody MobileProviderVO mobileProviderVO){
        userAuthService.isSuperAdmin(mobileProviderVO.getInitiatedBy());
        return societyService.changeMobileProvider(mobileProviderVO);
    }

    @ApiOperation(value = "updateMemberRole", notes = SwaggerMessage.Update_SocietyMember_Role)
    @PostMapping("/updateMemberRole")
    public ResponseVO updateMemberRole(@Valid @RequestBody OwnerMasterVO ownerMasterVO){
        userAuthService.isSociety(ownerMasterVO.getInitiatedBy());
        return societyService.updateMemberRole(ownerMasterVO);
    }

    @ApiOperation(value = "addDocument", notes = SwaggerMessage.Add_Document_Message)
    @PostMapping("/addDocument")
    public ResponseVO addDocument(@Valid @RequestBody TypeVO typeVO){
        userAuthService.isSuperAdmin(typeVO.getInitiatedBy());
        return societyService.addDocument(typeVO);
    }

    @ApiOperation(value = "getDocumentList", notes = SwaggerMessage.Get_Type_Message)
    @PostMapping("/getDocumentList")
    public ResponseVO getDocumentList(@Valid @RequestBody TypeVO typeVO){
        userAuthService.isAdmin(typeVO.getInitiatedBy());
        return societyService.getDocumentList(typeVO);
    }

    @ApiOperation(value = "updateDocument", notes = SwaggerMessage.Update_Type_Message)
    @PostMapping("/updateDocument")
    public ResponseVO updateDocument(@Valid @RequestBody TypeVO typeVO){
        userAuthService.isSuperAdmin(typeVO.getInitiatedBy());
        return societyService.updateDocument(typeVO);
    }

    @ApiOperation(value = "addSocietyReview", notes = SwaggerMessage.Society_Review)
    @PostMapping("/addSocietyReview")
    public ResponseVO addSocietyReview(@Valid @RequestBody SocietyReviewVO societyReviewVO){
        userAuthService.isUser(societyReviewVO.getInitiatedBy());
        return societyService.addSocietyReview(societyReviewVO);
    }
//
//    @ApiOperation(value = "getSocietyReview", notes = SwaggerMessage.Update_Type_Message)
//    @PostMapping("/getSocietyReview")
//    public ResponseVO getSocietyReview(@Valid @RequestBody TypeVO typeVO){
//        userAuthService.isUser(typeVO.getInitiatedBy());
//        return societyService.getSocietyReview(typeVO);
//    }
   @ApiOperation(value = "addParkingSlot", notes = SwaggerMessage.Parking_Slot)
   @PostMapping("/addParkingSlot")
   public ResponseVO addParkingSlot(@Valid @RequestBody List<ParkingSlotVO> ParkingSlotVO)throws PZApplicationException{
    userAuthService.isSociety(ParkingSlotVO.get(0).getInitiatedBy());
    return societyService.addParkingSlot(ParkingSlotVO);
}
    @ApiOperation(value = "updateParkingSlot", notes = SwaggerMessage.Parking_Slot)
    @PostMapping("/updateParkingSlot")
    public ResponseVO updateParkingSlot(@Valid @RequestBody  ParkingSlotVO parkingSlotVO)throws PZApplicationException{
        userAuthService.isSociety(parkingSlotVO.getInitiatedBy());
        return societyService.updateParkingSlot(parkingSlotVO);
    }

    @ApiOperation(value = "getParkingSlot", notes = SwaggerMessage.Get_Parking_Slot)
    @PostMapping("/getParkingSlot")
    public ResponseVO getParkingSlot(@Valid @RequestBody  ParkingSlotVO parkingSlotVO)throws PZApplicationException{
        userAuthService.isSociety(parkingSlotVO.getInitiatedBy());
        return societyService.getParkingSlot(parkingSlotVO);
    }
}
