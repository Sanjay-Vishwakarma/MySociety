package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.Amenity;
import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.NotificationAction;
import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.amenityEntity.AmenityChatRecordEntity;
import com.pz.mysociety.entity.amenityEntity.AmenityMasterEntity;
import com.pz.mysociety.entity.amenityEntity.AmenityTypeEntity;
import com.pz.mysociety.entity.societyEntity.AreaMasterEntity;
import com.pz.mysociety.model.Request.AmenityTypeVO;
import com.pz.mysociety.model.Request.AmenityVO;
import com.pz.mysociety.model.Request.AreaMasterVO;
import com.pz.mysociety.model.Request.amenityRequestVO.AmenityChatRecordVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.AmenityListVO;
import com.pz.mysociety.repository.amenityRepository.AmenityChatRecordRepository;
import com.pz.mysociety.repository.amenityRepository.AmenityMasterRepository;
import com.pz.mysociety.repository.amenityRepository.AmenityTypeRepository;
import com.pz.mysociety.validation.AmenityInputValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class AmenityMasterService {

    @Autowired
    private AmenityValidationService amenityValidationService;
    @Autowired
    private AmenityInputValidator amenityInputValidator;
    @Autowired
    private AmenityMasterRepository amenityMasterRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AmenityChatRecordRepository amenityChatRepository;
    @Autowired
    private AmenityTypeRepository amenityTypeRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Autowired
    private SocietyService societyService;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private PushNotificationService pushNotificationService;

    public ResponseVO addAmenity( AmenityVO amenityRequestVO) throws PZConstraintViolationException{
        ResponseVO responseVO= new ResponseVO();
        AmenityChatRecordEntity amenityRecordEntity = new AmenityChatRecordEntity();
        amenityInputValidator.addAmenityInputValidator(amenityRequestVO);
        amenityValidationService.isUserMappingExist(amenityRequestVO.getUserId());
        amenityValidationService.isAmenityBooked(amenityRequestVO);

        Date start = Functions.getFormatDate(amenityRequestVO.getStartDate());
        Date end = Functions.getFormatDate(amenityRequestVO.getEndDate());

        AmenityMasterEntity checkAmenity = amenityMasterRepository.getRepeatAmenity(amenityRequestVO.getUnitId(), start, end);

        if(checkAmenity != null) {
            responseVO.setFailResponse(Messages.Amenity_Exist_Error );
            return responseVO;
        }

        amenityRequestVO.setAmenityStatus(Amenity.Amenity_Status_Pending);
        amenityRequestVO.setIsRead(true);
        AmenityMasterEntity amenityMasterEntity= modelMapper.map(amenityRequestVO, AmenityMasterEntity.class);
        amenityMasterEntity.setStartDate(Functions.getFormatDate(amenityRequestVO.getStartDate()));
        amenityMasterEntity.setEndDate(Functions.getFormatDate(amenityRequestVO.getEndDate()));
        amenityMasterEntity.setAreaId(amenityRequestVO.getAreaId());
        AmenityMasterEntity amenityMasterEntity1= amenityMasterRepository.save(amenityMasterEntity);

        amenityRecordEntity.setAmenityId(amenityMasterEntity1.getId());
        amenityRecordEntity.setRole(Amenity.Role_User);
        amenityRecordEntity.setUserId(amenityRequestVO.getUserId());

        amenityRecordEntity.setContent(amenityRequestVO.getDescription());
        if(Functions.nonNullString(amenityRequestVO.getAttachment())){
            String fileName = this.generateFileName(amenityRequestVO.getSocietyId()) +".png";
            FileHandlingUtil.fileUpload(amenityRequestVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_AMENITY_CHAT_IMAGES, null);
            amenityRecordEntity.setAttachment(Types.AMENITY_CHAT_IMAGES + fileName);
        }
        amenityChatRepository.save(amenityRecordEntity);
        householdService.activityLog(amenityRequestVO.getUnitId(), amenityRequestVO.getUserId(),Messages.Amenity_Added, amenityRequestVO.getDescription()+Messages.Amenity_Added);//Activity Log
        responseVO.setSuccessResponse(Messages.Amenity_Saved_Successfully);
        return responseVO;
    }

    public ResponseVO changeAmenityStatus(AmenityVO amenityRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        amenityInputValidator.amenityInputValidator(amenityRequestVO);
        AmenityMasterEntity amenityMasterEntity = amenityMasterRepository.getOne(amenityRequestVO.getAmenityId());
        if(amenityMasterEntity.getAmenityStatus().equals(Amenity.Amenity_Status_Pending)) {
            if (amenityRequestVO.getAmenityStatus().equalsIgnoreCase(Amenity.Amenity_Status_Approved) || amenityRequestVO.getAmenityStatus().equalsIgnoreCase(Amenity.Amenity_Status_Rejected)) {
                amenityRequestVO.setStartDate(amenityMasterEntity.getStartDate().toString());
                amenityRequestVO.setEndDate(amenityMasterEntity.getEndDate().toString());
                amenityRequestVO.setAmenityTypeId(amenityMasterEntity.getAmenityTypeId());
                amenityValidationService.isAmenityBooked(amenityRequestVO);
                amenityMasterEntity.setAmenityStatus(amenityRequestVO.getAmenityStatus());
                amenityMasterRepository.save(amenityMasterEntity);
                householdService.activityLog(amenityMasterEntity.getUnitId(), amenityMasterEntity.getUserId(),Messages.Amenity_Updated, amenityMasterEntity.getDescription()+Messages.Amenity_Updated);//Activity Log


                //Notification Send To User
                List<String> tokenList = societyService.getUserTokens(amenityMasterEntity.getSocietyId(), amenityMasterEntity.getUnitId());

                if (tokenList.size() > 0 && Functions.nonNullString(amenityMasterEntity.getDescription()))
                    pushNotificationService.sendActionNotificationToToken(amenityRequestVO.getAmenityStatus(), amenityMasterEntity.getDescription() + " is " + amenityRequestVO.getAmenityStatus(), NotificationAction.AMENITY, amenityRequestVO.getAmenityId(), Messages.Amenity_Status, tokenList);
                //END


                responseVO.setSuccessResponse(Messages.StatusUpdated);
                return responseVO;
            }
        }
        responseVO.setFailResponse(Messages.Amenity_Invalid_Status);
        return responseVO;
    }

    public ResponseVO addAmenityRecord(AmenityVO amenityRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        amenityInputValidator.addAmenityReocrdInputValidator(amenityRequestVO);
        amenityValidationService.isAmenityIdExist(amenityRequestVO);
        if(amenityRequestVO.getRole().equals(Amenity.Role_User))
        amenityValidationService.isUserMappingExist(amenityRequestVO.getUserId());
         // check for Admin ID Exist
        if(amenityRequestVO.getRole().equals(Amenity.Role_Society)){
            AmenityMasterEntity amenityMasterEntity = amenityMasterRepository.getAmenityById(amenityRequestVO.getAmenityId());
            amenityMasterEntity.setIsRead(false);
            amenityMasterRepository.save(amenityMasterEntity);
        }
        AmenityChatRecordEntity amenityChatRecordEntityEntity1= modelMapper.map(amenityRequestVO, AmenityChatRecordEntity.class);
        if(Functions.nonNullString(amenityRequestVO.getAttachment())){
            String fileName = this.generateFileName(amenityRequestVO.getSocietyId()) +".png";
            FileHandlingUtil.fileUpload(amenityRequestVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_AMENITY_CHAT_IMAGES, null);
            amenityChatRecordEntityEntity1.setAttachment(Types.AMENITY_CHAT_IMAGES + fileName);
        }
        amenityChatRepository.save(amenityChatRecordEntityEntity1);

        //Notification Send To User
        AmenityMasterEntity amenityMasterEntity = amenityMasterRepository.getOne(amenityRequestVO.getAmenityId());
        List<String> tokenList = societyService.getUserTokens(amenityMasterEntity.getSocietyId(), amenityMasterEntity.getUnitId());

        if (tokenList.size() > 0 && Functions.nonNullString(amenityRequestVO.getContent()))
            pushNotificationService.sendActionNotificationToToken(amenityMasterEntity.getDescription(), amenityRequestVO.getContent(), NotificationAction.AMENITY_CHAT, amenityRequestVO.getAmenityId(), null, tokenList);
        //END

        responseVO.setAmenityId(amenityRequestVO.getAmenityId());
        responseVO.setSuccessResponse(Messages.Sent_Successfully);
        return responseVO;
    }

    public ResponseVO getAmenityRecord(AmenityVO amenityRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        amenityInputValidator.amenityInputValidator(amenityRequestVO);
        amenityValidationService.isAmenityIdExist(amenityRequestVO);

        List<AmenityChatRecordEntity> amenityChatRecordEntities = amenityChatRepository.findByAmenityId(amenityRequestVO.getAmenityId());

        if(amenityChatRecordEntities == null){
            responseVO.setFailResponse(Messages.Amenity_Record_NotFound);
            return responseVO;
        }
        List<AmenityChatRecordVO> amenityChatRecordVOS = modelMapperUtil.mapList(amenityChatRecordEntities, AmenityChatRecordVO.class);
        responseVO.setAmenityRecord(amenityChatRecordVOS);
        responseVO.setSuccessResponse(Messages.Amenity_Record);
        return responseVO;
    }
    public ResponseVO changeAmenityFlag(AmenityVO amenityMasterRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        amenityInputValidator.changeAmenityFlagInputValidator(amenityMasterRequestVO);
        amenityValidationService.isAmenityIdExist(amenityMasterRequestVO);
        if(amenityMasterRequestVO.getRole().equals(Amenity.Role_User)){
              amenityMasterRepository.changeAmenityFlag(true, amenityMasterRequestVO.getAmenityId());
            amenityChatRepository.changeAdminFlag(true, amenityMasterRequestVO.getAmenityId(), Amenity.Role_Society);
            responseVO.setSuccessResponse(Amenity.Role_Society+" "+Messages.Message_Read_Successfully);
            return responseVO;
        }else  if(amenityMasterRequestVO.getRole().equals(Amenity.Role_Society)){
            amenityChatRepository.changeAdminFlag(true, amenityMasterRequestVO.getAmenityId(), Amenity.Role_User);
            responseVO.setSuccessResponse(Amenity.Role_User+" "+Messages.Message_Read_Successfully);
            return responseVO;
        }
        responseVO.setFailResponse(Messages.Send_Proper_Role);
        return responseVO;
    }
   public ResponseVO getAmenity(AmenityVO amenityRequestVO) throws PZConstraintViolationException, ParseException {
       ResponseVO responseVO = new ResponseVO();
       amenityInputValidator.getAmenityListInputValidator(amenityRequestVO);

       int count=0;
       Pageable paging = Functions.getPage(amenityRequestVO.getPage(), amenityRequestVO.getLimit());
       AmenityMasterEntity amenityMasterEntity = new AmenityMasterEntity();
       Specification<AmenityMasterEntity> specification = Specification.where(!Functions.nonNullString(amenityRequestVO.getAmenityStatus()) ? null : SpecificationService.equalSpecification(amenityMasterEntity, EntityVariable.AMENITY_STATUS, amenityRequestVO.getAmenityStatus()))
               .and(amenityRequestVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(amenityMasterEntity, EntityVariable.SOCIETY_ID, amenityRequestVO.getSocietyId()))
               .and(amenityRequestVO.getAmenityTypeId() == 0 ? null : SpecificationService.equalSpecification(amenityMasterEntity, EntityVariable.AMENITY_TYPE_ID, amenityRequestVO.getAmenityTypeId()))
               .and(amenityRequestVO.getAreaId()==0? null:SpecificationService.equalSpecification(amenityMasterEntity,EntityVariable.AREA_ID ,amenityRequestVO.getAreaId()))
               //.and(!Functions.nonNullString(amenityRequestVO.getAreaName()) ? null : SpecificationService.likeSpecification(amenityMasterEntity, EntityVariable.AREA,amenityRequestVO.getAreaName()))
               .and(amenityRequestVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(amenityMasterEntity, EntityVariable.UNIT_ID, amenityRequestVO.getUnitId()))
               .and(amenityRequestVO.getUserId() == 0 ? null : SpecificationService.equalSpecification(amenityMasterEntity, EntityVariable.USER_ID, amenityRequestVO.getUserId()))
               .and(!Functions.nonNullString(amenityRequestVO.getStartDate()) ? null : SpecificationService.greaterThanOrEqualToDateSpecification(amenityMasterEntity, EntityVariable.START_DATE, new SimpleDateFormat("yyyy-MM-dd").parse(amenityRequestVO.getStartDate())))
               .and(!Functions.nonNullString(amenityRequestVO.getStartDate()) ? null : SpecificationService.lessThanOrEqualToDateSpecification(amenityMasterEntity, EntityVariable.START_DATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(amenityRequestVO.getStartDate() + " 23:59:59")))
               .and(SpecificationService.descendingOrder(amenityMasterEntity, EntityVariable.ID));


       Page<AmenityMasterEntity> amenityMasterEntities = amenityMasterRepository.findAll( specification, paging);
       count = (int) amenityMasterRepository.count(specification);

       if(amenityMasterEntities.isEmpty()){
           responseVO.setFailResponse(Messages.Not_Found);
           return responseVO;
       }else {
           List<AmenityListVO> amenityListVOs = new ArrayList<>();

           for (AmenityMasterEntity amenityMaster : amenityMasterEntities) {
               AmenityListVO amenityListVO = modelMapper.map(amenityMaster, AmenityListVO.class);
               AmenityTypeEntity amenityTypeEntity = amenityTypeRepository.getAmenityTypeById(amenityListVO.getAmenityTypeId());
               amenityListVO.setType(amenityTypeEntity.getType());
               ResponseVO responseVO1 = societyService.getSocietyDetail1(amenityListVO);
               amenityListVO.setUnit(responseVO1.getAmenityList().getUnit());
               amenityListVO.setAreaName(responseVO1.getAmenityList().getAreaName());
               amenityListVO.setUserName(responseVO1.getAmenityList().getUserName());
               amenityListVO.setEmail(responseVO1.getAmenityList().getEmail());
               amenityListVO.setMobileNumber(responseVO1.getAmenityList().getMobileNumber());
               amenityListVO.setDuration(Functions.getDuration(amenityListVO.getStartDate(), amenityListVO.getEndDate()));
//
//               String[] startDate = amenityListVO.getStartDate().split(" ");
//               String[] endDate = amenityListVO.getEndDate().split(" ");
//
//               amenityListVO.setStartDate(startDate[0]);
//               amenityListVO.setStartTime(startDate[1]);
//               amenityListVO.setEndDate(endDate[0]);
//               amenityListVO.setEndTime(endDate[1]);

               amenityListVOs.add(amenityListVO);

           }
               responseVO.setAmenityDetailsList(amenityListVOs);
               int pages = Functions.getPagesCount(count);
               responseVO.setCount(count);
               responseVO.setPages(pages);
               responseVO.setSuccessResponse(Messages.Amenity_Type_Found);
       }
       return responseVO;

   }
    //Amenity Type
    public ResponseVO addAmenityType(AmenityTypeVO amenityTypeRequest) throws PZConstraintViolationException {

        amenityInputValidator.addAmenityTypeInputValidator(amenityTypeRequest);
        amenityValidationService.isSocietyExist(amenityTypeRequest);
        ResponseVO responseVO= new ResponseVO();
        AmenityTypeEntity amenityTypeEntity = amenityTypeRepository.findBySocietyIdAndType(amenityTypeRequest.getSocietyId(), amenityTypeRequest.getType());
        if(amenityTypeEntity != null){
            responseVO.setFailResponse(Messages.Amenity_Type_Exist);
            return responseVO;
        }
        AmenityTypeEntity amenityMasterTypeEntity= modelMapper.map(amenityTypeRequest, AmenityTypeEntity.class);
        amenityMasterTypeEntity.setIsActive(true);
        amenityTypeRepository.save(amenityMasterTypeEntity);
        responseVO.setSuccessResponse(Messages.Amenity_Type_Added);
        return responseVO;
    }

    public ResponseVO getSocietyAmenityType(AmenityTypeVO amenityTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        Pageable paging = Functions.getPage(amenityTypeRequestVO.getPage(), amenityTypeRequestVO.getLimit());
       amenityInputValidator.getSocietyAmenityTypeInputValidator(amenityTypeRequestVO);
       amenityValidationService.isSocietyExist(amenityTypeRequestVO);

        List<AmenityTypeEntity> activeAmenityTypeEntities = amenityTypeRepository.findBySocietyIdAndIsActive(amenityTypeRequestVO.getSocietyId(), true, paging);
        List<AmenityTypeEntity> inActiveAmenityTypeEntities = amenityTypeRepository.findBySocietyIdAndIsActive(amenityTypeRequestVO.getSocietyId(), false, paging);
        int activeCount=amenityTypeRepository.countByIsActive(true);
        int inActiveCount=amenityTypeRepository.countByIsActive(false);
        int activePages = Functions.getPagesCount(activeCount);
        int inActivePages = Functions.getPagesCount(inActiveCount);

        if(activeAmenityTypeEntities.isEmpty() && inActiveAmenityTypeEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Amenity_Type_Not_Found);
            return responseVO;
        }
        List<AmenityTypeVO> activeAmenityTypeList=modelMapperUtil.mapList(activeAmenityTypeEntities, AmenityTypeVO.class);
        List<AmenityTypeVO> inActiveAmenityTypeList=modelMapperUtil.mapList(inActiveAmenityTypeEntities, AmenityTypeVO.class);
        responseVO.setActiveAmenityTypeList(activeAmenityTypeList);
        responseVO.setInActiveAmenityTypeList(inActiveAmenityTypeList);
        responseVO.setCount(activeCount);
        responseVO.setInActiveCount(inActiveCount);
        responseVO.setPages(activePages);
        responseVO.setInActivePages(inActivePages);
        responseVO.setSuccessResponse(Messages.Amenity_Type_Found);
        return responseVO;
    }

    public ResponseVO updateAmenityType(AmenityTypeVO amenityTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO= new ResponseVO();
        amenityInputValidator.updateAmenityTypeInputValidator(amenityTypeRequestVO);
        amenityValidationService.isAmenityTypeIdExist(amenityTypeRequestVO);

        AmenityTypeEntity amenityTypeEntity = amenityTypeRepository.getAmenityTypeById(amenityTypeRequestVO.getId());
        if(amenityTypeEntity.getType().equals(amenityTypeRequestVO.getType()) && amenityTypeEntity.getIsActive() == amenityTypeRequestVO.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Detected);
            return responseVO;
        }
        amenityTypeEntity.setType(amenityTypeRequestVO.getType());
        amenityTypeEntity.setIsActive(amenityTypeRequestVO.getIsActive());
        amenityTypeRepository.save(amenityTypeEntity);
        responseVO.setSuccessResponse(Messages.Amenity_Type_Updated);
        return responseVO;
    }

    private String generateFileName(int societyId) {

        Random random = new Random();

        String newFileName = ConstantVariables.AMENITY_CHAT_RECORD + "_" + random.nextInt(999999);
            AmenityChatRecordEntity entity = amenityChatRepository.findByAttachment( newFileName);

            if (entity != null) {
                generateFileName(societyId);
            }

        return newFileName;
    }
    public ResponseVO getAmenityDashBoard(AmenityVO amenityVO){
        ResponseVO responseVO = new ResponseVO();
        int loopCount = 7;

        List<AmenityVO> amenityVOs = new ArrayList<>();
        List<AmenityVO> amenityVOs1 = new ArrayList<>();
        List<AmenityVO> amenityVOs2 = new ArrayList<>();

        LocalDate fromDate = amenityVO.getFromDate();
        LocalDate fromDate1 = amenityVO.getFromDate();

        for (int i = 0; i < 12; i++) {

            if (i < loopCount) {
                AmenityVO amenityVO1 = new AmenityVO();
                LocalDate fromCurrentDate = fromDate1 ;
                LocalDate toLastDate = fromDate1.plus(-1, ChronoUnit.WEEKS);
                int pendingCount = amenityMasterRepository.countBasedOnWEEK(amenityVO.getSocietyId(), Status.PENDING, toLastDate, fromCurrentDate);
                int approvedCount = amenityMasterRepository.countBasedOnWEEK(amenityVO.getSocietyId(), Status.APPROVED, toLastDate, fromCurrentDate);
                int rejectedCount = amenityMasterRepository.countBasedOnWEEK(amenityVO.getSocietyId(), Status.REJECTED, toLastDate, fromCurrentDate);
                amenityVO1.setFromDate(fromDate1);
                amenityVO1.setToDate(toLastDate);
                amenityVO1.setPendingCount(pendingCount);
                amenityVO1.setApprovedCount(approvedCount);
                amenityVO1.setRejectedCount(rejectedCount);
                amenityVOs.add(amenityVO1);

                fromDate1 = toLastDate;
            }
            if (i < loopCount) {
                AmenityVO amenityVO1 = new AmenityVO();
                LocalDate result = fromDate.plus(-i, ChronoUnit.DAYS);
                int pendingCount = amenityMasterRepository.countBySocietyIdAndStatus(amenityVO.getSocietyId(), Status.PENDING, result);
                int approvedCount = amenityMasterRepository.countBySocietyIdAndStatus(amenityVO.getSocietyId(), Status.APPROVED, result);
                int rejectedCount = amenityMasterRepository.countBySocietyIdAndStatus(amenityVO.getSocietyId(), Status.REJECTED, result);
                amenityVO1.setFromDate(result);
                amenityVO1.setPendingCount(pendingCount);
                amenityVO1.setApprovedCount(approvedCount);
                amenityVO1.setRejectedCount(rejectedCount);
                amenityVOs1.add(amenityVO1);
            }

            AmenityVO complainVO1 = new AmenityVO();
            LocalDate toLastDate = fromDate.plus(-i, ChronoUnit.MONTHS);

            int pendingCount = amenityMasterRepository.countBasedOnMonth(amenityVO.getSocietyId(), Status.PENDING, toLastDate.getMonthValue(), toLastDate.getYear());
            int approvedCount = amenityMasterRepository.countBasedOnMonth(amenityVO.getSocietyId(), Status.APPROVED, toLastDate.getMonthValue(), toLastDate.getYear());
            int rejectedCount = amenityMasterRepository.countBasedOnMonth(amenityVO.getSocietyId(), Status.REJECTED, toLastDate.getMonthValue(), toLastDate.getYear());
            complainVO1.setMonth(toLastDate.getMonth());
            complainVO1.setYear(toLastDate.getYear());
            complainVO1.setPendingCount(pendingCount);
            complainVO1.setApprovedCount(approvedCount);
            complainVO1.setRejectedCount(rejectedCount);
            amenityVOs2.add(complainVO1);

        }

        if (amenityVOs.size() == 0 && amenityVOs1.size() == 0 && amenityVOs2.size() == 0) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        } else {
            responseVO.setAmenityMonthlyCount(amenityVOs);
            responseVO.setAmenityWeeklyCount(amenityVOs1);
            responseVO.setAmenityYearlyCount(amenityVOs2);

        }
        return responseVO;
    }
   /* public ResponseVO getAmenityTimeLine(AmenityVO amenityVO){
        ResponseVO responseVO= new ResponseVO();
        LocalDate fromDate = amenityVO.getFromDate();
        LocalDate toLastDate = fromDate.plus(+1, ChronoUnit.MONTHS);
        String toDate =toLastDate + " 23:59:59";
        List<AmenityMasterEntity> amenityMasterEntities = amenityMasterRepository.findBySocietyIdAndStartDateLessThanEqual(amenityVO.getSocietyId(), toDate);

        List<AmenityListVO> amenityVOs = modelMapperUtil.mapList(amenityMasterEntities, AmenityListVO.class);
        responseVO.setAmenityDetailsList(amenityVOs);
        responseVO.setSuccessResponse(Messages.Area_List_Message);

        return responseVO;
    }*/
}
