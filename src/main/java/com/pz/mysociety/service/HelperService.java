package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.LanguageType;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.helperEntity.*;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.HelperDocumentListVO;
import com.pz.mysociety.model.VO.HelperListVO;
import com.pz.mysociety.model.VO.UnitListVO;
import com.pz.mysociety.repository.helperRepository.*;
import com.pz.mysociety.service.societyUtilService.SocietyUtilService;
import com.pz.mysociety.validation.HelperInputValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class HelperService {

    @Autowired
    private HelperInputValidation helperInputValidation;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HelperRepository helperRepository;
    @Autowired
    private HouseholdService householdService;
    @Autowired
    private HelperTypeRepository helperTypeRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private HelperValidationService helperValidationService;
    @Autowired
    private HelperDocumentTypeRepository helperDocumentTypeRepository;
    @Autowired
    private HelperDocumentMasterRepository helperDocumentMasterRepository;
    @Autowired
    private HelperHistoryLogRepository helperHistoryLogRepository;
    @Autowired
    private HelperAttendaceMasterRepository helperAttendaceMasterRepository;
    @Autowired
    private PushNotificationService pushNotificationService;
    @Autowired
    private SocietyService societyService;
    @Autowired
    private UserService userService;

    @Autowired
    private SocietyUtilService societyUtilService;

    public ResponseVO insertDailyHelp(HelperVO helperRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        if (helperRequestVO.getAction().equalsIgnoreCase(Status.ADD)) {
            helperInputValidation.addHelperInputValidator(helperRequestVO);
            helperValidationService.isSocietyIdExist(helperRequestVO.getSocietyId());
            boolean check = false;
            HelperEntity helperEntity = helperRepository.findBySocietyIdAndHelperMobile(helperRequestVO.getSocietyId(), helperRequestVO.getHelperMobile());
            if (helperEntity != null) {
                responseVO.setFailResponse(Messages.Helper_Number_Exist);
                return responseVO;
            }
            HelperEntity helperEntity1 = modelMapper.map(helperRequestVO, HelperEntity.class);
            HelperTypeEntity helperTypeEntity = helperTypeRepository.findByType(helperRequestVO.getType());
            helperEntity1.setHelperTypeId(helperTypeEntity.getId());
            int pin = generatePin(helperRequestVO.getSocietyId());
            helperEntity1.setPassCode(pin);
            helperEntity1.setIsActive(true);
            helperEntity1.setType(UserRoles.HELPER);
            helperEntity1.setLoginStatus("in");
            if (Functions.nonNullString(helperRequestVO.getPhoto())) {

                String fileName = helperRequestVO.getHelperName() + "_" + helperRequestVO.getHelperMobile() + ".png";
                FileHandlingUtil.fileUpload(helperRequestVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER, null);
                helperEntity1.setPhoto(Types.HELPER_IMAGES + fileName);
            }
            HelperEntity helperEntity2 = helperRepository.save(helperEntity1);
            helperRequestVO.setHelperId(helperEntity2.getId());
            householdService.inserHelpermapping(helperRequestVO);
            // if(helperRequestVO.getInitiatedBy().equalsIgnoreCase(UserRoles.GUARD)) {
            //Helper Document Entry
            if (helperRequestVO.getDocumentList().size() > 0) {
                List<HelperDocumentTypeEntity> helperDetails = helperDocumentTypeRepository.findBySocietyIdAndHelperTypeIdAndIsMandatory(helperRequestVO.getSocietyId(), helperRequestVO.getHelperTypeId(), true);
                if (helperDetails.size() > 0) {
                    for (HelperDocumentTypeEntity helperDocumentTypeEntity : helperDetails) {
                        check = false;
                        for (HelperDocumentVO helperDocumentVO : helperRequestVO.getDocumentList()) {
                            if (helperDocumentTypeEntity.getDocumentTypeId() == helperDocumentVO.getDocumentTypeId() && Functions.nonNullString(helperDocumentVO.getDocument())) {
                                check = true;
                                helperDocumentVO.setHelperId(helperEntity1.getId());
                            }
                        }
                        if (!check) {

                            throw new PZConstraintViolationException(Messages.Proper_Document_Required);
//                            responseVO.setFailResponse(Messages.Proper_Document_Required);
//                            return responseVO;
                        }
                    }
                }


//                List<HelperDocumentMasterEntity> helperDocumentMappingEntities = modelMapperUtil.mapList(helperRequestVO.getDocumentList(), HelperDocumentMasterEntity.class);
                for (HelperDocumentVO helperVO : helperRequestVO.getDocumentList()) {
                    if (Functions.nonNullString(helperVO.getDocument())) {
                        HelperDocumentMasterEntity helperDocumentTypeEntity = modelMapper.map(helperVO, HelperDocumentMasterEntity.class);

                        String fileName = helperEntity2.getId() + "_" + helperDocumentTypeEntity.getDocumentType() + Functions.getRandomValue() + ".png";
                        FileHandlingUtil.fileUpload(helperDocumentTypeEntity.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER, null);
                        helperDocumentTypeEntity.setDocument(Types.HELPER_IMAGES + fileName);
                        helperDocumentTypeEntity.setHelperId(helperEntity2.getId());
                        helperDocumentMasterRepository.save(helperDocumentTypeEntity);
                    }
                }
            }
            // }
            responseVO.setSuccessResponse(Messages.Helper_Added);
            // send sms
            return responseVO;

        }

    if(helperRequestVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
        boolean isChange =false;
        helperInputValidation.addHelperInputValidator(helperRequestVO);
        helperValidationService.isHelperExist(helperRequestVO);

        HelperEntity helperEntity = helperRepository.findById(helperRequestVO.getId());
        if (helperEntity.getHelperName().equalsIgnoreCase(helperRequestVO.getHelperName()) && helperEntity.getHelperMobile().equalsIgnoreCase(helperRequestVO.getHelperMobile()) && helperEntity.getPhoto().equalsIgnoreCase(helperRequestVO.getPhoto()) && helperEntity.getIsActive() == helperRequestVO.getIsActive()) {
            responseVO.setFailResponse(Messages.No_Changes_Detected);
            return responseVO;
        }

        if (Functions.compareValue(helperEntity.getHelperName(), helperRequestVO.getHelperName())) {
            helperEntity.setHelperName(helperRequestVO.getHelperName());
            isChange = true;
        }
        if (Functions.compareValue(helperRequestVO.getHelperMobile(), helperEntity.getHelperMobile())) {
            helperEntity.setHelperMobile(helperRequestVO.getHelperMobile());
            isChange = true;
        }

        if (Functions.nonNullString(helperRequestVO.getPhoto())) {

            String fileName = helperEntity.getHelperName() + "_" + helperEntity.getHelperMobile() + "_" + Functions.getRandomValue() + ".png";
            FileHandlingUtil.fileUpload(helperRequestVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER, helperEntity.getPhoto());
            helperEntity.setPhoto(Types.HELPER_IMAGES + fileName);
            isChange = true;
        }

        if (helperEntity.getIsActive() != helperRequestVO.getIsActive()) {
            helperEntity.setIsActive(helperRequestVO.getIsActive());
            isChange = true;
        }
        if (isChange) {
            helperRepository.save(helperEntity);
            responseVO.setSuccessResponse(Messages.Helper_List_Updated);

        }else {
            responseVO.setFailResponse(Messages.No_Changes_Found);
            return responseVO;
        }
    }
    return responseVO;

}


    public ResponseVO getHelperByType(HelperVO helperVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getHelperTypeInputValidator(helperVO);
        List<HelperListVO> helperList = new ArrayList<>();
        HelperListVO helperVo = new HelperListVO();
        HelperTypeEntity helperTypeEntity=helperTypeRepository.findByType(helperVO.getType());
        helperList = helperRepository.helperDetailsBYType(helperVO.getSocietyId(), helperTypeEntity.getId());

        if (helperList.size() > 0 && helperList != null) {
            responseVO.setSuccessResponse(Messages.List_Of + helperVO.getType() + Messages.Helper);
            responseVO.setHelperDetail(helperList);

        } else {
            responseVO.setFailResponse(Messages.NO + helperVO.getType() + Messages.Found);
            return responseVO;
        }
        return responseVO;
    }

    public ResponseVO getHelper(HelperVO helperRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        HelperEntity helperEntity = new HelperEntity();
        helperInputValidation.getHelperInputValidator(helperRequestVO);

        Pageable paging = Functions.getPage(helperRequestVO.getPage(), helperRequestVO.getLimit());

        Specification<HelperEntity> specification = Specification
                .where(!Functions.nonNullString(helperRequestVO.getHelperName()) ? null : SpecificationService.likeSpecification(helperEntity, EntityVariable.HELPER_NAME, helperRequestVO.getHelperName()))
                .and(helperRequestVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.SOCIETY_ID, helperRequestVO.getSocietyId()))
                .and(helperRequestVO.getHelperTypeId() == 0 ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.HELPER_TYPE_ID, helperRequestVO.getHelperTypeId()))
                .and(Functions.nonNullString(helperRequestVO.getAction()) && helperRequestVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(helperEntity, EntityVariable.IS_ACTIVE, helperRequestVO.getIsActive()) : null)
                .and(!Functions.nonNullString(helperRequestVO.getHelperMobile()) ? null : SpecificationService.likeSpecification(helperEntity, EntityVariable.HELPER_MOBILE, helperRequestVO.getHelperMobile()))
                .and(SpecificationService.equalSpecification(helperEntity, EntityVariable.TYPE, UserRoles.HELPER));


        Page<HelperEntity> helperList1 = helperRepository.findAll(specification, paging);

        int count = (int) helperRepository.count(specification);
        if (helperList1.isEmpty()) {
            responseVO.setFailResponse(Messages.NO_Helper_Found);
            return responseVO;
        }
        List<HelperListVO> activeHelper = modelMapperUtil.mapPage(helperList1, HelperListVO.class);
        if (!activeHelper.isEmpty()) {
            for (HelperListVO helperListVO : activeHelper) {

                HelperTypeEntity helperTypeEntity = helperTypeRepository.findById(helperListVO.getHelperTypeId());
                helperListVO.setHelperType(helperTypeEntity.getType());
                helperListVO.setHelperTypeIcon(helperTypeEntity.getIconImage());

                if (helperTypeEntity.getServiceTypeId() != 0) {
                    ServiceTypeEntity serviceTypeEntity = serviceTypeRepository.getOne(helperTypeEntity.getServiceTypeId());
                    helperListVO.setServiceType(serviceTypeEntity.getType());
                    helperListVO.setServiceTypeIcon(serviceTypeEntity.getIconImage());
                }
            }
        }

        int pages = Functions.getPagesCount(count);

        responseVO.setPages(pages);
        responseVO.setCount(count);
        responseVO.setSuccessResponse(Messages.Helper_Found);
        responseVO.setActiveHelperDetail(activeHelper);
        return responseVO;
    }

    public ResponseVO addDailyHelperType(HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addHelperTypeInputValidator(helperTypeRequestVO);
        HelperTypeEntity HelperTypeEntity = helperTypeRepository.findByType(helperTypeRequestVO.getType());
        if (HelperTypeEntity != null) {
            responseVO.setFailResponse(Messages.HelperType_Exist);
            return responseVO;
        }
        HelperTypeEntity helperTypeEntity = modelMapper.map(helperTypeRequestVO, HelperTypeEntity.class);
        String fileName = helperTypeEntity.getType()+".png";
        FileHandlingUtil.fileUpload(helperTypeEntity.getIconImage(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER_TYPE,null);
        helperTypeEntity.setIconImage(Types.HELPER_TYPE_IMAGES+fileName);
        helperTypeEntity.setIsActive(true);
        helperTypeRepository.save(helperTypeEntity);
        responseVO.setSuccessResponse(Messages.HelperType_Added);
        return responseVO;
    }

    public ResponseVO getDailyHelperType(HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        HelperTypeEntity helperTypeEntity = new HelperTypeEntity();
        Pageable pageable = Functions.getPage(helperTypeRequestVO.getPage(),helperTypeRequestVO.getLimit());

        Specification<HelperTypeEntity> specification = Specification
                .where(helperTypeRequestVO.getId() == 0 ? null : SpecificationService.equalSpecification(helperTypeEntity, EntityVariable.HELPER_TYPE_ID, helperTypeRequestVO.getId()))
                .and(!Functions.nonNullString(helperTypeRequestVO.getType()) ? null : SpecificationService.likeSpecification(helperTypeEntity, EntityVariable.HELPER_TYPE, helperTypeRequestVO.getType()))
                .and(helperTypeRequestVO.getServiceTypeId() == 0 ? null : SpecificationService.equalSpecification(helperTypeEntity, EntityVariable.SERVICE_TYPE_ID, helperTypeRequestVO.getServiceTypeId()))
                .and(Functions.nonNullString(helperTypeRequestVO.getAction()) && helperTypeRequestVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(helperTypeEntity, EntityVariable.IS_ACTIVE, helperTypeRequestVO.getIsActive()) : null);

        Page<HelperTypeEntity> helperTypeEntities = helperTypeRepository.findAll(specification,pageable);

        if(helperTypeEntities.isEmpty()){
            responseVO.setFailResponse(Messages.NO_Helper_Found);
        }else {
            int count = (int) helperTypeRepository.count(specification);

            List<HelperTypeVO> helperTypeVOs = modelMapperUtil.mapPage(helperTypeEntities, HelperTypeVO.class);
            responseVO.setCount(count);
            responseVO.setHelperTypeList(helperTypeVOs);
            responseVO.setSuccessResponse(Messages.Helper_Type_List);
        }

        return responseVO;
    }

    public ResponseVO updateDailyHelperType(HelperTypeVO helperTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.updateHelperTypeInputValidator(helperTypeRequestVO);
        helperValidationService.isHelperTypeIdExist(helperTypeRequestVO);
        HelperTypeEntity dailyHelperTypeEntity = helperTypeRepository.getOne(helperTypeRequestVO.getId());
               boolean isChange=false;
        if (dailyHelperTypeEntity.getType().equals(helperTypeRequestVO.getType()) && dailyHelperTypeEntity.getIsFullTime() == helperTypeRequestVO.getIsFullTime() && dailyHelperTypeEntity.getIsActive() == helperTypeRequestVO.getIsActive()
         && Functions.nonNullString(dailyHelperTypeEntity.getIconImage()) && dailyHelperTypeEntity.getIconImage().equals(helperTypeRequestVO.getIconImage())) {
                responseVO.setFailResponse(Messages.No_Changes_Detected);
        } else {

            if(Functions.nonNullString(helperTypeRequestVO.getIconImage())) {
                String fileName = helperTypeRequestVO.getType()+"_"+Functions.getRandomValue()+".png";
                FileHandlingUtil.fileUpload(helperTypeRequestVO.getIconImage(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER_TYPE,dailyHelperTypeEntity.getIconImage());
                dailyHelperTypeEntity.setIconImage(Types.HELPER_TYPE_IMAGES+fileName);
                isChange=true;

            }
            if(Functions.compareValue(dailyHelperTypeEntity.getType(),helperTypeRequestVO.getType())){
                dailyHelperTypeEntity.setType(helperTypeRequestVO.getType());
                isChange=true;
            }
            if(dailyHelperTypeEntity.getIsActive()!= helperTypeRequestVO.getIsActive()){
                dailyHelperTypeEntity.setIsActive(helperTypeRequestVO.getIsActive());
                isChange=true;
            }
            if(dailyHelperTypeEntity.getIsFullTime()!=helperTypeRequestVO.getIsFullTime()){
                dailyHelperTypeEntity.setIsFullTime(helperTypeRequestVO.getIsFullTime());
                isChange=true;
            }
           // dailyHelperTypeEntity.setType(helperTypeRequestVO.getType());
            //dailyHelperTypeEntity.setIsActive(helperTypeRequestVO.getIsActive());
            //dailyHelperTypeEntity.setIsFullTime(helperTypeRequestVO.getIsFullTime());

            if(isChange){
            helperTypeRepository.save(dailyHelperTypeEntity);
            LanguageTypeVO languageTypeVO = new LanguageTypeVO(dailyHelperTypeEntity.getId(), helperTypeRequestVO.getType());
            societyUtilService.updateLanguage(languageTypeVO);
            responseVO.setSuccessResponse(Messages.HelperType_List_Updated);
                return responseVO;
    }
            else{
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }}
            return responseVO;
        }


    public ResponseVO addServiceType(ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addServiceTypeInputValidation(serviceTypeVO);

         String fileName = serviceTypeVO.getType()+".png";
        FileHandlingUtil.fileUpload(serviceTypeVO.getIconImage(), fileName, DocumentPath.UPLOAD_DIR_PATH_SERVICE_TYPE,null);

        ServiceTypeEntity serviceTypeEntity = serviceTypeRepository.findByType(serviceTypeVO.getType());

        if (serviceTypeEntity == null) {
            ServiceTypeEntity serviceType = modelMapper.map(serviceTypeVO, ServiceTypeEntity.class);
            serviceType.setIsActive(true);
            serviceType.setIconImage(Types.SERVICE_TYPE_IMAGES+fileName);
            serviceTypeRepository.save(serviceType);
            responseVO.setSuccessResponse(Messages.Type_Added_Success);
        } else {
            responseVO.setFailResponse(Messages.Already_Exist);
        }

        return responseVO;
    }

    public ResponseVO getServiceType(ServiceTypeVO serviceTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        List<TypeVO> typeVOs;
        List<ServiceTypeEntity> serviceTypeEntityList;
        int activeCount = 0;
        int inActiveCount = 0;
        Pageable paging = Functions.getPage(serviceTypeVO.getPage(), serviceTypeVO.getLimit());
        Pageable searchPageable = Functions.getPage(0, serviceTypeVO.getLimit());


            if(serviceTypeVO.getType() != null){
                typeVOs = serviceTypeRepository.getSearchServiceType(serviceTypeVO.getType(), true, searchPageable);
            }else {
                typeVOs = serviceTypeRepository.getActiveServiceType(true, searchPageable);
            }

            if (serviceTypeVO.getInitiatedBy().equalsIgnoreCase(UserRoles.SUPER_ADMIN)) {
                List<ServiceTypeEntity> activeServiceTypeEntities = serviceTypeRepository.findByIsActive(true, paging);

                List<ServiceTypeEntity> inActiveServiceTypeEntities = serviceTypeRepository.findByIsActive(false, paging);
                activeCount = serviceTypeRepository.countByIsActive(true);
                inActiveCount = serviceTypeRepository.countByIsActive(false);

                if (activeServiceTypeEntities.isEmpty() && inActiveServiceTypeEntities.isEmpty()) {
                    responseVO.setFailResponse(Messages.Not_Found);
                } else {

                    List<ServiceTypeVO> activeServiceTypeList = modelMapperUtil.mapList(activeServiceTypeEntities, ServiceTypeVO.class);
                    List<ServiceTypeVO> inActiveServiceTypeList = modelMapperUtil.mapList(inActiveServiceTypeEntities, ServiceTypeVO.class);

                    int pages = Functions.getPagesCount(activeCount);
                    int inActivePages = Functions.getPagesCount(inActiveCount);

                    if(!typeVOs.isEmpty()){
                        responseVO.setActiveType(typeVOs);
                    }
                    responseVO.setPages(pages);
                    responseVO.setCount(activeCount);
                    responseVO.setInActiveCount(inActiveCount);
                    responseVO.setInActivePages(inActivePages);
                    responseVO.setActiveServiceType(activeServiceTypeList);
                    responseVO.setInActiveServiceType(inActiveServiceTypeList);
                    responseVO.setSuccessResponse(Messages.Service_Type_List_Success);
                }
                return responseVO;

            } else if (serviceTypeVO.getInitiatedBy().equalsIgnoreCase(UserRoles.SOCIETY)) {
                serviceTypeEntityList = serviceTypeRepository.findByIsSocietyUser(true, paging);
                int count = serviceTypeRepository.countByIsActive(true);
                int pages = Functions.getPagesCount(count);
                List<ServiceTypeVO> serviceTypeVOList = modelMapperUtil.mapList(serviceTypeEntityList, ServiceTypeVO.class);
                if(!typeVOs.isEmpty()){
                    responseVO.setActiveType(typeVOs);
                }
                responseVO.setActiveServiceType(serviceTypeVOList);
                responseVO.setPages(pages);
                responseVO.setSuccessResponse(Messages.Service_Type_List_Success);

            } else {
                responseVO.setFailResponse(Messages.Not_Found);
            }

        return responseVO;
    }

    public ResponseVO updateServiceType(ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.updateServiceTypeInputValidator(serviceTypeVO);
             boolean isChange=false;
        ServiceTypeEntity serviceTypeEntity = serviceTypeRepository.getOne(serviceTypeVO.getId());

            ServiceTypeEntity serviceEntity = serviceTypeRepository.findByType(serviceTypeVO.getType());

            if (serviceEntity != null && serviceEntity.getId() != serviceTypeVO.getId()) {
                responseVO.setFailResponse(Messages.Type_Exist_Error);
            } else {

                if (Functions.nonNullString(serviceTypeVO.getIconImage())) {
                    String fileName = serviceTypeVO.getType() + "_" + Functions.getRandomValue() + ".png";
                    FileHandlingUtil.fileUpload(serviceTypeVO.getIconImage(), fileName, DocumentPath.UPLOAD_DIR_PATH_SERVICE_TYPE, serviceTypeEntity.getIconImage());
                    serviceTypeEntity.setIconImage(Types.SERVICE_TYPE_IMAGES + fileName);
                    isChange = true;

                }
                if (Functions.compareValue(serviceTypeEntity.getType(), serviceTypeVO.getType())) {
                    serviceTypeEntity.setType(serviceTypeVO.getType());
                    isChange = true;
                }
                if (serviceTypeEntity.getIsSocietyUser() != serviceTypeVO.getIsSocietyUser()) {
                    serviceTypeEntity.setIsSocietyUser(serviceTypeVO.getIsSocietyUser());
                    isChange = true;

                }

                if (serviceTypeEntity.getIsUser() != serviceTypeVO.getIsUser()) {
                    serviceTypeEntity.setIsUser(serviceTypeVO.getIsUser());
                    isChange = true;

                }
                if (serviceTypeEntity.getIsGuard() != serviceTypeVO.getIsGuard()) {
                    serviceTypeEntity.setIsGuard(serviceTypeVO.getIsGuard());
                    isChange = true;

                }
                if (serviceTypeEntity.getIsActive() != serviceTypeVO.getIsActive()) {
                    serviceTypeEntity.setIsActive(serviceTypeVO.getIsActive());
                    isChange = true;

                }
                if (serviceTypeEntity.getIsAttendance() != serviceTypeVO.getIsAttendance()) {
                    serviceTypeEntity.setIsAttendance(serviceTypeVO.getIsAttendance());
                    isChange = true;
                }

                if (serviceTypeEntity.getIsVisitor() != serviceTypeVO.getIsVisitor()) {
                    serviceTypeEntity.setIsVisitor(serviceTypeVO.getIsVisitor());
                    isChange = true;

                }
                //serviceTypeEntity.setType(serviceTypeVO.getType());
                //serviceTypeEntity.setIsSocietyUser(serviceTypeVO.getIsSocietyUser());
                // serviceTypeEntity.setIsUser(serviceTypeVO.getIsUser());
                // serviceTypeEntity.setIsGuard(serviceTypeVO.getIsGuard());
                //serviceTypeEntity.setIsActive(serviceTypeVO.getIsActive());
                //serviceTypeEntity.setIsAttendance(serviceTypeVO.getIsAttendance());
                //serviceTypeEntity.setIsVisitor(serviceTypeVO.getIsVisitor());

                if (isChange) {
                    serviceTypeRepository.save(serviceTypeEntity);
                    LanguageTypeVO languageTypeVO = new LanguageTypeVO(serviceTypeEntity.getId(), serviceTypeVO.getType());
                    societyUtilService.updateLanguage(languageTypeVO);
                    responseVO.setSuccessResponse(Messages.Type_Update_Success);
                    return responseVO;
                }
                else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                    return responseVO;
                }
            }
            return responseVO;
        }


    public ResponseVO getHelperDetail(List<HelperListVO> helperListVO) {
        ResponseVO responseVO = new ResponseVO();

        for (HelperListVO helperMappingVO : helperListVO) {
            HelperEntity helperEntity = helperRepository.getOne(helperMappingVO.getHelperId());

            String type = helperTypeRepository.getTypeById(helperEntity.getHelperTypeId());

            if(type != null){
                helperMappingVO.setType(type);
            }

            helperMappingVO.setHelperName(helperEntity.getHelperName());
            helperMappingVO.setHelperMobile(helperEntity.getHelperMobile());
            helperMappingVO.setPhoto(helperEntity.getPhoto());
        }

        responseVO.setHelperDetail(helperListVO);
        return responseVO;
    }

//    public ResponseVO updateHelper(HelperVO helperRequestVO) throws PZConstraintViolationException {
//        ResponseVO responseVO = new ResponseVO();
//
//        if(helperRequestVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
//            boolean isChange =false;
//        helperInputValidation.updateHelperInputValidator(helperRequestVO);
//        helperValidationService.isHelperExist(helperRequestVO);
//
//            HelperEntity helperEntity = helperRepository.findById(helperRequestVO.getId());
//            if (helperEntity.getHelperName().equalsIgnoreCase(helperRequestVO.getHelperName()) && helperEntity.getHelperMobile().equalsIgnoreCase(helperRequestVO.getHelperMobile()) && helperEntity.getPhoto().equalsIgnoreCase(helperRequestVO.getPhoto()) && helperEntity.getIsActive() == helperRequestVO.getIsActive()) {
//                responseVO.setFailResponse(Messages.No_Changes_Detected);
//                return responseVO;
//            }
//
//            if (Functions.compareValue(helperEntity.getHelperName(), helperRequestVO.getHelperName())) {
//                helperEntity.setHelperName(helperRequestVO.getHelperName());
//                isChange = true;
//            }
//            if (Functions.compareValue(helperRequestVO.getHelperMobile(), helperEntity.getHelperMobile())) {
//                helperEntity.setHelperMobile(helperRequestVO.getHelperMobile());
//                isChange = true;
//            }
////            if (Functions.compareValue(helperRequestVO.getPhoto(), helperEntity.getPhoto())) {
////
////                String fileName = helperEntity.getHelperName() + "_" + helperEntity.getHelperMobile() + "_" + Functions.getRandomValue() + ".png";
////                FileHandlingUtil.fileUpload(helperRequestVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER, helperEntity.getPhoto());
////                helperEntity.setPhoto(Types.HELPER_IMAGES + fileName);
////                isChange = true;
////            }
//
//            if (helperEntity.getIsActive() != helperRequestVO.getIsActive()) {
//                helperEntity.setIsActive(helperRequestVO.getIsActive());
//                isChange = true;
//            }
//            if (isChange) {
//                helperRepository.save(helperEntity);
//                responseVO.setSuccessResponse(Messages.Helper_List_Updated);
//
//            }else {
//                responseVO.setFailResponse(Messages.No_Changes_Found);
//                return responseVO;
//            }
//        }
//        return responseVO;
//
//    }

    public ResponseVO getServiceHelperType(ServiceTypeVO serviceTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        List<ServiceTypeEntity> serviceTypeEntityList;
        int count = 0;
        Pageable paging = Functions.getPage(serviceTypeVO.getPage(), serviceTypeVO.getLimit());

        if (serviceTypeVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER)) {
            serviceTypeEntityList = serviceTypeRepository.findByIsUserAndIsActive(true, true, paging);
            count = serviceTypeRepository.countByIsUser(true);

        } else if (serviceTypeVO.getInitiatedBy().equalsIgnoreCase(UserRoles.GUARD)) {
            serviceTypeEntityList = serviceTypeRepository.findByIsGuardAndIsActive(true, true, paging);
            count = serviceTypeRepository.countByIsGuard(true);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        if (!serviceTypeEntityList.isEmpty()) {
            List<ServiceTypeVO> serviceTypeVOList = new ArrayList<>();
            serviceTypeEntityList.forEach(serviceTypeEntity -> {
                ServiceTypeVO serviceType = modelMapper.map(serviceTypeEntity, ServiceTypeVO.class);
                List<HelperTypeVO> helperType = helperTypeRepository.getHelperType(serviceType.getId(), true, paging);

                if(Functions.nonNullString(serviceTypeVO.getLanguage())){
                    ResponseVO responseVO1 = societyUtilService.getLanguageConversion(serviceType.getId(), Types.SERVICE_TYPE ,serviceTypeVO.getLanguage());

                    if(responseVO1 != null && Functions.nonNullString(responseVO1.getLanguageType().getServiceName())){
                        serviceType.setLanguageType(responseVO1.getLanguageType().getServiceName());
                    }

                    helperType.forEach(helperTypeVO -> {
                        ResponseVO response = societyUtilService.getLanguageConversion(helperTypeVO.getId(), Types.HELPER_TYPE,serviceTypeVO.getLanguage());
                        if(response != null && Functions.nonNullString(response.getLanguageType().getServiceName())){
                            helperTypeVO.setLanguageType(response.getLanguageType().getServiceName());
                        }
                    });
                }

                serviceType.setHelperType(helperType);
                serviceTypeVOList.add(serviceType);
            });

//            List<ServiceTypeVO> serviceTypeVOList = modelMapperUtil.mapList(serviceTypeEntityList, ServiceTypeVO.class);
//            for (ServiceTypeVO serviceType : serviceTypeVOList) {
//                List<HelperTypeVO> helperType = helperTypeRepository.getHelperType(serviceType.getId(), true, paging);
//                serviceType.setHelperType(helperType);
//            }
            int pages = Functions.getPagesCount(count);
            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setActiveServiceType(serviceTypeVOList);
            responseVO.setSuccessResponse(Messages.Service_Type_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

    public ResponseVO addHelperPhoneNumber(HelperVO helperRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addPhoneNumberInputValidator(helperRequestVO);
        helperValidationService.isSocietyIdExist(helperRequestVO.getSocietyId());
        HelperEntity helperEntity = helperRepository.findBySocietyIdAndHelperMobile(helperRequestVO.getSocietyId(), helperRequestVO.getHelperMobile());

        if (helperEntity != null) {
            HelperVO helperVO = modelMapper.map(helperEntity, HelperVO.class);
            responseVO.setHelperDetails(helperVO);
            responseVO.setFailResponse(Messages.Helper_Number_Exist);
        } else {
            responseVO.setSuccessResponse(Messages.Helper_Number_Does_Not_Exist);
        }
        return responseVO;
    }


    public String generateLoginId(int societyId) {
        Random random = new Random();
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {

            int index = random.nextInt(lowerAlphabet.length());
            char randomChar = lowerAlphabet.charAt(index);
            sb.append(randomChar);
        }

        String LoginId = sb.toString() +random.nextInt(8999);
        HelperHistoryLogEntity helperHistoryLogEntity = helperHistoryLogRepository.findByLoginId(LoginId);
        if (helperHistoryLogEntity != null) {
            generateLoginId(societyId);
        }
        return LoginId;
    }

    public ResponseVO getCompanyType(List<CompanyMasterVO> activeCompany) {
        ResponseVO responseVO = new ResponseVO();

        activeCompany.forEach(a -> {
            HelperTypeEntity helperTypeEntity = helperTypeRepository.getOne(a.getCompanyTypeId());
            a.setCompanyType(helperTypeEntity.getType());
        });

        responseVO.setActiveCompany(activeCompany);
        return responseVO;
    }

    public ResponseVO getHelperList(HelperVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getHelperInputValidator(helperRequestVO);
        int count = 0;
        Pageable paging = Functions.getPage(helperRequestVO.getPage(), helperRequestVO.getLimit());
        List<HelperEntity> helperEntityList = helperRepository.findByIsActiveAndSocietyIdAndType(true, helperRequestVO.getSocietyId(), UserRoles.HELPER,paging);
        count = helperRepository.countByIsActiveAndSocietyIdAndType(true, helperRequestVO.getSocietyId(), UserRoles.HELPER);
        if (helperEntityList.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        List<HelperListVO> frequentVisitor = modelMapperUtil.mapList(helperEntityList, HelperListVO.class);

        int pages = Functions.getPagesCount(count);
        responseVO.setCount(count);
        responseVO.setPages(pages);
        responseVO.setHelperList(frequentVisitor);
        responseVO.setSuccessResponse(Messages.Frequent_Visitor_list);
        return responseVO;
    }

    public ResponseVO addHelperLogHistory(HelperHistoryLogVO helperHistoryLogVO) {
        ResponseVO responseVO = new ResponseVO();
        HelperHistoryLogEntity helperHistoryLogEntity1 = new HelperHistoryLogEntity();
        helperInputValidation.addHelperHistoryLogInputValidator(helperHistoryLogVO);
        boolean result = false;
        int statusCount = 0;
        List<String> tokens = new ArrayList<>();
        helperValidationService.isHelperIdExist(helperHistoryLogVO.getHelperId());
        List<HelperListVO> attendanceList = helperRepository.attendaceCheck(true);

        HelperEntity helperEntity = helperRepository.getOne(helperHistoryLogVO.getHelperId());
        if(!helperEntity.getIsActive()){
            responseVO.setFailResponse(Messages.Contact_Admin);
            return responseVO;
        }
        if(helperEntity.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_OUT) && helperHistoryLogVO.getAction().equalsIgnoreCase(Status.LOGIN_STATUS_IN) || (helperHistoryLogVO.getAction().equalsIgnoreCase(Status.LOGIN_STATUS_IN) && helperEntity.getLoginStatus().equalsIgnoreCase(Status.WAITING))) {
            responseVO.setSuccessResponse(Messages.Already_In);
            return responseVO;
        }

        if (attendanceList.size() > 0 && helperEntity != null) {
            for (HelperListVO list : attendanceList) {
                if (list.getId() == (helperHistoryLogVO.getId())) {
                    result = true;
                }
            }
        }

        if (helperHistoryLogVO.getAction().equals(Status.LOGIN_STATUS_IN)) {

            if (helperEntity.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_OUT) && helperHistoryLogVO.getAction().equalsIgnoreCase(Status.LOGIN_STATUS_IN)) {
                responseVO.setSuccessResponse(Messages.Already_In);
                return responseVO;
            }
            // find Lined Flat
            List<Integer> helperListVO = householdService.getHelperMappingList(helperHistoryLogVO.getSocietyId(), helperHistoryLogVO.getHelperId());
            if (helperListVO.size()==0) {
                responseVO.setFailResponse(Messages.Helper_Not_Linked);
                return responseVO;
            }
            String loginId = this.generateLoginId(helperHistoryLogVO.getSocietyId());
            // Notification
            String notificationTitle = Messages.Helper_Checked_In + helperEntity.getHelperName();
            String notificationContent = Messages.Helper + helperEntity.getHelperName() + Messages.Approved_BY_Guard;
            for (Integer unitid : helperListVO) {
                HelperHistoryLogEntity helperHistoryLogEntity = modelMapper.map(helperHistoryLogVO, HelperHistoryLogEntity.class);
                helperHistoryLogEntity.setBodyTemperature(helperHistoryLogVO.getBodyTemperature());
                helperHistoryLogEntity.setWearingMask(helperHistoryLogVO.getIsWearingMask());
                helperHistoryLogEntity.setVehicleNumber(helperHistoryLogVO.getVehicleNumber());
                helperHistoryLogEntity.setType(Messages.helper);
                helperHistoryLogEntity.setStatus(Status.LOGIN_STATUS_OUT);
                helperHistoryLogEntity.setInTime(helperHistoryLogVO.getInTime());
                helperHistoryLogEntity.setUnitId(unitid);
                helperHistoryLogEntity.setLoginId(loginId);
                helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);
                helperHistoryLogEntity.setAreaId(societyService.getAreaIdByUnitId(helperHistoryLogVO.getSocietyId(), unitid));
                HelperHistoryLogEntity helperHistoryLogEntity2 = helperHistoryLogRepository.save(helperHistoryLogEntity);
                helperHistoryLogEntity1.setId(helperHistoryLogEntity2.getId());
                helperRepository.updateHelperLoginStatusAndId(Status.LOGIN_STATUS_OUT, loginId, helperHistoryLogVO.getHelperId());

                List<String> memberToken = societyService.getUserTokens(helperEntity.getSocietyId(), unitid);
                for (String token : memberToken) {
                    tokens.add(token);
                }

            }
            if (tokens.size() > 0)
                pushNotificationService.sendMultipleNotificationToToken(notificationTitle, notificationContent, NotificationAction.ACTIVITY, tokens);  //Notification Sent

            if (result) {
                for (Integer unitid : helperListVO) {
                    HelperAttendaceMasterEntity helperAttendaceMasterEntity;
                    helperAttendaceMasterEntity = modelMapper.map(helperHistoryLogVO, HelperAttendaceMasterEntity.class);
                    helperAttendaceMasterEntity.setLoginId(loginId);
                    helperAttendaceMasterEntity.setUnitId(unitid);
                    helperAttendaceMasterEntity.setApprovedBy(UserRoles.GUARD);
                    helperAttendaceMasterEntity.setLoginStatus(Status.LOGIN_STATUS_OUT);
                    helperAttendaceMasterEntity.setAreaId(societyService.getAreaIdByUnitId(helperHistoryLogVO.getSocietyId(), unitid));
                    helperAttendaceMasterRepository.save(helperAttendaceMasterEntity);
                }
            }
            //responseVO.setLoginId(helperHistoryLogEntity1.getId());
            responseVO.setSuccessResponse(Messages.Helper_log_In);

        } else if (helperHistoryLogVO.getAction().equals(Status.LOGIN_STATUS_OUT)) {
            String loginStatus = Status.LOGIN_STATUS_IN;
            if (helperEntity == null) {
                responseVO.setFailResponse(Messages.Not_Found);
                return responseVO;
            }
            if (helperHistoryLogVO.getUnitIdList() != null && helperHistoryLogVO.getUnitIdList().size() > 0) {
                for (Integer unitId : helperHistoryLogVO.getUnitIdList()) {
                    helperHistoryLogRepository.updateOutTimeByUnitId(new Date(), Status.LOGIN_STATUS_IN, helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId(), unitId);
                    if (result) {
                        helperAttendaceMasterRepository.updateOutTimeByUnitId(new Date(), helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId(), unitId);
                    }
                }
            } else {
                helperHistoryLogRepository.updateOutTime(new Date(), Status.LOGIN_STATUS_IN, helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId());
                if (result) {
                    helperAttendaceMasterRepository.updateOutTime(new Date(), helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId());
                }
            }
            statusCount = helperHistoryLogRepository.countByStatusAndLoginId(Status.LOGIN_STATUS_OUT, helperHistoryLogVO.getLoginId());
            if (statusCount >= 1) {
                loginStatus = Status.LOGIN_STATUS_OUT;
            }
            helperRepository.updateHelperLoginStatus(loginStatus, helperHistoryLogVO.getHelperId());
            responseVO.setSuccessResponse(Messages.Helper_log_Out);

        } else {

            responseVO.setFailResponse(Messages.InvalidAction);

        }
        return responseVO;
    }

    public ResponseVO getVisitor(HelperVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getVisitorInputValidator(helperRequestVO);
        int count = 0;
        int pages = 0;
        HelperEntity helperEntity = new HelperEntity();
        Pageable paging = Functions.getPage(helperRequestVO.getPage(), helperRequestVO.getLimit());
        Specification<HelperEntity> specification = Specification
                .where(!Functions.nonNullString(helperRequestVO.getHelperName()) ? null : SpecificationService.likeSpecification(helperEntity, EntityVariable.HELPER_NAME, helperRequestVO.getHelperName()))
                .and(helperRequestVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.SOCIETY_ID, helperRequestVO.getSocietyId()))
                .and(helperRequestVO.getHelperTypeId() == 0 ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.HELPER_TYPE_ID, helperRequestVO.getHelperTypeId()))
                .and(!Functions.nonNullString(helperRequestVO.getHelperMobile()) ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.HELPER_MOBILE, helperRequestVO.getHelperMobile()))
                .and(!Functions.nonNullString(helperRequestVO.getLoginStatus()) ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.LOGIN_STATUS, helperRequestVO.getLoginStatus()))
                .and(!Functions.nonNullString(helperRequestVO.getCompanyName()) ? null : SpecificationService.equalSpecification(helperEntity, EntityVariable.COMPANY_NAME, helperRequestVO.getCompanyName()))
                .and(!Functions.nonNullString(helperRequestVO.getHelperName())?null: SpecificationService.likeSpecification(helperEntity,EntityVariable.HELPER_NAME,helperRequestVO.getHelperName()))
                .and(Functions.nonNullString(helperRequestVO.getAction()) && helperRequestVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(helperEntity, EntityVariable.IS_ACTIVE, helperRequestVO.getIsActive()) : null)
                .and(SpecificationService.equalSpecification(helperEntity, EntityVariable.TYPE, UserRoles.VISITOR))
                .and(SpecificationService.descendingOrder(helperEntity, EntityVariable.ID));

        Page<HelperEntity> helperList1 = helperRepository.findAll(specification, paging);
        List<HelperVO> helperVOList = modelMapperUtil.mapPage(helperList1, HelperVO.class);


        if (helperList1.isEmpty()) {
            responseVO.setFailResponse(Messages.NO_Helper_Found);
            return responseVO;
        }
        count = (int) helperRepository.count(specification);
        for (HelperVO helperVO : helperVOList) {
            if (helperVO.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_IN)) {
                helperVO.setLoginStatus(Status.LOGIN_STATUS_OUT);
            } else if (helperVO.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_OUT)) {
                helperVO.setLoginStatus(Status.LOGIN_STATUS_IN);
            }
            if (helperVO.getCompanyId() != 0) {
                CompanyMasterVO companyMasterVOs = householdService.getCompany(helperVO.getCompanyId());
                helperVO.setCompanyLogo(companyMasterVOs.getCompanyLogo());
            }
        }

        pages = Functions.getPagesCount(count);
        responseVO.setPages(pages);
        responseVO.setCount(count);
        responseVO.setHelperListVOList(helperVOList);
        responseVO.setSuccessResponse(Messages.Visitor_list);
        return responseVO;
    }

    public ResponseVO getByPassCode(HelperVO helperRequestVO) throws PZConstraintViolationException{
        helperInputValidation.getByPassCodeInputValidator(helperRequestVO);
        ResponseVO responseVO = new ResponseVO();
        List<HelperVO> helperList1 = new ArrayList<>();
        List<UnitListVO> unitList= new ArrayList<>();
        HelperEntity helperEntity = helperRepository.findByPassCodeAndSocietyId(helperRequestVO.getPassCode(), helperRequestVO.getSocietyId());

        if (helperEntity == null) {
            responseVO.setFailResponse(Messages.Invalid_PassCode);
        } else {
            HelperVO helperVO = modelMapper.map(helperEntity, HelperVO.class);
            List<Integer> unitId = householdService.getHelperMappingList(helperRequestVO.getSocietyId(), helperEntity.getId());
            unitId.forEach(unit -> {
                ResponseVO responseVO1 = societyService.getAreaByUnitId(unit);
                if (responseVO1.getUnitDetail() != null) {
                    unitList.add(responseVO1.getUnitDetail());
                }
            });
            helperVO.setUnitDetails(unitList);
            helperList1.add(helperVO);
            responseVO.setSuccessResponse(Messages.Helper_Found);
            responseVO.setHelperListVOList(helperList1);
        }
        return responseVO;
    }

    public ResponseVO insideHelperList(HelperVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getWaitingListInputValidator(helperRequestVO);
        helperValidationService.isSocietyIdExist(helperRequestVO.getSocietyId());
        List<HelperEntity> visitorEntity = helperRepository.findByLoginStatusAndSocietyId(Status.LOGIN_STATUS_OUT, helperRequestVO.getSocietyId());
        List<HelperListVO> helperListVOs = modelMapperUtil.mapList(visitorEntity, HelperListVO.class);

        if (!visitorEntity.isEmpty()) {
            for (HelperListVO visitorListVO : helperListVOs) {
                if (visitorListVO.getCompanyId() != 0) {
                    CompanyMasterVO companyMasterVOs = householdService.getCompanyDetails(visitorListVO.getCompanyId());
                    visitorListVO.setCompanyDetails(companyMasterVOs);
                }
            }
            if (helperListVOs.size() > 0 && !helperListVOs.isEmpty()) {
                responseVO.setInsideHelperVisitorList(helperListVOs);
                responseVO.setSuccessResponse(Messages.Insider_list);
            } else {
                responseVO.setFailResponse(Messages.Not_Found);
            }
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public ResponseVO getAttendance(HelperVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        List<String> dateList = new ArrayList<>();
        List<HelperListVO> attendanceList;
        String fromDate = helperRequestVO.getYear() + "-" + helperRequestVO.getMonth() + "-01 00:00:00";
        String todate = helperRequestVO.getYear() + "-" + helperRequestVO.getMonth() + "-31 23:59:59";

        if (helperRequestVO.getUnitId() != 0) {
            attendanceList = helperAttendaceMasterRepository.getAttendance(helperRequestVO.getSocietyId(), helperRequestVO.getUnitId(), helperRequestVO.getHelperId(), fromDate, todate);
        } else {
            attendanceList = helperAttendaceMasterRepository.getAttendanceBySocietyId(helperRequestVO.getSocietyId(), helperRequestVO.getHelperId(), fromDate, todate);
        }

        if (attendanceList.isEmpty()) {
            responseVO.setFailResponse(Messages.Attendance_Not_Found);
        }else {
            Map<String, String> map = new HashMap<>();
            for (HelperListVO list : attendanceList) {
                if (list.getInTime() != null && !map.containsKey(list.getLoginId())) {
                    String[] str = list.getInTime().split("\\s+");
                    String str1 = str[0];
                    map.put(list.getLoginId(), str1);
                }

                ResponseVO response = societyService.getUnitDetail(list);
                list.setUnit(response.getHelper().getUnit());

            }
            responseVO.setSuccessResponse(Messages.List_Of_Dates);
            responseVO.setDateList(map.values());
            responseVO.setDetailList(attendanceList);
        }

        return responseVO;
    }

    public ResponseVO getVisitorType(ServiceTypeVO serviceTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();

        List<Integer> serviceTypeEntityList = serviceTypeRepository.getHelperTypeList(true, true, true);

        if (!serviceTypeEntityList.isEmpty()) {

            List<HelperTypeVO> helperType = helperTypeRepository.getHelperTypeList(serviceTypeEntityList, true);

            if(!helperType.isEmpty() && Functions.nonNullString(serviceTypeVO.getLanguage())){
                helperType.forEach(helperTypeVO -> {
                    ResponseVO response = societyUtilService.getLanguageConversion(helperTypeVO.getId(), Types.HELPER_TYPE, serviceTypeVO.getLanguage());
                    if (response != null && Functions.nonNullString(response.getLanguageType().getServiceName())) {
                        helperTypeVO.setLanguageType(response.getLanguageType().getServiceName());
                    }
                });
            }

            responseVO.setHelperTypeList1(helperType);
            responseVO.setSuccessResponse(Messages.Helper_Type_List);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

    public ResponseVO recentActivity(ActivityVO activityVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getActivityInputValidation(activityVO);
        Pageable pageable = Functions.getPage(0, activityVO.getLimit());
        List<HelperListVO> activityListVO = helperRepository.getRecentActivity(activityVO.getUnitId(), pageable);
        if(!activityListVO.isEmpty()){
            for( HelperListVO activityListVO1 : activityListVO){
                if(activityListVO1.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_IN)){
                    activityListVO1.setLoginStatus(Status.LOGIN_STATUS_OUT);
                }else if(activityListVO1.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_OUT)){
                    activityListVO1.setLoginStatus(Status.LOGIN_STATUS_IN);
                }
                if(activityListVO1.getCompanyId()!=0) {
                    CompanyMasterVO companyMasterVO = householdService.getCompanyDetails(activityListVO1.getCompanyId());
                    activityListVO1.setCompanyName(companyMasterVO.getName());
                    activityListVO1.setCompanyLogo(companyMasterVO.getCompanyLogo());
                }
            }
            responseVO.setActivityList(activityListVO);
            responseVO.setSuccessResponse(Messages.Activity_List_Success);
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }
    private int generatePin(int societyId) {

        Random random = new Random();
        int pin = 100000 + random.nextInt(899999);

        HelperEntity helperEntity = helperRepository.findByPassCodeAndSocietyId(pin, societyId);

        if (helperEntity != null) {
            generatePin(societyId);
        }
        return pin;
    }

    public ResponseVO addVisitorHistoryLog(HelperHistoryLogVO helperHistoryLogVO) {
        ResponseVO responseVO = new ResponseVO();
        List<String> tokens = new ArrayList<>();
        System.out.println(helperHistoryLogVO.getAction());
        if (helperHistoryLogVO.getAction().equals("in")) {
            helperInputValidation.addVisitorHistoryLogInputValidator(helperHistoryLogVO);
            if (helperHistoryLogVO.getSocietyId() != 0)
                helperValidationService.isSocietyIdExist(helperHistoryLogVO.getSocietyId());
            if (helperHistoryLogVO.getHelperId() != 0)
                helperValidationService.isHelperIdExist(helperHistoryLogVO.getHelperId());
            HelperEntity helperEntity = helperRepository.getOne(helperHistoryLogVO.getHelperId());
            String loginId= this.generateLoginId(helperHistoryLogVO.getSocietyId());

            if(helperEntity.getLoginStatus().equalsIgnoreCase(Status.LOGIN_STATUS_OUT) && helperHistoryLogVO.getAction().equalsIgnoreCase(Status.LOGIN_STATUS_IN) || (helperHistoryLogVO.getAction().equalsIgnoreCase(Status.LOGIN_STATUS_IN) && helperEntity.getLoginStatus().equalsIgnoreCase(Status.WAITING)))
            {
                responseVO.setSuccessResponse(Messages.Already_In);
                return responseVO;
            }
            if (helperEntity != null) {
                String notificationTitle = Messages.Visitor_Checked_In + helperEntity.getHelperName();
                String notificationContent = Messages.Visitor + helperEntity.getHelperName() + Messages.Approved_BY_Guard;

//                helperInputValidation.addVisitorHistoryLogInputValidator(helperHistoryLogVO);
                helperEntity.setHelperTypeId(helperHistoryLogVO.getHelperTypeId());
                helperEntity.setCompanyId(helperHistoryLogVO.getCompanyId());
                helperEntity.setCompanyName(helperHistoryLogVO.getCompanyName());
                helperRepository.save(helperEntity);

                for (Integer unitid : helperHistoryLogVO.getUnitIdList()) {
                    HelperHistoryLogEntity helperHistoryLogEntity = new HelperHistoryLogEntity();
                    helperHistoryLogEntity.setType(UserRoles.VISITOR);
                    if (helperHistoryLogVO.getUnitIdList().size() > 1) {
                        helperHistoryLogEntity.setStatus(Status.LOGIN_STATUS_OUT);
                        helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);
                        helperRepository.updateHelperLoginStatusAndId(Status.LOGIN_STATUS_OUT, loginId, helperHistoryLogVO.getHelperId());
                    } else {
                        if (helperEntity.getLoginStatus().equalsIgnoreCase(Status.WAITING)) {
                            helperHistoryLogEntity.setStatus(Status.LOGIN_STATUS_OUT);
                            helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);
                            helperRepository.updateHelperLoginStatusAndId(Status.LOGIN_STATUS_OUT, loginId, helperHistoryLogVO.getHelperId());
                        } else {

                            notificationContent = Messages.Visitor + helperEntity.getHelperName() + Messages.Waiting_Message;
                            helperHistoryLogEntity.setStatus(Status.WAITING);
                            helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);
                            helperRepository.updateHelperLoginStatusAndId(Status.WAITING, loginId, helperHistoryLogVO.getHelperId());
                        }

                    }

                    helperHistoryLogEntity.setCompanyId(helperHistoryLogVO.getCompanyId());
                    helperHistoryLogEntity.setCompanyName(helperHistoryLogVO.getCompanyName());
                    helperHistoryLogEntity.setLoginId(loginId);
                    helperHistoryLogEntity.setHelperId(helperEntity.getId());
                    helperHistoryLogEntity.setSocietyId(helperEntity.getSocietyId());
                    helperHistoryLogEntity.setUnitId(unitid);
                    helperHistoryLogEntity.setBodyTemperature(helperHistoryLogVO.getBodyTemperature());
                    helperHistoryLogEntity.setWearingMask(helperHistoryLogVO.getIsWearingMask());
                    helperHistoryLogEntity.setVehicleNumber(helperHistoryLogVO.getVehicleNumber());
                    helperHistoryLogEntity.setInTime(helperHistoryLogVO.getInTime());
                    helperHistoryLogEntity.setAreaId(societyService.getAreaIdByUnitId(helperHistoryLogVO.getSocietyId(), unitid));
                    //Approved BY Logic
                    if (Functions.nonNullString(helperEntity.getHelperMobile()) && helperHistoryLogVO.getUnitIdList().size()==1) {
                        helperHistoryLogEntity.setApprovedBy(userService.getOwnerName(helperHistoryLogVO.getMobileNumber()));
                    }
                    helperHistoryLogRepository.save(helperHistoryLogEntity);

                    List<String> memberToken = societyService.getUserTokens(helperEntity.getSocietyId(), unitid);
                    for (String token : memberToken) {
                        tokens.add(token);
                    }

                }
                if (tokens.size() > 0)
                    pushNotificationService.sendMultipleNotificationToToken(notificationTitle, notificationContent, NotificationAction.VISITOR, tokens);
                responseVO.setSuccessResponse(Messages.Visitor_log_In);

            } else {
                responseVO.setFailResponse(Messages.Not_Found);
            }

        } else if (helperHistoryLogVO.getAction().equals("out")) {
            int statusCount=0;
            HelperEntity helperEntity = helperRepository.findById(helperHistoryLogVO.getHelperId());
            if (helperEntity == null) {
                responseVO.setFailResponse(Messages.Not_Found);
                return responseVO;
            }
            String loginStatus=Status.LOGIN_STATUS_IN;
            if(helperHistoryLogVO.getUnitIdList().size()>0){
                for(Integer unitId : helperHistoryLogVO.getUnitIdList()) {
                    helperHistoryLogRepository.updateOutTimeByUnitId(new Date(), Status.LOGIN_STATUS_IN, helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId(),unitId);
                }
            }
            else {
                helperHistoryLogRepository.updateOutTime(new Date(), Status.LOGIN_STATUS_IN, helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId());
            }
            statusCount= helperHistoryLogRepository.countByStatusAndLoginId(Status.LOGIN_STATUS_OUT, helperHistoryLogVO.getLoginId());
            if(statusCount>=1){
                loginStatus=Status.LOGIN_STATUS_OUT;
            }
            helperRepository.updateHelperLoginStatus(loginStatus, helperHistoryLogVO.getHelperId());
            responseVO.setSuccessResponse(Messages.Visitor_log_Out);
        } else {
            responseVO.setFailResponse(Messages.InvalidAction);
        }

        return responseVO;
    }

    public ResponseVO addWaitingVisitorHistoryLog(HelperHistoryLogVO helperHistoryLogVO) {
        ResponseVO responseVO = new ResponseVO();
        List<String> tokens = new ArrayList<>();
        if (helperHistoryLogVO.getAction().equals("in")) {
            if (helperHistoryLogVO.getSocietyId() != 0)
                helperValidationService.isSocietyIdExist(helperHistoryLogVO.getSocietyId());
            if (helperHistoryLogVO.getHelperId() != 0)
                helperValidationService.isHelperIdExist(helperHistoryLogVO.getHelperId());
            HelperEntity helperEntity = helperRepository.getOne(helperHistoryLogVO.getHelperId());
            if (helperEntity != null) {
                String notificationTitle = Messages.Visitor_Checked_In + helperEntity.getHelperName();
                String notificationContent = Messages.Visitor + helperEntity.getHelperName() + Messages.Approved_BY_Guard;

                if (helperEntity.getLoginStatus().equals(Status.WAITING)) {
                    helperRepository.updateHelperLoginStatus(Status.LOGIN_STATUS_OUT, helperHistoryLogVO.getHelperId());
                    helperHistoryLogRepository.updateStatus(Status.LOGIN_STATUS_OUT, helperHistoryLogVO.getLoginId(), helperHistoryLogVO.getHelperId());
                    List<String> memberToken = societyService.getUserTokens(helperEntity.getSocietyId(), helperHistoryLogVO.getUnitId());
                    for (String token : memberToken) {
                        tokens.add(token);
                    }
                    if (tokens.size() > 0)
                        pushNotificationService.sendMultipleNotificationToToken(notificationTitle, notificationContent, NotificationAction.VISITOR, tokens);
                    responseVO.setSuccessResponse(Messages.Visitor_log_In);
                    return responseVO;
                } else {
                    responseVO.setFailResponse(Messages.Not_Found);
                }
            }
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }


        return responseVO;
    }
    public ResponseVO addVisitor(VisitorVO visitorRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addVisitorInputValidator(visitorRequestVO);
        helperValidationService.isSocietyIdExist(visitorRequestVO.getSocietyId());
        List<String> tokens = new ArrayList<>();
        HelperEntity helperEntity = helperRepository.findBySocietyIdAndHelperMobile(visitorRequestVO.getSocietyId(), visitorRequestVO.getHelperMobile());
        if (helperEntity == null) {
            HelperEntity helperEntity1 = modelMapper.map(visitorRequestVO, HelperEntity.class);
            if (visitorRequestVO.getUnitIdList().size() > 1) {
                helperEntity1.setLoginStatus(Status.LOGIN_STATUS_OUT);
            } else {
                helperEntity1.setLoginStatus(Status.WAITING);
            }
            String loginId= this.generateLoginId(visitorRequestVO.getSocietyId());
            helperEntity1.setLoginId(loginId);
            helperEntity1.setIsActive(true);
            helperEntity1.setAddedBy(visitorRequestVO.getInitiatedBy());
            helperEntity1.setType(UserRoles.VISITOR);
            if(Functions.nonNullString(visitorRequestVO.getPhoto())){

                String fileName = visitorRequestVO.getHelperName()+"_"+visitorRequestVO.getHelperMobile()+".png";
                FileHandlingUtil.fileUpload(visitorRequestVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER,null);
                helperEntity1.setPhoto(Types.HELPER_IMAGES+fileName);
            }
            HelperEntity helperEntity2=helperRepository.save(helperEntity1);
            String notificationTitle = Messages.Visitor_Checked_In + visitorRequestVO.getHelperName();
            String notificationContent = Messages.Visitor + visitorRequestVO.getHelperName() + Messages.Approved_BY_Guard;

            for (Integer unitId : visitorRequestVO.getUnitIdList()) {
                HelperHistoryLogEntity helperHistoryLogEntity= modelMapper.map(visitorRequestVO, HelperHistoryLogEntity.class);
                helperHistoryLogEntity.setType(UserRoles.VISITOR);
                helperHistoryLogEntity.setHelperId(helperEntity2.getId());
                helperHistoryLogEntity.setCompanyName(visitorRequestVO.getCompanyName());
                helperHistoryLogEntity.setCompanyId(visitorRequestVO.getCompanyId());
                helperHistoryLogEntity.setAreaId(societyService.getAreaIdByUnitId(visitorRequestVO.getSocietyId(), unitId));
                if (visitorRequestVO.getUnitIdList().size() > 1) {
                    helperHistoryLogEntity.setStatus(Status.LOGIN_STATUS_OUT);
                    helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);

                } else {
                    helperHistoryLogEntity.setStatus(Status.WAITING);
                    helperHistoryLogEntity.setApprovedBy(UserRoles.GUARD);
                }
                helperHistoryLogEntity.setUnitId(unitId);
                helperHistoryLogEntity.setLoginId(loginId);
                helperHistoryLogRepository.save(helperHistoryLogEntity);
                if(visitorRequestVO.getUnitIdList().size()>1) {
                    List<String> memberToken = societyService.getUserTokens(visitorRequestVO.getSocietyId(), unitId);
                    for (String token : memberToken) {
                        tokens.add(token);
                    }
                }
            }
            if (tokens.size() > 0 && visitorRequestVO.getUnitIdList().size() > 1) {
                pushNotificationService.sendMultipleNotificationToToken(notificationTitle, notificationContent, NotificationAction.VISITOR, tokens);
            }
            responseVO.setSuccessResponse(Messages.VisitorAdded);
        }
        else {
            responseVO.setFailResponse(Messages.VisitorAlreadyExist);
        }
        return responseVO;
    }
    public ResponseVO getWaitingList(HelperVO visitorRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getWaitingListInputValidator(visitorRequestVO);
        helperValidationService.isSocietyIdExist(visitorRequestVO.getSocietyId());
        List<HelperListVO> visitorEntity = helperRepository.getWaitingVisitorList(visitorRequestVO.getSocietyId(), Status.WAITING);
        if (!visitorEntity.isEmpty()) {
            for (HelperListVO visitorListVO : visitorEntity) {
                List<String> ownernumber = societyService.getMobileNumber(visitorListVO.getUnitId());
                if(ownernumber.size()>0)
                visitorListVO.setOwnerMobileNumber(ownernumber);

                if(visitorListVO.getCompanyId() !=0) {
                    CompanyMasterVO companyMasterVOs = householdService.getCompanyDetails(visitorListVO.getCompanyId());
                    visitorListVO.setCompanyDetails(companyMasterVOs);
                }
            }

            if (visitorEntity.size() > 0 && !visitorEntity.isEmpty()) {

                responseVO.setWaitingVisitorList(visitorEntity);
                responseVO.setSuccessResponse(Messages.Waiting_Visitor_list);
            } else {
                responseVO.setFailResponse(Messages.Not_Found);
            }
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }
    public ResponseVO checkVisitorPhoneNumber(HelperVO visitorRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        List<VisitorVO> visitorData = new ArrayList<>();
        helperInputValidation.checkVisitorInputValidator(visitorRequestVO);
        helperValidationService.isSocietyIdExist(visitorRequestVO.getSocietyId());
        HelperEntity helperEntity=helperRepository.findBySocietyIdAndHelperMobileAndIsActive(visitorRequestVO.getSocietyId(), visitorRequestVO.getHelperMobile(), true);

        if (helperEntity != null) {

            List<Integer> unitIdList = helperHistoryLogRepository.getUnitIdList(helperEntity.getId());
            VisitorVO helperVO = modelMapper.map(helperEntity, VisitorVO.class);
            helperVO.setUnitIdList(unitIdList);
            visitorData.add(helperVO);
            responseVO.setVisitorList(visitorData);
            responseVO.setFailResponse(Messages.VisitorAlreadyExist);

        } else {
            responseVO.setSuccessResponse(Messages.Proceed);
        }
        return responseVO;
    }
    public ResponseVO getUnitDetails(HelperVO helperRequestVO) {
        helperInputValidation.getLoginDetailsInputValidator(helperRequestVO);
        ResponseVO responseVO = new ResponseVO();
        List<HelperHistoryLogEntity> helperHistoryLogEntity;
        if(helperRequestVO.getInitiatedBy().equalsIgnoreCase(UserRoles.GUARD)) {
            helperHistoryLogEntity = helperHistoryLogRepository.findByLoginIdAndSocietyIdAndStatus(helperRequestVO.getLoginId(), helperRequestVO.getSocietyId(), Status.LOGIN_STATUS_OUT);
        }else {
            helperHistoryLogEntity = helperHistoryLogRepository.getUnitStatus(helperRequestVO.getSocietyId(), helperRequestVO.getLoginId());
        }

        if (helperHistoryLogEntity.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        List<HelperVO> helperListVO = modelMapperUtil.mapList(helperHistoryLogEntity, HelperVO.class);

        for(HelperVO helperListVO1: helperListVO) {
            helperListVO1.setAreaName(societyService.getAreaName(helperListVO1.getUnitId()));
            helperListVO1.setUnit(societyService.getUnit(helperListVO1.getUnitId()));
        }
        responseVO.setSuccessResponse(Messages.Helper_Found);
        responseVO.setHelperListVOList(helperListVO);
        return responseVO;
    }
    public ResponseVO getInOutHistory(HelperVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getInOutHistoryInputValidator(helperRequestVO);
        int pages=0;
        int count=0;
        List<HelperListVO> helperListVO =null;
        String fromDate = helperRequestVO.getDate()+ " 00:00:00";
        String todate =helperRequestVO.getDate() + " 23:59:59";
        Pageable pageable = Functions.getPage(helperRequestVO.getPage(), helperRequestVO.getLimit());

        if(helperRequestVO.getSocietyId()!=0 && helperRequestVO.getAreaId()!=0 && helperRequestVO.getUnitId()!=0){

            helperListVO = helperRepository.getInOutHistoryByAreaIdAndUnitId(helperRequestVO.getSocietyId(), helperRequestVO.getAreaId(), helperRequestVO.getUnitId(), fromDate, todate, pageable);
            count = helperHistoryLogRepository.countBySocietyIdAndAreaIdAndUnitIdAndInTimeGreaterThanAndInTimeLessThan(helperRequestVO.getSocietyId(),helperRequestVO.getAreaId(), helperRequestVO.getUnitId(), fromDate, todate);
        }
        else {

            helperListVO = helperRepository.getInOutHistoryByAreaId(helperRequestVO.getSocietyId(), helperRequestVO.getAreaId(), fromDate, todate, pageable);
            count = helperHistoryLogRepository.countBySocietyIdAndAreaIdAndInTimeGreaterThanAndInTimeLessThan(helperRequestVO.getSocietyId(), helperRequestVO.getAreaId(), fromDate,todate);
        }

        if (helperListVO == null) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        for(HelperListVO helperListVO1: helperListVO) {

            if(helperListVO1.getUnitId()!=0) {
                String unit = societyService.getUnit(helperListVO1.getUnitId());
                helperListVO1.setUnit(unit);
            }

            if(helperListVO1.getCompanyId()!=0) {
                CompanyMasterVO companyMasterVO = householdService.getCompanyDetails(helperListVO1.getCompanyId());
                helperListVO1.setCompanyLogo(companyMasterVO.getCompanyLogo());
            }
        }


        pages = Functions.getPagesCount(count);
        responseVO.setPages(pages);
        responseVO.setCount(count);
        responseVO.setHelperDetail(helperListVO);
        responseVO.setSuccessResponse(Messages.Helper_Found);
        return responseVO;

    }
    public ResponseVO addHelperTypeDocument(HelperDocumentVO helperDocRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addHelperTypeDocument(helperDocRequestVO);

        HelperDocumentTypeEntity helperDocumentTypeEntity = helperDocumentTypeRepository.findBySocietyIdAndServiceTypeIdAndHelperTypeIdAndDocumentTypeIdAndIsActive(helperDocRequestVO.getSocietyId(), helperDocRequestVO.getServiceTypeId(), helperDocRequestVO.getHelperTypeId(), helperDocRequestVO.getDocumentTypeId(), true);

        if (helperDocumentTypeEntity == null) {

            for (Integer helperTypeId : helperDocRequestVO.getHelperTypeIdList()) {

                helperDocRequestVO.setIsActive(true);
                helperDocRequestVO.setHelperTypeId(helperTypeId);
                HelperDocumentTypeEntity helperDocumentTypeEntity1 = modelMapper.map(helperDocRequestVO, HelperDocumentTypeEntity.class);
                helperDocumentTypeRepository.save(helperDocumentTypeEntity1);
            }
            responseVO.setSuccessResponse(Messages.Type_Added_Success);

        } else {

            responseVO.setFailResponse(Messages.Helper_Document_Already_Exist);
        }

        return responseVO;
    }
    public ResponseVO addHelperDocument(HelperDocumentVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.addHelperDocument(helperRequestVO);

      HelperDocumentMasterEntity helperDocumentMasterEntity = helperDocumentMasterRepository.findBySocietyIdAndHelperIdAndDocumentTypeId(helperRequestVO.getSocietyId(), helperRequestVO.getHelperId(), helperRequestVO.getDocumentTypeId());

        if (helperDocumentMasterEntity == null) {

            HelperDocumentMasterEntity helperDocumentMasterEntity1 = modelMapper.map(helperRequestVO, HelperDocumentMasterEntity.class);
            String fileName = helperRequestVO.getHelperName() + "_" + helperRequestVO.getHelperMobile() + ".png";
            FileHandlingUtil.fileUpload(helperRequestVO.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER,null);
            helperDocumentMasterEntity1.setDocument(Types.HELPER_IMAGES+fileName);

              helperDocumentMasterRepository.save(helperDocumentMasterEntity1);
              responseVO.setSuccessResponse(Messages.Document_Add_Success);

        } else {

            responseVO.setFailResponse(Messages.Helper_Document_Already_Exist);
        }
        return responseVO;
    }
    public ResponseVO updateHelperTypeDocument(HelperDocumentVO helperDocRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperValidationService.isSocietyIdExist(helperDocRequestVO.getSocietyId());

        HelperDocumentTypeEntity helperDocumentTypeEntity1 = helperDocumentTypeRepository.getOne(helperDocRequestVO.getId());

        if(helperDocumentTypeEntity1.getHelperTypeId()== helperDocRequestVO.getHelperTypeId() && helperDocumentTypeEntity1.getDocumentTypeId() == helperDocRequestVO.getDocumentTypeId() && helperDocumentTypeEntity1.getIsActive() == helperDocRequestVO.getIsActive() && helperDocumentTypeEntity1.getIsMandatory() == helperDocRequestVO.getIsMandatory() && helperDocumentTypeEntity1.getSocietyId() == helperDocRequestVO.getSocietyId() ){
            responseVO.setFailResponse(Messages.No_Changes_Detected);

        }
        else {

            helperDocumentTypeEntity1.setSocietyId(helperDocRequestVO.getSocietyId());
            helperDocumentTypeEntity1.setHelperTypeId(helperDocRequestVO.getHelperTypeId());
            helperDocumentTypeEntity1.setServiceTypeId(helperDocRequestVO.getServiceTypeId());
            helperDocumentTypeEntity1.setDocumentTypeId(helperDocRequestVO.getDocumentTypeId());
            helperDocumentTypeEntity1.setIsMandatory(helperDocRequestVO.getIsMandatory());
            helperDocumentTypeEntity1.setIsActive(helperDocRequestVO.getIsActive());
            helperDocumentTypeRepository.save(helperDocumentTypeEntity1);
            responseVO.setSuccessResponse(Messages.Type_Added_Success);
        }

        return responseVO;
    }

    public ResponseVO getHelperTypeDocument(HelperVO helperDocRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        int activeCount = 0;
        int pages = 0;
        helperValidationService.isSocietyIdExist(helperDocRequestVO.getSocietyId());
        Pageable pageable = Functions.getPage(helperDocRequestVO.getPage(), helperDocRequestVO.getLimit());

        HelperDocumentTypeEntity helperDocumentTypeEntity = new HelperDocumentTypeEntity();

        Specification<HelperDocumentTypeEntity> specification = Specification.where(helperDocRequestVO.getHelperTypeId() == 0 ? null : SpecificationService.equalSpecification(helperDocumentTypeEntity, EntityVariable.HELPER_TYPE_ID, helperDocRequestVO.getHelperTypeId()))
                .and(helperDocRequestVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(helperDocumentTypeEntity, EntityVariable.SOCIETY_ID, helperDocRequestVO.getSocietyId()))
                .and(helperDocRequestVO.getServiceTypeId() == 0 ? null : SpecificationService.equalSpecification(helperDocumentTypeEntity, EntityVariable.SERVICE_TYPE_ID, helperDocRequestVO.getServiceTypeId()))
                .and(Functions.nonNullString(helperDocRequestVO.getAction()) && helperDocRequestVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(helperDocumentTypeEntity, EntityVariable.IS_ACTIVE, helperDocRequestVO.getIsActive()) : null)
                .and(helperDocRequestVO.getDocumentTypeId() == 0 ? null : SpecificationService.equalSpecification(helperDocumentTypeEntity, EntityVariable.DOCUMENT_TYPE_ID, helperDocRequestVO.getDocumentTypeId()));

        Page<HelperDocumentTypeEntity> helperDocumentTypeEntities = helperDocumentTypeRepository.findAll(specification, pageable);

        if (helperDocumentTypeEntities.isEmpty()) {
            responseVO.setFailResponse(Messages.NO_Helper_Found);
            return responseVO;
        }
        List<HelperDocumentListVO> helperDocumentTypeVo1 = modelMapperUtil.mapPage(helperDocumentTypeEntities, HelperDocumentListVO.class);
        activeCount = (int) helperDocumentTypeRepository.count(specification);

        if (helperDocumentTypeVo1.size() > 0) {

            for (HelperDocumentListVO helperDocumentTypeVo2 : helperDocumentTypeVo1) {
                if (helperDocumentTypeVo2.getHelperTypeId() != 0) {
                    HelperTypeEntity helperTypeEntity = helperTypeRepository.findById(helperDocumentTypeVo2.getHelperTypeId());
                    helperDocumentTypeVo2.setHelperType(helperTypeEntity.getType());
                    helperDocumentTypeVo2.setIconImage(helperTypeEntity.getIconImage());
                }

                if (helperDocumentTypeVo2.getServiceTypeId() != 0) {
                    ServiceTypeEntity serviceTypeEntity = serviceTypeRepository.getOne(helperDocumentTypeVo2.getServiceTypeId());
                    helperDocumentTypeVo2.setServiceType(serviceTypeEntity.getType());
                }
                if (helperDocumentTypeVo2.getDocumentTypeId() != 0) {
                    String documentName = societyService.getDocumentType(helperDocumentTypeVo2.getDocumentTypeId());
                    helperDocumentTypeVo2.setDocumentName(documentName);
                }

            }

        }
        responseVO.setSuccessResponse(Messages.Document_List);
        responseVO.setActiveHelperDocList(helperDocumentTypeVo1);
        responseVO.setCount(activeCount);
        pages = Functions.getPagesCount(activeCount);
        responseVO.setPages(pages);
        return responseVO;
    }

    public ResponseVO getHelperMandatoryDocuments(HelperDocumentVO helperDocRequestVO) {

        ResponseVO responseVO = new ResponseVO();
        int activeCount = 0;
        int inActiveCount = 0;
        int pages = 0;
        int inActivePage = 0;
        helperInputValidation.getHelperDocumentByHelperTypeIdInputValidator(helperDocRequestVO);
        helperValidationService.isSocietyIdExist(helperDocRequestVO.getSocietyId());
        Pageable pageable = Functions.getPage(helperDocRequestVO.getPage(), helperDocRequestVO.getLimit());

        List<HelperDocumentListVO> activeHelperDocMapping = helperDocumentTypeRepository.getDocumentByHelperTypeIdAndSocietyId(helperDocRequestVO.getSocietyId(),helperDocRequestVO.getHelperTypeId(), true, pageable);
        activeCount = helperDocumentTypeRepository.countBySocietyIdAndIsActive(helperDocRequestVO.getSocietyId(), true);

        if (activeHelperDocMapping.size() > 0) {

            for (HelperDocumentListVO helperDocumentTypeVo1 : activeHelperDocMapping) {

                String documentName=societyService.getDocumentType(helperDocumentTypeVo1.getDocumentTypeId());
                helperDocumentTypeVo1.setDocumentName(documentName);
            }

        }
        if(activeHelperDocMapping.isEmpty()){

            responseVO.setFailResponse(Messages.Not_Found);
        }
        else {
            responseVO.setSuccessResponse(Messages.Document_List);
            if(!activeHelperDocMapping.isEmpty())
            responseVO.setActiveHelperDocList(activeHelperDocMapping);

            responseVO.setCount(activeCount);
            responseVO.setInActiveCount(inActiveCount);

            pages = Functions.getPagesCount(activeCount);
            inActivePage = Functions.getPagesCount(inActiveCount);

            responseVO.setPages(pages);
            responseVO.setInActivePages(inActivePage);

        }
        return responseVO;
    }

    public ResponseVO getVisitorDashBoard(HelperHistoryLogVO helperDocRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        int count = 0;
        int loopCount=7;

        List<HelperHistoryLogVO> helperListVO = new ArrayList<>();
        List<HelperHistoryLogVO> helperListVO1 = new ArrayList<>();
        List<HelperHistoryLogVO> helperListVO2 = new ArrayList<>();

        LocalDate fromDate = helperDocRequestVO.getFromDate();
        LocalDate fromDate1 = helperDocRequestVO.getFromDate();

        for (int i = 0; i < 12; i++) {


            if(i < loopCount) {
                HelperHistoryLogVO helperHistoryLogVO1 = new HelperHistoryLogVO();
                String fromCurrentDate = fromDate1 + " 23:59:59";
                LocalDate toLastDate = fromDate1.plus(-1, ChronoUnit.WEEKS);
                LocalDate result = toLastDate.plus(1, ChronoUnit.DAYS);
                String toDate = result + " 00:00:00";
                count = helperHistoryLogRepository.getVisitorByWeek(UserRoles.VISITOR, helperDocRequestVO.getSocietyId(), toDate, fromCurrentDate);
                helperHistoryLogVO1.setFromDate(fromDate1);
                helperHistoryLogVO1.setToDate(result);
                helperHistoryLogVO1.setCount(count);
                helperListVO1.add(helperHistoryLogVO1);

               fromDate1 = toLastDate;
            }
            if(i < loopCount) {
                HelperHistoryLogVO helperHistoryLogVO = new HelperHistoryLogVO();
                LocalDate result = fromDate.plus(-i, ChronoUnit.DAYS);
                String startTime = result.toString() + " 00:00:00";
                String endTime = result.toString() + " 23:59:59";
                count = helperHistoryLogRepository.getVisitorByWeek(UserRoles.VISITOR, helperDocRequestVO.getSocietyId(), startTime,endTime);
                helperHistoryLogVO.setFromDate(result);
                helperHistoryLogVO.setCount(count);
                helperListVO.add(helperHistoryLogVO);
            }

            HelperHistoryLogVO helperHistoryLogVO2 = new HelperHistoryLogVO();
            LocalDate toLastDate = fromDate.plus(-i, ChronoUnit.MONTHS);

            count = helperHistoryLogRepository.countBasedOnMonth(UserRoles.VISITOR, helperDocRequestVO.getSocietyId(), toLastDate.getMonthValue(),toLastDate.getYear());
            helperHistoryLogVO2.setMonth(toLastDate.getMonth());
            helperHistoryLogVO2.setYear(toLastDate.getYear());
            helperHistoryLogVO2.setCount(count);
            helperListVO2.add(helperHistoryLogVO2);

        }

        if (helperListVO.size() == 0 && helperListVO1.size() == 0 && helperListVO2.size() == 0) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        } else {
            responseVO.setWeeklyCount(helperListVO);
            responseVO.setMonthlyCount(helperListVO1);
            responseVO.setYearlyCount(helperListVO2);

        }

        return responseVO;
    }


    public  List<HelperDocumentVO> getDocumentList(int societyId,int helperTypeId){
        List<HelperDocumentTypeEntity> helperDetails = helperDocumentTypeRepository.findBySocietyIdAndHelperTypeIdAndIsMandatory(societyId, helperTypeId, true);
        return modelMapperUtil.mapList(helperDetails,HelperDocumentVO.class);
    }



    public ResponseVO getHeplerDocument(HelperDocumentVO helperRequestVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.getHelperDocumentByHelperIdInputValidator(helperRequestVO);

        List<HelperDocumentVO> helperEntities = helperDocumentMasterRepository.getBySocietyIdAndHelperId(helperRequestVO.getSocietyId(), helperRequestVO.getHelperId());

        if (helperEntities.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        }else {
            responseVO.setSuccessResponse(Messages.Helper_Found);
            responseVO.setHelperDocVOs(helperEntities);
        }

        return responseVO;
    }


    public ResponseVO recentVisitor(HelperVO helperVO) {
        ResponseVO responseVO = new ResponseVO();
        helperInputValidation.recentVisitorInputValidation(helperVO);

        Pageable pageable = Functions.getPage(helperVO.getPage(), helperVO.getLimit());
        Pageable page = Functions.getPage(0, 300);

//        Specification<HelperHistoryLogEntity> specification = Specification
//                .where(helperVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(helperLog, EntityVariable.SOCIETY_ID, helperVO.getSocietyId()))
//                .and(helperVO.getAreaId() == 0 ? null : SpecificationService.equalSpecification(helperLog, EntityVariable.AREA_ID, helperVO.getSocietyId()))
//                .and(SpecificationService.groupBySpecification(helperLog, EntityVariable.LOGIN_ID))
//                .and(SpecificationService.descendingOrder(helperLog, EntityVariable.ID));
//
//        Page<HelperHistoryLogEntity> helperEntities = helperHistoryLogRepository.findAll(specification, pageable);

        List<HelperListVO> helperListVOS = helperHistoryLogRepository.getRecentVisitor(helperVO.getSocietyId(), UserRoles.VISITOR, page);

        if(helperListVOS.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        Map<String, HelperListVO> map = new TreeMap<>();

        for (HelperListVO helper : helperListVOS){
            if(map.size() == pageable.getPageSize()) break;

            if(!map.containsKey(helper.getLoginId())){
                if (helper.getCompanyId() != 0) {
                    CompanyMasterVO companyMasterVOs = householdService.getCompanyDetails(helper.getCompanyId());
                    helper.setCompanyId(0);
                    helper.setCompanyName(companyMasterVOs.getName());
                    helper.setCompanyLogo(companyMasterVOs.getCompanyLogo());
                }
                map.put(helper.getLoginId(), helper);
            }
        }

        ArrayList<HelperListVO> helperList = new ArrayList<>(map.values());

        responseVO.setSuccessResponse(Messages.Recent_Visitor_list);
        responseVO.setRecentVisitor(helperList);
        return responseVO;
    }
}
