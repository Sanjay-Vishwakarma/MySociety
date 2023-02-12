package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZApplicationException;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.societyEntity.*;
import com.pz.mysociety.entity.societyEntity.OwnerMasterEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.Request.SocietyRequestVO.ParkingSlotVO;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.*;

import com.pz.mysociety.repository.societyRepository.*;
import com.pz.mysociety.service.societyUtilService.SocietyUtilService;
import com.pz.mysociety.validation.SocietyInputValidator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private SocietyInputValidator societyInputValidator;

    @Autowired
    private SocietyValidationService societyValidationService;

    @Autowired
    private SocietyUserMappingRepository societyUserMappingRepository;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private AreaMasterRepository areaMasterRepository;

    @Autowired
    private AreaTypeRepository areaTypeRepository;

    @Autowired
    private UnitMasterRepository unitMasterRepository;

    @Autowired
    private UnitDocumentTypeRepository unitDocumentTypeRepository;

    @Autowired
    private UnitDocumentMasterRepository unitDocumentMasterRepository;

    @Autowired
    private OwnerMasterRepository ownerMasterRepository;

    @Autowired
    private EmergencyNumberRepository emergencyNumberRepository;

    @Autowired
    private SocietyDocumentRepository societyDocumentRepository;

    @Autowired
    private SocietyDocumentTypeRepository societyDocumentTypeRepository;

    @Autowired
    private MobileConfigurationRepository mobileConfigurationRepository;

    @Autowired
    private CityMasterRepository cityMasterRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DocumentMasterRepository documentMasterRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private SocietyReviewRepository societyReviewRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private SocietyUtilService societyUtilService;


    public ResponseVO addSociety(SocietyVO societyVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addSocietyInputValidation(societyVO);
        societyValidationService.isSocietyExist(societyVO);
        societyVO.setIsActive(true);
        SocietyEntity societyEntity = modelMapper.map(societyVO, SocietyEntity.class);
        societyEntity.setSocietyStatus(UserStatus.SOCIETY_REJECTED);
        if (Functions.nonNullString(societyVO.getPhoto())) {

            String fileName = societyVO.getSocietyName() + ".png";
            FileHandlingUtil.fileUpload(societyVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_SOCIETY, null);
            societyEntity.setPhoto(Types.SOCIETY_IMAGES + fileName);
        }
        SocietyEntity societyEntity1 = societyRepository.save(societyEntity);
        SocietyVO societyVO1 = new SocietyVO();
        societyVO1.setId(societyEntity1.getId());
        responseVO.setSociety(societyVO1);
        responseVO.setSuccessResponse(Messages.Success_Society_Added);
        return responseVO;

    }

    public ResponseVO updateSociety(SocietyVO societyVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateSocietyInputValidation(societyVO);
        boolean isChange = false;
        SocietyEntity societyEntity = societyRepository.getOne(societyVO.getId());

        SocietyEntity checkSociety = societyRepository.findBySocietyNameAndPincode(societyVO.getSocietyName(), societyVO.getPincode());

        if (checkSociety != null && checkSociety.getId() != societyEntity.getId()) {
            responseVO.setFailResponse(Messages.Already_Exist);
        } else {
            if (Functions.nonNullString(societyVO.getPhoto())) {

                String fileName = societyVO.getSocietyName() + Functions.getRandomValue() + ".png";
                FileHandlingUtil.fileUpload(societyVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_SOCIETY, societyEntity.getPhoto());
                societyEntity.setPhoto(Types.SOCIETY_IMAGES + fileName);
                isChange = true;
            }

            if (Functions.compareValue(societyEntity.getRegistrationNumber(), societyVO.getRegistrationNumber())) {
                societyEntity.setRegistrationNumber(societyVO.getRegistrationNumber());
                isChange = true;
            }

            if (Functions.compareValue(societyEntity.getSocietyName(), societyVO.getSocietyName())) {
                societyEntity.setSocietyName(societyVO.getSocietyName());
                isChange = true;
            }

            if (Functions.compareValue(societyEntity.getAddress(), societyVO.getAddress())) {
                societyEntity.setAddress(societyVO.getAddress());
                isChange = true;
            }
            if (Functions.compareValue(societyEntity.getSocietyBlock(), societyVO.getSocietyBlock())) {
                societyEntity.setSocietyBlock(societyVO.getSocietyBlock());
                isChange = true;
            }

            if (Functions.compareValue(societyEntity.getLandmark(), societyVO.getLandmark())) {
                societyEntity.setLandmark(societyVO.getLandmark());
                isChange = true;
            }
            if (Functions.compareValue(societyEntity.getState(), societyVO.getState())) {
                societyEntity.setState(societyVO.getState());
                isChange = true;
            }

            if (Functions.compareValue(societyEntity.getCity(), societyVO.getCity())) {
                societyEntity.setCity(societyVO.getCity());
                isChange = true;
            }
            if (Functions.compareValue(societyEntity.getPincode(), societyVO.getPincode())) {
                societyEntity.setPincode(societyVO.getPincode());
                isChange = true;
            }
            if (societyEntity.getIsActive() != societyVO.getIsActive()) {
                societyEntity.setIsActive(societyVO.getIsActive());
                isChange = true;
            }

            if (isChange) {
                societyRepository.save(societyEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            } else {
                responseVO.setFailResponse(Messages.No_Changes_Found);
                return responseVO;
            }
            //return responseVO;

        }
        return responseVO;
    }

    public ResponseVO getSocietyList(SocietyVO societyVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        SocietyEntity societyEntity = new SocietyEntity();

        Pageable pageable = Functions.getPage(societyVO.getPage(), societyVO.getLimit());

        Specification<SocietyEntity> specification = Specification
                .where(!Functions.nonNullString(societyVO.getSocietyName()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.SOCIETY_NAME, societyVO.getSocietyName()))
                .and(!Functions.nonNullString(societyVO.getSocietyBlock()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.SOCIETY_BLOCK, societyVO.getSocietyBlock()))
                .and(!Functions.nonNullString(societyVO.getState()) ? null : SpecificationService.equalSpecification(societyEntity, EntityVariable.STATE, societyVO.getState()))
                .and(!Functions.nonNullString(societyVO.getCity()) ? null : SpecificationService.equalSpecification(societyEntity, EntityVariable.CITY, societyVO.getCity()))
                .and(!Functions.nonNullString(societyVO.getPincode()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.PINCODE, societyVO.getPincode()))
                .and(!Functions.nonNullString(societyVO.getSocietyStatus()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.SOCIETY_STATUS, societyVO.getSocietyStatus()))
                .and(Functions.nonNullString(societyVO.getAction()) && societyVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(societyEntity, EntityVariable.IS_ACTIVE, societyVO.getIsActive()) : null);

        Page<SocietyEntity> societyEntityList = societyRepository.findAll(specification, pageable);
        if (!societyEntityList.isEmpty()) {

            List<SocietyVO> societyVOList = modelMapperUtil.mapPage(societyEntityList, SocietyVO.class);

            int count = (int) societyRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setCount(count);
            responseVO.setSocietyList(societyVOList);
            responseVO.setSuccessResponse(Messages.Society_List);
        } else {
            responseVO.setFailResponse(Messages.No_Society_Found);
        }
        return responseVO;
    }

    public ResponseVO linkSocietyUser(SocietyMappingVO societyMappingVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.linkSocietyInputValidation(societyMappingVO);
        societyValidationService.isUserExist(societyMappingVO.getUser().getId());
        SocietyUserEntity societyUserEntity = new SocietyUserEntity();

//        SocietyUserEntity societyUser = societyUserMappingRepository.getSocietyRole(societyMappingVO.getSociety().getId(), societyMappingVO.getSocietyRole(), UserStatus.USER_REJECTED, true);

//        if(!societyUser.isEmpty()){
//            responseVO.setFailResponse(Messages.Request_Already_Exist);
//        }else {

        if (societyMappingVO.getSociety().getId() != 0) {
            societyValidationService.isSocietyValid(societyMappingVO.getSociety().getId());
            societyUserEntity.setSocietyId(societyMappingVO.getSociety().getId());

        } else {
            ResponseVO responseVO1 = this.addSociety(societyMappingVO.getSociety());
            societyUserEntity.setSocietyId(responseVO1.getSociety().getId());
        }

//            societyUserEntity.setSocietyRole(societyMappingVO.getSocietyRole());
        societyUserEntity.setSocietyUserId(societyMappingVO.getUser().getId());
        societyUserEntity.setIsActive(true);

        SocietyEntity societyEntity = societyRepository.getOne(societyUserEntity.getSocietyId());

        if (societyEntity.getSocietyStatus().equalsIgnoreCase(UserStatus.SOCIETY_APPROVED) || societyEntity.getSocietyStatus().equalsIgnoreCase(UserStatus.SOCIETY_PENDING)) {
            societyUserEntity.setAdminStatus(UserStatus.USER_PENDING);
        } else {
            societyUserEntity.setAdminStatus(UserStatus.USER_DOCUMENT_PENDING);
        }
        societyUserMappingRepository.save(societyUserEntity);

        //Send Mail to Super Admin

        responseVO.setSuccessResponse(Messages.Society_User_Linked);
//        }
        return responseVO;

    }

    public ResponseVO getSocietyUserMapping(SocietyMappingVO societyMappingVO) {
        ResponseVO responseVO = new ResponseVO();
        SocietyVO societyVO = new SocietyVO();

        SocietyUserEntity societyUserEntity = societyUserMappingRepository.getSocietyUserDetail(societyMappingVO.getUser().getId(), UserStatus.USER_REJECTED);

        if (societyUserEntity != null) {
            SocietyEntity societyEntity = societyRepository.findSocietyById(societyUserEntity.getSocietyId());
            societyVO = modelMapper.map(societyEntity, SocietyVO.class);
//            responseVO.setSocietyRole(societyUserEntity.getSocietyRole());
            responseVO.setId(societyUserEntity.getId());
            responseVO.setAdminStatus(societyUserEntity.getAdminStatus());
        } else {

            Pageable pageable = PageRequest.of(0, 1);

            List<SocietyUserEntity> societyUser = societyUserMappingRepository.getUser(societyMappingVO.getUser().getId(), pageable);

            if (!societyUser.isEmpty()) {
                responseVO.setAdminStatus(societyUser.get(0).getAdminStatus());
                responseVO.setRemark(societyUser.get(0).getMessage());
            }
        }

        responseVO.setSociety(societyVO);
        return responseVO;
    }

    public ResponseVO addArea(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addAreaInputValidation(areaMasterVO);
        societyValidationService.isAreaExist(areaMasterVO);

        AreaTypeEntity areaTypeEntity = areaTypeRepository.getOne(areaMasterVO.getAreaTypeId());
        AreaMasterEntity areaMasterEntity = modelMapper.map(areaMasterVO, AreaMasterEntity.class);
        areaMasterEntity.setIsActive(true);
        AreaMasterEntity areaMasterEntity1 = areaMasterRepository.save(areaMasterEntity);

        if (!areaTypeEntity.getType().equalsIgnoreCase(AreaType.Building)) {

            UnitMasterEntity unitMasterEntity = new UnitMasterEntity();
            unitMasterEntity.setSocietyId(areaMasterEntity1.getSocietyId());
            unitMasterEntity.setAreaId(areaMasterEntity1.getId());
            unitMasterEntity.setAreaTypeId(areaTypeEntity.getId());
            unitMasterEntity.setFloor(areaMasterVO.getFloor());
            unitMasterEntity.setIsParking(areaMasterVO.getIsParking());
            unitMasterEntity.setIsActive(true);

            if (areaMasterEntity1.getFloor() != 0) {
                unitMasterEntity.setFloor(areaMasterEntity1.getFloor());
            }

            unitMasterRepository.save(unitMasterEntity);

        }
        responseVO.setSuccessResponse(areaTypeEntity.getType() + Messages.Successfully_Added);
        return responseVO;

    }

    public ResponseVO getAreaList(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int pages = 0;
//        int inActivePages = 0;
        List<UnitListVO> activeUnitListVO;
//        List<UnitListVO> inActiveUnitListVO;
        societyInputValidator.getAreaInputValidation(areaMasterVO);

        Pageable pageable = Functions.getPage(areaMasterVO.getPage(), areaMasterVO.getLimit());

        if (areaMasterVO.getAreaType() == null) {

            int count = areaMasterRepository.getResidentialCount(areaMasterVO.getSocietyId(), true, true);
            pages = Functions.getPagesCount(count);

            activeUnitListVO = areaMasterRepository.getAreaDetail(areaMasterVO.getSocietyId(), true, true, pageable);

            if (activeUnitListVO != null) {

                activeUnitListVO.forEach(a -> {
                    if (!a.getAreaType().equalsIgnoreCase(AreaType.Building)) {
                        UnitMasterEntity unitMasterEntity = unitMasterRepository.findByAreaId(a.getAreaId());
                        if (unitMasterEntity != null) {
                            a.setUnitId(unitMasterEntity.getId());
                        }
                    }
                });

                responseVO.setPages(pages);
                responseVO.setCount(count);
                responseVO.setActiveAreaList(activeUnitListVO);
                responseVO.setSuccessResponse(Messages.Area_List_Message);
            } else {
                responseVO.setFailResponse(Messages.Not_Found);
            }

        } else {

            AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(areaMasterVO.getAreaType());

            if (areaTypeEntity != null) {

                if (!areaTypeEntity.getType().equalsIgnoreCase(AreaType.Building)) {

                    int count = unitMasterRepository.countBySocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId());
                    pages = Functions.getPagesCount(count);
                    responseVO.setCount(count);
                    activeUnitListVO = unitMasterRepository.getSocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), pageable);
//                    inActiveUnitListVO = unitMasterRepository.getSocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), false, pageable);

                } else {

                    int count = areaMasterRepository.countBySocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId());
                    pages = Functions.getPagesCount(count);
                    responseVO.setCount(count);

                    activeUnitListVO = areaMasterRepository.getSocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), pageable);
