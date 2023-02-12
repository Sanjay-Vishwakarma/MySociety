package com.pz.mysociety.controller.dashboard;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.model.Request.dashboardRequestVO.DashboardMasterVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.UserAuthService;
import com.pz.mysociety.service.dashboardService.DashboardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addFeatureCategory", notes = SwaggerMessage.Add_Feature_Category)
    @PostMapping("/addFeatureCategory")
    public ResponseVO addFeatureCategory(@RequestBody @Valid FeatureCategoryVO featureCategoryVO){
        userAuthService.isSuperAdmin(featureCategoryVO.getInitiatedBy());
        return dashboardService.addFeatureCategory(featureCategoryVO);
    }

    @ApiOperation(value = "getFeatureCategoryList", notes = SwaggerMessage.Get_Feature_Category_List)
    @PostMapping("/getFeatureCategoryList")
    public ResponseVO getFeatureCategoryList(@RequestBody @Valid FeatureCategoryVO featureCategoryVO){
        userAuthService.isSuperAdmin(featureCategoryVO.getInitiatedBy());
        return dashboardService.getFeatureCategoryList(featureCategoryVO);
    }

    @ApiOperation(value = "updateFeatureCategory", notes = SwaggerMessage.Update_Feature_Category)
    @PostMapping("/updateFeatureCategory")
    public ResponseVO updateFeatureCategory(@RequestBody @Valid FeatureCategoryVO featureCategoryVO){
        userAuthService.isSuperAdmin(featureCategoryVO.getInitiatedBy());
        return dashboardService.updateFeatureCategory(featureCategoryVO);
    }

    @ApiOperation(value = "addSubCategory", notes = SwaggerMessage.Add_Sub_Category)
    @PostMapping("/addSubCategory")
    public ResponseVO addSubCategory(@RequestBody @Valid SubCategoryVO subCategoryVO){
        userAuthService.isSuperAdmin(subCategoryVO.getInitiatedBy());
        return dashboardService.addSubCategory(subCategoryVO);
    }

    @ApiOperation(value = "getSubCategoryList", notes = SwaggerMessage.Get_Sub_Category_List)
    @PostMapping("/getSubCategoryList")
    public ResponseVO getSubCategoryList(@RequestBody @Valid SubCategoryVO subCategoryVO){
        userAuthService.isSuperAdmin(subCategoryVO.getInitiatedBy());
        return dashboardService.getSubCategoryList(subCategoryVO);
    }

    @ApiOperation(value = "updateSubCategory", notes = SwaggerMessage.Update_Sub_Category)
    @PostMapping("/updateSubCategory")
    public ResponseVO updateSubCategory(@RequestBody @Valid SubCategoryVO subCategoryVO){
        userAuthService.isSuperAdmin(subCategoryVO.getInitiatedBy());
        return dashboardService.updateSubCategory(subCategoryVO);
    }


    @ApiOperation(value = "addFeatureType", notes = SwaggerMessage.Add_Feature_Type)
    @PostMapping("/addFeatureType")
    public ResponseVO addFeatureType(@RequestBody @Valid FeatureTypeVO featureTypeVO){
        userAuthService.isSuperAdmin(featureTypeVO.getInitiatedBy());
        return dashboardService.addFeatureType(featureTypeVO);
    }

    @ApiOperation(value = "getFeatureTypeList", notes = SwaggerMessage.Get_Feature_Type_List)
    @PostMapping("/getFeatureTypeList")
    public ResponseVO getFeatureTypeList(@RequestBody @Valid FeatureTypeVO featureTypeVO){
        userAuthService.isSuperAdmin(featureTypeVO.getInitiatedBy());
        return dashboardService.getFeatureTypeList(featureTypeVO);
    }

    @ApiOperation(value = "updateFeatureType", notes = SwaggerMessage.Update_Feature_Type)
    @PostMapping("/updateFeatureType")
    public ResponseVO updateFeatureType(@RequestBody @Valid FeatureTypeVO featureTypeVO){
        userAuthService.isSuperAdmin(featureTypeVO.getInitiatedBy());
        return dashboardService.updateFeatureType(featureTypeVO);
    }

    @ApiOperation(value = "addDashboard", notes = SwaggerMessage.Add_Dashboard_Label)
    @PostMapping("/addDashboard")
    public ResponseVO addDashboard(@RequestBody @Valid DashboardMasterVO dashboardMasterVO){
        userAuthService.isSuperAdmin(dashboardMasterVO.getInitiatedBy());
        return dashboardService.addDashboard(dashboardMasterVO);
    }

    @ApiOperation(value = "getDashboard", notes = SwaggerMessage.Get_Dashboard_List)
    @PostMapping("/getDashboard")
    public ResponseVO getDashboard(@RequestBody @Valid DashboardMasterVO dashboardMasterVO){
        userAuthService.isSuperAdmin(dashboardMasterVO.getInitiatedBy());
        return dashboardService.getDashboard(dashboardMasterVO);
    }

    @ApiOperation(value = "getDashboardList", notes = SwaggerMessage.Get_User_Dashboard_List)
    @PostMapping("/getDashboardList")
    public ResponseVO getDashboardList(@RequestBody @Valid DashboardMasterVO dashboardMasterVO){
        userAuthService.isUser(dashboardMasterVO.getInitiatedBy());
        return dashboardService.getDashboardList(dashboardMasterVO);
    }

    @ApiOperation(value = "getDashboardFeatureList", notes = SwaggerMessage.Get_Dashboard_Feature_List)
    @PostMapping("/getDashboardFeatureList")
    public ResponseVO getDashboardFeatureList(@RequestBody @Valid FeatureCategoryVO featureCategoryVO){
        userAuthService.isUser(featureCategoryVO.getInitiatedBy());
        return dashboardService.getDashboardFeatureList(featureCategoryVO);
    }

    @ApiOperation(value = "addDashboardFeatureType", notes = SwaggerMessage.Add_Dashboard_Feature_Type_)
    @PostMapping("/addDashboardFeatureType")
    public ResponseVO addDashboardFeatureType(@RequestBody @Valid FeatureTypeVO featureTypeVO){
        userAuthService.isSuperAdmin(featureTypeVO.getInitiatedBy());
        return dashboardService.addDashboardFeatureType(featureTypeVO);
    }

    @ApiOperation(value = "addCardMaster", notes = SwaggerMessage.Add_card_Master)
    @PostMapping("/addCardMaster")
    public ResponseVO addCardMaster(@RequestBody @Valid CardMasterVO cardMasterVO){
        userAuthService.isSuperAdmin(cardMasterVO.getInitiatedBy());
        return dashboardService.addCardMaster(cardMasterVO);

    }

    @ApiOperation(value = "getCardMaster", notes = SwaggerMessage.Get_card_Master)
    @PostMapping("/getCardMaster")
    public ResponseVO getCardMaster(@RequestBody @Valid CardMasterVO cardMasterVO) {
        userAuthService.isSuperAdmin(cardMasterVO.getInitiatedBy());
        return dashboardService.getCardMaster(cardMasterVO);

    }

}
