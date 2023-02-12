package com.pz.mysociety.controller.household;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.HouseholdService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/household")
@Validated
public class HouseholdController {

    @Autowired
    private UserAuthService userAuthService;


    @Autowired
    private HouseholdService houseHoldService;

    @ApiOperation(value = "addGuest", notes = SwaggerMessage.Add_Guest_Message)
    @PostMapping("/addGuest")
    public ResponseVO addGuest(@RequestBody @Valid GuestVO guestVO) throws PZConstraintViolationException {
        userAuthService.isUser(guestVO.getInitiatedBy());
        return houseHoldService.addGuest(guestVO);
    }

    @ApiOperation(value = "addVehicle", notes = SwaggerMessage.Add_Vehicle_Message)
    @PostMapping("/addVehicle")
    public ResponseVO addVehicle(@RequestBody @Valid VehicleVO vehicleVO) throws PZConstraintViolationException {
        userAuthService.isUser(vehicleVO.getInitiatedBy());
        return houseHoldService.addVehicle(vehicleVO);
    }

    @ApiOperation(value = "updateVehicle", notes = SwaggerMessage.Update_Vehicle_Message)
    @PostMapping("/updateVehicle")
    public ResponseVO updateVehicle(@RequestBody @Valid VehicleVO vehicleVO) throws PZConstraintViolationException {
        userAuthService.isUser(vehicleVO.getInitiatedBy());
        return houseHoldService.updateVehicle(vehicleVO);
    }

    @ApiOperation(value = "addFrequentEntry", notes = SwaggerMessage.Add_Frequent_Entry_Message)
    @PostMapping("/addFrequentEntry")
    public ResponseVO addFrequentEntry(@RequestBody @Valid FrequentEntryVO frequentRequestVO) throws PZConstraintViolationException {
        userAuthService.isUser(frequentRequestVO.getInitiatedBy());
        return houseHoldService.addFrequentEntry(frequentRequestVO);
    }

    @ApiOperation(value = "addFamilyMember", notes = SwaggerMessage.Add_Family_Message)
    @PostMapping("/addFamily")
    public ResponseVO addFamilyMember(@RequestBody @Valid FamilyMemberVO familyMemberVO) throws PZConstraintViolationException {
        userAuthService.isUser(familyMemberVO.getInitiatedBy());
        return houseHoldService.addFamilyMember(familyMemberVO);
    }

    @ApiOperation(value = "updateFamilyMember", notes = SwaggerMessage.Update_Family_Message)
    @PostMapping("/updateFamily")
    public ResponseVO updateFamilyMember(@RequestBody @Valid FamilyMemberVO familyMemberVO) throws PZConstraintViolationException {
        userAuthService.isUser(familyMemberVO.getInitiatedBy());
        return houseHoldService.updateFamilyMember(familyMemberVO);
    }

    @ApiOperation(value = "deleteFamilyMember", notes = SwaggerMessage.Delete_Family_Message)
    @PostMapping("/deleteFamily")
    public ResponseVO deleteFamilyMember(@RequestBody @Valid FamilyMemberVO familyMemberVO) throws PZConstraintViolationException {
        userAuthService.isUser(familyMemberVO.getInitiatedBy());
        return houseHoldService.deleteFamilyMember(familyMemberVO);
    }

    @ApiOperation(value = "linkHelper", notes = SwaggerMessage.Link_Helper_Message)
    @PostMapping("/linkHelper")
    public ResponseVO linkHelper(@RequestBody @Valid HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        userAuthService.isUser(helperMappingVO.getInitiatedBy());
        return houseHoldService.linkHelper(helperMappingVO);
    }

    @ApiOperation(value = "getHelperDetail", notes = SwaggerMessage.Get_Helper_Detail_Message)
    @PostMapping("/getHelperDetail")
    public ResponseVO helperLinked(@Valid @RequestBody HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(helperMappingVO.getInitiatedBy());
        return houseHoldService.getHelperDetail(helperMappingVO);
    }

    @ApiOperation(value = "addReview", notes = SwaggerMessage.Add_Review_Message)
    @PostMapping("/addReview")
    public ResponseVO addReview(@Valid @RequestBody  HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        userAuthService.isUser(helperMappingVO.getInitiatedBy());
        return houseHoldService.addReview(helperMappingVO);
    }

    @ApiOperation(value = "getUserReview", notes = SwaggerMessage.Get_Review_Message)
    @PostMapping("/getUserReview")
    public ResponseVO getReview(@Valid @RequestBody  HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        userAuthService.isUser(helperMappingVO.getInitiatedBy());
        return houseHoldService.getReview(helperMappingVO);
    }

    @ApiOperation(value = "unLinkHelper", notes = SwaggerMessage.UnLink_Helper_Message)
    @PostMapping("/unLinkHelper")
    public ResponseVO unLinkHelper(@Valid @RequestBody  HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        userAuthService.isUser(helperMappingVO.getInitiatedBy());
        return houseHoldService.unLinkHelper(helperMappingVO);
    }

    @ApiOperation(value = "addCompany", notes = SwaggerMessage.Add_Company_Message)
    @PostMapping("/addCompany")
    public ResponseVO addCompany(@Valid @RequestBody CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(companyMasterVO.getInitiatedBy());
        return houseHoldService.addCompany(companyMasterVO);
    }

    @ApiOperation(value = "getCompany", notes = SwaggerMessage.Get_Company_Message)
    @PostMapping("/getCompany")
    public ResponseVO getCompany(@Valid @RequestBody CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        userAuthService.isAllUser(companyMasterVO.getInitiatedBy());
        return houseHoldService.getCompany(companyMasterVO);
    }

    @ApiOperation(value = "updateCompany", notes = SwaggerMessage.Update_Company_Message)
    @PostMapping("/updateCompany")
    public ResponseVO updateCompany(@Valid @RequestBody CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        userAuthService.isSuperAdmin(companyMasterVO.getInitiatedBy());
        return houseHoldService.updateCompany(companyMasterVO);
    }


    @ApiOperation(value = "getHouseHoldDetails", notes = SwaggerMessage.Get_Household_Detail_Message)
    @PostMapping("/getHouseHoldDetails")
    public ResponseVO getHouseHold(@Valid @RequestBody HouseholdVO householdVO) throws PZConstraintViolationException {
        userAuthService.isUser(householdVO.getInitiatedBy());
        return houseHoldService.getHouseHoldType(householdVO);
    }


    @ApiOperation(value = "getActivityLog", notes = SwaggerMessage.Recent_Activity_Message)
    @PostMapping("/getActivityLog")
    public ResponseVO getActivityLog(@Valid @RequestBody ActivityLogVO activityLogVO) throws PZConstraintViolationException {
        userAuthService.isUser(activityLogVO.getInitiatedBy());
        return  houseHoldService.getActivityLog(activityLogVO);
    }

    @ApiOperation(value = "getTopActivityLog", notes = SwaggerMessage.Recent_Activity_Message)
    @PostMapping("/getTopActivityLog")
    public ResponseVO getTopActivityLog(@Valid @RequestBody ActivityLogVO activityLogVO) throws PZConstraintViolationException {
        userAuthService.isUser(activityLogVO.getInitiatedBy());
        return  houseHoldService.getTopActivityLog(activityLogVO);
    }

}