//                    inActiveUnitListVO = areaMasterRepository.getSocietyIdAndAreaTypeId(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), false, pageable);
                }

                if (activeUnitListVO.isEmpty()) {
                    responseVO.setFailResponse(Messages.NO + areaTypeEntity.getType() + Messages.Found);
                } else {
                    responseVO.setPages(pages);
//                    responseVO.setInActivePages(inActivePages);
                    responseVO.setSuccessResponse(areaTypeEntity.getType() + Messages.Society_List);
                    responseVO.setActiveAreaList(activeUnitListVO);
//                    responseVO.setInActiveAreaList(inActiveUnitListVO);
                }
            } else {
                responseVO.setFailResponse(Messages.Area_Type_Invalid);
            }
        }
        return responseVO;

    }

    public ResponseVO updateAreaDetail(AreaMasterVO areaMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateAreaInputValidation(areaMasterVO);

        AreaTypeEntity areaTypeEntity = areaTypeRepository.getOne(areaMasterVO.getAreaTypeId());

        AreaMasterEntity areaMasterEntity = areaMasterRepository.getOne(areaMasterVO.getId());

        if (!areaTypeEntity.getType().equalsIgnoreCase(AreaType.Building)) {
            UnitMasterEntity unitMasterEntity = unitMasterRepository.findByAreaId(areaMasterVO.getId());

            if (areaMasterEntity.getAreaName().equalsIgnoreCase(areaMasterVO.getAreaName()) && areaMasterEntity.getFloor() == areaMasterVO.getFloor()
                    && areaMasterVO.getIsParking() == unitMasterEntity.getIsParking() && areaMasterEntity.getIsActive() == areaMasterVO.getIsActive()) {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            } else {
                AreaMasterEntity areaMasterEntity1 = areaMasterRepository.findBySocietyIdAndAreaTypeIdAndAreaName(areaMasterEntity.getSocietyId(), areaMasterEntity.getAreaTypeId(), areaMasterVO.getAreaName());
                if (areaMasterEntity1 != null && areaMasterEntity1.getId() != areaMasterVO.getId()) {
                    responseVO.setFailResponse(ValidationMessages.Area_Exist_Error);
                } else {
                    areaMasterEntity.setAreaName(areaMasterVO.getAreaName());
                    areaMasterEntity.setFloor(areaMasterVO.getFloor());
                    areaMasterEntity.setIsActive(areaMasterVO.getIsActive());
                    areaMasterRepository.save(areaMasterEntity);

                    unitMasterEntity.setFloor(areaMasterVO.getFloor());
                    unitMasterEntity.setIsParking(areaMasterVO.getIsParking());
                    unitMasterEntity.setIsActive(areaMasterVO.getIsActive());
                    unitMasterRepository.save(unitMasterEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }
            }
        } else {

            if (areaMasterEntity.getAreaName().equalsIgnoreCase(areaMasterVO.getAreaName()) && areaMasterEntity.getFloor() == areaMasterVO.getFloor() && areaMasterEntity.getIsActive() == areaMasterVO.getIsActive()) {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            } else {
                AreaMasterEntity areaMasterEntity1 = areaMasterRepository.findBySocietyIdAndAreaTypeIdAndAreaName(areaMasterEntity.getSocietyId(), areaMasterEntity.getAreaTypeId(), areaMasterVO.getAreaName());
                if (areaMasterEntity1 != null && areaMasterEntity1.getId() != areaMasterVO.getId()) {
                    responseVO.setFailResponse(ValidationMessages.Area_Exist_Error);
                } else {
                    areaMasterEntity.setAreaName(areaMasterVO.getAreaName());
                    areaMasterEntity.setFloor(areaMasterVO.getFloor());
                    areaMasterEntity.setIsActive(areaMasterVO.getIsActive());
                    areaMasterRepository.save(areaMasterEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }
            }
        }
        return responseVO;

    }

    public ResponseVO addUnitList(List<UnitMasterVO> unitMasterVO) throws PZApplicationException {
        ResponseVO responseVO = new ResponseVO();

        for (UnitMasterVO unitMasterVO1 : unitMasterVO) {
            societyInputValidator.addUnitListInputValidation(unitMasterVO1);

            UnitMasterEntity checkRepeatedUnit = unitMasterRepository.findByAreaIdAndUnit(unitMasterVO1.getAreaId(), unitMasterVO1.getUnit());

            if (checkRepeatedUnit != null) {
                throw new PZApplicationException(Messages.Duplicate_Unit_Data + checkRepeatedUnit.getFloor());
            } else {
                UnitMasterEntity unitMasterEntity = modelMapper.map(unitMasterVO1, UnitMasterEntity.class);
                unitMasterEntity.setIsActive(true);
                unitMasterRepository.save(unitMasterEntity);
            }

        }
        responseVO.setSuccessResponse(Messages.Successfully_Added);
        return responseVO;

    }

    public ResponseVO getUnitList(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int pages;
        societyInputValidator.getUnitListInputValidation(unitMasterVO);

        Pageable pageable = Functions.getPage(unitMasterVO.getPage(), unitMasterVO.getLimit());
        Pageable searchPageable = Functions.getPage(0, unitMasterVO.getLimit());

        if (unitMasterVO.getAreaType() == null) {
            List<UnitMasterVO> unitActiveList;

            AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(AreaType.Flat);

            if (unitMasterVO.getUnit() != null) {
                unitActiveList = unitMasterRepository.getSearchUnit(unitMasterVO.getUnit(), true, unitMasterVO.getAreaId(), areaTypeEntity.getId(), searchPageable);
            } else {
                unitActiveList = unitMasterRepository.getActiveUnit(true, unitMasterVO.getAreaId(), areaTypeEntity.getId(), searchPageable);
            }

            responseVO.setSuccessResponse(Messages.Unit_List_Success);
            responseVO.setActiveUnitList(unitActiveList);

        } else {

            AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(unitMasterVO.getAreaType());
            if (areaTypeEntity == null) {
                responseVO.setFailResponse(Messages.Area_Type_Invalid);
            } else {

                List<UnitMasterVO> unitActiveList = unitMasterRepository.getSocietyIdAreaId(unitMasterVO.getSocietyId(), unitMasterVO.getAreaId(), areaTypeEntity.getId(), pageable);

                if (unitActiveList.isEmpty()) {
                    responseVO.setFailResponse(Messages.Not_Found);
                } else {

                    int count = unitMasterRepository.countBySocietyIdAndAreaIdAndAreaTypeId(unitMasterVO.getSocietyId(), unitMasterVO.getAreaId(), areaTypeEntity.getId());
                    pages = Functions.getPagesCount(count);

                    responseVO.setPages(pages);
                    responseVO.setSuccessResponse(areaTypeEntity.getType() + Messages.Society_List);
                    responseVO.setActiveUnitList(unitActiveList);
                    responseVO.setCount(count);
                }
            }
        }
        return responseVO;

    }

    public ResponseVO updateUnitDetail(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateUnitInputValidation(unitMasterVO);
        UnitMasterEntity unitMasterEntity = unitMasterRepository.getOne(unitMasterVO.getId());
        boolean isChange = false;

        if(Functions.compareValue(unitMasterEntity.getUnit(), unitMasterVO.getUnit())){
            UnitMasterEntity unitMaster = unitMasterRepository.findByAreaIdAndUnit(unitMasterEntity.getAreaId(), unitMasterEntity.getUnit());
            if(unitMaster != null){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {
                unitMasterEntity.setUnit(unitMasterVO.getUnit());
                isChange = true;
            }
        }

        if(unitMasterEntity.getFloor() != unitMasterVO.getFloor()){
            unitMasterEntity.setFloor(unitMasterVO.getFloor());
            isChange = true;
        }

        if(unitMasterEntity.getIsActive() != unitMasterVO.getIsActive()){
            unitMasterEntity.setIsActive(unitMasterVO.getIsActive());
            isChange = true;
        }

        if(unitMasterEntity.getIsParking() != unitMasterVO.getIsParking()){
            unitMasterEntity.setIsActive(unitMasterVO.getIsActive());
            isChange = true;
        }

        if(!unitMasterVO.getParkingSlot().isEmpty()){

            for(Integer parkingId : unitMasterVO.getParkingSlot()){

                ParkingSlotEntity parkingSlotEntity = parkingSlotRepository.getOne(parkingId);

                if(parkingSlotEntity.getIsOccupied()){
                    responseVO.setFailResponse(Messages.Parking_Slot_Occupied);
                    return responseVO;
                }

                parkingSlotEntity.setUnitIdMapping(unitMasterEntity.getId());
                parkingSlotEntity.setIsOccupied(true);
                parkingSlotRepository.save(parkingSlotEntity);
            }

            isChange = true;

        }

        if(isChange){
            unitMasterRepository.save(unitMasterEntity);
            responseVO.setSuccessResponse(Messages.Update_Successfully);
        }else {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        }

        return responseVO;

    }

    public ResponseVO addUnit(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addUnitInputValidation(ownerMasterVO);

        OwnerMasterEntity ownerApproveApplication = ownerMasterRepository.findUserRequest(ownerMasterVO.getUnitId(), ownerMasterVO.getUserId(), UserStatus.USER_REJECTED, true);

        if (ownerApproveApplication != null) {
            responseVO.setFailResponse(Messages.Request_Exist);
        } else {

            List<UnitDocumentTypeVO> unitDocumentTypeVO = unitDocumentTypeRepository.getMandatoryList(ownerMasterVO.getSocietyId(), ownerMasterVO.getType(), true, true);

            if (unitDocumentTypeVO.size() > 0) {
                for (UnitDocumentTypeVO checkFlag : unitDocumentTypeVO) {
                    boolean checkStatus = false;
                    for (UnitDocumentMasterVO checkDocument : ownerMasterVO.getDocumentList()) {
                        if (checkFlag.getDocumentType().equalsIgnoreCase(checkDocument.getDocumentType())) {
                            checkStatus = true;
                        }
                    }

                    if (!checkStatus) {
                        responseVO.setFailResponse(checkFlag.getDocumentType() + Messages.Not_Found);
                        return responseVO;
                    }
                }
            }

            ownerMasterVO.setSocietyRole(UserRoles.NA);
            ownerMasterVO.setUserStatus(UserStatus.USER_PENDING);
            OwnerMasterEntity ownerMasterEntity = modelMapper.map(ownerMasterVO, OwnerMasterEntity.class);

            ownerMasterEntity.setIsActive(true);
            OwnerMasterEntity ownerMasterEntity1 = ownerMasterRepository.save(ownerMasterEntity);

            if (ownerMasterVO.getDocumentList() != null) {
                for (UnitDocumentMasterVO unitDocumentMasterVO : ownerMasterVO.getDocumentList()) {
                    unitDocumentMasterVO.setOwnerId(ownerMasterEntity1.getId());
                    unitDocumentMasterVO.setStatus(UserStatus.DOC_PENDING);
                }

                List<UnitDocumentMasterEntity> unitDocumentMasterEntityList = modelMapperUtil.mapList(ownerMasterVO.getDocumentList(), UnitDocumentMasterEntity.class);
                for (UnitDocumentMasterEntity helperDocumentTypeEntity : unitDocumentMasterEntityList) {
                    String fileName = ownerMasterEntity1.getId() + "_" + helperDocumentTypeEntity.getDocumentType() + ".png";
                    FileHandlingUtil.fileUpload(helperDocumentTypeEntity.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_UNIT, null);
                    helperDocumentTypeEntity.setDocument(Types.UNIT_IMAGES + fileName);
                }
                unitDocumentMasterRepository.saveAll(unitDocumentMasterEntityList);
            }


            //Send Mail to All Society User

            OwnerMasterVO ownerMasterVO1 = new OwnerMasterVO();
            ownerMasterVO1.setUserStatus(ownerMasterEntity1.getUserStatus());
            responseVO.setOwner(ownerMasterVO1);
            responseVO.setSuccessResponse(Messages.Unit_Detail_Success);
        }
        return responseVO;
    }

    public ResponseVO getUserDashboard(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.userDashboardInputValidate(ownerMasterVO);
        societyValidationService.isOwnerExist(ownerMasterVO);

        DashBoardVO userDashBoard = ownerMasterRepository.getUserDashBoard(ownerMasterVO.getUnitId(), ownerMasterVO.getUserId(), UserStatus.USER_APPROVED);

        if (userDashBoard != null) {
            ResponseVO responseVO1 = userService.getUserDetail(ownerMasterVO);
            userDashBoard.setName(responseVO1.getUser().getName());
            userDashBoard.setEmail(responseVO1.getUser().getEmail());
            userDashBoard.setMobileNumber(responseVO1.getUser().getMobileNumber());
            userDashBoard.setUserProfile(responseVO1.getUser().getPhoto());
            responseVO.setUserDashBoard(userDashBoard);
            responseVO.setSuccessResponse(Messages.User_Dashboard);
            return responseVO;
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

    public ResponseVO getUserMapping(UserVO userVO) {
        ResponseVO responseVO = new ResponseVO();
        List<DashBoardVO> dashBoardVOs = ownerMasterRepository.getUserRequest(userVO.getId());
        responseVO.setDashBoard(dashBoardVOs);
        return responseVO;
    }

    public ResponseVO getOwnerList(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        OwnerMasterEntity ownerMasterEntity = new OwnerMasterEntity();
        int pages = 0;
        ArrayList<UserListVO> userListRequest = new ArrayList<>();
        societyInputValidator.societyIdInputValidation(ownerMasterVO);
        societyValidationService.isSocietyValid(ownerMasterVO.getSocietyId());

        Pageable pageable = Functions.getPage(ownerMasterVO.getPage(), ownerMasterVO.getLimit());

        Specification<OwnerMasterEntity> specification = Specification
                .where(ownerMasterVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.SOCIETY_ID, ownerMasterVO.getSocietyId()))
                .and(ownerMasterVO.getUserId() == 0 ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.USER_ID, ownerMasterVO.getUserId()))
                .and(ownerMasterVO.getAreaId() == 0 ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.AREA_ID, ownerMasterVO.getAreaId()))
                .and(ownerMasterVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.UNIT_ID, ownerMasterVO.getUnitId()))
                .and(!Functions.nonNullString(ownerMasterVO.getOccupancyStatus()) ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.OCCUPANCY_STATUS, ownerMasterVO.getOccupancyStatus()))
                .and(!Functions.nonNullString(ownerMasterVO.getUserStatus()) ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.USER_STATUS, ownerMasterVO.getUserStatus()))
                .and(!Functions.nonNullString(ownerMasterVO.getType()) ? null : SpecificationService.equalSpecification(ownerMasterEntity, EntityVariable.TYPE, ownerMasterVO.getType()))
                .and(SpecificationService.descendingOrder(ownerMasterEntity, EntityVariable.ID));


        Page<OwnerMasterEntity> owner = ownerMasterRepository.findAll(specification, pageable);
        int count = (int) ownerMasterRepository.count(specification);

        if (!owner.isEmpty()) {
            for (OwnerMasterEntity ownerMaster : owner) {
                UserListVO userList = new UserListVO();
                OwnerMasterVO ownerMasterVO1 = new OwnerMasterVO();
                ownerMasterVO1.setUserId(ownerMaster.getUserId());

                UserListVO user = unitMasterRepository.getSocietyAndAreaName(ownerMaster.getUnitId());

                List<UnitDocumentMasterEntity> unitDocumentList = unitDocumentMasterRepository.findByOwnerId(ownerMaster.getId());
                List<UnitDocumentMasterVO> unitDocumentMasterVO = modelMapperUtil.mapList(unitDocumentList, UnitDocumentMasterVO.class);

                ResponseVO responseVO1 = userService.getUserDetail(ownerMasterVO1);

                userList.setApplicationId(ownerMaster.getId());
                userList.setSocietyName(user.getSocietyName());
                userList.setAreaName(user.getAreaName());
                userList.setUnit(user.getUnit());
                userList.setName(responseVO1.getUser().getName());
                userList.setEmail(responseVO1.getUser().getEmail());
                userList.setMobileNumber(responseVO1.getUser().getMobileNumber());
                userList.setType(ownerMaster.getType());
                userList.setOccupancyStatus(ownerMaster.getOccupancyStatus());
                userList.setSocietyRole(ownerMaster.getSocietyRole());
                userList.setDocumentList(unitDocumentMasterVO);

                userListRequest.add(userList);
            }
            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setOwnerList(userListRequest);
            responseVO.setSuccessResponse(Messages.Owner_List_Success);

        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

//        responseVO.setOwnerMasterEntity(owner);

//        ExampleMatcher matcher = ExampleMatcher
//                .matchingAll()
//                .withMatcher("userStatus", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//
//        OwnerMasterEntity ownerMasterEntity = OwnerMasterEntity.builder()
//                .societyId(ownerMasterVO.getSocietyId())
//                .userStatus(ownerMasterVO.getUserStatus())
//                .build();
//
//        System.out.println("--------- 1");
//
//        Example<OwnerMasterEntity> own = Example.of(ownerMasterEntity);
//
//        System.out.println("--------- 1");
//
//        Page<OwnerMasterEntity> owner = ownerMasterRepository.findAll(Example.of(ownerMasterEntity, matcher), pageable);


//        userListRequest = modelMapperUtil.mapList(owner, UserListVO.class);


//        if (Functions.nonNullString(ownerMasterVO.getUserStatus())) {
//            userListRequest = ownerMasterRepository.getOwnerAndStatusList(ownerMasterVO.getSocietyId(), ownerMasterVO.getUserStatus(), pageable);
//
//            int count = ownerMasterRepository.countBySocietyIdAndUserStatus(ownerMasterVO.getSocietyId(), ownerMasterVO.getUserStatus());
//            pages = Functions.getPagesCount(count);
//            responseVO.setCount(count);
//
//        } else {
//            userListRequest = ownerMasterRepository.getOwnerList(ownerMasterVO.getSocietyId(), pageable);
//
//            int count = unitMasterRepository.countBySocietyId(ownerMasterVO.getSocietyId());
//            pages = Functions.getPagesCount(count);
//            responseVO.setCount(count);
//
//        }

//        if (!userListRequest.isEmpty()) {
//            for (UserListVO userListVO : userListRequest) {
//                OwnerMasterVO ownerMasterVO1 = new OwnerMasterVO();
//                ownerMasterVO1.setUserId(userListVO.getUserId());
//
//                List<UnitDocumentMasterEntity> unitDocumentList = unitDocumentMasterRepository.findByOwnerId(userListVO.getApplicationId());
//                List<UnitDocumentMasterVO> unitDocumentMasterVO = modelMapperUtil.mapList(unitDocumentList, UnitDocumentMasterVO.class);
//
//                ResponseVO responseVO1 = userService.getUserDetail(ownerMasterVO1);
//                userListVO.setName(responseVO1.getUser().getName());
//                userListVO.setEmail(responseVO1.getUser().getEmail());
//                userListVO.setMobileNumber(responseVO1.getUser().getMobileNumber());
//                userListVO.setDocumentList(unitDocumentMasterVO);
//            }
//            responseVO.setPages(pages);
//            responseVO.setOwnerList(userListRequest);
//            responseVO.setSuccessResponse(Messages.Owner_List_Success);
//
//        } else {
//            responseVO.setFailResponse(Messages.Not_Found);
//        }

        return responseVO;
    }

    public ResponseVO updateOwnerStatus(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateOwnerStatusInputValidation(ownerMasterVO);

        OwnerMasterEntity ownerMasterEntity = ownerMasterRepository.getOne(ownerMasterVO.getId());

        if (ownerMasterEntity.getUserStatus().equalsIgnoreCase(UserStatus.USER_PENDING)) {
            if (ownerMasterVO.getUserStatus().equalsIgnoreCase(UserStatus.USER_REJECTED)) {
                ownerMasterEntity.setMessage(ownerMasterVO.getMessage());
                ownerMasterEntity.setIsActive(false);
            }

            ownerMasterEntity.setUserStatus(ownerMasterVO.getUserStatus());
            ownerMasterRepository.save(ownerMasterEntity);

            if (ownerMasterVO.getUserStatus().equalsIgnoreCase(UserStatus.USER_APPROVED)) {
                OwnerMasterVO owner = new OwnerMasterVO();
                SocietyMappingVO societyMappingVO = new SocietyMappingVO();
                UnitMasterVO unitMasterVO = new UnitMasterVO();

                owner.setUserId(ownerMasterEntity.getUserId());
                unitMasterVO.setId(ownerMasterEntity.getUnitId());

                ResponseVO response = userService.getUserDetail(owner);

                societyMappingVO.setUser(response.getUser());
                societyMappingVO.setUnit(unitMasterVO);

                householdService.isFamilyMember(societyMappingVO);
            }
            responseVO.setSuccessResponse(Messages.Status_Updated);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

    public ResponseVO applicationMessage(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.applicationMessageInputValidation(ownerMasterVO);

        OwnerMasterEntity ownerMasterEntity = ownerMasterRepository.getOne(ownerMasterVO.getId());

        ownerMasterEntity.setMessage(ownerMasterVO.getMessage());
        ownerMasterRepository.save(ownerMasterEntity);
        responseVO.setSuccessResponse(Messages.Message_Success);
        return responseVO;

    }

    public ResponseVO getUserDocument(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        List<UnitDocumentMasterEntity> unitDocuments;
        if (ownerMasterVO.getInitiatedBy().equals(UserRoles.USER)) {
            societyInputValidator.getUserDocumentInputValidation(ownerMasterVO);

            unitDocuments = unitDocumentMasterRepository.getUserIdAndUnitId(ownerMasterVO.getUserId(), ownerMasterVO.getUnitId());
        } else {
            societyInputValidator.societyIdAndUserIdInputValidation(ownerMasterVO);
            unitDocuments = unitDocumentMasterRepository.findBySocietyIdAndUserId(ownerMasterVO.getSocietyId(), ownerMasterVO.getUserId());
        }

        if (!unitDocuments.isEmpty()) {
            List<UnitDocumentMasterVO> unitDocumentMasterVO = modelMapperUtil.mapList(unitDocuments, UnitDocumentMasterVO.class);
            responseVO.setSuccessResponse(Messages.Document_List);
            responseVO.setUnitDocument(unitDocumentMasterVO);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public ResponseVO updateUnitDocument(UnitDocumentMasterVO documentMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateUserUnitDocumentInputValidation(documentMasterVO);

        UnitDocumentMasterEntity unitDocument = unitDocumentMasterRepository.getOne(documentMasterVO.getId());

        if (unitDocument.getStatus().equalsIgnoreCase(UserStatus.DOC_REJECTED)) {
            unitDocument.setStatus(UserStatus.DOC_PENDING);
            if (Functions.nonNullString(documentMasterVO.getDocument())) {

                String fileName = documentMasterVO.getDocumentType() + Functions.getRandomValue() + ".png";
                FileHandlingUtil.fileUpload(documentMasterVO.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_UNIT, unitDocument.getDocument());
                unitDocument.setDocument(Types.UNIT_IMAGES + fileName);
            }
            unitDocumentMasterRepository.save(unitDocument);
            responseVO.setSuccessResponse(Messages.Document_Update_Success);
        } else {
            responseVO.setFailResponse(Messages.Invalid_Update);
        }
        return responseVO;

    }

    public ResponseVO addEmergencyNumber(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addEmergencyNumberInputValidation(emergencyNumberVO);
        societyValidationService.isEmergencyNumberLinked(emergencyNumberVO);

        EmergencyNumberEntity emergencyNumberEntity = modelMapper.map(emergencyNumberVO, EmergencyNumberEntity.class);
        emergencyNumberEntity.setIsActive(true);
        emergencyNumberRepository.save(emergencyNumberEntity);
        responseVO.setSuccessResponse(Messages.Emergency_Added_Success);
        return responseVO;

    }

    public ResponseVO getEmergencyNumber(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.societyIdInputValidation(ownerMasterVO);
        EmergencyNumberEntity emergencyNumberEntity = new EmergencyNumberEntity();
        Specification<EmergencyNumberEntity> specification = Specification
                .where(ownerMasterVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(emergencyNumberEntity, EntityVariable.SOCIETY_ID, ownerMasterVO.getSocietyId()))
                .and(!ownerMasterVO.getIsActive() ? null : SpecificationService.equalSpecification(emergencyNumberEntity, EntityVariable.IS_ACTIVE, ownerMasterVO.getIsActive()));

        Pageable pageable = Functions.getPage(ownerMasterVO.getPage(), ownerMasterVO.getLimit());
        Page<EmergencyNumberEntity> emergencyNumberEntities = emergencyNumberRepository.findAll(specification, pageable);

        if (!emergencyNumberEntities.isEmpty()) {

            List<EmergencyNumberVO> activeEmergencyNumber = modelMapperUtil.mapPage(emergencyNumberEntities, EmergencyNumberVO.class);
            int count = (int) emergencyNumberRepository.count(specification);
            responseVO.setCount(count);
            responseVO.setEmergencyNumberList(activeEmergencyNumber);
            responseVO.setSuccessResponse(Messages.Emergency_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Emergency_List_Failed);
        }
        return responseVO;


//    public ResponseVO getEmergencyNumber(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
//        ResponseVO responseVO = new ResponseVO();
//        societyInputValidator.societyIdInputValidation(ownerMasterVO);
//        int pages = 0;
//        int inActivePages = 0;
//        int count = 0;
//        int inActiveCount = 0;

//        Pageable pageable = Functions.getPage(ownerMasterVO.getPage(), ownerMasterVO.getLimit());
//        Page<EmergencyNumberEntity> emergencyNumberEntities = ownerMasterRepository.findAll(specification, pageable);
//
//
//        List<EmergencyNumberEntity> activeEmergencyNumberEntity = emergencyNumberRepository.findBySocietyIdAndIsActive(ownerMasterVO.getSocietyId(), true, pageable);
//        count = emergencyNumberRepository.countBySocietyIdAndIsActive(ownerMasterVO.getSocietyId(), true);
//       pages = Functions.getPagesCount(count);
//
//        List<EmergencyNumberEntity> inActiveEmergencyNumberEntity = emergencyNumberRepository.findBySocietyIdAndIsActive(ownerMasterVO.getSocietyId(), false, pageable);
//        inActiveCount = emergencyNumberRepository.countBySocietyIdAndIsActive(ownerMasterVO.getSocietyId(), false);
//        inActivePages = Functions.getPagesCount(count);
//
//        if (activeEmergencyNumberEntity.isEmpty() && inActiveEmergencyNumberEntity.isEmpty()) {
//            responseVO.setFailResponse(Messages.Not_Found);
//
//        } else {
//            List<EmergencyNumberVO> activeEmergencyNumber = modelMapperUtil.mapList(activeEmergencyNumberEntity, EmergencyNumberVO.class);
//            List<EmergencyNumberVO> inActiveEmergencyNumber = modelMapperUtil.mapList(inActiveEmergencyNumberEntity, EmergencyNumberVO.class);
//            responseVO.setActiveEmergencyNumber(activeEmergencyNumber);
//            responseVO.setInActiveEmergencyNumber(inActiveEmergencyNumber);
//            responseVO.setPages(pages);
//            responseVO.setInActivePages(inActivePages);
//            responseVO.setCount(count);
//            responseVO.setInActiveCount(inActiveCount);
//            responseVO.setSuccessResponse(Messages.Emergency_List_Success);
//        }
//
//        return responseVO;

    }

    public ResponseVO updateEmergencyNumber(EmergencyNumberVO emergencyNumberVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.emergencyUpdateInputValidation(emergencyNumberVO);

        EmergencyNumberEntity emergencyNumberEntity = emergencyNumberRepository.getOne(emergencyNumberVO.getId());

        if (emergencyNumberEntity.getName().equalsIgnoreCase(emergencyNumberVO.getName()) && emergencyNumberEntity.getEmail().equalsIgnoreCase(emergencyNumberVO.getEmail())
                && emergencyNumberEntity.getMobileNumber().equals(emergencyNumberVO.getMobileNumber()) && emergencyNumberEntity.getAddress().equals(emergencyNumberVO.getAddress())
                && emergencyNumberEntity.getType().equals(emergencyNumberVO.getType()) && emergencyNumberEntity.getIsActive() == emergencyNumberVO.getIsActive()) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            EmergencyNumberEntity checkMobileNumber = emergencyNumberRepository.findBySocietyIdAndMobileNumber(emergencyNumberVO.getSocietyId(), emergencyNumberVO.getMobileNumber());

            if (checkMobileNumber != null && checkMobileNumber.getId() != emergencyNumberVO.getId()) {
                responseVO.setFailResponse(ValidationMessages.Mobile_Present_Error);
            } else {
                emergencyNumberEntity.setName(emergencyNumberVO.getName());
                emergencyNumberEntity.setMobileNumber(emergencyNumberVO.getMobileNumber());
                emergencyNumberEntity.setType(emergencyNumberVO.getType());
                emergencyNumberEntity.setEmail(emergencyNumberVO.getEmail());
                emergencyNumberEntity.setAddress(emergencyNumberVO.getAddress());
                emergencyNumberEntity.setIsActive(emergencyNumberVO.getIsActive());
                emergencyNumberRepository.save(emergencyNumberEntity);
                responseVO.setSuccessResponse(Messages.Emergency_Update_Success);
            }
        }
        return responseVO;

    }

    public ResponseVO addAreaType(TypeVO typeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addTypeInputValidation(typeVO);
        AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(typeVO.getType());

        if (areaTypeEntity == null) {
            AreaTypeEntity areaTypeEntity1 = modelMapper.map(typeVO, AreaTypeEntity.class);
            areaTypeRepository.save(areaTypeEntity1);
            responseVO.setSuccessResponse(Messages.Type_Added_Success);
        } else {
            responseVO.setFailResponse(Messages.Type_Exist_Error);
        }
        return responseVO;
    }

    public ResponseVO getAreaType(AreaTypeVO areaTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        AreaTypeEntity areaTypeEntity = new AreaTypeEntity();
//        int pages;
//        int inActivePages;
//        List<TypeVO> typeVOs;

        Pageable pageable = Functions.getPage(areaTypeVO.getPage(), areaTypeVO.getLimit());
//        Pageable searchPageable = Functions.getPage(0, areaTypeVO.getLimit());

        Specification<AreaTypeEntity> specification = Specification
                .where(!Functions.nonNullString(areaTypeVO.getType()) ? null : SpecificationService.likeSpecification(areaTypeEntity, EntityVariable.TYPE, areaTypeVO.getType()))
                .and(Functions.nonNullString(areaTypeVO.getFilter()) && areaTypeVO.getFilter().equalsIgnoreCase(Status.RESIDENTIAL) ? SpecificationService.equalSpecification(areaTypeEntity, EntityVariable.IS_RESIDENTIAL, areaTypeVO.getIsResidential()) : null)
                .and(Functions.nonNullString(areaTypeVO.getAction()) && areaTypeVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(areaTypeEntity, EntityVariable.IS_ACTIVE, areaTypeVO.getIsActive()) : null);

        Page<AreaTypeEntity> areaTypeEntities = areaTypeRepository.findAll(specification, pageable);

        if (!areaTypeEntities.isEmpty()) {

            List<AreaTypeVO> areaTypeVOS = modelMapperUtil.mapPage(areaTypeEntities, AreaTypeVO.class);
            int count = (int) areaTypeRepository.count(specification);

            responseVO.setCount(count);
            responseVO.setAreaTypeList(areaTypeVOS);
            responseVO.setSuccessResponse(Messages.Area_Type_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Area_Type_Invalid);
        }

//            if(typeVO.getType() != null){
//                typeVOs = areaTypeRepository.getSearchAreaType(typeVO.getType(), true, searchPageable);
//            }else {
//                typeVOs = areaTypeRepository.getActiveAreaType(true, searchPageable);
//            }
//
//            List<AreaTypeEntity> activeType = areaTypeRepository.findByIsActive(true, pageable);
//            List<AreaTypeEntity> inActiveType = areaTypeRepository.findByIsActive(false, pageable);
//
//            if (activeType.isEmpty() && inActiveType.isEmpty()) {
//                responseVO.setFailResponse(Messages.Area_Type_Invalid);
//            } else {
//
//                List<TypeVO> activeTypeVO = modelMapperUtil.mapList(activeType, TypeVO.class);
//                List<TypeVO> inActiveTypeVO = modelMapperUtil.mapList(inActiveType, TypeVO.class);
//
//                int count = areaTypeRepository.countByIsActive(true);
//                pages = Functions.getPagesCount(count);
//
//                int inActiveCount = areaTypeRepository.countByIsActive(false);
//                inActivePages = Functions.getPagesCount(inActiveCount);
//
//                if(!typeVOs.isEmpty()){
//                    responseVO.setActiveType(typeVOs);
//                }
//                responseVO.setPages(pages);
//                responseVO.setInActivePages(inActivePages);
//                responseVO.setSuccessResponse(Messages.Area_Type_List_Success);
//                responseVO.setActiveType(activeTypeVO);
//                responseVO.setInActiveType(inActiveTypeVO);
//                responseVO.setCount(count);
//                responseVO.setInActiveCount(inActiveCount);
//            }
        return responseVO;
    }

    public ResponseVO updateAreaType(AreaTypeVO areaTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateAreaTypeInputValidation(areaTypeVO);
        AreaTypeEntity areaTypeEntity = areaTypeRepository.getOne(areaTypeVO.getId());

        if (areaTypeEntity.getType().equalsIgnoreCase(areaTypeVO.getType()) && areaTypeEntity.getIsActive() == areaTypeVO.getIsActive() && areaTypeEntity.getIsResidential() == areaTypeVO.getIsResidential()) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            AreaTypeEntity checkRepeatedValue = areaTypeRepository.findByType(areaTypeVO.getType());

            if (checkRepeatedValue != null && checkRepeatedValue.getId() != areaTypeVO.getId()) {
                responseVO.setFailResponse(Messages.Type_Exist_Error);
            } else {

                areaTypeEntity.setType(areaTypeVO.getType());
                areaTypeEntity.setIsActive(areaTypeVO.getIsActive());
                areaTypeEntity.setIsResidential(areaTypeVO.getIsResidential());
                areaTypeRepository.save(areaTypeEntity);
                responseVO.setSuccessResponse(Messages.Type_Update_Success);
            }
        }
        return responseVO;

    }

    public ResponseVO addSocietyDocumentType(SocietyDocumentTypeVO societyDocumentTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addSocietyDocumentTypeInputValidation(societyDocumentTypeVO);

        SocietyDocumentTypeEntity societyDocumentTypeEntity = societyDocumentTypeRepository.findByDocumentTypeId(societyDocumentTypeVO.getId());

        if (societyDocumentTypeEntity == null) {

            societyDocumentTypeVO.setIsActive(true);
            SocietyDocumentTypeEntity societyDocumentTypeEntity1 = modelMapper.map(societyDocumentTypeVO, SocietyDocumentTypeEntity.class);
            societyDocumentTypeRepository.save(societyDocumentTypeEntity1);
            responseVO.setSuccessResponse(Messages.Type_Added_Success);
        } else {
            responseVO.setFailResponse(Messages.Document_Exist);
        }
        return responseVO;
    }

    public ResponseVO getSocietyDocumentType(SocietyDocumentTypeVO societyDocumentTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        SocietyDocumentTypeEntity societyDocumentTypeEntity = new SocietyDocumentTypeEntity();
        int pages = 0;

        Pageable pageable = Functions.getPage(societyDocumentTypeVO.getPage(), societyDocumentTypeVO.getLimit());

        Specification<SocietyDocumentTypeEntity> specification = Specification
                .where(societyDocumentTypeVO.getDocumentTypeId() == 0 ? null : SpecificationService.equalSpecification(societyDocumentTypeEntity, EntityVariable.DOCUMENT_TYPE_ID, societyDocumentTypeVO.getDocumentTypeId()))
                .and(Functions.nonNullString(societyDocumentTypeVO.getFilter()) && societyDocumentTypeVO.getFilter().equalsIgnoreCase(Status.MANDATORY) ? SpecificationService.equalSpecification(societyDocumentTypeEntity, EntityVariable.IS_MANDATORY, societyDocumentTypeVO.getIsMandatory()) : null)
                .and(Functions.nonNullString(societyDocumentTypeVO.getAction()) && societyDocumentTypeVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(societyDocumentTypeEntity, EntityVariable.IS_ACTIVE, societyDocumentTypeVO.getIsActive()) : null);

//        List<SocietyDocumentTypeVO> activeDocumentType = societyDocumentTypeRepository.getActiveDocumentType(pageable);

        Page<SocietyDocumentTypeEntity> activeDocumentTypeEntity = societyDocumentTypeRepository.findAll(specification, pageable);

        if (activeDocumentTypeEntity.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {

            List<SocietyDocumentTypeVO> societyDocumentTypeVOS = new ArrayList<>();

            activeDocumentTypeEntity.forEach(documentType -> {
                SocietyDocumentTypeVO societyDocumentType = modelMapper.map(documentType, SocietyDocumentTypeVO.class);
                DocumentMasterEntity documentMasterEntity = documentMasterRepository.getOne(documentType.getDocumentTypeId());
                societyDocumentType.setType(documentMasterEntity.getType());

                societyDocumentTypeVOS.add(societyDocumentType);
            });

            int count = (int) societyDocumentTypeRepository.count(specification);
            pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setSocietyDocumentTypeList(societyDocumentTypeVOS);
            responseVO.setSuccessResponse(Messages.Document_List);
            responseVO.setCount(count);
        }
        return responseVO;

    }

    public ResponseVO updateSocietyDocumentType(SocietyDocumentTypeVO societyDocumentTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateSocietyDocumentTypeInputValidation(societyDocumentTypeVO);

        SocietyDocumentTypeEntity documentTypeEntity = societyDocumentTypeRepository.getOne(societyDocumentTypeVO.getId());

        if (documentTypeEntity.getDocumentTypeId() == societyDocumentTypeVO.getDocumentTypeId() && documentTypeEntity.getIsMandatory() == societyDocumentTypeVO.getIsMandatory() && documentTypeEntity.getIsActive() == societyDocumentTypeVO.getIsActive()) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            SocietyDocumentTypeEntity societyDocumentTypeEntity = societyDocumentTypeRepository.findByDocumentTypeId(societyDocumentTypeVO.getDocumentTypeId());

            if (societyDocumentTypeEntity != null && societyDocumentTypeEntity.getId() != societyDocumentTypeVO.getId()) {
                responseVO.setFailResponse(Messages.Type_Exist_Error);
            } else {

                documentTypeEntity.setDocumentTypeId(societyDocumentTypeVO.getDocumentTypeId());
                documentTypeEntity.setIsActive(societyDocumentTypeVO.getIsActive());
                documentTypeEntity.setIsMandatory(societyDocumentTypeVO.getIsMandatory());
                societyDocumentTypeRepository.save(documentTypeEntity);
                responseVO.setSuccessResponse(Messages.Type_Update_Success);
            }
        }
        return responseVO;
    }

    public ResponseVO addSocietyDocument(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addSocietyDocumentInputValidation(societyDocumentVO);
        societyValidationService.isSocietyValid(societyDocumentVO.getSocietyId());
        societyValidationService.isUserExist(societyDocumentVO.getSocietyUserId());

        SocietyDocumentEntity societyDocumentEntity = societyDocumentRepository.findBySocietyIdAndDocumentTypeIdAndDocumentStatus(societyDocumentVO.getSocietyId(), societyDocumentVO.getDocumentTypeId(), UserStatus.DOC_REJECTED);

        if (societyDocumentEntity == null) {
            societyDocumentVO.setDocumentStatus(UserStatus.DOC_PENDING);
            SocietyDocumentEntity societyDocumentEntity1 = modelMapper.map(societyDocumentVO, SocietyDocumentEntity.class);
            if (Functions.nonNullString(societyDocumentVO.getDocument())) {

                String fileName = societyDocumentVO.getSocietyUserId() + "_" + societyDocumentVO.getDocumentTypeId() + "_" + Functions.getRandomValue() + ".png";
                FileHandlingUtil.fileUpload(societyDocumentVO.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_SOCIETY, null);
                societyDocumentEntity1.setDocument(Types.SOCIETY_IMAGES + fileName);
            }
            societyDocumentRepository.save(societyDocumentEntity1);
            responseVO.setSuccessResponse(Messages.Document_Add_Success);

        } else {
            responseVO.setFailResponse(Messages.Document_Already_Submitted);
            return responseVO;
        }

        List<SocietyDocumentTypeEntity> societyDocumentTypeEntities = societyDocumentTypeRepository.findByIsMandatoryAndIsActive(true, true);

        int checkDocument = 0;
        for (SocietyDocumentTypeEntity societyDocumentType : societyDocumentTypeEntities) {
            SocietyDocumentEntity societyDocumentEntity1 = societyDocumentRepository.findBySocietyIdAndDocumentTypeId(societyDocumentVO.getSocietyId(), societyDocumentType.getDocumentTypeId());

            if (societyDocumentEntity1 != null) {
                checkDocument++;
            }
        }

        if (checkDocument == societyDocumentTypeEntities.size()) {
            List<SocietyUserEntity> societyUserEntity = societyUserMappingRepository.findBySocietyIdAndAdminStatus(societyDocumentVO.getSocietyId(), UserStatus.USER_DOCUMENT_PENDING);
            for (SocietyUserEntity societyUser : societyUserEntity) {
                societyUser.setAdminStatus(UserStatus.USER_PENDING);
                societyUserMappingRepository.save(societyUser);
            }

            SocietyEntity societyEntity = societyRepository.getOne(societyDocumentVO.getSocietyId());
            if (societyEntity.getSocietyStatus().equalsIgnoreCase(UserStatus.SOCIETY_REJECTED)) {
                societyEntity.setSocietyStatus(UserStatus.SOCIETY_PENDING);
                societyRepository.save(societyEntity);
            }

        }

        return responseVO;

    }

    public ResponseVO getSocietyDocument(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.getSocietyDocumentInputValidation(societyDocumentVO);
        societyValidationService.isSocietyValid(societyDocumentVO.getSocietyId());

        List<SocietyDocumentVO> societyDocumentVOs = societyDocumentRepository.getSocietyDocument(societyDocumentVO.getSocietyId());

        if (!societyDocumentVOs.isEmpty()) {

            SocietyUserEntity userEntity = societyUserMappingRepository.getOne(societyDocumentVO.getId());

            responseVO.setAdminStatus(userEntity.getAdminStatus());
            responseVO.setSocietyDocumentList(societyDocumentVOs);
            responseVO.setSuccessResponse(Messages.Document_List);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;

    }

    public ResponseVO updateSocietyDocument(SocietyDocumentVO societyDocumentVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateSocietyDocumentInputValidation(societyDocumentVO);

        SocietyDocumentEntity societyDocumentEntity = societyDocumentRepository.getOne(societyDocumentVO.getId());

        if (societyDocumentEntity.getDocumentStatus().equals(UserStatus.DOC_REJECTED)) {

            societyDocumentEntity.setRemark(null);
            societyDocumentEntity.setDocumentStatus(UserStatus.DOC_PENDING);
            if (Functions.nonNullString(societyDocumentVO.getDocument())) {
                String fileName = societyDocumentEntity.getSocietyUserId() + "_" + societyDocumentEntity.getDocumentTypeId() + "_" + Functions.getRandomValue() + ".png";
                FileHandlingUtil.fileUpload(societyDocumentVO.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_SOCIETY, societyDocumentEntity.getDocument());
                societyDocumentEntity.setDocument(Types.SOCIETY_IMAGES + fileName);
            }
            societyDocumentRepository.save(societyDocumentEntity);
            responseVO.setSuccessResponse(Messages.Document_Update_Success);
        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;
    }

    public ResponseVO addUnitDocumentType(UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addUnitDocumentTypeInputValidation(unitDocumentTypeVO);

        UnitDocumentTypeEntity unitDocumentTypeEntity = unitDocumentTypeRepository.findBySocietyIdAndResidentTypeAndDocumentTypeId(unitDocumentTypeVO.getSocietyId(), unitDocumentTypeVO.getResidentType(), unitDocumentTypeVO.getDocumentTypeId());

        if (unitDocumentTypeEntity == null) {
            UnitDocumentTypeEntity unitDocumentTypeEntity1 = modelMapper.map(unitDocumentTypeVO, UnitDocumentTypeEntity.class);

            unitDocumentTypeEntity1.setIsActive(true);
            unitDocumentTypeRepository.save(unitDocumentTypeEntity1);
            responseVO.setSuccessResponse(Messages.Type_Added_Success);
        } else {
            responseVO.setFailResponse(Messages.Document_Exist);
        }
        return responseVO;

    }

    public ResponseVO getUnitDocumentType(UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
        UnitDocumentTypeEntity documentMasterEntity = new UnitDocumentTypeEntity();
        List<UnitDocumentTypeVO> unitDocumentType = new ArrayList<>();

        ownerMasterVO.setSocietyId(unitDocumentTypeVO.getSocietyId());
        societyInputValidator.societyIdInputValidation(ownerMasterVO);
        societyValidationService.isSocietyValid(unitDocumentTypeVO.getSocietyId());

        Pageable pageable = Functions.getPage(unitDocumentTypeVO.getPage(), unitDocumentTypeVO.getLimit());

        Specification<UnitDocumentTypeEntity> specification = Specification
                .where(unitDocumentTypeVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.SOCIETY_ID, unitDocumentTypeVO.getSocietyId()))
                .and(!Functions.nonNullString(unitDocumentTypeVO.getResidentType()) ? null : SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.RESIDENT_TYPE, unitDocumentTypeVO.getResidentType()))
                .and(unitDocumentTypeVO.getDocumentTypeId() == 0 ? null : SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.DOCUMENT_TYPE_ID, unitDocumentTypeVO.getDocumentTypeId()))
                .and(Functions.nonNullString(unitDocumentTypeVO.getFilter()) && unitDocumentTypeVO.getFilter().equalsIgnoreCase(Status.MANDATORY) ? SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.IS_MANDATORY, unitDocumentTypeVO.getIsMandatory()) : null)
                .and(Functions.nonNullString(unitDocumentTypeVO.getAction()) && unitDocumentTypeVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.IS_ACTIVE, unitDocumentTypeVO.getIsActive()) : null);

        Page<UnitDocumentTypeEntity> unitDocumentMasterEntities = unitDocumentTypeRepository.findAll(specification, pageable);

