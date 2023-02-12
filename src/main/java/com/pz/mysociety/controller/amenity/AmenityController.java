package com.pz.mysociety.controller.amenity;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.Request.AmenityTypeVO;
import com.pz.mysociety.model.Request.AmenityVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.AmenityMasterService;
import com.pz.mysociety.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/amenity")
public class AmenityController {

    @Autowired
    private AmenityMasterService amenityMasterService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addAmenity", notes = SwaggerMessage.Add_Amenity_Message)
    @PostMapping("/addAmenity")
    public ResponseVO addAmenity(@RequestBody @Valid AmenityVO amenityRequestVO) throws  PZConstraintViolationException{
     userAuthService.isUser(amenityRequestVO.getInitiatedBy());
     return amenityMasterService.addAmenity(amenityRequestVO);
    }

    @ApiOperation(value = "updateAmenityStatus", notes = SwaggerMessage.Update_AmenityStatus_Message)
    @PostMapping("/updateAmenityStatus")
    public ResponseVO amenityStatus(@Valid @RequestBody AmenityVO amenityRequestVO) throws PZConstraintViolationException {
        userAuthService.isSociety(amenityRequestVO.getInitiatedBy());
        return amenityMasterService.changeAmenityStatus(amenityRequestVO);
    }

    @ApiOperation(value = "addAmenityRecord", notes = SwaggerMessage.Add_Amenity_Record_Message)
    @PostMapping("/addAmenityRecord")
    public ResponseVO addChatHistory(@RequestBody @Valid AmenityVO amenityRequestVO) throws PZConstraintViolationException {
       userAuthService.isSocietyOrUser(amenityRequestVO.getInitiatedBy());
       return amenityMasterService.addAmenityRecord(amenityRequestVO);
    }

    @ApiOperation(value = "getAmenityRecord", notes = SwaggerMessage.Get_Amenity_Record_Message)
    @PostMapping("/getAmenityRecord")
    public ResponseVO getAmenityRecord(@RequestBody @Valid AmenityVO amenityRequestVO) throws PZConstraintViolationException {
        userAuthService.isSocietyOrUser(amenityRequestVO.getInitiatedBy());
        return amenityMasterService.getAmenityRecord(amenityRequestVO);
    }

   @ApiOperation(value = "changeAmenityFlag", notes = SwaggerMessage.Change_AmenityFlag_Message)
   @PostMapping("/changeAmenityFlag")
   public ResponseVO changeAmenityFlag(@RequestBody @Valid AmenityVO amenityRequestVO) throws PZConstraintViolationException {
       userAuthService.isSocietyOrUser(amenityRequestVO.getInitiatedBy());
       return amenityMasterService.changeAmenityFlag(amenityRequestVO);
   }

   @ApiOperation(value = "getAmenityList", notes = SwaggerMessage.Get_AmenityList_Message)
   @PostMapping("/getAmenityList")
    public ResponseVO societyAmenity(@RequestBody @Valid AmenityVO amenityRequestVO) throws PZConstraintViolationException, ParseException {
        userAuthService.isSocietyOrUser(amenityRequestVO.getInitiatedBy());
        return amenityMasterService.getAmenity(amenityRequestVO);
   }

    @ApiOperation(value = "addAmenityType", notes = SwaggerMessage.Add_AmenityType_Message)
    @PostMapping("/addAmenityType")
    public ResponseVO insertAmenityType(@RequestBody @Valid AmenityTypeVO amenityTypeRequestVO) throws  PZConstraintViolationException{
        userAuthService.isSociety(amenityTypeRequestVO.getInitiatedBy());
        return amenityMasterService.addAmenityType(amenityTypeRequestVO);
    }
    @ApiOperation(value = "getAmenityType", notes = SwaggerMessage.Get_AmenityType_Message)
    @PostMapping("/getAmenityType")
    public ResponseVO fetchSoietyAmenity(@RequestBody @Valid AmenityTypeVO amenityTypeRequestVO) throws  PZConstraintViolationException{
       userAuthService.isSociety(amenityTypeRequestVO.getInitiatedBy());
        return amenityMasterService.getSocietyAmenityType(amenityTypeRequestVO);
    }

    @ApiOperation(value = "updateAmenityType", notes = SwaggerMessage.Update_AmenityType_Message)
    @PostMapping("/updateAmenityType")
    public ResponseVO changeSocietyAmenity(@RequestBody @Valid AmenityTypeVO amenityTypeRequestVO) throws  PZConstraintViolationException{
        userAuthService.isSociety(amenityTypeRequestVO.getInitiatedBy());
        return amenityMasterService.updateAmenityType(amenityTypeRequestVO);
    }
    @ApiOperation(value = "getAmenityDashBoard", notes = SwaggerMessage.Get_Amenity_DashBoard_Message)
    @PostMapping("/getAmenityDashBoard")
    public ResponseVO getAmenityDashBoard(@RequestBody @Valid AmenityVO amenityVO) throws  PZConstraintViolationException{
        userAuthService.isSociety(amenityVO.getInitiatedBy());
        return amenityMasterService.getAmenityDashBoard(amenityVO);
    }

   /* @ApiOperation(value = "getAmenityTimeLine", notes = SwaggerMessage.Get_Amenity_TimeLine_Message)
    @PostMapping("/getAmenityTimeLine")
    public ResponseVO getAmenityTimeLine(@RequestBody @Valid AmenityVO amenityVO) throws PZConstraintViolationException{
        userAuthService.isSociety(amenityVO.getInitiatedBy());
        return amenityMasterService.getAmenityTimeLine(amenityVO);
    }*/
}
