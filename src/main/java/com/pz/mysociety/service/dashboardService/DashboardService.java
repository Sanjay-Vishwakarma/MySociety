package com.pz.mysociety.service.dashboardService;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.dashboardEntity.*;
import com.pz.mysociety.model.Request.dashboardRequestVO.DashboardMasterVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.SubCategoryVO;
import com.pz.mysociety.model.Request.dashboardRequestVO.CardMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.UserDashboardVO;
import com.pz.mysociety.repository.dashboardRepository.*;
import com.pz.mysociety.validation.DashboardInputValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DashboardService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private FeatureCategoryRepository featureCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private FeatureTypeRepository featureTypeRepository;

    @Autowired
    private DashboardMasterRepository dashboardMasterRepository;

    @Autowired
    private DashboardValidationService dashboardValidationService;

    @Autowired
    private DashboardInputValidator dashboardInputValidator;

    @Autowired
    private CardMasterRepository cardMasterRepository;

    public ResponseVO addFeatureCategory(FeatureCategoryVO featureCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.addFeatureCategoryInputValidation(featureCategoryVO);

        FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.findByName(featureCategoryVO.getName());

        if(featureCategoryEntity != null){
            responseVO.setFailResponse(Messages.Feature_Category_Already_Exist);
        }else {
            FeatureCategoryEntity featureCategory = modelMapper.map(featureCategoryVO, FeatureCategoryEntity.class);
            featureCategory.setIsActive(true);
            featureCategoryRepository.save(featureCategory);

            responseVO.setSuccessResponse(Messages.Feature_Category_Added_Success);
        }

        return responseVO;
    }

    public ResponseVO getFeatureCategoryList(FeatureCategoryVO featureCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        FeatureCategoryEntity featureCategoryEntity = new FeatureCategoryEntity();

        Pageable pageable = Functions.getPage(featureCategoryVO.getPage(),featureCategoryVO.getLimit());

        Specification<FeatureCategoryEntity> specification = Specification
                .where(!Functions.nonNullString(featureCategoryVO.getName()) ? null : SpecificationService.likeSpecification(featureCategoryEntity, EntityVariable.NAME, featureCategoryVO.getName()))
                .and(!Functions.nonNullString(featureCategoryVO.getRedirect()) ? null : SpecificationService.likeSpecification(featureCategoryEntity, EntityVariable.REDIRECT, featureCategoryVO.getRedirect()))
                .and(Functions.nonNullString(featureCategoryVO.getAction()) && featureCategoryVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(featureCategoryEntity, EntityVariable.IS_ACTIVE, featureCategoryVO.getIsActive()) : null);

        Page<FeatureCategoryEntity> featureCategoryEntities = featureCategoryRepository.findAll(specification, pageable);



//        List<FeatureCategoryVO> activeFeature = featureCategoryRepository.getFeatureCategory(pageable);

        if(featureCategoryEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {

            List<FeatureCategoryVO> activeFeature = modelMapperUtil.mapPage(featureCategoryEntities, FeatureCategoryVO.class);

            int count = (int)featureCategoryRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setFeatureCategory(activeFeature);
            responseVO.setSuccessResponse(Messages.Feature_Category_List_Success);
        }
        return responseVO;
    }

    public ResponseVO updateFeatureCategory(FeatureCategoryVO featureCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.updateFeatureCategoryInputValidation(featureCategoryVO);

        FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(featureCategoryVO.getId());

        if(featureCategoryEntity.getName().equals(featureCategoryVO.getName()) && featureCategoryEntity.getRedirect().equals(featureCategoryVO.getRedirect()) &&  featureCategoryEntity.getIsActive() == featureCategoryVO.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }else {

            FeatureCategoryEntity featureCategory = featureCategoryRepository.findByName(featureCategoryVO.getName());

            if(featureCategory != null && featureCategory.getId() != featureCategoryVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                featureCategoryEntity.setName(featureCategoryVO.getName());
                featureCategoryEntity.setRedirect(featureCategoryVO.getRedirect());
                featureCategoryEntity.setIsActive(featureCategoryVO.getIsActive());
                featureCategoryRepository.save(featureCategoryEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;
    }

    public ResponseVO addSubCategory(SubCategoryVO subCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.addSubCategoryInputValidation(subCategoryVO);

        SubCategoryEntity subCategoryEntity = subCategoryRepository.findByName(subCategoryVO.getName());

        if(subCategoryEntity != null){
            responseVO.setFailResponse(Messages.Sub_Category_Already_Exist);
        }else {
            SubCategoryEntity subCategory = modelMapper.map(subCategoryVO, SubCategoryEntity.class);
            subCategory.setIsActive(true);
            subCategoryRepository.save(subCategory);

            responseVO.setSuccessResponse(Messages.Sub_Category_Added_Success);
        }

        return responseVO;
    }

    public ResponseVO getSubCategoryList(SubCategoryVO subCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        SubCategoryEntity subCategoryEntity = new SubCategoryEntity();
        List<SubCategoryVO> subCategoryVOs = new ArrayList<>();

        Pageable pageable = Functions.getPage(subCategoryVO.getPage(),subCategoryVO.getLimit());

        Specification<SubCategoryEntity> specification = Specification
                .where(subCategoryVO.getFeatureId() == 0 ? null : SpecificationService.equalSpecification(subCategoryEntity, EntityVariable.FEATURE_ID, subCategoryVO.getFeatureId()))
                .and(!Functions.nonNullString(subCategoryVO.getName()) ? null : SpecificationService.likeSpecification(subCategoryEntity, EntityVariable.NAME, subCategoryVO.getName()))
                .and(!Functions.nonNullString(subCategoryVO.getRedirect()) ? null : SpecificationService.likeSpecification(subCategoryEntity, EntityVariable.REDIRECT, subCategoryVO.getRedirect()))
                .and(Functions.nonNullString(subCategoryVO.getAction()) && subCategoryVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(subCategoryEntity, EntityVariable.IS_ACTIVE, subCategoryVO.getIsActive()) : null);

        Page<SubCategoryEntity> subCategoryEntities = subCategoryRepository.findAll(specification, pageable);

        if(subCategoryEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {

            subCategoryEntities.forEach(subCategory -> {
                SubCategoryVO subCategoryVO1 = new SubCategoryVO();
                subCategoryVO1 = modelMapper.map(subCategory, SubCategoryVO.class);

                FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(subCategory.getFeatureId());

                subCategoryVO1.setFeatureName(featureCategoryEntity.getName());
                subCategoryVOs.add(subCategoryVO1);
            });

            int count = (int)subCategoryRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setSubCategory(subCategoryVOs);
            responseVO.setSuccessResponse(Messages.Sub_Category_List_Success);

        }

//        if(subCategoryVO.getFeatureId() != 0){
//            List<SubCategoryVO> activeSubCategory = subCategoryRepository.getByFeatureIdAndIsActive(subCategoryVO.getFeatureId(), true);
//
//            if(activeSubCategory.isEmpty()){
//                responseVO.setFailResponse(Messages.Not_Found);
//            }else {
//                responseVO.setSubCategory(activeSubCategory);
//                responseVO.setSuccessResponse(Messages.Sub_Category_List_Success);
//            }
//        }else {
//
//            List<SubCategoryVO> subCategoryVOs = subCategoryRepository.getAllSubCategory(pageable);
//
//            if (subCategoryVOs.isEmpty()) {
//                responseVO.setFailResponse(Messages.Not_Found);
//            } else {
//                int count = (int)subCategoryRepository.count();
//                int pages = Functions.getPagesCount(count);
//
//                responseVO.setCount(count);
//                responseVO.setPages(pages);
//                responseVO.setSubCategory(subCategoryVOs);
//                responseVO.setSuccessResponse(Messages.Sub_Category_List_Success);
//            }
//        }
        return responseVO;

    }

    public ResponseVO updateSubCategory(SubCategoryVO subCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.updateSubCategoryInputValidation(subCategoryVO);

        SubCategoryEntity subCategoryEntity = subCategoryRepository.getOne(subCategoryVO.getId());

        if(subCategoryEntity.getName().equals(subCategoryVO.getName()) && subCategoryEntity.getRedirect().equals(subCategoryVO.getRedirect())
                && subCategoryEntity.getFeatureId() == subCategoryVO.getFeatureId() &&  subCategoryEntity.getIsActive() == subCategoryVO.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }else {

            SubCategoryEntity subCategory = subCategoryRepository.findByName(subCategoryVO.getName());

            if(subCategory != null && subCategory.getId() != subCategoryVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                subCategoryEntity.setName(subCategoryVO.getName());
                subCategoryEntity.setFeatureId(subCategoryVO.getFeatureId());
                subCategoryEntity.setRedirect(subCategoryVO.getRedirect());
                subCategoryEntity.setIsActive(subCategoryVO.getIsActive());
                subCategoryRepository.save(subCategoryEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;
    }

    public ResponseVO addFeatureType(FeatureTypeVO featureTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        String fileName="";
        dashboardInputValidator.addFeatureTypeCategoryInputValidation(featureTypeVO);

        FeatureTypeEntity featureTypeEntity = featureTypeRepository.findByName(featureTypeVO.getName());

        if(featureTypeEntity != null){
            responseVO.setFailResponse(Messages.Feature_Type_Already_Exist);
        }else {
            FeatureTypeEntity featureType = modelMapper.map(featureTypeVO, FeatureTypeEntity.class);
            featureType.setIsActive(true);
            if(Functions.nonNullString(featureTypeVO.getIconUrl())){

                fileName = featureType.getName()+ ".png";
                FileHandlingUtil.fileUpload(featureTypeVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_FEATURE_TYPE,null);
            }
            featureType.setIconUrl(Types.FEATURE_TYPE_IMAGES+fileName);
            featureTypeRepository.save(featureType);

            responseVO.setSuccessResponse(Messages.Feature_Type_Added_Success);
        }

        return responseVO;

    }

    public ResponseVO getFeatureTypeList(FeatureTypeVO featureTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        FeatureTypeEntity featureTypeEntity = new FeatureTypeEntity();
        List<FeatureTypeVO> activeFeatureType = new ArrayList<>();

        Pageable pageable = Functions.getPage(featureTypeVO.getPage(),featureTypeVO.getLimit());

        Specification<FeatureTypeEntity> specification = Specification
                .where(featureTypeVO.getFeatureId() == 0 ? null : SpecificationService.equalSpecification(featureTypeEntity, EntityVariable.FEATURE_ID, featureTypeVO.getFeatureId()))
                .and(featureTypeVO.getSubCategoryId() == 0 ? null : SpecificationService.equalSpecification(featureTypeEntity, EntityVariable.SUB_CATEGORY_ID, featureTypeVO.getSubCategoryId()))
                .and(!Functions.nonNullString(featureTypeVO.getName()) ? null : SpecificationService.likeSpecification(featureTypeEntity, EntityVariable.NAME, featureTypeVO.getName()))
                .and(!Functions.nonNullString(featureTypeVO.getRedirect()) ? null : SpecificationService.likeSpecification(featureTypeEntity, EntityVariable.REDIRECT, featureTypeVO.getRedirect()))
                .and(Functions.nonNullString(featureTypeVO.getAction()) && featureTypeVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(featureTypeEntity, EntityVariable.IS_ACTIVE, featureTypeVO.getIsActive()) : null);

        Page<FeatureTypeEntity> activeFeatureTypeEntity = featureTypeRepository.findAll(specification, pageable);


//        List<FeatureTypeVO> activeFeatureType = featureTypeRepository.getAllFeatureType(pageable);

        if(activeFeatureTypeEntity.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {

            activeFeatureTypeEntity.forEach(featureType -> {
                FeatureTypeVO featureTypeVO1 = new FeatureTypeVO();

                featureTypeVO1 = modelMapper.map(featureType, FeatureTypeVO.class);

                if(featureType.getFeatureId() != 0){
                    FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(featureType.getFeatureId());
                    featureTypeVO1.setFeatureName(featureCategoryEntity.getName());
                }

                if(featureType.getSubCategoryId() != 0){
                    SubCategoryEntity subCategoryEntity = subCategoryRepository.getOne(featureType.getSubCategoryId());
                    featureTypeVO1.setSubCategoryName(subCategoryEntity.getName());
                }

                activeFeatureType.add(featureTypeVO1);
            });

            int count = (int)featureTypeRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setFeatureType(activeFeatureType);
            responseVO.setSuccessResponse(Messages.Feature_Type_List_Success);
        }
        return responseVO;

    }

    public ResponseVO updateFeatureType(FeatureTypeVO featureTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.updateFeatureTypeCategoryInputValidation(featureTypeVO);

        FeatureTypeEntity featureTypeEntity = featureTypeRepository.getOne(featureTypeVO.getId());
      boolean isChange=false;

//        if(featureTypeEntity.getName().equals(featureTypeVO.getName())&& featureTypeEntity.getFeatureId() == featureTypeVO.getFeatureId() && featureTypeEntity.  featureTypeEntity.getIsActive() == featureTypeVO.getIsActive()){
//            responseVO.setFailResponse(Messages.No_Changes_Found);
//        }else {

            FeatureTypeEntity featureType = featureTypeRepository.findByName(featureTypeVO.getName());

            if(featureType != null && featureType.getId() != featureType.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                if(Functions.nonNullString(featureTypeVO.getIconUrl())){
                String fileName = featureTypeVO.getName()+"_"+Functions.getRandomValue()+ ".png";
                FileHandlingUtil.fileUpload(featureTypeVO.getIconUrl(), fileName, DocumentPath.UPLOAD_DIR_PATH_FEATURE_TYPE,featureTypeEntity.getIconUrl());
                featureTypeEntity.setIconUrl(Types.FEATURE_TYPE_IMAGES+fileName);
                    isChange=true;
                }

                if(Functions.compareValue(featureTypeEntity.getName(),featureTypeVO.getName())){
                    featureTypeEntity.setName(featureTypeVO.getName());
                    isChange=true;

                }
                if(featureTypeEntity.getFeatureId()!=featureTypeVO.getFeatureId()){
                    featureTypeEntity.setFeatureId(featureTypeVO.getFeatureId());
                    isChange=true;
                }
                if(featureTypeEntity.getSubCategoryId()!=featureTypeVO.getSubCategoryId()){
                    featureTypeEntity.setSubCategoryId(featureTypeVO.getSubCategoryId());
                    isChange=true;
                }
                if(Functions.compareValue(featureTypeEntity.getDescription(),featureTypeVO.getDescription())){
                    featureTypeEntity.setDescription(featureTypeVO.getDescription());
                    isChange=true;
                }
                if(Functions.compareValue(featureTypeEntity.getRedirect(),featureTypeVO.getRedirect())){
                    featureTypeEntity.setRedirect(featureTypeVO.getRedirect());
                    isChange=true;
                }
                if(featureTypeEntity.getIsFavorite()!=featureTypeVO.getIsFavorite()){
                    featureTypeEntity.setIsFavorite(featureTypeVO.getIsFavorite());
                     isChange=true;
                }
                if(featureTypeEntity.getIsNew()!= featureTypeVO.getIsNew()){
                    featureTypeEntity.setIsNew(featureTypeVO.getIsNew());
                    isChange=true;
                }
                if(featureTypeEntity.getIsActive()!=featureTypeVO.getIsActive()){
                    featureTypeEntity.setIsActive(featureTypeVO.getIsActive());
                    isChange=true;
                }
//               featureTypeEntity.setName(featureTypeVO.getName());
//                featureTypeEntity.setFeatureId(featureTypeVO.getFeatureId());
//               featureTypeEntity.setSubCategoryId(featureTypeVO.getSubCategoryId());
//                featureTypeEntity.setDescription(featureTypeVO.getDescription());
//               featureTypeEntity.setRedirect(featureTypeVO.getRedirect());
//               featureTypeEntity.setIsFavorite(featureTypeVO.getIsFavorite());
//                featureTypeEntity.setIsNew(featureTypeVO.getIsNew());
//               featureTypeEntity.setIsActive(featureTypeVO.getIsActive());
                if(isChange) {
                    featureTypeRepository.save(featureTypeEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }
                else{
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }
//        }
        return responseVO;
    }

//    public ResponseVO addDashboard(DashboardMasterVO dashboardMasterVO) {
//        ResponseVO responseVO = new ResponseVO();
//        dashboardInputValidator.addDashboardInputValidation(dashboardMasterVO);
//
//        DashboardMasterEntity dashboardMasterEntity = dashboardMasterRepository.findByLabelIdAndLabelType(dashboardMasterVO.getLabelId(), dashboardMasterVO.getLabelType());
//
//        if(dashboardMasterEntity != null){
//            responseVO.setFailResponse(Messages.Already_Exist);
//        }else {
//            DashboardMasterEntity dashboardMaster = modelMapper.map(dashboardMasterVO, DashboardMasterEntity.class);
//            dashboardMaster.setIsActive(true);
//            dashboardMasterRepository.save(dashboardMaster);
//            responseVO.setSuccessResponse(Messages.Dashboard_Label_Added_Success);
//        }
//        return responseVO;
//    }


    public ResponseVO getDashboardList(DashboardMasterVO dashboardMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        List<UserDashboardVO> userDashboard = new ArrayList<>();

        List<DashboardMasterEntity> dashboardMasterEntity = dashboardMasterRepository.findByIsActiveOrderByPriorityOrder(true);

        if(!dashboardMasterEntity.isEmpty()){
            dashboardMasterEntity.forEach(dashboard -> {
                UserDashboardVO userDashboardVO = new UserDashboardVO();

                if(dashboard.getLabelType().equalsIgnoreCase(Types.FEATURE_CATEGORY)){
                    FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(dashboard.getLabelId());

                    List<FeatureTypeVO> featureTypeVO = featureTypeRepository.getByDashboardIdAndIsDashboardAndIsActive(dashboard.getId(), true);

                    userDashboardVO.setLabel(featureCategoryEntity.getName());
                    userDashboardVO.setRedirect(featureCategoryEntity.getRedirect());
                    userDashboardVO.setCardName(dashboard.getCardName());
                    userDashboardVO.setCardSize(dashboard.getCardSize());
                    if(!featureTypeVO.isEmpty()){
                        userDashboardVO.setFeatureType(featureTypeVO);
                    }
                }else {
                    SubCategoryEntity subCategoryEntity = subCategoryRepository.getOne(dashboard.getLabelId());

                    List<FeatureTypeVO> featureTypeVO = featureTypeRepository.getByDashboardIdAndIsDashboardAndIsActive(dashboard.getId(), true);

                    userDashboardVO.setLabel(subCategoryEntity.getName());
                    userDashboardVO.setRedirect(subCategoryEntity.getRedirect());
                    userDashboardVO.setCardName(dashboard.getCardName());
                    userDashboardVO.setCardSize(dashboard.getCardSize());
                    if(!featureTypeVO.isEmpty()){
                        userDashboardVO.setFeatureType(featureTypeVO);
                    }
                }
                userDashboard.add(userDashboardVO);
            });

            responseVO.setUserDashboardList(userDashboard);
            responseVO.setSuccessResponse(Messages.Dashboard_Label_List_Success);
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;

    }

    public ResponseVO addDashboard(DashboardMasterVO dashboardMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        DashboardMasterEntity dashboardMasterEntity;
        dashboardInputValidator.addDashboardInputValidation(dashboardMasterVO);

        if(dashboardMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {


            dashboardMasterEntity = dashboardMasterRepository.getOne(dashboardMasterVO.getId());

            if (dashboardMasterEntity.getLabelId() == dashboardMasterVO.getLabelId() && dashboardMasterEntity.getLabelType().equalsIgnoreCase(dashboardMasterVO.getLabelType()) && dashboardMasterEntity.getCardSize() == dashboardMasterVO.getCardSize()
                    && dashboardMasterEntity.getPriorityOrder() == dashboardMasterVO.getPriorityOrder() && dashboardMasterEntity.getCardName().equalsIgnoreCase(dashboardMasterVO.getCardName()) && dashboardMasterEntity.getIsActive() == dashboardMasterVO.getIsActive()) {
                responseVO.setFailResponse(Messages.No_Changes_Found);
                return responseVO;
            }else {
                DashboardMasterEntity dashboardLabelExist = dashboardMasterRepository.findByLabelIdAndLabelType(dashboardMasterVO.getLabelId(), dashboardMasterVO.getLabelType());

                if(dashboardLabelExist != null && dashboardLabelExist.getId() != dashboardMasterVO.getId()){
                    responseVO.setFailResponse(Messages.Already_Exist);
                    return responseVO;
                }
            }
        }else {

            DashboardMasterEntity dashboardLabelExist = dashboardMasterRepository.findByLabelIdAndLabelType(dashboardMasterVO.getLabelId(), dashboardMasterVO.getLabelType());

            if(dashboardLabelExist != null){
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }

        }

        if(dashboardMasterVO.getAction().equalsIgnoreCase(Status.ADD) || dashboardMasterVO.getIsPriority()){

            int dashboardSize = (int)featureTypeRepository.count();

            int dashboardId = 0;

            for(int i = 0; i <= dashboardSize; i++){


                DashboardMasterEntity dashboardMaster = dashboardMasterRepository.findByPriorityOrder(dashboardMasterVO.getPriorityOrder() + i);

                if(dashboardMaster != null){

                    if(dashboardId!=0){
                        DashboardMasterEntity dashboard = dashboardMasterRepository.getOne(dashboardId);
                        dashboard.setPriorityOrder(dashboardMasterVO.getPriorityOrder() + i);
                        dashboardMasterRepository.save(dashboard);
                    }

                    dashboardId = dashboardMaster.getId();
                }else {

                    if(dashboardId!=0){
                        DashboardMasterEntity dashboard = dashboardMasterRepository.getOne(dashboardId);
                        dashboard.setPriorityOrder(dashboardMasterVO.getPriorityOrder() + i);
                        dashboardMasterRepository.save(dashboard);
                    }

                    if(dashboardMasterVO.getAction().equalsIgnoreCase(Status.ADD)){
                        dashboardMasterEntity = modelMapper.map(dashboardMasterVO, DashboardMasterEntity.class);
                        dashboardMasterEntity.setIsActive(true);
                        dashboardMasterRepository.save(dashboardMasterEntity);
                        responseVO.setSuccessResponse(Messages.Dashboard_Label_Added_Success);
                    }
                    break;
                }

            }

        }

        if(dashboardMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            dashboardMasterEntity = dashboardMasterRepository.getOne(dashboardMasterVO.getId());

            DashboardMasterEntity dashboardLabelExist = dashboardMasterRepository.findByLabelIdAndLabelType(dashboardMasterVO.getLabelId(), dashboardMasterVO.getLabelType());

            if(dashboardLabelExist != null && dashboardLabelExist.getId() != dashboardMasterVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                dashboardMasterEntity.setLabelId(dashboardMasterVO.getLabelId());
                dashboardMasterEntity.setLabelType(dashboardMasterVO.getLabelType());
                dashboardMasterEntity.setPriorityOrder(dashboardMasterVO.getPriorityOrder());
                dashboardMasterEntity.setCardName(dashboardMasterVO.getCardName());
                dashboardMasterEntity.setCardSize(dashboardMasterVO.getCardSize());
                dashboardMasterEntity.setIsActive(dashboardMasterVO.getIsActive());
                dashboardMasterRepository.save(dashboardMasterEntity);
                responseVO.setSuccessResponse(Messages.Dashboard_Update_Success);
            }

        }

        return responseVO;
    }

    public ResponseVO addDashboardFeatureType(FeatureTypeVO featureTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.addDashboardFeatureTypeInputValidation(featureTypeVO);

        FeatureTypeEntity featureTypeEntity = featureTypeRepository.getOne(featureTypeVO.getId());

        if(featureTypeVO.getAction().equalsIgnoreCase(Status.ADD)){

            List<FeatureTypeEntity> featureTypeEntities = featureTypeRepository.findByDashboardId(featureTypeVO.getDashboardId());

            int a = 0;

            for(int i = 0; i <= featureTypeEntities.size(); i++){


                FeatureTypeEntity featureType = featureTypeRepository.findByDashboardIdAndPriorityOrder(featureTypeVO.getDashboardId(), featureTypeVO.getPriorityOrder() + i);

                if(featureType != null){

                    if(a!=0){
                        FeatureTypeEntity feature = featureTypeRepository.getOne(a);
                        feature.setPriorityOrder(featureTypeVO.getPriorityOrder() + i);
                        featureTypeRepository.save(feature);
                    }

                    a = featureType.getId();
//                    int priorityOrder = 0;
//                    featureType.setPriorityOrder(priorityOrder);
//                    featureTypeRepository.save(featureType);
                }else {

                    if(a!=0){
                        FeatureTypeEntity feature = featureTypeRepository.getOne(a);
                        feature.setPriorityOrder(featureTypeVO.getPriorityOrder() + i);
                        featureTypeRepository.save(feature);
                    }

                    featureTypeEntity.setDashboardId(featureTypeVO.getDashboardId());
                    featureTypeEntity.setPriorityOrder(featureTypeVO.getPriorityOrder());
                    responseVO.setSuccessResponse(Messages.Dashboard_Feature_Type_Added);
                    break;
                }

            }

        }else if(featureTypeVO.getAction().equalsIgnoreCase(Status.REMOVE)){
            featureTypeEntity.setDashboardId(0);
            featureTypeEntity.setPriorityOrder(0);
            responseVO.setSuccessResponse(Messages.Dashboard_Feature_Type_Remove);
        }

        featureTypeRepository.save(featureTypeEntity);

        return responseVO;
    }

    public ResponseVO getDashboard(DashboardMasterVO dashboardMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        DashboardMasterEntity dashboardMasterEntity = new DashboardMasterEntity();

        List<UserDashboardVO> userDashboardVO = new ArrayList<>();

        Specification<DashboardMasterEntity> specification = Specification
                .where(dashboardMasterVO.getLabelId() == 0 ? null : SpecificationService.equalSpecification(dashboardMasterEntity, EntityVariable.LABEL_ID, dashboardMasterVO.getLabelId()))
                .and(!Functions.nonNullString(dashboardMasterVO.getLabelType()) ? null : SpecificationService.equalSpecification(dashboardMasterEntity, EntityVariable.LABEL_TYPE, dashboardMasterVO.getLabelType()))
                .and(!Functions.nonNullString(dashboardMasterVO.getCardName()) ? null : SpecificationService.likeSpecification(dashboardMasterEntity, EntityVariable.CARD_NAME, dashboardMasterVO.getCardName()))
                .and(dashboardMasterVO.getCardSize() == 0 ? null : SpecificationService.equalSpecification(dashboardMasterEntity, EntityVariable.CARD_SIZE, dashboardMasterVO.getCardSize()))
                .and(dashboardMasterVO.getPriorityOrder() == 0 ? null : SpecificationService.equalSpecification(dashboardMasterEntity, EntityVariable.PRIORITY_ORDER, dashboardMasterVO.getPriorityOrder()))
                .and(Functions.nonNullString(dashboardMasterVO.getAction()) && dashboardMasterVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(dashboardMasterEntity, EntityVariable.IS_ACTIVE, dashboardMasterVO.getIsActive()) : null);

        List<DashboardMasterEntity> activeFeatureTypeEntity = dashboardMasterRepository.findAll(specification);

        if(activeFeatureTypeEntity.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else {
            activeFeatureTypeEntity.forEach(dashboard -> {
                UserDashboardVO userDashboardVO1 = new UserDashboardVO();

                userDashboardVO1 = modelMapper.map(dashboard, UserDashboardVO.class);

                if(dashboard.getLabelType().equalsIgnoreCase(Types.FEATURE_CATEGORY)){
                    FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(dashboard.getLabelId());
                    userDashboardVO1.setLabel(featureCategoryEntity.getName());
                }else {
                    SubCategoryEntity subCategoryEntity = subCategoryRepository.getOne(dashboard.getLabelId());
                    userDashboardVO1.setLabel(subCategoryEntity.getName());
                }

                List<FeatureTypeVO> featureTypeVO = featureTypeRepository.getByDashboardIdAndIsActive(dashboard.getId(), true);

                if(!featureTypeVO.isEmpty()){
                    userDashboardVO1.setFeatureType(featureTypeVO);
                }

                userDashboardVO.add(userDashboardVO1);

            });

            responseVO.setUserDashboardList(userDashboardVO);
            responseVO.setSuccessResponse(Messages.Dashboard_Label_List_Success);
        }

//
//        List<UserDashboardVO> userDashboard = dashboardMasterRepository.getAllDashboard();
//
//        if(!userDashboard.isEmpty()){
//            userDashboard.forEach(dashboard -> {
//
//                if(dashboard.getLabelType().equalsIgnoreCase(Types.FEATURE_CATEGORY)){
//                    FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.getOne(dashboard.getLabelId());
//
//                    List<FeatureTypeVO> featureTypeVO = featureTypeRepository.getByDashboardIdAndIsActive(dashboard.getId(), true);
//
//                    dashboard.setLabel(featureCategoryEntity.getName());
//                    if(!featureTypeVO.isEmpty()){
//                        dashboard.setFeatureType(featureTypeVO);
//                    }
//                }else {
//                    SubCategoryEntity subCategoryEntity = subCategoryRepository.getOne(dashboard.getLabelId());
//
//                    List<FeatureTypeVO> featureTypeVO = featureTypeRepository.getByDashboardIdAndIsActive(dashboard.getId(), true);
//
//                    dashboard.setLabel(subCategoryEntity.getName());
//                    if(!featureTypeVO.isEmpty()){
//                        dashboard.setFeatureType(featureTypeVO);
//                    }
//                }
//
//                userDashboardVO.add(dashboard);
//            });
//
//            responseVO.setUserDashboardList(userDashboardVO);
//            responseVO.setSuccessResponse(Messages.Dashboard_Label_List_Success);
//        }else {
//            responseVO.setFailResponse(Messages.Not_Found);
//        }

        return responseVO;

    }


    public ResponseVO getDashboardFeatureList(FeatureCategoryVO featureCategoryVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.getDashboardFeatureListInputValidation(featureCategoryVO);

        List<SubCategoryVO> subCategoryVOs = new ArrayList<>();

        FeatureCategoryEntity featureCategoryEntity = featureCategoryRepository.findByNameAndIsActive(featureCategoryVO.getName(), true);

        if (featureCategoryEntity != null){
            List<SubCategoryEntity> subCategoryEntities = subCategoryRepository.findByFeatureIdAndIsActive(featureCategoryEntity.getId(), true);

            if (!subCategoryEntities.isEmpty()){

                subCategoryEntities.forEach(subCategory -> {

                    SubCategoryVO subCategoryVO = new SubCategoryVO();
                    subCategoryVO.setName(subCategory.getName());

                    List<FeatureTypeVO> featureTypeVOs = featureTypeRepository.getBySubCategoryIdAndIsActive(subCategory.getId(), true);

                    if(!featureTypeVOs.isEmpty()){
                        subCategoryVO.setFeatureType(featureTypeVOs);
                    }

                    subCategoryVOs.add(subCategoryVO);
                });

                responseVO.setSubCategory(subCategoryVOs);
                responseVO.setSuccessResponse(Messages.Dashboard_Label_List_Success);
            }
        }else{
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public ResponseVO addCardMaster(CardMasterVO cardMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        dashboardInputValidator.addCardMaster(cardMasterVO);

        if (cardMasterVO.getAction().equalsIgnoreCase(Status.ADD)) {

            CardMasterEntity cardMasterEntity1 = cardMasterRepository.findByCardName(cardMasterVO.getCardName());
            if (cardMasterEntity1 != null) {
                responseVO.setFailResponse(Messages.Already_Exist);
            } else {
                CardMasterEntity cardMasterEntity = modelMapper.map(cardMasterVO, CardMasterEntity.class);
                cardMasterEntity.setIsActive(true);
                cardMasterRepository.save(cardMasterEntity);
                responseVO.setSuccessResponse(Messages.Added_Success);

            }

        } else if (cardMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {

            boolean isChange = false;

            CardMasterEntity cardMasterEntity = cardMasterRepository.getOne(cardMasterVO.getId());
            if (cardMasterEntity != null && cardMasterEntity.getCardName().equalsIgnoreCase(cardMasterVO.getCardName())) {
                responseVO.setFailResponse(Messages.Already_Exist);
            } else {

                if (Functions.nonNullString(cardMasterVO.getCardName()) && !cardMasterEntity.getCardName().equals(cardMasterVO.getCardName())) {
                    cardMasterEntity.setCardName(cardMasterVO.getCardName());
                    isChange = true;
                }

                if (cardMasterEntity.getIsActive() != cardMasterVO.getIsActive()) {
                    cardMasterEntity.setIsActive(cardMasterVO.getIsActive());
                    isChange = true;
                }


                if (isChange) {
                    cardMasterRepository.save(cardMasterEntity);

                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                } else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);

                }
            }

        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);

        }

        return responseVO;
    }


    public ResponseVO getCardMaster(CardMasterVO cardMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        int count = 0;
        Pageable paging = Functions.getPage(cardMasterVO.getPage(), cardMasterVO.getLimit());

        CardMasterEntity cardMasterEntity = new CardMasterEntity();
        Specification<CardMasterEntity> specification = Specification
                .where(!Functions.nonNullString(cardMasterVO.getCardName()) ? null : SpecificationService.likeSpecification(cardMasterEntity, EntityVariable.CARD_MASTER, cardMasterVO.getCardName()))
                .and(Functions.nonNullString(cardMasterVO.getAction()) && cardMasterVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(cardMasterEntity, EntityVariable.IS_AACTIVE, cardMasterVO.getIsActive()) : null);
        Page<CardMasterEntity> cardMasterEntities = cardMasterRepository.findAll(specification, paging);
        count = (int) cardMasterRepository.count(specification);
        if (cardMasterEntities.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {
            List<CardMasterVO> activeCard = modelMapperUtil.mapPage(cardMasterEntities, CardMasterVO.class);
            responseVO.setCardList(activeCard);
            responseVO.setCount(count);
            int pages = Functions.getPagesCount(count);
            responseVO.setPages(pages);
            responseVO.setSuccessResponse(Messages.list_of_cards);

        }
        return responseVO;
    }



}