//        if (unitDocumentTypeVO.getResidentType() != null) {
//
//            activeUnitDocumentType = unitDocumentTypeRepository.getResidentUnitDocumentType(unitDocumentTypeVO.getSocietyId(), unitDocumentTypeVO.getResidentType(), true, pageable);
//            inActiveUnitDocumentType = unitDocumentTypeRepository.getResidentUnitDocumentType(unitDocumentTypeVO.getSocietyId(), unitDocumentTypeVO.getResidentType(), false, pageable);
//
//            int count = unitDocumentTypeRepository.countBySocietyIdAndResidentTypeAndIsActive(unitDocumentTypeVO.getSocietyId(), unitDocumentTypeVO.getResidentType(), true);
//            pages = Functions.getPagesCount(count);
//            responseVO.setCount(count);
//
//            int inActiveCount = unitDocumentTypeRepository.countBySocietyIdAndResidentTypeAndIsActive(unitDocumentTypeVO.getSocietyId(), unitDocumentTypeVO.getResidentType(), false);
//            inActivePages = Functions.getPagesCount(inActiveCount);
//            responseVO.setInActiveCount(inActiveCount);
//
//
//        } else {
//            activeUnitDocumentType = unitDocumentTypeRepository.getSocietyUnitDocumentType(unitDocumentTypeVO.getSocietyId(), true, pageable);
//            inActiveUnitDocumentType = unitDocumentTypeRepository.getSocietyUnitDocumentType(unitDocumentTypeVO.getSocietyId(), false, pageable);
//
//            int count = unitDocumentTypeRepository.countBySocietyIdAndIsActive(unitDocumentTypeVO.getSocietyId(), true);
//            pages = Functions.getPagesCount(count);
//
//            int inActiveCount = unitDocumentTypeRepository.countBySocietyIdAndIsActive(unitDocumentTypeVO.getSocietyId(), false);
//            inActivePages = Functions.getPagesCount(inActiveCount);
//
//            responseVO.setCount(count);
//            responseVO.setInActiveCount(inActiveCount);
//        }

        if (unitDocumentMasterEntities.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {


            unitDocumentMasterEntities.forEach(unitDocument -> {
                UnitDocumentTypeVO unitDocumentMasterVO = new UnitDocumentTypeVO();
                unitDocumentMasterVO = modelMapper.map(unitDocument, UnitDocumentTypeVO.class);

                DocumentMasterEntity documentMaster = documentMasterRepository.getOne(unitDocument.getDocumentTypeId());

                unitDocumentMasterVO.setDocumentType(documentMaster.getType());
                unitDocumentType.add(unitDocumentMasterVO);

            });

            int count = (int) unitDocumentTypeRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setCount(count);
            responseVO.setActiveUnitDocumentType(unitDocumentType);
            responseVO.setSuccessResponse(Messages.Document_List);
        }
        return responseVO;
    }

    public ResponseVO updateUnitDocumentType(UnitDocumentTypeVO unitDocumentTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateUnitDocumentTypeInputValidation(unitDocumentTypeVO);

        UnitDocumentTypeEntity unitDocumentTypeEntity = unitDocumentTypeRepository.getOne(unitDocumentTypeVO.getId());

        if (unitDocumentTypeEntity.getResidentType().equals(unitDocumentTypeVO.getResidentType()) && unitDocumentTypeEntity.getDocumentTypeId() == unitDocumentTypeVO.getDocumentTypeId()
                && unitDocumentTypeEntity.getIsMandatory() == unitDocumentTypeVO.getIsMandatory() && unitDocumentTypeEntity.getIsActive() == unitDocumentTypeVO.getIsActive()) {

            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            UnitDocumentTypeEntity unitDocumentTypeEntity1 = unitDocumentTypeRepository.findBySocietyIdAndResidentTypeAndDocumentTypeId(unitDocumentTypeEntity.getSocietyId(), unitDocumentTypeVO.getResidentType(), unitDocumentTypeVO.getDocumentTypeId());

            if (unitDocumentTypeEntity1 != null && unitDocumentTypeEntity1.getId() != unitDocumentTypeVO.getId()) {
                responseVO.setFailResponse(Messages.Type_Exist_Error);
            } else {

                unitDocumentTypeEntity.setDocumentTypeId(unitDocumentTypeVO.getDocumentTypeId());
                unitDocumentTypeEntity.setResidentType(unitDocumentTypeVO.getResidentType());
                unitDocumentTypeEntity.setIsMandatory(unitDocumentTypeVO.getIsMandatory());
                unitDocumentTypeEntity.setIsActive(unitDocumentTypeVO.getIsActive());
                unitDocumentTypeRepository.save(unitDocumentTypeEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }
        }
        return responseVO;
    }

    public void isSocietyExist(AmenityTypeVO amenityTypeVO) throws PZConstraintViolationException {
        SocietyVO societyVO = new SocietyVO();
        societyVO.setId(amenityTypeVO.getSocietyId());
        societyValidationService.isSocietyExistById(societyVO);
    }

    public void isSocietyExistNotice(SocietyNoticeVO societyNoticeVO) throws PZConstraintViolationException {
        SocietyVO societyVO = new SocietyVO();
        societyVO.setId(societyNoticeVO.getSocietyId());
        societyValidationService.isSocietyValid(societyVO.getId());
    }

    public ResponseVO getSocietyDetail(ComplainListVO complainListVO) {
        ResponseVO responseVO = new ResponseVO();
        ResponseVO responseVO1 = new ResponseVO();
        ComplainListVO complainListVO1 = new ComplainListVO();
        if (complainListVO.getUnitId() != 0) {
            complainListVO1 = unitMasterRepository.getUnitDetails(complainListVO.getUnitId());
            complainListVO.setAreaName(complainListVO1.getAreaName());
            complainListVO.setUnit(complainListVO1.getUnit());
        }
        if (complainListVO.getUserId() != 0) {
            responseVO1 = userService.getUserDatai1(complainListVO.getUserId());
            complainListVO.setUserName(responseVO1.getComplainList().getUserName());
            complainListVO.setEmail(responseVO1.getComplainList().getEmail());
            complainListVO.setMobileNumber(responseVO1.getComplainList().getMobileNumber());
        }
        responseVO.setComplainList(complainListVO);
        return responseVO;
    }

    public ResponseVO getSocietyDetail1(AmenityListVO amenityListVO) {
        ResponseVO responseVO = new ResponseVO();
        ResponseVO responseVO1 = new ResponseVO();
        ComplainListVO complainListVO1 = new ComplainListVO();
        if (amenityListVO.getUnitId() != 0) {
            complainListVO1 = unitMasterRepository.getUnitDetails(amenityListVO.getUnitId());
            amenityListVO.setAreaName(complainListVO1.getAreaName());
            amenityListVO.setUnit(complainListVO1.getUnit());
        }
        if (amenityListVO.getUserId() != 0) {
            responseVO1 = userService.getUserDatai1(amenityListVO.getUserId());
            amenityListVO.setUserName(responseVO1.getComplainList().getUserName());
            amenityListVO.setEmail(responseVO1.getComplainList().getEmail());
            amenityListVO.setMobileNumber(responseVO1.getComplainList().getMobileNumber());
        }
        responseVO.setAmenityList(amenityListVO);
        return responseVO;
    }

    public void isUnitExist(OwnerMasterVO ownerMasterVO) {
        societyValidationService.isUnitExist(ownerMasterVO);
    }

    public ResponseVO isUserMappingExist(OwnerMasterVO ownerMasterVO) {
        ResponseVO responseVO = new ResponseVO();

        OwnerMasterEntity ownerMasterEntity = ownerMasterRepository.findByUserIdAndUnitId(ownerMasterVO.getUserId(), ownerMasterVO.getUnitId());

        if (ownerMasterEntity != null) {
            OwnerMasterVO owner = modelMapper.map(ownerMasterEntity, OwnerMasterVO.class);
            responseVO.setOwner(owner);
        }

        return responseVO;
    }


    public void isSocietyIdAndUnitIdValid(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        societyValidationService.isSocietyIdAndUnitIdValid(ownerMasterVO);
    }

    public ResponseVO getUnitDetail(HelperListVO helperListVO) {
        ResponseVO responseVO = new ResponseVO();

        HelperListVO helperList = unitMasterRepository.getUnitIdDetail(helperListVO.getUnitId());
        responseVO.setHelper(helperList);
        return responseVO;
    }


    public ResponseVO getUserStatus(OwnerMasterVO ownerMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        List<DashBoardVO> userStatus;
        societyInputValidator.getUserStatusInputValidation(ownerMasterVO);

        if (Functions.nonNullString(ownerMasterVO.getUserStatus())) {
            userStatus = ownerMasterRepository.getUserRequestByStatus(ownerMasterVO.getUserId(), ownerMasterVO.getUserStatus());
        } else {
            userStatus = ownerMasterRepository.getUserRequest(ownerMasterVO.getUserId());
        }

        if (!userStatus.isEmpty()) {
            responseVO.setDashBoard(userStatus);
            responseVO.setSuccessResponse(Messages.User_Status);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;

    }

    public ResponseVO getFamilyMemberList(HouseholdVO householdVO) {
        ResponseVO responseVO = new ResponseVO();


        List<OwnerMasterEntity> ownerMasterEntities = ownerMasterRepository.findByUnitIdAndUserStatus(householdVO.getUnitId(), UserStatus.USER_APPROVED);

        if (!ownerMasterEntities.isEmpty()) {
            List<OwnerMasterVO> ownerMasterVOList = modelMapperUtil.mapList(ownerMasterEntities, OwnerMasterVO.class);
            responseVO = userService.getUserListDetail(ownerMasterVOList);
        }
        return responseVO;
    }

    public ResponseVO getCity(TypeVO typeVO) throws IOException, ParseException {
        ResponseVO responseVO = new ResponseVO();
        int pages = 0;
        List<String> cityList;
        JSONParser parser = new JSONParser();

        Pageable pageable = Functions.getPage(typeVO.getPage(), typeVO.getLimit());
        Pageable searchPageable = Functions.getPage(0, typeVO.getLimit());

        int count = (int) cityMasterRepository.count();

        pages = Functions.getPagesCount(count);

        if (typeVO.getType() != null) {
            cityList = cityMasterRepository.getCityByName(typeVO.getType(), searchPageable);
        } else {
            cityList = cityMasterRepository.getCity(pageable);
        }

        if (!cityList.isEmpty()) {
            responseVO.setSuccessResponse(Messages.City_List_Message);
            responseVO.setCount(count);
            responseVO.setCity(cityList);
            responseVO.setPages(pages);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }


//            JSONArray jsonObject = (JSONArray) parser.parse(new FileReader("src\\main\\resources\\json\\city.json"));
////            JSONArray jsonObject = (JSONArray) parser.parse(new FileReader("E:\\tomcat8\\webapps\\mySocietyServices\\WEB-INF\\classes\\json\\city.json"));
////        JSONArray jsonObject = (JSONArray) parser.parse(new FileReader("/apps/tomcat/tomcat8/webapps/mySocietyServicesApp/WEB-INF/classes/json/city.json"));
//
//        pages = Functions.getPagesCount(jsonObject.size());
//
//        int index = typeVO.getPage() * 30;
//
//        if(index + 30 < jsonObject.size()) {
//            cityList = jsonObject.subList(index, index + 30);
//            responseVO.setSuccessResponse(Messages.City_List_Message);
//            responseVO.setCity(cityList);
//            responseVO.setPages(pages);
//        }else if(index < jsonObject.size()){
//            cityList = jsonObject.subList(index, jsonObject.size());
//            responseVO.setSuccessResponse(Messages.City_List_Message);
//            responseVO.setCity(cityList);
//            responseVO.setPages(pages);
//        }else {
//            responseVO.setFailResponse(Messages.Not_Found);
//        }

        return responseVO;
    }

    public ResponseVO getSocietyUserList(SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        SocietyEntity societyEntity = new SocietyEntity();
        List<SocietyUserListVO> societyUserListVO;
        List<Integer> societyId = null;
        List<Integer> userId = null;

        int pages;
        societyInputValidator.getSocietyUserListInputValidation(societyUserMasterVO);

        Pageable pageable = Functions.getPage(societyUserMasterVO.getPage(), societyUserMasterVO.getLimit());

        if (Functions.nonNullString(societyUserMasterVO.getSocietyName()) || Functions.nonNullString(societyUserMasterVO.getCity()) || Functions.nonNullString(societyUserMasterVO.getPincode())) {
            Specification<SocietyEntity> specification = Specification
                    .where(!Functions.nonNullString(societyUserMasterVO.getSocietyName()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.SOCIETY_NAME, societyUserMasterVO.getSocietyName()))
                    .and(!Functions.nonNullString(societyUserMasterVO.getCity()) ? null : SpecificationService.equalSpecification(societyEntity, EntityVariable.CITY, societyUserMasterVO.getCity()))
                    .and(!Functions.nonNullString(societyUserMasterVO.getPincode()) ? null : SpecificationService.likeSpecification(societyEntity, EntityVariable.PINCODE, societyUserMasterVO.getPincode()));

            List<SocietyEntity> societyEntityPage = societyRepository.findAll(specification);

            if (!societyEntityPage.isEmpty())
                societyId = societyEntityPage.stream().map(SocietyEntity::getId).collect(Collectors.toList());
        }

        if (Functions.nonNullString(societyUserMasterVO.getName()) || Functions.nonNullString(societyUserMasterVO.getMobileNumber())) {
            userId = userService.getUserIdList(societyUserMasterVO);
        }

        if (societyId != null && userId != null) {
            societyUserListVO = societyUserMappingRepository.getAdminBySocietyAndUser(societyUserMasterVO.getAdminStatus(), societyId, userId, pageable);
        } else if (societyId != null) {
            societyUserListVO = societyUserMappingRepository.getAdminBySocietyId(societyUserMasterVO.getAdminStatus(), societyId, pageable);
        } else if (userId != null) {
            societyUserListVO = societyUserMappingRepository.getAdminByUserId(societyUserMasterVO.getAdminStatus(), userId, pageable);
        } else {
            societyUserListVO = societyUserMappingRepository.getAdminStatus(societyUserMasterVO.getAdminStatus(), pageable);
        }

        if (!societyUserListVO.isEmpty()) {
            for (SocietyUserListVO societyUserVO : societyUserListVO) {
                OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                ownerMasterVO.setUserId(societyUserVO.getSocietyUserId());

                ResponseVO response = userService.getUserDetail(ownerMasterVO);

                societyUserVO.setName(response.getUser().getName());
                societyUserVO.setEmail(response.getUser().getEmail());
                societyUserVO.setMobileNumber(response.getUser().getMobileNumber());

                List<SocietyDocumentVO> societyDocumentVO = societyDocumentRepository.getSocietyDocument(societyUserVO.getSocietyId());

                if (!societyDocumentVO.isEmpty()) {
                    societyUserVO.setSocietyDocument(societyDocumentVO);
                }
            }

            int count = societyUserMappingRepository.countByAdminStatus(societyUserMasterVO.getAdminStatus());
            pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setSocietyUserList(societyUserListVO);
            responseVO.setCount(count);
            responseVO.setSuccessResponse(Messages.Society_User_List);
        } else {
            responseVO.setFailResponse(Messages.No_Society_User_Found);
        }
        return responseVO;
    }

    public ResponseVO updateSocietyUserStatus(SocietyUserMasterVO societyUserMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateSocietyUserStatusInputValidation(societyUserMasterVO);

        SocietyUserEntity societyUserEntity = societyUserMappingRepository.getOne(societyUserMasterVO.getId());

        if (societyUserEntity.getAdminStatus().equalsIgnoreCase(UserStatus.USER_PENDING)) {

            if (societyUserMasterVO.getAdminStatus().equalsIgnoreCase(UserStatus.USER_APPROVED)) {

                List<SocietyDocumentTypeEntity> societyDocumentTypeEntities = societyDocumentTypeRepository.findByIsMandatoryAndIsActive(true, true);

                int checkDocument = 0;
                for (SocietyDocumentTypeEntity societyDocumentType : societyDocumentTypeEntities) {
                    SocietyDocumentEntity societyDocumentEntity1 = societyDocumentRepository.findBySocietyIdAndDocumentTypeIdAndDocumentStatus(societyUserEntity.getSocietyId(), societyDocumentType.getDocumentTypeId(), UserStatus.DOC_APPROVE);
                    if (societyDocumentEntity1 == null) {
                        checkDocument++;
                    }
                }

                if (checkDocument == 0) {
                    societyUserEntity.setAdminStatus(societyUserMasterVO.getAdminStatus());
                } else {
                    responseVO.setFailResponse(Messages.Mandatory_Document_Not_Found);
                    return responseVO;
                }

            } else {
                societyUserEntity.setAdminStatus(societyUserMasterVO.getAdminStatus());
                societyUserEntity.setMessage(societyUserMasterVO.getMessage());
            }
            societyUserMappingRepository.save(societyUserEntity);
            responseVO.setSuccessResponse(Messages.Status_Updated);
        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;
    }

    public ResponseVO allMemberDetails(UnitMasterVO unitMasterVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int pages;
        societyInputValidator.allMemberDetailInputValidation(unitMasterVO);
        Pageable pageable = Functions.getPage(unitMasterVO.getPage(), unitMasterVO.getLimit());

        List<UserListVO> userListVO = ownerMasterRepository.getAllMember(unitMasterVO.getAreaId(), UserStatus.USER_APPROVED, pageable);

        if (!userListVO.isEmpty()) {
            for (UserListVO userList : userListVO) {
                OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                ownerMasterVO.setUserId(userList.getUserId());

                ResponseVO response = userService.getUserDetail(ownerMasterVO);
                userList.setName(response.getUser().getName());
                userList.setMobileNumber(response.getUser().getMobileNumber());
                userList.setIsCall(response.getUser().getIsCall());
            }

            int count = ownerMasterRepository.getOwnerCount(unitMasterVO.getAreaId(), UserStatus.USER_APPROVED);
            pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setOwnerList(userListVO);
            responseVO.setSuccessResponse(Messages.Member_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;

    }

    public void isSocietyValid(GuardVO guardVO) throws PZConstraintViolationException {
        societyValidationService.isSocietyValid(guardVO.getSocietyId());
    }

    public void societyIdExist(SocietyVO societyVO) throws PZConstraintViolationException {
        societyValidationService.isSocietyValid(societyVO.getId());
    }

    public ResponseVO getSociety(SocietyVO societyVO) {
        ResponseVO responseVO = new ResponseVO();

        SocietyEntity societyEntity = societyRepository.getOne(societyVO.getId());

        SocietyVO society = modelMapper.map(societyEntity, SocietyVO.class);
        responseVO.setSociety(society);
        return responseVO;
    }

    public ResponseVO updateSocietyDocumentStatus(SocietyDocumentVO societyDocumentVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateSocietyDocumentStatusInputValidation(societyDocumentVO);

        SocietyDocumentEntity societyDocumentEntity = societyDocumentRepository.getOne(societyDocumentVO.getId());
        SocietyEntity societyEntity = societyRepository.getOne(societyDocumentEntity.getSocietyId());

        if (societyDocumentEntity.getDocumentStatus().equalsIgnoreCase(UserStatus.DOC_PENDING)) {
            societyDocumentEntity.setDocumentStatus(societyDocumentVO.getDocumentStatus());
            societyDocumentEntity.setRemark(societyDocumentVO.getRemark());
            societyDocumentRepository.save(societyDocumentEntity);
            responseVO.setSuccessResponse(Messages.Status_Updated);

            if (!societyEntity.getSocietyStatus().equalsIgnoreCase(UserStatus.SOCIETY_APPROVED)) {
                List<SocietyDocumentTypeEntity> societyDocumentTypeEntities = societyDocumentTypeRepository.findByIsMandatoryAndIsActive(true, true);

                int checkDocument = 0;
                for (SocietyDocumentTypeEntity societyDocumentType : societyDocumentTypeEntities) {
                    SocietyDocumentEntity societyDocumentEntity1 = societyDocumentRepository.findBySocietyIdAndDocumentTypeIdAndDocumentStatus(societyDocumentEntity.getSocietyId(), societyDocumentType.getDocumentTypeId(), UserStatus.DOC_APPROVE);
                    if (societyDocumentEntity1 == null) {
                        checkDocument++;
                    }
                }

                if (checkDocument == 0) {
                    societyEntity.setSocietyStatus(UserStatus.SOCIETY_APPROVED);
                    societyRepository.save(societyEntity);
                }
            }
        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }

        return responseVO;
    }

    public ResponseVO updateUnitDocumentStatus(UnitDocumentMasterVO unitDocumentMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateUnitDocumentStatusInputValidation(unitDocumentMasterVO);

        UnitDocumentMasterEntity unitDocumentMasterEntity = unitDocumentMasterRepository.getOne(unitDocumentMasterVO.getId());

        if (unitDocumentMasterEntity.getStatus().equalsIgnoreCase(UserStatus.DOC_PENDING)) {
            unitDocumentMasterEntity.setStatus(unitDocumentMasterVO.getStatus());
            unitDocumentMasterEntity.setRemark(unitDocumentMasterVO.getRemark());
            unitDocumentMasterRepository.save(unitDocumentMasterEntity);
            responseVO.setSuccessResponse(Messages.Status_Updated);
        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }

        return responseVO;
    }

    public ResponseVO getMessageProvider() {
        ResponseVO responseVO = new ResponseVO();

        List<MobileConfigurationEntity> providerList = mobileConfigurationRepository.getProvider();

        if (providerList.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Provider_Found);
        } else {

            List<MobileProviderVO> mobileProviderVOs = modelMapperUtil.mapList(providerList, MobileProviderVO.class);
            responseVO.setProvider(mobileProviderVOs);
            responseVO.setSuccessResponse(Messages.Provider_List);
        }
        return responseVO;
    }

    public ResponseVO changeMobileProvider(MobileProviderVO mobileProviderVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.changeProviderInputValidation(mobileProviderVO);

        MobileConfigurationEntity mobileConfigurationEntity = mobileConfigurationRepository.getOne(mobileProviderVO.getId());

        if (mobileProviderVO.getIsActive()) {
            mobileConfigurationRepository.getActiveProvider(false);
        }

        mobileConfigurationEntity.setIsActive(mobileProviderVO.getIsActive());
        mobileConfigurationRepository.save(mobileConfigurationEntity);
        responseVO.setSuccessResponse(Messages.Provider_Changed_Success);
        return responseVO;
    }

    public void SocietyIdExist(int societyId) throws PZConstraintViolationException {
        societyValidationService.isSocietyValid(societyId);
    }

    public ResponseVO getSocietyAdmin(SocietyVO societyVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.getSocietyAdminInputValidation(societyVO);

        Pageable pageable = Functions.getPage(societyVO.getPage(), societyVO.getLimit());

        List<Integer> societyUserId = societyUserMappingRepository.getSocietyUserId(societyVO.getId(), UserStatus.USER_APPROVED, pageable);

        if (!societyUserId.isEmpty()) {
            responseVO = userService.getUserList(societyUserId);
            responseVO.setSuccessResponse(Messages.Society_User_List);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

    public ResponseVO updateMemberRole(OwnerMasterVO ownerMasterVO) {
        ResponseVO response = new ResponseVO();
        societyInputValidator.getUpdateMemberRole(ownerMasterVO);
        OwnerMasterEntity ownerMasterEntity = ownerMasterRepository.getOne(ownerMasterVO.getId());

        if (ownerMasterEntity.getSocietyRole().equalsIgnoreCase(ownerMasterVO.getSocietyRole())) {
            response.setFailResponse(Messages.No_Changes_Found);
        } else {
            ownerMasterEntity.setSocietyRole(ownerMasterVO.getSocietyRole());
            ownerMasterRepository.save(ownerMasterEntity);

            //Notification Start
            OwnerMasterVO ownerMasterVO1 = new OwnerMasterVO();
            ownerMasterVO1.setSocietyId(ownerMasterEntity.getSocietyId());
            List<String> memberToken = getMemberToken(ownerMasterVO);

            pushNotificationService.sendActionNotificationToToken(Messages.Congratulation, Messages.Role_Promote, null, 0, null, memberToken);
            //END

            response.setSuccessResponse(Messages.Owner_Role_Update);
        }

        return response;
    }

    public ResponseVO getMemberDetail(UnitMasterVO unitMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.getMemberRoleInputValidation(unitMasterVO);
        societyValidationService.isSocietyValid(unitMasterVO.getSocietyId());
        int pages = 0;

        List<UserListVO> userListVO = ownerMasterRepository.getMemberRole(unitMasterVO.getSocietyId(), UserRoles.NA, UserStatus.USER_APPROVED, true);

        if (!userListVO.isEmpty()) {

            for (UserListVO userList : userListVO) {
                OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                ownerMasterVO.setUserId(userList.getUserId());

                ResponseVO response = userService.getUserDetail(ownerMasterVO);
                userList.setName(response.getUser().getName());
                userList.setMobileNumber(response.getUser().getMobileNumber());
            }

            int count = ownerMasterRepository.countBySocietyIdAndSocietyRoleAndUserStatusAndIsActive(unitMasterVO.getSocietyId(), UserRoles.NA, UserStatus.USER_APPROVED, true);
            pages = Functions.getPagesCount(count);

            responseVO.setPages(pages);
            responseVO.setOwnerList(userListVO);
            responseVO.setSuccessResponse(Messages.Member_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }

    public List<String> getMemberToken(OwnerMasterVO ownerMasterVO) {
        ResponseVO responseVO = new ResponseVO();

        List<Integer> userId = ownerMasterRepository.getUserId(ownerMasterVO.getSocietyId(), UserStatus.USER_APPROVED, true);

        return userService.getUserToken(userId);
    }

    public String getUnit(int unitId) {
        String unit;
        HelperListVO helperList = unitMasterRepository.getUnitIdDetail(unitId);
        unit = helperList.getUnit();
        return unit;
    }

    public List<String> getMobileNumber(int unit) {
        List<String> mobileNumber = new ArrayList<>();
        List<Integer> userId = ownerMasterRepository.getMobileNumber(unit, UserStatus.USER_APPROVED, true);

        for (Integer mobile : userId) {
            mobileNumber.add(userService.getMobileNumber(mobile));

        }
        return mobileNumber;
    }

    public List<String> getUserTokens(int societyId, int unitId) {
        List<String> userToken = new ArrayList<>();
        List<Integer> a = ownerMasterRepository.getUserIdListByUnitId(societyId, UserStatus.USER_APPROVED, true, unitId);
        for (Integer userId : a) {
            userToken.add(userService.getUserTokenBYUserId(userId));
        }
        return userToken;
    }

    public int getAreaIdByUnitId(int societyId, int unitId) {
        UnitMasterEntity unitMasterEntity = unitMasterRepository.findAreaIdBySocietyIdAndId(societyId, unitId);
        int areaId = unitMasterEntity.getAreaId();
        return areaId;
    }

    public String getAreaName(int unitId) {
        String areaName;
        HelperListVO helperList = unitMasterRepository.getUnitIdDetail(unitId);
        areaName = helperList.getAreaName();
        return areaName;
    }

    public String getDocumentType(int documentTypeId) {

        String documentType = "";
        DocumentMasterEntity documentMasterEntity = documentMasterRepository.findById(documentTypeId);
        if (documentMasterEntity != null)
            documentType = documentMasterEntity.getType();

        return documentType;
    }

    public ResponseVO addDocument(TypeVO typeVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.addTypeInputValidation(typeVO);

        DocumentMasterEntity documentMasterEntity = documentMasterRepository.findByType(typeVO.getType());

        if (documentMasterEntity == null) {
            DocumentMasterEntity documentMaster = modelMapper.map(typeVO, DocumentMasterEntity.class);
            documentMaster.setIsActive(true);
            documentMasterRepository.save(documentMaster);
            responseVO.setSuccessResponse(Messages.Document_Add_Success);
        } else {
            responseVO.setFailResponse(Messages.Document_Exist);
        }

        return responseVO;
    }

    public ResponseVO getDocumentList(TypeVO typeVO) {
        ResponseVO responseVO = new ResponseVO();
        DocumentMasterEntity documentMasterEntity = new DocumentMasterEntity();

        Pageable pageable = Functions.getPage(typeVO.getPage(), typeVO.getLimit());

        Specification<DocumentMasterEntity> specification = Specification
                .where(!Functions.nonNullString(typeVO.getType()) ? null : SpecificationService.likeSpecification(documentMasterEntity, EntityVariable.TYPE, typeVO.getType()))
                .and(Functions.nonNullString(typeVO.getAction()) && typeVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(documentMasterEntity, EntityVariable.IS_ACTIVE, typeVO.getIsActive()) : null);

        Page<DocumentMasterEntity> activeDocument = documentMasterRepository.findAll(specification, pageable);


//        List<DocumentMasterEntity> activeDocument = documentMasterRepository.findByIsActive(true);
//        int count = documentMasterRepository.countByIsActive(true);
//        int pages = Functions.getPagesCount(count);
//
//        List<DocumentMasterEntity> inActiveDocument = documentMasterRepository.findByIsActive(false);
//        int inActiveCount = documentMasterRepository.countByIsActive(false);
//        int inActivePage = Functions.getPagesCount(inActiveCount);

        if (activeDocument.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {
            List<TypeVO> activeType = modelMapperUtil.mapPage(activeDocument, TypeVO.class);

            int count = (int) documentMasterRepository.count(specification);
            int pages = Functions.getPagesCount(count);

            responseVO.setActiveType(activeType);
            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setSuccessResponse(Messages.Document_List);
        }

        return responseVO;
    }

    public ResponseVO updateDocument(TypeVO typeVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.updateTypeInputValidation(typeVO);

        DocumentMasterEntity documentMasterEntity = documentMasterRepository.getOne(typeVO.getId());

        if (documentMasterEntity.getType().equals(typeVO.getType()) && documentMasterEntity.getIsActive() == typeVO.getIsActive()) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {

            DocumentMasterEntity documentMaster = documentMasterRepository.findByType(typeVO.getType());

            if (documentMaster != null && documentMaster.getId() != typeVO.getId()) {
                responseVO.setFailResponse(Messages.Type_Exist_Error);
            } else {

                documentMasterEntity.setType(typeVO.getType());
                documentMasterEntity.setIsActive(typeVO.getIsActive());
                documentMasterRepository.save(documentMasterEntity);

                LanguageTypeVO languageTypeVO = new LanguageTypeVO(documentMasterEntity.getId(), typeVO.getType());
                societyUtilService.updateLanguage(languageTypeVO);

                responseVO.setSuccessResponse(Messages.Document_Update_Success);
            }
        }
        return responseVO;

    }

    public ResponseVO getAreaSearchList(AreaMasterVO areaMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        int count = 0;
        List<UnitListVO> activeUnitListVO;
        societyInputValidator.getAreaInputValidation(areaMasterVO);
        Pageable searchPageable = Functions.getPage(0, areaMasterVO.getLimit());

        AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(areaMasterVO.getAreaType());

        if (areaTypeEntity != null) {


            if (!areaTypeEntity.getType().equalsIgnoreCase(AreaType.Building)) {

                if (areaMasterVO.getAreaName() != null) {
                    List<UnitListVO> areaList = areaMasterRepository.getSearchAreaList(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true, searchPageable);
                    List<UnitListVO> unitList = unitMasterRepository.getBuildingUnit(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true, searchPageable);
                    activeUnitListVO = Stream.concat(areaList.stream(), unitList.stream()).collect(Collectors.toList());

                    int areaCount = areaMasterRepository.countBySocietyIdAndAreaTypeIdAndAreaNameLikeAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true);
                    int unitCount = unitMasterRepository.countBySocietyIdAndAreaTypeIdAndUnitLikeAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true);
                    count = areaCount + unitCount;

                } else {
                    List<UnitListVO> areaList = areaMasterRepository.getSearchList(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true, searchPageable);
                    List<UnitListVO> unitList = unitMasterRepository.getAreaTypeUnit(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true, searchPageable);
                    activeUnitListVO = Stream.concat(areaList.stream(), unitList.stream()).collect(Collectors.toList());

                    int areaCount = areaMasterRepository.countBySocietyIdAndAreaTypeIdAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true);
                    int unitCount = unitMasterRepository.countBySocietyIdAndAreaTypeIdAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true);
                    count = areaCount + unitCount;

                }

            } else {

                if (areaMasterVO.getAreaName() != null) {
                    activeUnitListVO = areaMasterRepository.getSearchAreaList(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true, searchPageable);
                    count = areaMasterRepository.countBySocietyIdAndAreaTypeIdAndAreaNameLikeAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), areaMasterVO.getAreaName(), true);
                } else {
                    activeUnitListVO = areaMasterRepository.getSearchList(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true, searchPageable);
                    count = areaMasterRepository.countBySocietyIdAndAreaTypeIdAndIsActive(areaMasterVO.getSocietyId(), areaTypeEntity.getId(), true);

                }
            }

            if (!activeUnitListVO.isEmpty()) {

                responseVO.setCount(count);
                responseVO.setSuccessResponse(areaTypeEntity.getType() + Messages.Society_List);
                responseVO.setActiveAreaList(activeUnitListVO);
            } else {
                responseVO.setFailResponse(Messages.NO + areaTypeEntity.getType() + Messages.Found);
            }
        } else {
            responseVO.setFailResponse(Messages.Area_Type_Invalid);
        }

        return responseVO;
    }

    public ResponseVO getAreaByUnitId(Integer unit) {
        ResponseVO responseVO = new ResponseVO();
        UnitListVO unitListVO = unitMasterRepository.getAreaByUnitId(unit);
        if (unitListVO != null) {
            responseVO.setUnitDetail(unitListVO);
        }
        return responseVO;
    }

    public ResponseVO getSocietyByPincode(String pincode) {
        ResponseVO responseVO = new ResponseVO();
        String lastCharRemoved = pincode.substring(0, pincode.length() - 1);

        List<Integer> firstSocietyId = societyRepository.getSocietyIdByPinCode(pincode);
        List<Integer> secondSocietyId = societyRepository.getSocietyIdByPinCodeLike(pincode, lastCharRemoved);

        List<Integer> societyIdList = new ArrayList<>();

        societyIdList.addAll(firstSocietyId);
        societyIdList.addAll(secondSocietyId);

//        SELECT * FROM `mysociety`.`society_master` WHERE pincode = '400067' OR pincode LIKE '40006%' ORDER BY pincode = '400064' DESC;

        responseVO.setSocietyId(societyIdList);
        return responseVO;
    }

    public ResponseVO addSocietyReview(SocietyReviewVO societyReviewVO) {
        ResponseVO responseVO = new ResponseVO();
        societyInputValidator.societyReviewInputValidation(societyReviewVO);

        SocietyReviewEntity societyReviewEntity = societyReviewRepository.findByOwnerId(societyReviewVO.getOwnerId());
        if (societyReviewVO.getAction().equalsIgnoreCase(Status.ADD)) {
            if (societyReviewEntity != null) {
                responseVO.setFailResponse(Messages.Already_Exist);
            }
            if (societyReviewEntity == null) {
                SocietyReviewEntity societyReviewEntity1 = modelMapper.map(societyReviewVO, SocietyReviewEntity.class);
                societyReviewRepository.save(societyReviewEntity1);
                responseVO.setSuccessResponse(Messages.Added_Success);
                return responseVO;

            }
        }
        if (societyReviewVO.getAction().equalsIgnoreCase(Status.UPDATE)) {

            SocietyReviewEntity societyReviewEntities = societyReviewRepository.findByOwnerId(societyReviewVO.getOwnerId());
            boolean isChange = false;

            if (societyReviewEntity != null) {

                if (societyReviewEntity.getRating() != societyReviewVO.getRating()) {
                    societyReviewEntity.setRating(societyReviewVO.getRating());
                    isChange = true;
                }
                if (!societyReviewEntity.getDescription().equalsIgnoreCase(societyReviewVO.getDescription())) {
                    societyReviewEntity.setDescription(societyReviewVO.getDescription());
                    isChange = true;
                }

                if (societyReviewEntity.getAmenityQuality() != societyReviewVO.getAmenityQuality()) {
                    societyReviewEntity.setAmenityQuality(societyReviewVO.getAmenityQuality());
                    isChange = true;
                }
                if (societyReviewEntity.getFriction() != societyReviewVO.getFriction()) {
                    societyReviewEntity.setFriction(societyReviewVO.getFriction());
                    isChange = true;
                }

                if (!societyReviewEntity.getClean().equalsIgnoreCase(societyReviewVO.getClean())) {
                    societyReviewEntity.setClean(societyReviewVO.getClean());
                    isChange = true;
                }

                if (societyReviewEntity.getConstruction() != (societyReviewVO.getConstruction())) {
                    societyReviewEntity.setConstruction(societyReviewVO.getConstruction());
                    isChange = true;
                }
                if (!societyReviewEntity.getCommute().equalsIgnoreCase(societyReviewVO.getCommute())) {
                    societyReviewEntity.setCommute(societyReviewVO.getCommute());
                    isChange = true;
                }
                if (societyReviewEntity.getNameDisplay() != societyReviewVO.getNameDisplay()) {
                    societyReviewEntity.setNameDisplay(societyReviewVO.getNameDisplay());
                    isChange = true;
                }
                if (!societyReviewEntity.getNoise().equalsIgnoreCase(societyReviewVO.getNoise())) {
                    societyReviewEntity.setNoise(societyReviewVO.getNoise());
                    isChange = true;
                }
                if (!societyReviewEntity.getSafe().equalsIgnoreCase(societyReviewVO.getSafe())) {
                    societyReviewEntity.setSafe(societyReviewVO.getSafe());
                    isChange = true;
                }
                if (societyReviewEntity.getProminent() != societyReviewVO.getProminent()) {
                    societyReviewEntity.setProminent(societyReviewVO.getProminent());
                    isChange = true;
                }

                if (societyReviewEntity.getEventFull() != (societyReviewVO.getEventFull())) {
                    societyReviewEntity.setEventFull(societyReviewVO.getEventFull());
                    isChange = true;
                }

                // societyReviewEntities.setRating(societyReviewVO.getRating());
                //societyReviewEntities.setFriction(societyReviewVO.getFriction());
                //societyReviewEntities.setAmenityQuality(societyReviewVO.getAmenityQuality());
                // societyReviewEntities.setFriction(societyReviewVO.getFriction());
                // societyReviewEntities.setClean(societyReviewVO.getClean());
                //societyReviewEntities.setConstruction(societyReviewVO.getConstruction());
                // societyReviewEntities.setCommute(societyReviewVO.getCommute());
                // societyReviewEntities.setDescription(societyReviewVO.getDescription());
                //societyReviewEntities.setNameDisplay(societyReviewVO.getNameDisplay());
                // societyReviewEntities.setNoise(societyReviewVO.getNoise());
                //societyReviewEntities.setSafe(societyReviewVO.getSafe());

                //  SocietyReviewEntity societyReviewEntities = modelMapper.map(societyReviewVO, SocietyReviewEntity.class);
                if (isChange) {
                    societyReviewRepository.save(societyReviewEntities);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                    return responseVO;
                } else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }
            return responseVO;
        }
        return responseVO;
    }


    public UnitListVO getUnitAndAreaDetail(UnitListVO unitListVO) {
        return unitMasterRepository.getByAreaAndUnit(unitListVO.getSocietyId(), unitListVO.getAreaName(), unitListVO.getUnit());

    }

    public UnitListVO getAreaDetail(SocietyUserListVO listVO) {
        AreaTypeEntity areaTypeEntity = areaTypeRepository.findByType(AreaType.Building);
        return unitMasterRepository.getByAreaAndType(listVO.getSocietyId(), listVO.getAreaName(), areaTypeEntity.getId());
    }

    public ResponseVO addParkingSlot(List<ParkingSlotVO> parkingSlotVO) {
        ResponseVO responseVO = new ResponseVO();

        for (ParkingSlotVO parkingSlotVO1 : parkingSlotVO) {
            societyInputValidator.addsocietyParkingInputValidation(parkingSlotVO1);
            ParkingSlotEntity checkDuplicate = parkingSlotRepository.findByUnitIdAndName(parkingSlotVO1.getUnitId(),parkingSlotVO1.getName());
            if (checkDuplicate != null) {
                throw new PZApplicationException(Messages.Parking_Slot_Exist);
            } else {
                ParkingSlotEntity parkingSlotEntity1 = modelMapper.map(parkingSlotVO1, ParkingSlotEntity.class);
                parkingSlotEntity1.setIsActive(true);
//                parkingSlotEntity1.setIsVisitor(parkingSlotVO1.isVisitor());
//                parkingSlotEntity1.setIsOccupied(parkingSlotVO1.isOccupied());
                parkingSlotRepository.save(parkingSlotEntity1);
                responseVO.setSuccessResponse(Messages.Added_Success);
            }
            }
            return responseVO;
        }

    public ResponseVO updateParkingSlot(ParkingSlotVO parkingSlotVO) {
        ResponseVO responseVO = new ResponseVO();
        SocietyInputValidator.updatesocietyParkingInputValidation(parkingSlotVO);
        ParkingSlotEntity parkingSlotEntity = parkingSlotRepository.getOne(parkingSlotVO.getId());
         boolean isChange=false;

            if(!parkingSlotEntity.getName().equalsIgnoreCase(parkingSlotVO.getName())){
                parkingSlotEntity.setName(parkingSlotVO.getName());
                isChange=true;
            }
          if(parkingSlotEntity.getIsActive() !=parkingSlotVO.getIsActive()){
              parkingSlotEntity.setIsActive(parkingSlotVO.getIsActive());
              isChange=true;
          }
        if(parkingSlotEntity.getIsOccupied() != parkingSlotVO.getIsOccupied()){
            parkingSlotEntity.setIsVisitor(parkingSlotVO.getIsVisitor());
            isChange=true;
        }
        if(isChange){
            parkingSlotRepository.save(parkingSlotEntity);
            responseVO.setSuccessResponse(Messages.Update_Successfully);
            return responseVO;

        }
        else{
          responseVO.setFailResponse(Messages.No_Changes_Found);
            return responseVO;
        }
    }


    public ResponseVO getParkingSlot(ParkingSlotVO parkingSlotVO){
        ResponseVO responseVO = new ResponseVO();
        SocietyInputValidator.getsocietyParkingInputValidation(parkingSlotVO);
        Pageable pageable = Functions.getPage(parkingSlotVO.getPage(), parkingSlotVO.getLimit());
        ParkingSlotEntity parkingSlotEntity = new ParkingSlotEntity();

        Specification <ParkingSlotEntity> specification = Specification
               .where(!Functions.nonNullString(parkingSlotVO.getName()) ? null : SpecificationService.likeSpecification(parkingSlotEntity, EntityVariable.NAME,parkingSlotVO.getName())
                       .and(parkingSlotVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.UNIT_ID, parkingSlotVO.getUnitId()))
                       .and(parkingSlotVO.getAreaId() == 0 ? null : SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.AREA_ID, parkingSlotVO.getAreaId()))
                       .and(parkingSlotVO.getIsVisitor() ? SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.Is_VISITOR, true) : SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.Is_VISITOR, false))
                       .and(parkingSlotVO.getIsOccupied() ? SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.Is_OCCUPIED, true) : SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.Is_OCCUPIED, false))
                       .and(parkingSlotVO.getIsActive() ? SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.IS_ACTIVE, true) : SpecificationService.equalSpecification(parkingSlotEntity, EntityVariable.IS_ACTIVE, false)));
        Page<ParkingSlotEntity> parkingSlotEntitie = parkingSlotRepository.findAll(specification , pageable);

        if(!parkingSlotEntitie.isEmpty()){
            List<ParkingSlotVO> parkingSlotList = modelMapperUtil.mapPage(parkingSlotEntitie, ParkingSlotVO.class);
            responseVO.setParkingSlotVOList(parkingSlotList);
            responseVO.setSuccessResponse(Messages.Added_Success);
        }
        else{
            responseVO.setFailResponse(Messages.Not_Found);
        }
        return responseVO;
    }

}
