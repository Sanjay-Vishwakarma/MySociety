package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.noticeEntity.SocietyNoticeEntity;
import com.pz.mysociety.entity.noticeEntity.SocietyNoticeTypeEntity;
import com.pz.mysociety.entity.noticeEntity.UnitNoticeMasterEntity;
import com.pz.mysociety.model.Request.NoticeTypeVO;
import com.pz.mysociety.model.Request.OwnerMasterVO;
import com.pz.mysociety.model.Request.SocietyNoticeVO;
import com.pz.mysociety.model.Request.TypeVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.UnitListVO;
import com.pz.mysociety.repository.noticeRepository.NoticeTypeRepository;
import com.pz.mysociety.repository.noticeRepository.SocietyNoticeRepository;
import com.pz.mysociety.repository.noticeRepository.UnitNoticeMasterRepository;
import com.pz.mysociety.validation.SocietyNoticeInputValidator;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocietyNoticeService {

    @Autowired
    private SocietyNoticeRepository societyNoticeRepository;
    @Autowired
    private SocietyNoticeValidationService noticeValidation;
    @Autowired
    private SocietyNoticeInputValidator noticeInputValidator;
    @Autowired
    private SocietyService societyService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Autowired
    private NoticeTypeRepository noticeTypeRepository;
    @Autowired
    private UnitNoticeMasterRepository unitNoticeMasterRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    public ResponseVO addNotice(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();

        if (societyNoticeRequestVO.getAction().equalsIgnoreCase(Status.ADD)) {
            noticeInputValidator.addNoticeInputValidator(societyNoticeRequestVO);
            societyService.isSocietyExistNotice(societyNoticeRequestVO);

            SocietyNoticeEntity societyNoticeEntity = modelMapper.map(societyNoticeRequestVO, SocietyNoticeEntity.class);

            if (Functions.nonNullString(societyNoticeRequestVO.getAttachment())) {
                String fileName = this.generateFileName(societyNoticeRequestVO.getSocietyId());
                FileHandlingUtil.fileUpload(societyNoticeRequestVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_NOTICE_IMAGES, null);
                societyNoticeEntity.setAttachment(Types.NOTICE + fileName);
            }
            SocietyNoticeEntity noticeEntity = societyNoticeRepository.save(societyNoticeEntity);

            //Notification Start

            if (societyNoticeRequestVO.getUnitId() != null) {
                List<String> memberToken = new ArrayList<>();
                societyNoticeRequestVO.getUnitId().forEach(unitId -> {

                    UnitNoticeMasterEntity unitNoticeMasterEntity = new UnitNoticeMasterEntity();
                    unitNoticeMasterEntity.setSocietyId(societyNoticeRequestVO.getSocietyId());
                    unitNoticeMasterEntity.setNoticeId(noticeEntity.getId());
                    unitNoticeMasterEntity.setUnitId(unitId);
                    unitNoticeMasterRepository.save(unitNoticeMasterEntity);

                    List<String> userToken = societyService.getUserTokens(societyNoticeRequestVO.getSocietyId(), unitId);
                    memberToken.addAll(userToken);
                });

                pushNotificationService.sendActionNotificationToToken(societyNoticeEntity.getNoticeTitle(), societyNoticeEntity.getNoticeContent(), NotificationAction.NOTICE, societyNoticeEntity.getId(), null, memberToken);
            } else {

                OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                ownerMasterVO.setSocietyId(societyNoticeRequestVO.getSocietyId());
                List<String> memberToken = societyService.getMemberToken(ownerMasterVO);

                pushNotificationService.sendActionNotificationToToken(societyNoticeEntity.getNoticeTitle(), societyNoticeEntity.getNoticeContent(), NotificationAction.NOTICE, societyNoticeEntity.getId(), null, memberToken);
                responseVO.setSuccessResponse(Messages.Notice_Added_Successfully);

            }
            responseVO.setSuccessResponse(Messages.Notice_Updated_Successfully);

        }
        //END


        if (societyNoticeRequestVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            boolean isChange = false;

            noticeInputValidator.addNoticeInputValidator(societyNoticeRequestVO);

            SocietyNoticeEntity societyNoticeEntity = societyNoticeRepository.getOne(societyNoticeRequestVO.getId());
            //noticeValidation.updateNoticeValidator(societyNoticeRequestVO);

            if (Functions.compareValue(societyNoticeEntity.getNoticeTitle(), societyNoticeRequestVO.getNoticeTitle())) {
                societyNoticeEntity.setNoticeTitle(societyNoticeRequestVO.getNoticeTitle());
                isChange = true;
            }
            if (Functions.compareValue(societyNoticeEntity.getNoticeContent(), societyNoticeRequestVO.getNoticeContent())) {
                societyNoticeEntity.setNoticeContent(societyNoticeRequestVO.getNoticeContent());
                isChange = true;
            }

            if (Functions.compareValue(societyNoticeEntity.getNoticeType(), societyNoticeRequestVO.getNoticeType())) {
                societyNoticeEntity.setNoticeType(societyNoticeRequestVO.getNoticeType());
                isChange = true;
            }


            //societyNoticeEntity.setNoticeTitle(noticeRequestVO.getNoticeTitle());
            // societyNoticeEntity.setNoticeContent(noticeRequestVO.getNoticeContent());
            //  societyNoticeEntity.setNoticeType(noticeRequestVO.getNoticeType());

            if (Functions.nonNullString(societyNoticeRequestVO.getAttachment())) {
                String fileName = this.generateFileName(societyNoticeRequestVO.getSocietyId());
                FileHandlingUtil.fileUpload(societyNoticeRequestVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_NOTICE_IMAGES, societyNoticeEntity.getAttachment());
                societyNoticeEntity.setAttachment(Types.NOTICE + fileName);
                isChange = true;
            }

            String a = societyNoticeEntity.getStartDate() == null ? null : societyNoticeEntity.getStartDate().toString();
            String b = societyNoticeRequestVO.getStartDate() == null ? null : societyNoticeRequestVO.getStartDate().toString();

             if (Functions.compareValue(societyNoticeEntity.getStartDate() == null ? null : societyNoticeEntity.getStartDate().toString()
                    , societyNoticeRequestVO.getStartDate() == null ? null : societyNoticeRequestVO.getStartDate().toString())) {

//               LocalDate local = societyNoticeEntity.getStartDate();
//               LocalDate date = LocalDate.parse("yyyy-mm-dd");
//                societyNoticeEntity.setStartDate(date);
                societyNoticeEntity.setStartDate(societyNoticeRequestVO.getStartDate());
                isChange = true;
            }

//            if (Functions.compareValue(societyNoticeEntity.getStartDate().toString(), societyNoticeRequestVO.getStartDate().toString())) {
////                 //   LocalDate date =LocalDate.parse("yyyy-mm-dd");
////                 //   societyNoticeEntity.setEndDate(date);
//                societyNoticeEntity.setEndDate(societyNoticeRequestVO.getEndDate());
//                isChange = true;
//            }
            // societyNoticeEntity.setStartDate(societyNoticeRequestVO.getStartDate());
            //  societyNoticeEntity.setEndDate(societyNoticeRequestVO.getEndDate());

                if (!isChange) {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                    return responseVO;
                }

                societyNoticeRepository.save(societyNoticeEntity);

                //Notification Start
                OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
                ownerMasterVO.setSocietyId(societyNoticeRequestVO.getSocietyId());
                List<String> memberToken = societyService.getMemberToken(ownerMasterVO);

                pushNotificationService.sendActionNotificationToToken(societyNoticeEntity.getNoticeTitle(), societyNoticeEntity.getNoticeContent(), NotificationAction.NOTICE, societyNoticeEntity.getId(), null, memberToken);
                //END

                responseVO.setSuccessResponse(Messages.Notice_Updated_Successfully);
                return responseVO;
            }

            return responseVO;
        }


    public ResponseVO getAllNotices(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int count=0;
        Pageable paging = Functions.getPage(societyNoticeRequestVO.getPage(), societyNoticeRequestVO.getLimit());
        noticeInputValidator.getAllNoticesInputValidator(societyNoticeRequestVO);
        societyService.isSocietyExistNotice(societyNoticeRequestVO);
        SocietyNoticeEntity societyNoticeEntity= new SocietyNoticeEntity();

        if(societyNoticeEntity.getId() != 0){

            List<SocietyNoticeVO> societyNoticeVOS =  unitNoticeMasterRepository.getByUnitId(societyNoticeRequestVO.getId());

            if (societyNoticeVOS.isEmpty()) {
                responseVO.setFailResponse(Messages.Notice_Not_Found);
            }else {
                int pages = Functions.getPagesCount(count);
                responseVO.setCount(count);
                responseVO.setPages(pages);
                responseVO.setSuccessResponse(Messages.Notice_Found);
                responseVO.setSocietyNoticeVO(societyNoticeVOS);
            }

        }else {

            List<SocietyNoticeVO> societyNoticeVOS = new ArrayList<>();

            Specification<SocietyNoticeEntity> specification = Specification.where(!Functions.nonNullString(societyNoticeRequestVO.getNoticeType()) ? null : SpecificationService.equalSpecification(societyNoticeEntity, EntityVariable.NOTICE_TYPE, societyNoticeRequestVO.getNoticeType()))
                    .and(societyNoticeRequestVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(societyNoticeEntity, EntityVariable.SOCIETY_ID, societyNoticeRequestVO.getSocietyId()))
                    .and(!Functions.nonNullString(societyNoticeRequestVO.getNoticeTitle()) ? null : SpecificationService.equalSpecification(societyNoticeEntity, EntityVariable.NOTICE_TITLE, societyNoticeRequestVO.getNoticeTitle()))
                    .and(!societyNoticeRequestVO.getInitiatedBy().equalsIgnoreCase(UserRoles.USER) ? null : SpecificationService.equalSpecification(societyNoticeEntity, EntityVariable.NOTICE_FOR, Status.SOCIETY))
                    .and(societyNoticeRequestVO.getStartDate()==null ? null : SpecificationService.equalSpecification(societyNoticeEntity, EntityVariable.START_DATE, societyNoticeRequestVO.getStartDate()))
                    .and(SpecificationService.descendingOrder(societyNoticeEntity, EntityVariable.ID));

            Page<SocietyNoticeEntity> noticeEntityList = societyNoticeRepository.findAll(specification, paging);
            count = (int) societyNoticeRepository.count(specification);

            if (!noticeEntityList.isEmpty()) {
                count = (int) societyNoticeRepository.count(specification);
//                societyNoticeVOS = modelMapperUtil.mapPage(noticeEntityList, SocietyNoticeVO.class);
                    noticeEntityList.forEach(notice -> {
                        SocietyNoticeVO societyNoticeVO = modelMapper.map(notice, SocietyNoticeVO.class);
                        List<UnitListVO> unitListVOS = new ArrayList<>();

                        if(societyNoticeRequestVO.getInitiatedBy().equalsIgnoreCase(UserRoles.SOCIETY) && notice.getNoticeFor().equalsIgnoreCase(Status.UNIT)){
                            List<Integer> unitId = unitNoticeMasterRepository.getUnitByNoticeId(notice.getId());

                            unitId.forEach(unit -> {
                                ResponseVO responseVO1 = societyService.getAreaByUnitId(unit);
                                if(responseVO1.getUnitDetail() != null){
                                    unitListVOS.add(responseVO1.getUnitDetail());
                                }
                            });
                            societyNoticeVO.setUnitList(unitListVOS);
                        }
                        societyNoticeVOS.add(societyNoticeVO);
                    });

            }

            if (societyNoticeVOS.isEmpty()) {
                responseVO.setFailResponse(Messages.Notice_Not_Found);
            }else {
                int pages = Functions.getPagesCount(count);
                responseVO.setCount(count);
                responseVO.setPages(pages);
                responseVO.setSuccessResponse(Messages.Notice_Found);
                responseVO.setSocietyNoticeVO(societyNoticeVOS);
            }
        }

        return responseVO;
    }

 //   public ResponseVO updateNotice(SocietyNoticeVO noticeRequestVO) throws PZConstraintViolationException {
  //      ResponseVO responseVO = new ResponseVO();

//        boolean isChange = false;
//        noticeInputValidator.updateNoticeInputValidator(noticeRequestVO);
//        noticeValidation.updateNoticeValidator(noticeRequestVO);
//        if (noticeRequestVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
//
//            SocietyNoticeEntity societyNoticeEntity = societyNoticeRepository.getNoiceById(noticeRequestVO.getId());
//
//
//            if (societyNoticeEntity == null) {
//
//                return responseVO;
//            }
//            if (Functions.compareValue(societyNoticeEntity.getNoticeTitle(), noticeRequestVO.getNoticeTitle())) {
//                societyNoticeEntity.setNoticeTitle(noticeRequestVO.getNoticeTitle());
//                isChange = true;
//            }
//            if (Functions.compareValue(societyNoticeEntity.getNoticeContent(), noticeRequestVO.getNoticeContent())) {
//                societyNoticeEntity.setNoticeContent(noticeRequestVO.getNoticeContent());
//                isChange = true;
//            }
//
//            if (Functions.compareValue(societyNoticeEntity.getNoticeType(), noticeRequestVO.getNoticeType())) {
//                societyNoticeEntity.setNoticeType(noticeRequestVO.getNoticeType());
//                isChange = true;
//            }
//
//
//            //societyNoticeEntity.setNoticeTitle(noticeRequestVO.getNoticeTitle());
//            // societyNoticeEntity.setNoticeContent(noticeRequestVO.getNoticeContent());
//            //  societyNoticeEntity.setNoticeType(noticeRequestVO.getNoticeType());
////            if (Functions.nonNullString(noticeRequestVO.getAttachment())) {
////                String fileName = this.generateFileName(noticeRequestVO.getSocietyId());
////                FileHandlingUtil.fileUpload(noticeRequestVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_NOTICE_IMAGES, societyNoticeEntity.getAttachment());
////                societyNoticeEntity.setAttachment(Types.NOTICE + fileName);
////                isChange = true;
////            }
//            if (societyNoticeEntity.getStartDate() != noticeRequestVO.getStartDate()) {
////                LocalDate local = noticeRequestVO.getStartDate();
////                LocalDate date = LocalDate.parse("yyyy-mm-dd");
////                societyNoticeEntity.setStartDate(date);
//                isChange = true;
//            }
//
//            if (societyNoticeEntity.getEndDate() != noticeRequestVO.getEndDate()) {
//                societyNoticeEntity.setEndDate(noticeRequestVO.getEndDate());
//                isChange = true;
//            }
////            societyNoticeEntity.setStartDate(noticeRequestVO.getStartDate());
////             societyNoticeEntity.setEndDate(noticeRequestVO.getEndDate());
//
//            if (isChange) {
//                societyNoticeRepository.save(societyNoticeEntity);
//                responseVO.setSuccessResponse("updated successfully");
//            }
//
//
//            //Notification Start
//            OwnerMasterVO ownerMasterVO = new OwnerMasterVO();
//            ownerMasterVO.setSocietyId(noticeRequestVO.getSocietyId());
//            List<String> memberToken = societyService.getMemberToken(ownerMasterVO);
//
//            pushNotificationService.sendActionNotificationToToken(societyNoticeEntity.getNoticeTitle(), societyNoticeEntity.getNoticeContent(), NotificationAction.NOTICE, societyNoticeEntity.getId(), null, memberToken);
//            //END
//
//            responseVO.setSuccessResponse(Messages.Notice_Updated_Successfully);
//            return responseVO;
//        }
//        return responseVO;
//    }

    public ResponseVO deleteNotice( SocietyNoticeVO noticeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        noticeInputValidator.deleteNoticeInputValidator(noticeRequestVO);
        SocietyNoticeEntity societyNoticeEntity = societyNoticeRepository.getNoiceById(noticeRequestVO.getId());
        if (societyNoticeEntity == null) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }
        societyNoticeRepository.deleteById(noticeRequestVO.getId());
        responseVO.setSuccessResponse(Messages.Notice_Deleted_Successfully);
        return responseVO;

    }
    public ResponseVO addNoticeType(NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        noticeInputValidator.addNoticeTypeInputValidator(noticeTypeVO);
        SocietyNoticeTypeEntity noticeTypeEntity = noticeTypeRepository.findByType(noticeTypeVO.getType());
        if(noticeTypeEntity !=null){
        if(noticeTypeEntity.getType().equals(noticeTypeVO.getType())){
            responseVO.setFailResponse(Messages.Notice_Already_Present);
            return responseVO;
        }}
        SocietyNoticeTypeEntity societyNoticeEntity= modelMapper.map(noticeTypeVO, SocietyNoticeTypeEntity.class);
        societyNoticeEntity.setType(noticeTypeVO.getType());
        societyNoticeEntity.setIsActive(true);
        noticeTypeRepository.save(societyNoticeEntity);
        responseVO.setSuccessResponse(Messages.NoticeType_Added);
        return responseVO;
    }
    public ResponseVO updateNoticeType(NoticeTypeVO noticeTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        noticeValidation.updateNoticeTypeValidator(noticeTypeRequestVO);
        SocietyNoticeTypeEntity noticeTypeEntity = noticeTypeRepository.getsocietyNoticeTypeById(noticeTypeRequestVO.getId());
        if(noticeTypeEntity.getType().equals(noticeTypeRequestVO.getType()) && noticeTypeEntity.getIsActive()==noticeTypeRequestVO.getIsActive()){
            responseVO.setFailResponse(Messages.No_Changes_Detected);
            return responseVO;
        }
        noticeTypeEntity.setType(noticeTypeRequestVO.getType());
        noticeTypeEntity.setIsActive(noticeTypeRequestVO.getIsActive());
        noticeTypeRepository.save(noticeTypeEntity);
        responseVO.setSuccessResponse(Messages.NoticeType_Updated);
        return responseVO;
    }

    public ResponseVO deleteNoticeType(NoticeTypeVO noticeTypeVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        SocietyNoticeTypeEntity noticeTypeEntity = noticeTypeRepository.getOne(noticeTypeVO.getId());
        noticeTypeRepository.deleteById(noticeTypeEntity.getId());
        responseVO.setSuccessResponse(noticeTypeEntity.getType() + Messages.NoticeType_Deleted);
        return responseVO;
    }

    public ResponseVO getNoticeType(NoticeTypeVO noticeTypeRequestVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        SocietyNoticeTypeEntity societyNoticeTypeEntity = new SocietyNoticeTypeEntity();
        List<TypeVO> typeVO;
        Pageable pageable = Functions.getPage(noticeTypeRequestVO.getPage(), noticeTypeRequestVO.getLimit());

        Specification<SocietyNoticeTypeEntity> specification = Specification
                .where(!Functions.nonNullString(noticeTypeRequestVO.getType()) ? null : SpecificationService.likeSpecification(societyNoticeTypeEntity, EntityVariable.TYPE,noticeTypeRequestVO.getType()))
                .and(Functions.nonNullString(noticeTypeRequestVO.getAction()) && noticeTypeRequestVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(societyNoticeTypeEntity, EntityVariable.IS_ACTIVE,noticeTypeRequestVO.getIsActive()) : null);

        Page<SocietyNoticeTypeEntity> societyNoticeTypeEntityList = noticeTypeRepository.findAll(specification, pageable);

        if(societyNoticeTypeEntityList.isEmpty()){
            responseVO.setFailResponse(Messages.No_Notice_Type_Found);
        }else {
            int count = (int) noticeTypeRepository.count(specification);

            List<NoticeTypeVO> noticeTypeVOList = modelMapperUtil.mapPage(societyNoticeTypeEntityList, NoticeTypeVO.class);
            responseVO.setCount(count);
            responseVO.setActiveNoticeType(noticeTypeVOList);
            responseVO.setSuccessResponse(Messages.NoticeType_List);
        }

//        Pageable searchPageable = Functions.getPage(0, noticeTypeRequestVO.getLimit());
//
//            if(noticeTypeRequestVO.getType() != null){
//                typeVO = noticeTypeRepository.getSearchNoticeType(noticeTypeRequestVO.getType(), true, searchPageable);
//            }else {
//                typeVO = noticeTypeRepository.getSearchNoticeType(noticeTypeRequestVO.getType(), true, searchPageable);
//            }
//
//            List<SocietyNoticeTypeEntity> activeNoticeTypeEntity = noticeTypeRepository.findByIsActive(true, paging);
//            List<SocietyNoticeTypeEntity> inActiveNoticeTypeEntity = noticeTypeRepository.findByIsActive(false, paging);
//            int activeCount = noticeTypeRepository.countByIsActive(true);
//            int inActiveCount = noticeTypeRepository.countByIsActive(false);
//            int activePages = Functions.getPagesCount(activeCount);
//            int inActivePages = Functions.getPagesCount(inActiveCount);
//            if (activeNoticeTypeEntity.isEmpty() && inActiveNoticeTypeEntity.isEmpty()) {
//                responseVO.setFailResponse(Messages.No_Notice_Type_Found);
//            } else {
//
//                List<NoticeTypeVO> activeNoticeType = modelMapperUtil.mapList(activeNoticeTypeEntity, NoticeTypeVO.class);
//                List<NoticeTypeVO> inActiveNoticeType = modelMapperUtil.mapList(inActiveNoticeTypeEntity, NoticeTypeVO.class);
//
//                if(!typeVO.isEmpty()){
//                    responseVO.setActiveType(typeVO);
//                }
//
//                responseVO.setActiveNoticeType(activeNoticeType);
//                responseVO.setInActiveNoticeType(inActiveNoticeType);
//                responseVO.setPages(activePages);
//                responseVO.setCount(activeCount);
//                responseVO.setInActiveCount(inActiveCount);
//                responseVO.setInActivePages(inActivePages);
//                responseVO.setSuccessResponse(Messages.NoticeType_List);
//            }
        return responseVO;
    }

    private String generateFileName(int societyId) {

        Random random = new Random();

        String newFileName = ConstantVariables.NOTICE + "_" + random.nextInt(999999);
        SocietyNoticeEntity Entity = societyNoticeRepository.findByAttachment(newFileName);

        if (Entity != null) {
            generateFileName(societyId);
        }

        return newFileName;
    }
}
