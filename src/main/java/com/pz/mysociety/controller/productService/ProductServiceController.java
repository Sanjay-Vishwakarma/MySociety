package com.pz.mysociety.controller.productService;

import com.pz.mysociety.common.constant.SwaggerMessage;
import com.pz.mysociety.entity.productServiceEntity.ProductServiceMasterEntity;
import com.pz.mysociety.model.Request.productServiceRequest.*;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.service.UserAuthService;
import com.pz.mysociety.service.productService.ProductMasterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/productService")
public class ProductServiceController {

    @Autowired
    private ProductMasterService productMasterService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "addProductServiceCategory", notes = SwaggerMessage.Add_Product_Category)
    @PostMapping("/addProductServiceCategory")
    public ResponseVO addProductServiceCategory(@Valid @RequestBody ProductServiceCategoryVO productServiceCategoryVO) {
        userAuthService.isSuperAdmin(productServiceCategoryVO.getInitiatedBy());
        return productMasterService.addProductServiceCategory(productServiceCategoryVO);
    }

    @ApiOperation(value = "getProductServiceCategory", notes = SwaggerMessage.Get_Product_Category_List)
    @PostMapping("/getProductServiceCategory")
    public ResponseVO getProductServiceCategory(@Valid @RequestBody ProductServiceCategoryVO productServiceCategoryVO) {
        userAuthService.isSuperAdminOrUser(productServiceCategoryVO.getInitiatedBy());
        return productMasterService.getProductServiceCategory(productServiceCategoryVO);
    }

    @ApiOperation(value = "addProductServiceSubCategory", notes = SwaggerMessage.Add_Product_Sub_Category)
    @PostMapping("/addProductServiceSubCategory")
    public ResponseVO addProductServiceSubCategory(@RequestBody @Valid ProductServiceSubCategoryVO productServiceSubCategoryVO) {
        userAuthService.isSuperAdmin(productServiceSubCategoryVO.getInitiatedBy());
        return productMasterService.addProductServiceSubCategory(productServiceSubCategoryVO);
    }

    @ApiOperation(value = "getProductServiceSubCategory", notes = SwaggerMessage.Get_Product_Sub_Category_List)
    @PostMapping("/getProductServiceSubCategory")
    public ResponseVO getProductServiceSubCategory(@Valid @RequestBody ProductServiceSubCategoryVO productServiceSubCategoryVO) {
        userAuthService.isSuperAdminOrUser(productServiceSubCategoryVO.getInitiatedBy());
        return productMasterService.getProductServiceSubCategory(productServiceSubCategoryVO);
    }

    @ApiOperation(value = "addProduct", notes = SwaggerMessage.Add_Product_Master)
    @PostMapping("/addProduct")
    public ResponseVO addProduct(@RequestBody @Valid ProductServiceMasterVO productServiceMasterVO) {
        userAuthService.isUser(productServiceMasterVO.getInitiatedBy());
        return productMasterService.addProduct(productServiceMasterVO);

    }

    @ApiOperation(value = "getProduct", notes = SwaggerMessage.Get_Product_Master)
    @PostMapping("/getProduct")
    public ResponseVO getProduct(@Valid @RequestBody ProductServiceMasterVO productServiceMasterVO){
        userAuthService.isSuperAdminOrUser(productServiceMasterVO.getInitiatedBy());
        return productMasterService.getProduct(productServiceMasterVO);
    }

    @ApiOperation(value = "addProductImage", notes = SwaggerMessage.Add_Product_Master_Image)
    @PostMapping("/addProductImage")
    public ResponseVO addProductImage(@RequestBody @Valid ProductServiceImageMasterVO productServiceImageMasterVO){
        userAuthService.isUser(productServiceImageMasterVO.getInitiatedBy());
        return productMasterService.addProductImage(productServiceImageMasterVO);
    }

    @ApiOperation(value = "deleteProductImage", notes = SwaggerMessage.Delete_Product_Master_Image)
    @PostMapping("/deleteProductImage")
    public ResponseVO deleteProductImage(@RequestBody @Valid ProductServiceImageMasterVO productServiceImageMasterVO){
        userAuthService.isUser(productServiceImageMasterVO.getInitiatedBy());
        return productMasterService.deleteProductImage(productServiceImageMasterVO);
    }

    @ApiOperation(value = "getNearByProduct", notes = SwaggerMessage.Get_Near_By_Product_Master)
    @PostMapping("/getNearByProduct")
    public ResponseVO getNearByProduct(@RequestBody @Valid ProductServiceMasterVO productServiceMasterVO){
        userAuthService.isUser(productServiceMasterVO.getInitiatedBy());
        return productMasterService.getNearByProduct(productServiceMasterVO);
    }


    @ApiOperation(value = "addService", notes = SwaggerMessage.Add_Service_Master)
    @PostMapping("/addService")
    public ResponseVO addService(@RequestBody @Valid UserServiceMasterVO serviceMasterVO) {
        userAuthService.isUser(serviceMasterVO.getInitiatedBy());
        return productMasterService.addService(serviceMasterVO);
    }

    @ApiOperation(value = "getService", notes = SwaggerMessage.Get_Service_Master)
    @PostMapping("/getService")
    public ResponseVO getService(@Valid @RequestBody UserServiceMasterVO serviceMasterVO){
        userAuthService.isSuperAdminOrUser(serviceMasterVO.getInitiatedBy());
        return productMasterService.getService(serviceMasterVO);
    }



}

