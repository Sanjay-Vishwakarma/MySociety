package com.pz.mysociety.service.productService;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.productServiceEntity.*;
import com.pz.mysociety.model.Request.productServiceRequest.*;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.repository.productServiceRepository.*;
import com.pz.mysociety.repository.dashboardRepository.CardMasterRepository;
import com.pz.mysociety.service.SocietyService;
import com.pz.mysociety.validation.ProductServiceInputValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductMasterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private ProductServiceInputValidation productInputValidation;

    @Autowired
    private ProductServiceCategoryRepository productCategoryRepository;

    @Autowired
    private ProductServiceSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private ProductServiceMasterRepository productMasterRepository;

    @Autowired
    private CardMasterRepository cardMasterRepository;

    @Autowired
    private ProductServiceImageMasterRepository productImageMasterRepository;

    @Autowired
    private UserServiceMasterRepository serviceMasterRepository;

    @Autowired
    private SocietyService societyService;

    public ResponseVO addProductServiceCategory(ProductServiceCategoryVO productServiceCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceCategoryEntity productServiceCategoryEntity = new ProductServiceCategoryEntity();
        productInputValidation.addProductServiceCategoryInputValidation(productServiceCategoryVO);

        ProductServiceCategoryEntity productCategoryEntity = productCategoryRepository.findByName(productServiceCategoryVO.getName());

        if(productServiceCategoryVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(productCategoryEntity != null){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {
                productServiceCategoryEntity = modelMapper.map(productServiceCategoryVO, ProductServiceCategoryEntity.class);
                productServiceCategoryEntity.setIsActive(true);

                if(Functions.nonNullString(productServiceCategoryVO.getIconUrl())) {

                    String fileName = productServiceCategoryVO.getName() + "_" + Functions.getRandomValue() + ".png";
                    FileHandlingUtil.fileUpload(productServiceCategoryVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_PRODUCT_CATEGORY, null);

                    productServiceCategoryEntity.setIconUrl(Types.PRODUCT_CATEGORY + fileName);
                }
                productCategoryRepository.save(productServiceCategoryEntity);

                responseVO.setSuccessResponse(Messages.Added_Success);

            }
        }else if(productServiceCategoryVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            boolean isChange = false;

            productServiceCategoryEntity = productCategoryRepository.getOne(productServiceCategoryVO.getId());

            if(productCategoryEntity != null && productCategoryEntity.getId() != productServiceCategoryVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                if (Functions.compareValue(productServiceCategoryEntity.getName(),productServiceCategoryVO.getName())) {
                    productServiceCategoryEntity.setName(productServiceCategoryVO.getName());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceCategoryEntity.getType(),productServiceCategoryVO.getType())) {
                    productServiceCategoryEntity.setType(productServiceCategoryVO.getType());
                    isChange = true;
                }
                if (productServiceCategoryEntity.getIsActive() != productServiceCategoryVO.getIsActive()) {
                    productServiceCategoryEntity.setIsActive(productServiceCategoryVO.getIsActive());
                    isChange = true;
                }

                if (Functions.nonNullString(productServiceCategoryVO.getIconUrl())) {
                    String fileName = productServiceCategoryEntity.getName()+ "_" +Functions.getRandomValue()+".png";
                    FileHandlingUtil.fileUpload(productServiceCategoryVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_PRODUCT_CATEGORY, productServiceCategoryEntity.getIconUrl());
                    productServiceCategoryEntity.setIconUrl(Types.PRODUCT_CATEGORY + fileName);
                    isChange = true;
                }

                if (isChange){
                    productCategoryRepository.save(productServiceCategoryEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }

        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }


        return responseVO;
    }

    public ResponseVO getProductServiceCategory(ProductServiceCategoryVO productServiceCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceCategoryEntity productCategoryEntity = new ProductServiceCategoryEntity();

        Pageable pageable = Functions.getPage(productServiceCategoryVO.getPage(), productServiceCategoryVO.getLimit());

        Specification<ProductServiceCategoryEntity> specification = Specification
                .where(!Functions.nonNullString(productServiceCategoryVO.getName()) ? null : SpecificationService.likeSpecification(productCategoryEntity, EntityVariable.NAME,productServiceCategoryVO.getName()))
                .and(!Functions.nonNullString(productServiceCategoryVO.getType()) ? null : SpecificationService.equalSpecification(productCategoryEntity, EntityVariable.TYPE,productServiceCategoryVO.getType()))
                .and(Functions.nonNullString(productServiceCategoryVO.getAction()) && productServiceCategoryVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(productCategoryEntity, EntityVariable.IS_ACTIVE,productServiceCategoryVO.getIsActive()) : null);

        Page<ProductServiceCategoryEntity> productServiceCategoryEntityPage = productCategoryRepository.findAll(specification, pageable);

        if(productServiceCategoryEntityPage.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {
            List<ProductServiceCategoryVO> productCategoryVO = modelMapperUtil.mapPage(productServiceCategoryEntityPage, ProductServiceCategoryVO.class);

            int count = (int) productCategoryRepository.count(specification);
            int page = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(page);
            responseVO.setProductCategoryList(productCategoryVO);
            responseVO.setSuccessResponse(Messages.Get_Product_Category_Success);

        }

        return responseVO;
    }

    public ResponseVO addProductServiceSubCategory(ProductServiceSubCategoryVO productServiceSubCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceSubCategoryEntity productServiceSubCategoryEntity = new ProductServiceSubCategoryEntity();
        productInputValidation.addProductServiceSubCategoryInputValidation(productServiceSubCategoryVO);

        ProductServiceSubCategoryEntity productSubCategoryEntity = productSubCategoryRepository.findByName(productServiceSubCategoryVO.getName());

        if(productServiceSubCategoryVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(productSubCategoryEntity != null){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {
                productServiceSubCategoryEntity = modelMapper.map(productServiceSubCategoryVO, ProductServiceSubCategoryEntity.class);
                productServiceSubCategoryEntity.setIsActive(true);

                String fileName=productServiceSubCategoryVO.getName()+ "_" +Functions.getRandomValue()+".png";
                FileHandlingUtil.fileUpload(productServiceSubCategoryVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_PRODUCT_SUB_CATEGORY,null);

                productServiceSubCategoryEntity.setIconUrl(Types.PRODUCT_SUB_CATEGORY + fileName);
                productSubCategoryRepository.save(productServiceSubCategoryEntity);
                responseVO.setSuccessResponse(Messages.Added_Success);

            }
        }else if(productServiceSubCategoryVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            boolean isChange = false;

            productServiceSubCategoryEntity = productSubCategoryRepository.getOne(productServiceSubCategoryVO.getId());

            if(productSubCategoryEntity != null && productSubCategoryEntity.getId() != productServiceSubCategoryVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                if (productServiceSubCategoryVO.getProductCategoryId() != 0 && productServiceSubCategoryEntity.getProductCategoryId() != productServiceSubCategoryVO.getProductCategoryId()) {
                    productServiceSubCategoryEntity.setProductCategoryId(productServiceSubCategoryVO.getProductCategoryId());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceSubCategoryEntity.getName(), (productServiceSubCategoryVO.getName()))) {
                    productServiceSubCategoryEntity.setName(productServiceSubCategoryVO.getName());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceSubCategoryEntity.getType(), (productServiceSubCategoryVO.getType()))) {
                    productServiceSubCategoryEntity.setType(productServiceSubCategoryVO.getType());
                    isChange = true;
                }
                if (productServiceSubCategoryEntity.getIsActive() != productServiceSubCategoryVO.getIsActive()) {
                    productServiceSubCategoryEntity.setIsActive(productServiceSubCategoryVO.getIsActive());
                    isChange = true;
                }

                if (Functions.nonNullString(productServiceSubCategoryVO.getIconUrl())) {

                    String fileName= productServiceSubCategoryEntity.getName()+ "_" +Functions.getRandomValue()+".png";
                    FileHandlingUtil.fileUpload(productServiceSubCategoryVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_PRODUCT_SUB_CATEGORY,productServiceSubCategoryEntity.getIconUrl());

                    productServiceSubCategoryEntity.setIconUrl(Types.PRODUCT_SUB_CATEGORY +fileName);
                    isChange = true;
                }


                if (isChange){
                    productSubCategoryRepository.save(productServiceSubCategoryEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }

        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }


        return responseVO;

    }

    public ResponseVO getProductServiceSubCategory(ProductServiceSubCategoryVO productServiceSubCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceSubCategoryEntity productServiceSubCategoryEntity = new ProductServiceSubCategoryEntity();

        Pageable pageable = Functions.getPage(productServiceSubCategoryVO.getPage(), productServiceSubCategoryVO.getLimit());

        Specification<ProductServiceSubCategoryEntity> specification = Specification
                .where(!Functions.nonNullString(productServiceSubCategoryVO.getName()) ? null : SpecificationService.likeSpecification(productServiceSubCategoryEntity, EntityVariable.NAME,productServiceSubCategoryVO.getName()))
                .and(productServiceSubCategoryVO.getProductCategoryId() == 0 ? null : SpecificationService.equalSpecification(productServiceSubCategoryEntity, EntityVariable.PRODUCT_CATEGORY_ID,productServiceSubCategoryVO.getProductCategoryId()))
                .and(!Functions.nonNullString(productServiceSubCategoryVO.getType()) ? null : SpecificationService.equalSpecification(productServiceSubCategoryEntity, EntityVariable.TYPE,productServiceSubCategoryVO.getType()))
                .and(Functions.nonNullString(productServiceSubCategoryVO.getAction()) && productServiceSubCategoryVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(productServiceSubCategoryEntity, EntityVariable.TYPE,productServiceSubCategoryVO.getIsActive()) : null);

        Page<ProductServiceSubCategoryEntity> productServiceCategoryEntityPage = productSubCategoryRepository.findAll(specification, pageable);

        if(productServiceCategoryEntityPage.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {
            List<ProductServiceSubCategoryVO> productCategoryVO = modelMapperUtil.mapPage(productServiceCategoryEntityPage, ProductServiceSubCategoryVO.class);

            productCategoryVO.forEach(subCategory -> {
                ProductServiceCategoryEntity productServiceCategoryEntity = productCategoryRepository.getOne(subCategory.getProductCategoryId());
                subCategory.setProductCategory(productServiceCategoryEntity.getName());
            });

            int count = (int) productSubCategoryRepository.count(specification);
            int page = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(page);
            responseVO.setProductSubCategoryList(productCategoryVO);
            responseVO.setSuccessResponse(Messages.Get_Product_Category_Success);

        }

        return responseVO;

    }

    public ResponseVO addProduct(ProductServiceMasterVO productServiceMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceMasterEntity productServiceMasterEntity = new ProductServiceMasterEntity();
        productInputValidation.addProductInputValidation(productServiceMasterVO);

        ProductServiceMasterEntity productServiceMaster = productMasterRepository.findByUnitIdAndTitle(productServiceMasterVO.getUnitId(), productServiceMasterVO.getTitle());

        if(productServiceMasterVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(productServiceMaster != null){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {
                productServiceMasterEntity = modelMapper.map(productServiceMasterVO, ProductServiceMasterEntity.class);
                productServiceMasterEntity.setIsActive(true);
                ProductServiceMasterEntity productService = productMasterRepository.save(productServiceMasterEntity);
                if(productServiceMasterVO.getProductImage().size() > 6){
                    responseVO.setFailResponse(Messages.Image_Limit_Fail);
                }else {
                    productServiceMasterVO.getProductImage().forEach(productImage -> {
                        productImage.setProductId(productService.getId());
                        productImage.setType(Types.PRODUCT);
                        addProductImage(productImage);
                    });
                }

                responseVO.setSuccessResponse(Messages.Added_Success);

            }
        }else if(productServiceMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            boolean isChange = false;

            productServiceMasterEntity = productMasterRepository.getOne(productServiceMasterVO.getId());

            if(productServiceMaster != null && productServiceMaster.getId() != productServiceMasterVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                if (Functions.compareValue(productServiceMasterEntity.getTitle(), productServiceMasterVO.getTitle())) {
                    productServiceMasterEntity.setTitle(productServiceMasterVO.getTitle());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getDescription(), productServiceMasterVO.getDescription())) {
                    productServiceMasterEntity.setDescription(productServiceMasterVO.getDescription());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getPurchaseYear(), productServiceMasterVO.getPurchaseYear())) {
                    productServiceMasterEntity.setPurchaseYear(productServiceMasterVO.getPurchaseYear());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getMobileNumber(), productServiceMasterVO.getMobileNumber())) {
                    productServiceMasterEntity.setMobileNumber(productServiceMasterVO.getMobileNumber());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getAddress(), productServiceMasterVO.getAddress())) {
                    productServiceMasterEntity.setAddress(productServiceMasterVO.getAddress());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getBrand(), productServiceMasterVO.getBrand())) {
                    productServiceMasterEntity.setBrand(productServiceMasterVO.getBrand());
                    isChange = true;
                }

                if (Functions.compareValue(productServiceMasterEntity.getProductCondition(), productServiceMasterVO.getProductCondition())) {
                    productServiceMasterEntity.setProductCondition(productServiceMasterVO.getProductCondition());
                    isChange = true;
                }

//                if (Functions.compareValue(productServiceMasterEntity.getProductUrl(), productServiceMasterVO.getProductUrl())) {
//                    productServiceMasterEntity.setProductUrl(productServiceMasterVO.getProductUrl());
//                    isChange = true;
//                }

                if (productServiceMasterEntity.getIsNegotiable() != productServiceMasterVO.getIsNegotiable()) {
                    productServiceMasterEntity.setIsNegotiable(productServiceMasterVO.getIsNegotiable());
                    isChange = true;
                }

                if (productServiceMasterEntity.getIsCall() != productServiceMasterVO.getIsCall()) {
                    productServiceMasterEntity.setIsCall(productServiceMasterVO.getIsCall());
                    isChange = true;
                }

                if (productServiceMasterEntity.getIsUnitVisible() != productServiceMasterVO.getIsUnitVisible()) {
                    productServiceMasterEntity.setIsUnitVisible(productServiceMasterVO.getIsUnitVisible());
                    isChange = true;
                }

                if (productServiceMasterEntity.getIsActive() != productServiceMasterVO.getIsActive()) {
                    productServiceMasterEntity.setIsActive(productServiceMasterVO.getIsActive());
                    isChange = true;
                }

                if (isChange){
                    productMasterRepository.save(productServiceMasterEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }

        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }


        return responseVO;

    }

    public ResponseVO addProductImage(ProductServiceImageMasterVO productServiceImageMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        productInputValidation.addProductImageInputValidation(productServiceImageMasterVO);

        int imageCount = productImageMasterRepository.countByProductIdAndType(productServiceImageMasterVO.getProductId(), productServiceImageMasterVO.getType());

        if(imageCount >= 6){
            responseVO.setFailResponse(Messages.Image_Limit_Fail);
        }else {
            ProductServiceImageMasterEntity productImageEntity = modelMapper.map(productServiceImageMasterVO, ProductServiceImageMasterEntity.class);

            String fileName= productServiceImageMasterVO.getProductId()+ "_" +Functions.getRandomValue()+"_" + Functions.getRandomValue()+".png";
            FileHandlingUtil.fileUpload(productServiceImageMasterVO.getImageUrl(), fileName, DocumentPath.UPLOAD_DIR_PRODUCT_SERVICE_IMAGES,null);

            productImageEntity.setImageUrl(Types.PRODUCT_IMAGES + fileName);

            productImageMasterRepository.save(productImageEntity);
            responseVO.setSuccessResponse(Messages.Add_Image_Success);
        }

        return responseVO;

    }

    public ResponseVO deleteProductImage(ProductServiceImageMasterVO productServiceImageMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        productInputValidation.deleteProductImageInputValidation(productServiceImageMasterVO);
        productImageMasterRepository.deleteById(productServiceImageMasterVO.getId());
        responseVO.setSuccessResponse(Messages.Delete_Image_Success);
        return responseVO;
    }

    public ResponseVO getProduct(ProductServiceMasterVO productServiceMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        ProductServiceMasterEntity productEntity = new ProductServiceMasterEntity();
//        productInputValidation.getProductInputValidation(productServiceMasterVO);

        Pageable pageable = Functions.getPage(productServiceMasterVO.getPage(), productServiceMasterVO.getLimit());

        Specification<ProductServiceMasterEntity> specification = Specification
                .where(productServiceMasterVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(productEntity, EntityVariable.SOCIETY_ID, productServiceMasterVO.getSocietyId()))
                .and(productServiceMasterVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(productEntity, EntityVariable.UNIT_ID, productServiceMasterVO.getUnitId()))
                .and(productServiceMasterVO.getProductCategoryId() == 0 ? null : SpecificationService.equalSpecification(productEntity, EntityVariable.PRODUCT_CATEGORY_ID, productServiceMasterVO.getProductCategoryId()))
                .and(productServiceMasterVO.getProductSubCategoryId() == 0 ? null : SpecificationService.equalSpecification(productEntity, EntityVariable.PRODUCT_SUB_CATEGORY_ID, productServiceMasterVO.getProductSubCategoryId()))
                .and(Functions.nonNullString(productServiceMasterVO.getAction()) && productServiceMasterVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(productEntity, EntityVariable.IS_ACTIVE,productServiceMasterVO.getIsActive()) : null)
                .and(SpecificationService.descendingOrder(productEntity, EntityVariable.ID));

        Page<ProductServiceMasterEntity> productMasterEntities = productMasterRepository.findAll(specification, pageable);

        if(!productMasterEntities.isEmpty()){
            List<ProductServiceMasterVO> productServiceMasterVOS = new ArrayList<>();

            productMasterEntities.forEach(product -> {
                ProductServiceMasterVO productServiceMaster = modelMapper.map(product, ProductServiceMasterVO.class);
                ProductServiceCategoryEntity productCategory = productCategoryRepository.getOne(product.getProductCategoryId());
                productServiceMaster.setProductCategoryName(productCategory.getName());

                if(product.getProductSubCategoryId() != 0){
                    ProductServiceSubCategoryEntity productSubCategory = productSubCategoryRepository.getOne(product.getProductSubCategoryId());
                    productServiceMaster.setProductSubCategoryName(productSubCategory.getName());
                }

                List<ProductServiceImageMasterVO> productImage = productImageMasterRepository.getByProductId(product.getId(), Types.PRODUCT);
                if(!productImage.isEmpty()){
                    productServiceMaster.setProductImage(productImage);
                }

                productServiceMasterVOS.add(productServiceMaster);
            });

            int count = (int) productMasterRepository.count(specification);

            responseVO.setCount(count);
            responseVO.setProductList(productServiceMasterVOS);
            responseVO.setSuccessResponse(Messages.Get_Product_Category_Success);
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public ResponseVO getNearByProduct(ProductServiceMasterVO productServiceMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        productInputValidation.getNearByProduct(productServiceMasterVO);

        ResponseVO response = societyService.getSocietyByPincode(productServiceMasterVO.getPincode());

        if(response.getSocietyId() != null){

            if(productServiceMasterVO.getType().equalsIgnoreCase(Types.PRODUCT)) {
                List<ProductServiceMasterVO> productServiceMasterVOS = productMasterRepository.getProductBySocietyId(response.getSocietyId(), productServiceMasterVO.getProductCategoryId());

                if (!productServiceMasterVOS.isEmpty()) {

                    productServiceMasterVOS.forEach(product -> {
                        ProductServiceCategoryEntity productCategory = productCategoryRepository.getOne(product.getProductCategoryId());
                        product.setProductCategoryName(productCategory.getName());

                        if (product.getProductSubCategoryId() != 0) {
                            ProductServiceSubCategoryEntity productSubCategory = productSubCategoryRepository.getOne(product.getProductSubCategoryId());
                            product.setProductSubCategoryName(productSubCategory.getName());
                        }


                        List<ProductServiceImageMasterVO> productImage = productImageMasterRepository.getByProductId(product.getId(), Types.PRODUCT);
                        if (!productImage.isEmpty()) {
                            product.setProductImage(productImage);
                        }

                    });

                    int count = productMasterRepository.countBySocietyIdInAndProductCategoryId(response.getSocietyId(), productServiceMasterVO.getProductCategoryId());

                    responseVO.setCount(count);
                    responseVO.setProductList(productServiceMasterVOS);
                    responseVO.setSuccessResponse(Messages.Get_Product_Category_Success);
                } else {
                    responseVO.setFailResponse(Messages.Not_Found);
                }
            }else {
                List<UserServiceMasterVO> userServiceMasterVOS = serviceMasterRepository.getServiceBySocietyId(response.getSocietyId());

                if (!userServiceMasterVOS.isEmpty()) {

                    userServiceMasterVOS.forEach(product -> {
                        ProductServiceCategoryEntity productCategory = productCategoryRepository.getOne(product.getServiceCategoryId());
                        product.setServiceSubCategoryName(productCategory.getName());

                        if (product.getServiceSubCategoryId() != 0) {
                            ProductServiceSubCategoryEntity productSubCategory = productSubCategoryRepository.getOne(product.getServiceSubCategoryId());
                            product.setServiceSubCategoryName(productSubCategory.getName());
                        }


                        List<ProductServiceImageMasterVO> productImage = productImageMasterRepository.getByProductId(product.getId(), Types.SERVICE);
                        if (!productImage.isEmpty()) {
                            product.setProductImage(productImage);
                        }

                    });

                    int count = serviceMasterRepository.countBySocietyIdIn(response.getSocietyId());

                    responseVO.setCount(count);
                    responseVO.setServiceList(userServiceMasterVOS);
                    responseVO.setSuccessResponse(Messages.Get_Service_Success);
                } else {
                    responseVO.setFailResponse(Messages.Not_Found);
                }
            }
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public ResponseVO addService(UserServiceMasterVO serviceMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        UserServiceMasterEntity serviceMasterEntity;
        productInputValidation.addServiceInputValidation(serviceMasterVO);

        UserServiceMasterEntity userServiceMasterEntity = serviceMasterRepository.findByUnitIdAndTitle(serviceMasterVO.getUnitId(), serviceMasterVO.getTitle());

        if(serviceMasterVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(userServiceMasterEntity != null){
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }

            serviceMasterEntity = modelMapper.map(serviceMasterVO, UserServiceMasterEntity.class);
            serviceMasterEntity.setIsActive(true);
            UserServiceMasterEntity serviceMaster = serviceMasterRepository.save(serviceMasterEntity);
            if(serviceMasterVO.getProductImage().size() > 6){
                responseVO.setFailResponse(Messages.Image_Limit_Fail);
            }else {
                serviceMasterVO.getProductImage().forEach(serviceImage -> {
                    serviceImage.setProductId(serviceMaster.getId());
                    serviceImage.setType(Types.SERVICE);
                    this.addProductImage(serviceImage);
                });
            }

            responseVO.setSuccessResponse(Messages.Added_Success);

        }else if(serviceMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            boolean isChange = false;

            serviceMasterEntity = serviceMasterRepository.getOne(serviceMasterVO.getId());

            if(userServiceMasterEntity != null && userServiceMasterEntity.getId() != serviceMasterVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                if (Functions.compareValue(serviceMasterEntity.getTitle(), serviceMasterVO.getTitle())) {
                    serviceMasterEntity.setTitle(serviceMasterVO.getTitle());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getDescription(), serviceMasterVO.getDescription())) {
                    serviceMasterEntity.setDescription(serviceMasterVO.getDescription());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getMobileNumber(), serviceMasterVO.getMobileNumber())) {
                    serviceMasterEntity.setMobileNumber(serviceMasterVO.getMobileNumber());
                    isChange = true;
                }

                if (serviceMasterEntity.getServicePrice() != serviceMasterVO.getServicePrice()) {
                    serviceMasterEntity.setServicePrice(serviceMasterVO.getServicePrice());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceType(), serviceMasterVO.getServiceType())) {
                    serviceMasterEntity.setServiceType(serviceMasterVO.getServiceType());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceDay(), serviceMasterVO.getServiceDay())) {
                    serviceMasterEntity.setServiceDay(serviceMasterVO.getServiceDay());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceProvide(), serviceMasterVO.getServiceProvide())) {
                    serviceMasterEntity.setServiceProvide(serviceMasterVO.getServiceProvide());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceRange(), serviceMasterVO.getServiceRange())) {
                    serviceMasterEntity.setServiceRange(serviceMasterVO.getServiceRange());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceLocation(), serviceMasterVO.getServiceLocation())) {
                    serviceMasterEntity.setServiceLocation(serviceMasterVO.getServiceLocation());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getWhatsappUrl(), serviceMasterVO.getWhatsappUrl())) {
                    serviceMasterEntity.setWhatsappUrl(serviceMasterVO.getWhatsappUrl());
                    isChange = true;
                }

                if (Functions.compareValue(serviceMasterEntity.getServiceUrl(), serviceMasterVO.getServiceUrl())) {
                    serviceMasterEntity.setServiceUrl(serviceMasterVO.getServiceUrl());
                    isChange = true;
                }

                if (serviceMasterEntity.getIsCall() != serviceMasterVO.getIsCall()) {
                    serviceMasterEntity.setIsCall(serviceMasterVO.getIsCall());
                    isChange = true;
                }

                if (serviceMasterEntity.getIsUnitVisible() != serviceMasterVO.getIsUnitVisible()) {
                    serviceMasterEntity.setIsUnitVisible(serviceMasterVO.getIsUnitVisible());
                    isChange = true;
                }

                if (serviceMasterEntity.getIsActive() != serviceMasterVO.getIsActive()) {
                    serviceMasterEntity.setIsActive(serviceMasterVO.getIsActive());
                    isChange = true;
                }

                if (isChange){
                    serviceMasterRepository.save(serviceMasterEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }

        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;

    }

    public ResponseVO getService(UserServiceMasterVO serviceMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        UserServiceMasterEntity serviceEntity = new UserServiceMasterEntity();
//        productInputValidation.getProductInputValidation(productServiceMasterVO);

        Pageable pageable = Functions.getPage(serviceMasterVO.getPage(), serviceMasterVO.getLimit());

        Specification<UserServiceMasterEntity> specification = Specification
                .where(serviceMasterVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(serviceEntity, EntityVariable.SOCIETY_ID, serviceMasterVO.getSocietyId()))
                .and(serviceMasterVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(serviceEntity, EntityVariable.UNIT_ID, serviceMasterVO.getUnitId()))
                .and(serviceMasterVO.getServiceCategoryId() == 0 ? null : SpecificationService.equalSpecification(serviceEntity, EntityVariable.SERVICE_CATEGORY_ID, serviceMasterVO.getServiceCategoryId()))
                .and(serviceMasterVO.getServiceSubCategoryId() == 0 ? null : SpecificationService.equalSpecification(serviceEntity, EntityVariable.SERVICE_SUB_CATEGORY_ID, serviceMasterVO.getServiceSubCategoryId()))
                .and(serviceMasterVO.getIsActive() ? SpecificationService.equalSpecification(serviceEntity, EntityVariable.IS_ACTIVE,serviceMasterVO.getIsActive()) : null)
                .and(SpecificationService.descendingOrder(serviceEntity, EntityVariable.ID));

        Page<UserServiceMasterEntity> serviceMasterEntities = serviceMasterRepository.findAll(specification, pageable);

        if(!serviceMasterEntities.isEmpty()){
            List<UserServiceMasterVO> userServiceMasterVOS = new ArrayList<>();

            serviceMasterEntities.forEach(service -> {
                UserServiceMasterVO userServiceMasterVO = modelMapper.map(service, UserServiceMasterVO.class);
                ProductServiceCategoryEntity productCategory = productCategoryRepository.getOne(service.getServiceCategoryId());
                userServiceMasterVO.setServiceSubCategoryName(productCategory.getName());

                if(service.getServiceSubCategoryId() != 0){
                    ProductServiceSubCategoryEntity productSubCategory = productSubCategoryRepository.getOne(service.getServiceSubCategoryId());
                    userServiceMasterVO.setServiceSubCategoryName(productSubCategory.getName());
                }

                List<ProductServiceImageMasterVO> productImage = productImageMasterRepository.getByProductId(service.getId(), Types.SERVICE);
                if(!productImage.isEmpty()){
                    userServiceMasterVO.setProductImage(productImage);
                }

                userServiceMasterVOS.add(userServiceMasterVO);
            });

            int count = (int) serviceMasterRepository.count(specification);

            responseVO.setCount(count);
            responseVO.setServiceList(userServiceMasterVOS);
            responseVO.setSuccessResponse(Messages.Get_User_Service_Success);
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;

    }


}
