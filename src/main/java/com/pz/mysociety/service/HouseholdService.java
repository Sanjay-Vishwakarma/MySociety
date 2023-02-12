package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.helperEntity.*;
import com.pz.mysociety.entity.householdEntity.*;
import com.pz.mysociety.entity.householdEntity.HelperMappingEntity;
import com.pz.mysociety.entity.userEntity.UserEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.ActivityListVO;
import com.pz.mysociety.model.VO.HelperListVO;
import com.pz.mysociety.model.VO.HouseHoldVO;
import com.pz.mysociety.repository.householdRepository.*;
import com.pz.mysociety.validation.HouseholdInputValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class HouseholdService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired(required = false)
    private GuestRepository guestRepository;

    @Autowired(required = false)
    private VehicleRepository vehicleRepository;

    @Autowired
    private FrequentEntryRepository frequentEntryRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private HelperMappingRepository helperMappingRepository;

    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private HouseholdValidationService householdValidationService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private HouseholdInputValidator householdInputValidator;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public ResponseVO addGuest(GuestVO guestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addGuestInputValidation(guestVO);
        householdValidationService.isUnitValid(guestVO.getUnitId());

        GuestEntity checkGuest = guestRepository.findByUnitIdAndMobileNumber(guestVO.getUnitId(), guestVO.getMobileNumber());

        if (checkGuest == null) {
            GuestEntity guestEntity = modelMapper.map(guestVO, GuestEntity.class);
            guestRepository.save(guestEntity);
            activityLog(guestEntity.getUnitId(), 0, Types.GUEST, Messages.New_Guest_Linked);
            responseVO.setSuccessResponse(Messages.Guest_Added_Success);
        }else {
            responseVO.setFailResponse(Messages.Already_Exist);
        }
        return responseVO;
    }

    public ResponseVO addVehicle(VehicleVO vehicleVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addVehicleInputValidation(vehicleVO);
        householdValidationService.isUnitValid(vehicleVO.getUnitId());

        VehicleEntity vehicleEntity = vehicleRepository.findByUnitIdAndVehicleNumber(vehicleVO.getUnitId(), vehicleVO.getVehicleNumber());

        if(vehicleEntity == null){
            VehicleEntity vehicle = modelMapper.map(vehicleVO, VehicleEntity.class);
            vehicle.setIsActive(true);
            if(Functions.nonNullString(vehicleVO.getPhoto())){

                String fileName = vehicleVO.getVehicleNumber()+vehicleVO.getUnitId()+".png";
                FileHandlingUtil.fileUpload(vehicleVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_VEHICLE,null);
                vehicle.setPhoto(Types.VEHICLE_IMAGES + fileName);
            }
            vehicleRepository.save(vehicle);
            responseVO.setSuccessResponse(Messages.Vehicle_Added_Success);
        }else {
            responseVO.setFailResponse(Messages.Already_Exist);
        }

        return responseVO;
    }

    public ResponseVO updateVehicle(VehicleVO vehicleVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.updateVehicleInputValidation(vehicleVO);

        VehicleEntity vehicleEntity = vehicleRepository.getOne(vehicleVO.getId());

        if(vehicleVO.getName().equals(vehicleEntity.getName()) && vehicleVO.getVehicleNumber().equals(vehicleEntity.getVehicleNumber())
                && vehicleVO.getType().equals(vehicleEntity.getType()) && vehicleVO.getPhoto().equals(vehicleEntity.getPhoto()) &&
                vehicleEntity.getIsActive() == vehicleVO.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }else {
            VehicleEntity vehicle = vehicleRepository.findByUnitIdAndVehicleNumber(vehicleEntity.getUnitId(), vehicleVO.getVehicleNumber());

            if(vehicle != null && vehicleVO.getId() != vehicleEntity.getId()){
                responseVO.setFailResponse(Messages.Vehicle_Exist_Error);
            }else {
                vehicleEntity.setName(vehicleVO.getName());
                vehicleEntity.setVehicleNumber(vehicleVO.getVehicleNumber());
                vehicleEntity.setType(vehicleVO.getType());
                vehicleEntity.setIsActive(vehicleVO.getIsActive());
                if(Functions.nonNullString(vehicleVO.getPhoto())){

                    String fileName = vehicleVO.getVehicleNumber()+vehicleVO.getUnitId()+Functions.getRandomValue()+".png";
                    FileHandlingUtil.fileUpload(vehicleVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_VEHICLE,vehicleEntity.getPhoto());
                    vehicleEntity.setPhoto(Types.VEHICLE_IMAGES + fileName);
                }
                vehicleRepository.save(vehicleEntity);
                responseVO.setSuccessResponse(Messages.Vehicle_Update_Success);
            }
        }
        return responseVO;
    }

    public ResponseVO addFrequentEntry(FrequentEntryVO frequentRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addFrequentEntryInputValidation(frequentRequestVO);
        householdValidationService.isUnitValid(frequentRequestVO.getUnitId());

        FrequentEntryEntity frequentEntryEntity = modelMapper.map(frequentRequestVO, FrequentEntryEntity.class);

        frequentEntryRepository.save(frequentEntryEntity);
        responseVO.setSuccessResponse(Messages.Frequent_Entry_Added_Success);
        return responseVO;
    }

    public ResponseVO addFamilyMember(FamilyMemberVO familyMemberVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addFamilyMemberInputValidation(familyMemberVO);
        householdValidationService.isUnitValid(familyMemberVO.getUnitId());

        if (familyMemberVO.getType().equalsIgnoreCase(Types.ADULT)){

            FamilyMemberEntity familyMember = familyMemberRepository.findByUnitIdAndMobileNumber(familyMemberVO.getUnitId(), familyMemberVO.getMobileNumber());

            if(familyMember != null){
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }else {
                UserVO userVO = new UserVO();
                userVO.setMobileNumber(familyMemberVO.getMobileNumber());

                ResponseVO responseVO1 = userService.isUserMobileExist(userVO);

                if(responseVO1.getUserVO() != null){
                    OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                    ownerMasterVO.setUserId(responseVO1.getUserVO().getId());
                    ownerMasterVO.setUnitId(familyMemberVO.getUnitId());
                    ResponseVO response = societyService.isUserMappingExist(ownerMasterVO);

                    if(response.getOwner() != null){
                        responseVO.setFailResponse(Messages.Already_Exist);
                        return response;
                    }
                }
            }
        }

        FamilyMemberEntity familyMemberEntity = modelMapper.map(familyMemberVO, FamilyMemberEntity.class);
        familyMemberEntity.setIsActive(true);
        if(Functions.nonNullString(familyMemberVO.getPhoto())) {
            String fileName = familyMemberVO.getMobileNumber()+ ".png";
            FileHandlingUtil.fileUpload(familyMemberVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_FAMILY,null);
            familyMemberEntity.setPhoto(Types.FAMILY_IMAGES + fileName);
        }
        familyMemberRepository.save(familyMemberEntity);
        activityLog(familyMemberEntity.getUnitId(), familyMemberEntity.getParentId(), Types.FAMILY, Messages.New_Family_Member_Linked);
        responseVO.setSuccessResponse(Messages.Family_Member_Added_Success);
        return  responseVO;

    }

    public ResponseVO linkHelper(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.linkHelperInputValidation(helperMappingVO);
        householdValidationService.isSocietyAndUnit(helperMappingVO.getSocietyId(), helperMappingVO.getUnitId());

        HelperMappingEntity checkMapping = helperMappingRepository.findByUnitIdAndHelperId(helperMappingVO.getUnitId(), helperMappingVO.getHelperId());

        if(checkMapping != null){

            if(!checkMapping.getIsActive()){
                checkMapping.setIsActive(true);
                helperMappingRepository.save(checkMapping);
                responseVO.setSuccessResponse(Messages.Helper_Linked_Success);
            }else {
                responseVO.setFailResponse(Messages.Helper_Exist_Error);
            }
        }else {
            HelperMappingEntity helperMappingEntity = modelMapper.map(helperMappingVO, HelperMappingEntity.class);
            helperMappingEntity.setIsActive(true);
            helperMappingRepository.save(helperMappingEntity);
            responseVO.setSuccessResponse(Messages.Helper_Linked_Success);
        }

        return responseVO;
    }

    public ResponseVO getHelperDetail(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int averageRating = 0;
        householdInputValidator.getHelperDetailInputValidation(helperMappingVO);

        List<HelperListVO> helperMapping = helperMappingRepository.getHelperDetail(helperMappingVO.getSocietyId(), helperMappingVO.getHelperId());

        if(!helperMapping.isEmpty()){

            List<HelperListVO> helperListVOS = new ArrayList<>();

            for(HelperListVO helperListVO : helperMapping){

                ResponseVO response = societyService.getUnitDetail(helperListVO);
                helperListVO.setAreaName(response.getHelper().getAreaName());
                helperListVO.setUnitType(response.getHelper().getUnitType());
                helperListVO.setUnit(response.getHelper().getUnit());
                helperListVO.setUnitId(0);

                if(helperListVO.getIsActive()){
                    HelperListVO helperList = new HelperListVO();
                    helperList.setAreaName(response.getHelper().getAreaName());
                    helperList.setUnitType(response.getHelper().getUnitType());
                    helperList.setUnit(response.getHelper().getUnit());
                    helperListVOS.add(helperList);
                }

            }

            String allRating = helperMappingRepository.getAverageRating(helperMappingVO.getSocietyId(), helperMappingVO.getHelperId());
            if(Functions.nonNullString(allRating)){
                float a = Float.parseFloat(allRating);
                averageRating = (int) a;
            }
            responseVO.setSuccessResponse(Messages.Helper_Detail_Message);
            responseVO.setAverageRating(averageRating);
            responseVO.setHelperDetail(helperMapping);
            responseVO.setActiveHelperDetail(helperListVOS);
        }else {
            responseVO.setFailResponse(Messages.Helper_Not_Linked);
        }

        return responseVO;
    }

    public ResponseVO addReview(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addReviewInputValidation(helperMappingVO);

        HelperMappingEntity helperMappingEntity = helperMappingRepository.getOne(helperMappingVO.getId());

        if(helperMappingVO.getRating()==helperMappingEntity.getRating()&& helperMappingVO.getReview().equalsIgnoreCase(helperMappingEntity.getReview())){
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }else {
            helperMappingEntity.setRating(helperMappingVO.getRating());
            helperMappingEntity.setReview(helperMappingVO.getReview());
            helperMappingRepository.save(helperMappingEntity);
            responseVO.setSuccessResponse(Messages.Helper_Review_Success);
        }
        return responseVO;

    }

    public ResponseVO unLinkHelper(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.unLinkHelperInputValidation(helperMappingVO);

        HelperMappingEntity helperMappingEntity = helperMappingRepository.getOne(helperMappingVO.getId());

        helperMappingEntity.setRemark(helperMappingVO.getRemark());
        helperMappingEntity.setIsActive(false);
        helperMappingRepository.save(helperMappingEntity);
        responseVO.setSuccessResponse(Messages.Helper_Remove_Success);
        return responseVO;

    }

    public ResponseVO getReview(HelperMappingVO helperMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.getReviewInputValidation(helperMappingVO);

        HelperMappingEntity helperMappingEntity = helperMappingRepository.findByUnitIdAndHelperId(helperMappingVO.getUnitId(), helperMappingVO.getHelperId());
        HelperListVO helperListVO = new HelperListVO();
        helperListVO.setRating(helperMappingEntity.getRating());
        helperListVO.setReview(helperMappingEntity.getReview());
        responseVO.setSuccessResponse(Messages.User_Review_Success);
        responseVO.setHelper(helperListVO);
        return  responseVO;

    }

    public ResponseVO addCompany(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.addCompanyInputValidation(companyMasterVO);

        CompanyMasterEntity companyMasterEntity = companyMasterRepository.findByName(companyMasterVO.getName());

        if(companyMasterEntity == null){
            CompanyMasterEntity companyMaster = modelMapper.map(companyMasterVO, CompanyMasterEntity.class);
            companyMaster.setIsActive(true);
            String fileName = companyMasterVO.getName() + ".png";
            FileHandlingUtil.fileUpload(companyMasterVO.getCompanyLogo(), fileName, DocumentPath.UPLOAD_DIR_PATH_COMPANY_LOGO,null);
            companyMaster.setCompanyLogo(Types.COMPANY_LOGO_IMAGES+fileName);

            companyMasterRepository.save(companyMaster);
            responseVO.setSuccessResponse(Messages.Company_Added_Success);
        }else {
            responseVO.setFailResponse(Messages.Already_Exist);
        }
        return responseVO;
    }

    public ResponseVO getCompany(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        CompanyMasterEntity companyMasterEntity = new CompanyMasterEntity();
        int pages;
        householdInputValidator.getCompanyInputValidation(companyMasterVO);

        Pageable pageable = Functions.getPage(companyMasterVO.getPage(), companyMasterVO.getLimit());

        Specification<CompanyMasterEntity> specification = Specification.where(!Functions.nonNullString(companyMasterVO.getName()) ? null : SpecificationService.likeSpecification(companyMasterEntity, EntityVariable.NAME, companyMasterVO.getName()))
                .and(companyMasterVO.getCompanyTypeId() == 0 ? null : SpecificationService.equalSpecification(companyMasterEntity, EntityVariable.COMPANY_TYPE_ID, companyMasterVO.getCompanyTypeId()))
                .and(Functions.nonNullString(companyMasterVO.getAction()) && companyMasterVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(companyMasterEntity, EntityVariable.IS_ACTIVE, companyMasterVO.getIsActive()) : null);

        Page<CompanyMasterEntity> companyMasterEntityList = companyMasterRepository.findAll(specification, pageable);

            if(companyMasterEntityList.isEmpty()){
                responseVO.setFailResponse(Messages.Not_Found);
            }else {
                List<CompanyMasterVO> activeCompany = modelMapperUtil.mapPage(companyMasterEntityList, CompanyMasterVO.class);

                ResponseVO response = helperService.getCompanyType(activeCompany);

                int count = (int)companyMasterRepository.count(specification);
                pages = Functions.getPagesCount(count);

                responseVO.setPages(pages);
                responseVO.setActiveCompany(response.getActiveCompany());
                responseVO.setCount(count);
                responseVO.setSuccessResponse(Messages.Company_List_Success);
            }

        return responseVO;
    }

    public ResponseVO updateCompany(CompanyMasterVO companyMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.updateCompanyInputValidation(companyMasterVO);

        CompanyMasterEntity companyMasterEntity = companyMasterRepository.getOne(companyMasterVO.getId());

        if(companyMasterVO.getName().equals(companyMasterEntity.getName()) && companyMasterVO.getCompanyTypeId() == companyMasterEntity.getCompanyTypeId()
                && companyMasterVO.getCompanyLogo().equals(companyMasterEntity.getCompanyLogo()) && companyMasterVO.getIsActive() == companyMasterEntity.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }else {

            CompanyMasterEntity companyMaster = companyMasterRepository.findByName(companyMasterVO.getName());

            if(companyMaster != null && companyMaster.getId() != companyMasterVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {

                companyMasterEntity.setName(companyMasterVO.getName());
                companyMasterEntity.setCompanyTypeId(companyMasterVO.getCompanyTypeId());
                if(Functions.nonNullString(companyMasterVO.getCompanyLogo())) {
                    String fileName = companyMasterVO.getName() +"_"+Functions.getRandomValue()+ ".png";
                    FileHandlingUtil.fileUpload(companyMasterVO.getCompanyLogo(), fileName, DocumentPath.UPLOAD_DIR_PATH_COMPANY_LOGO,companyMasterEntity.getCompanyLogo());
                    companyMasterEntity.setCompanyLogo(Types.COMPANY_LOGO_IMAGES + fileName);
                }
                companyMasterEntity.setIsActive(companyMasterVO.getIsActive());
                companyMasterRepository.save(companyMasterEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;
    }

//    public ResponseVO addCompanyType(TypeVO typeVO) throws PZConstraintViolationException {
//        ResponseVO responseVO = new ResponseVO();
//        householdInputValidator.addTypeInputValidation(typeVO);
//
//        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByType(typeVO.getType());
//
//        if(companyTypeEntity == null){
//            CompanyTypeEntity companyType = modelMapper.map(typeVO, CompanyTypeEntity.class);
//            companyType.setIsActive(true);
//            companyTypeRepository.save(companyType);
//            responseVO.setSuccessResponse(Messages.Company_Type_Added_Success);
//        }else {
//            responseVO.setFailResponse(Messages.Already_Exist);
//        }
//        return responseVO;
//    }
//
//    public ResponseVO updateCompanyType(TypeVO typeVO) throws PZConstraintViolationException {
//        ResponseVO responseVO = new ResponseVO();
//        householdInputValidator.updateTypeInputValidation(typeVO);
//        CompanyTypeEntity companyTypeEntity = companyTypeRepository.getOne(typeVO.getId());
//
//        if(typeVO.getType().equalsIgnoreCase(companyTypeEntity.getType()) && typeVO.getIsActive() == companyTypeEntity.getIsActive()){
//            responseVO.setFailResponse(Messages.No_Changes_Found);
//        }else {
//
//            CompanyTypeEntity checkRepeatedValue = companyTypeRepository.findByType(typeVO.getType());
//
//            if (checkRepeatedValue != null && checkRepeatedValue.getId() != typeVO.getId()) {
//                responseVO.setFailResponse(Messages.Type_Exist_Error);
//            }else {
//
//                companyTypeEntity.setType(typeVO.getType());
//                companyTypeEntity.setIsActive(typeVO.getIsActive());
//                companyTypeRepository.save(companyTypeEntity);
//                responseVO.setSuccessResponse(Messages.Type_Update_Success);
//            }
//        }
//        return responseVO;
//
//    }
//
//    public ResponseVO getCompanyType() {
//        ResponseVO responseVO = new ResponseVO();
//
//        List<CompanyTypeEntity> activeCompanyTypeEntity = companyTypeRepository.findByIsActive(true);
//        List<CompanyTypeEntity> inActiveCompanyTypeEntity = companyTypeRepository.findByIsActive(false);
//
//        if(activeCompanyTypeEntity == null && inActiveCompanyTypeEntity == null){
//            responseVO.setFailResponse(Messages.Not_Found);
//        }else {
//
//            List<TypeVO> activeType = modelMapperUtil.mapList(activeCompanyTypeEntity, TypeVO.class);
//            List<TypeVO> inActiveType = modelMapperUtil.mapList(inActiveCompanyTypeEntity, TypeVO.class);
//
//            responseVO.setActiveType(activeType);
//            responseVO.setInActiveType(inActiveType);
//
//            responseVO.setSuccessResponse(Messages.Company_Type_List_Success);
//        }
//        return responseVO;
//    }

    public ResponseVO getHouseHoldType(HouseholdVO householdVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.getHouseHoldTypeInputValidation(householdVO);


        if(householdVO.getHouseholdType().equalsIgnoreCase(Types.VEHICLE)){

            List<HouseHoldVO> vehicleHouseHold = vehicleRepository.getVehicleDetail(householdVO.getUnitId(), true);

            if(!vehicleHouseHold.isEmpty()){
                responseVO.setHouseHold(vehicleHouseHold);
                responseVO.setSuccessResponse(Messages.Vehicle_List_Success);
            }else {
                responseVO.setFailResponse(Types.VEHICLE + Messages.Not_Found);
            }
            return responseVO;
        }else if (householdVO.getHouseholdType().equalsIgnoreCase(Types.GUEST)){

            List<HouseHoldVO> guestHouseHold = guestRepository.getGuestDetail(householdVO.getUnitId());

            if(!guestHouseHold.isEmpty()){
                responseVO.setHouseHold(guestHouseHold);
                responseVO.setSuccessResponse(Messages.Guest_Added_Success);
            }else {
                responseVO.setFailResponse(Types.GUEST + Messages.Not_Found);
            }
            return responseVO;
        }else if (householdVO.getHouseholdType().equalsIgnoreCase(Types.FREQUENT_ENTRY)){

            List<FrequentEntryEntity> frequentEntryHouseHold = frequentEntryRepository.findByUnitId(householdVO.getUnitId());

            if(!frequentEntryHouseHold.isEmpty()){
                List<FrequentEntryVO> frequentEntryVO = modelMapperUtil.mapList(frequentEntryHouseHold, FrequentEntryVO.class);
                responseVO.setFrequentEntry(frequentEntryVO);
                responseVO.setSuccessResponse(Messages.Frequent_Entry_List_Success);
            }else {
                responseVO.setFailResponse(Messages.Not_Found);
            }

            return responseVO;
        }else if(householdVO.getHouseholdType().equalsIgnoreCase(Types.FAMILY)) {
            List<FamilyMemberEntity> familyMember = familyMemberRepository.getFamilyMember(householdVO.getUnitId());
            ResponseVO response = societyService.getFamilyMemberList(householdVO);

            if(familyMember.isEmpty() && response.getOwnerList().isEmpty()){
                responseVO.setFailResponse(Types.FAMILY + Messages.Not_Found);
            }else {
                List<FamilyMemberVO> familyMemberVO = modelMapperUtil.mapList(familyMember, FamilyMemberVO.class);

                responseVO.setOwnerList(response.getOwnerList());
                responseVO.setFamilyList(familyMemberVO);
                responseVO.setSuccessResponse(Messages.Family_Member_List_Success);
            }
            return responseVO;

        }else if(householdVO.getHouseholdType().equalsIgnoreCase(Types.HELPER)){

            List<HelperListVO> helperList = helperMappingRepository.getHelperMapping(householdVO.getUnitId(), true);

            if(!helperList.isEmpty()){
                // Get Helper Detail
                ResponseVO response = helperService.getHelperDetail(helperList);
                responseVO.setHelperDetail(response.getHelperDetail());
                responseVO.setSuccessResponse(Messages.Helper_List_Success);
            }else {
                responseVO.setFailResponse(Types.HELPER + Messages.Not_Found);
            }
            return responseVO;
        }else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }
    public void inserHelpermapping(HelperVO helperMappingVO){
            HelperMappingEntity helperMappingEntity=modelMapper.map(helperMappingVO, HelperMappingEntity.class);
            helperMappingEntity.setSocietyId(helperMappingVO.getSocietyId());
            helperMappingEntity.setUnitId(helperMappingVO.getUnitId());
            helperMappingEntity.setHelperId(helperMappingVO.getHelperId());
            helperMappingEntity.setIsActive(true);
            helperMappingRepository.save(helperMappingEntity);
    }

    public void isFamilyMember(SocietyMappingVO societyMappingVO) {

        FamilyMemberEntity familyMemberEntity = familyMemberRepository.findByUnitIdAndMobileNumberAndIsActive(societyMappingVO.getUnit().getId(), societyMappingVO.getUser().getMobileNumber(), true);
        if(familyMemberEntity != null){
            familyMemberEntity.setIsActive(false);
            familyMemberRepository.save(familyMemberEntity);
        }
    }

    public void activityLog(int unitId, int userId, String activityType, String description){
        ActivityLogEntity activityLogEntity = new ActivityLogEntity();
        activityLogEntity.setUnitId(unitId);
        activityLogEntity.setUserId(userId);
        activityLogEntity.setActivityType(activityType);
        activityLogEntity.setDescription(description);
        activityLogRepository.save(activityLogEntity);
    }
    public ResponseVO getActivityLog(ActivityLogVO activityRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int pages;
        householdInputValidator.getActivityLogInputValidation(activityRequestVO);
        householdValidationService.isUnitansUserMapping(activityRequestVO.getUserId(),activityRequestVO.getUnitId());

        Pageable pageable = Functions.getPage(activityRequestVO.getPage(), activityRequestVO.getLimit());

        List<ActivityLogEntity> activityLogEntity=activityLogRepository.findByUnitIdAndUserId(activityRequestVO.getUnitId(), activityRequestVO.getUserId(), pageable);
        if (activityLogEntity.isEmpty()){
            responseVO.setFailResponse(Messages.Activity_Log_Failed);
            return responseVO;
        }

        int count = activityLogRepository.countByUserIdAndUnitId(activityRequestVO.getUserId(), activityRequestVO.getUnitId());
        pages = Functions.getPagesCount(count);

        responseVO.setPages(pages);
        responseVO.setSuccessResponse(Messages.Activity_Log_Success);
        responseVO.setCount(count);
        List<ActivityLogVO> activityLogVO = modelMapperUtil.mapList(activityLogEntity, ActivityLogVO.class);
        responseVO.setActivityLogList(activityLogVO);
        return responseVO;
    }

    public ResponseVO getTopActivityLog(ActivityLogVO activityLogVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.getActivityLogInputValidation(activityLogVO);
        householdValidationService.isUnitansUserMapping(activityLogVO.getUserId(),activityLogVO.getUnitId());
        List<ActivityLogEntity> activityLogEntity=activityLogRepository.getTopActivityLog(activityLogVO.getUnitId(), activityLogVO.getUserId());
        if(activityLogEntity.isEmpty()){
            responseVO.setFailResponse(Messages.Activity_Log_Failed);
            return responseVO;
        }
        List<ActivityLogVO> activityLogList = modelMapperUtil.mapList(activityLogEntity, ActivityLogVO.class);
        responseVO.setActivityLogList(activityLogList);
        responseVO.setSuccessResponse(Messages.Activity_Log_Success);
        return responseVO;
    }

    public ResponseVO updateFamilyMember(FamilyMemberVO familyMemberVO) {
        ResponseVO responseVO = new ResponseVO();
        ResponseVO responseVO1 = new ResponseVO();
        UserVO userVO = new UserVO();

        FamilyMemberEntity familyMemberEntity = familyMemberRepository.getOne(familyMemberVO.getId());
        familyMemberVO.setType(familyMemberEntity.getType());
        householdInputValidator.updateFamilyMemberInputValidation(familyMemberVO);

        if(familyMemberEntity.getType().equalsIgnoreCase(Types.ADULT)){
            if(familyMemberEntity.getName().equalsIgnoreCase(familyMemberVO.getName()) && familyMemberEntity.getMobileNumber().equalsIgnoreCase(familyMemberVO.getMobileNumber())
                    && familyMemberEntity.getPhoto().equals(familyMemberVO.getPhoto())) {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }else {

                FamilyMemberEntity familyMember = familyMemberRepository.findByUnitIdAndMobileNumber(familyMemberEntity.getUnitId(), familyMemberVO.getMobileNumber());

                userVO.setMobileNumber(familyMemberVO.getMobileNumber());

                ResponseVO response = userService.isUserMobileExist(userVO);

                if(response.getUser() != null){
                    OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                    ownerMasterVO.setUserId(response.getUser().getId());
                    ownerMasterVO.setUnitId(familyMemberEntity.getUnitId());

                    responseVO1 = societyService.isUserMappingExist(ownerMasterVO);
                }

                if((familyMember != null && familyMember.getId() != familyMemberVO.getId()) || responseVO1.getOwner() != null){
                    responseVO.setFailResponse(Messages.Mobile_Exist);
                }else {
                    familyMemberEntity.setName(familyMemberVO.getName());
                    familyMemberEntity.setMobileNumber(familyMemberVO.getMobileNumber());
                    if(Functions.nonNullString(familyMemberVO.getPhoto())) {
                        String fileName = familyMemberVO.getMobileNumber()+"_"+Functions.getRandomValue() + ".png";
                        FileHandlingUtil.fileUpload(familyMemberVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_FAMILY,familyMemberEntity.getPhoto());
                        familyMemberEntity.setPhoto(Types.FAMILY_IMAGES + fileName);
                    }
                    familyMemberRepository.save(familyMemberEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }

            }
        }else {

            if(familyMemberEntity.getName().equalsIgnoreCase(familyMemberVO.getName()) && familyMemberEntity.getMonitor().equalsIgnoreCase(familyMemberVO.getMonitor())) {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }else {
                familyMemberEntity.setName(familyMemberVO.getName());
                familyMemberEntity.setMonitor(familyMemberVO.getMonitor());
                if(Functions.nonNullString(familyMemberVO.getPhoto())) {
                    String fileName = familyMemberVO.getMobileNumber() +"_"+Functions.getRandomValue()+ ".png";
                    FileHandlingUtil.fileUpload(familyMemberVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_FAMILY,familyMemberEntity.getPhoto());
                    familyMemberEntity.setPhoto(Types.FAMILY_IMAGES + fileName);
                }
                familyMemberRepository.save(familyMemberEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }

        return responseVO;
    }

    public ResponseVO deleteFamilyMember(FamilyMemberVO familyMemberVO) {
        ResponseVO responseVO = new ResponseVO();
        householdInputValidator.deleteFamilyMemberInputValidation(familyMemberVO);

        familyMemberRepository.deleteById(familyMemberVO.getId());

        responseVO.setSuccessResponse(Messages.Family_Member_Delete_Success);
        return responseVO;
    }

    public List<Integer> getHelperMappingList(int societyId, int helperId){
        List<Integer> helperEntity =  helperMappingRepository.getHelperMappingDetails(societyId, helperId, true);
        return helperEntity;
    }

    public CompanyMasterVO getCompany(int id)
    {
        CompanyMasterEntity companyMasterEntity= companyMasterRepository.findById( id);
        CompanyMasterVO companyMasterVO= modelMapper.map(companyMasterEntity, CompanyMasterVO.class);
        return companyMasterVO ;
    }


    public CompanyMasterVO getCompanyDetails(int id)
    {
        CompanyMasterEntity companyMasterEntity = companyMasterRepository.findByIdAndIsActive(id, true);
        CompanyMasterVO companyMasterVO= modelMapper.map(companyMasterEntity, CompanyMasterVO.class);

        return companyMasterVO;
    }
}
