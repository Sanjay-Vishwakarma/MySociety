package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.complainEntity.ComplainChatRecordEntity;
import com.pz.mysociety.entity.complainEntity.ComplainMasterEntity;
import com.pz.mysociety.entity.householdEntity.CompanyMasterEntity;
import com.pz.mysociety.model.Request.CompanyMasterVO;
import com.pz.mysociety.model.Request.ComplainVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.ComplainListVO;
import com.pz.mysociety.repository.complainRepository.ComplainChatRecordRepository;

import com.pz.mysociety.repository.complainRepository.ComplainMasterRepository;
import com.pz.mysociety.validation.ComplainInputValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class ComplainService {

    @Autowired
    private ComplainInputValidator complainInputValidator;

    @Autowired
    private ComplainValidationService complainValidationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComplainMasterRepository complainMasterRepository;

    @Autowired
    private ComplainChatRecordRepository complainChatRepository;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private PushNotificationService pushNotificationService;


    public ResponseVO addComplain(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        ComplainChatRecordEntity complainChatRecordEntity = new ComplainChatRecordEntity();

        complainInputValidator.addComplainInputValidator(complainVO);
        complainValidationService.isUserMappingExist(complainVO);
        ComplainMasterEntity complainMasterEntity = modelMapper.map(complainVO, ComplainMasterEntity.class);
        complainMasterEntity.setComplainStatus(ComplainStatus.PENDING);
        complainMasterEntity.setIsRead(true);
        String fileName = ConstantVariables.COMPLAIN + "_" + Functions.getRandomValue() +".png";
        if(Functions.nonNullString(complainVO.getAttachment())){
            FileHandlingUtil.fileUpload(complainVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_COMPLAIN_IMAGES, null);
            complainMasterEntity.setAttachment(Types.COMPLAIN_IMAGES + fileName);
            complainChatRecordEntity.setAttachment(Types.COMPLAIN_IMAGES + fileName);
        }
        ComplainMasterEntity complainMasterEntity1 = complainMasterRepository.save(complainMasterEntity);

        complainChatRecordEntity.setComplainId(complainMasterEntity1.getId());
        complainChatRecordEntity.setRole(UserRoles.USER);
        complainChatRecordEntity.setUserId(complainVO.getUserId());
        complainChatRecordEntity.setContent(complainVO.getComplainContent());
        complainChatRepository.save(complainChatRecordEntity);
        householdService.activityLog(complainVO.getUnitId(), complainVO.getUserId(),Messages.Message_Complain_Added, complainVO.getComplainTitle()+Messages.Message_Complain_Added);
        responseVO.setSuccessResponse(Messages.Complain_Added_Successfully);
        return responseVO;
    }

    public ResponseVO changeComplainStatus(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        complainInputValidator.updateComplainInputValidator(complainVO);
        complainValidationService.isComplainIdExist(complainVO);
        ComplainMasterEntity complainMasterEntity = complainMasterRepository.getOne(complainVO.getComplainId());
        if (complainMasterEntity.getComplainStatus().equals(ComplainStatus.PENDING)) {
            if (complainVO.getComplainStatus().equals(ComplainStatus.RESOLVED) || complainVO.getComplainStatus().equals(ComplainStatus.REJECTED)) {
                complainMasterEntity.setComplainStatus(complainVO.getComplainStatus());
                complainMasterRepository.save(complainMasterEntity);
                householdService.activityLog(complainMasterEntity.getUnitId(), complainMasterEntity.getUserId(),Messages.Message_Complain_Status_Upload, complainMasterEntity.getComplainTitle()+Messages.Message_Complain_Status_Upload);

                //Complain Chat Notification to User

                List<String> tokenList = societyService.getUserTokens(complainMasterEntity.getSocietyId(), complainMasterEntity.getUnitId());

                if (tokenList.size() > 0 && Functions.nonNullString(complainMasterEntity.getComplainContent()))
                    pushNotificationService.sendMultipleNotificationToToken(complainMasterEntity.getComplainTitle(), complainVO.getComplainStatus(), NotificationAction.COMPLAIN, tokenList);
                //END

                responseVO.setSuccessResponse(Messages.Complain_Updated_Successfully);
            }
        }else {
            responseVO.setFailResponse(Messages.Application_Not_Pending);
        }
        return responseVO;
    }

    public ResponseVO addComplainRecord(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        complainInputValidator.addComplainRecordInputValidator(complainVO);
        if(complainVO.getUserId()!=0){
        complainValidationService.isComplainIdAndUserIdExist(complainVO);}
        if(complainVO.getAdminId()!=0){
        complainValidationService.isUserMappingExist(complainVO.getAdminId());}
        if (complainVO.getRole().equals(UserRoles.SOCIETY)) {
           ComplainMasterEntity complainMasterEntity = complainMasterRepository.getComplainById(complainVO.getComplainId());
            complainMasterEntity.setIsRead(false);
            complainMasterRepository.save(complainMasterEntity);
        }
        ComplainChatRecordEntity complainChatRecordEntity = modelMapper.map(complainVO, ComplainChatRecordEntity.class);
        if(Functions.nonNullString(complainVO.getAttachment())){
            String fileName = ConstantVariables.COMPLAIN_CHAT_RECORD + "_" + Functions.getRandomValue()+".png";
            FileHandlingUtil.fileUpload(complainVO.getAttachment(), fileName, DocumentPath.UPLOAD_DIR_COMPLAIN_CHAT_IMAGES, null);
            complainChatRecordEntity.setAttachment(Types.COMPLAIN_CHAT_IMAGES + fileName);
        }
        complainChatRepository.save(complainChatRecordEntity);

        //Complain Chat Notification to User
        ComplainMasterEntity complainMasterEntity = complainMasterRepository.getOne(complainVO.getComplainId());

        List<String> tokenList = societyService.getUserTokens(complainMasterEntity.getSocietyId(), complainMasterEntity.getUnitId());

        if (tokenList.size() > 0 && Functions.nonNullString(complainVO.getContent()))
            pushNotificationService.sendMultipleNotificationToToken(complainMasterEntity.getComplainTitle(), complainVO.getContent(), NotificationAction.COMPLAIN_CHAT, tokenList);
        //END

        responseVO.setComplaiId(complainVO.getComplainId());
        responseVO.setSuccessResponse(Messages.SentSuccessFully);
        return responseVO;
    }

    public ResponseVO changeFlag(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        complainInputValidator.complainFlagChangeInputValidator(complainVO);
        complainValidationService.isComplainIdExist(complainVO);
        if(complainVO.getRole().equals(UserRoles.USER)){
            complainMasterRepository.changeComplainFlag(true, complainVO.getComplainId());
            complainChatRepository.changeAdminFlag(true, complainVO.getComplainId(), UserRoles.SOCIETY);
            responseVO.setSuccessResponse(Messages.Message_Read);
            return responseVO;
        }else if(complainVO.getRole().equals(UserRoles.SOCIETY)){
            complainChatRepository.changeAdminFlag(true, complainVO.getComplainId(), UserRoles.USER);
            responseVO.setSuccessResponse(Messages.Message_Read);
            return responseVO;
        }
        responseVO.setFailResponse(Messages.Send_Proper_Role);
        return responseVO;
    }
    public ResponseVO getComplainRecord(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        if(complainVO.getComplainId()!= 0) {
            complainValidationService.isComplainIdExist(complainVO);
        }
        if(complainVO.getComplainId() == 0){
            List<ComplainChatRecordEntity> chatRecordEntities = complainChatRepository.findAll();
            List<ComplainVO> complainVO1 = modelMapperUtil.mapList(chatRecordEntities, ComplainVO.class);
            responseVO.setComplainRecord(complainVO1);
            responseVO.setSuccessResponse(Messages.ComplainHistory);
            return responseVO;
        }
        List<ComplainChatRecordEntity> chatRecordEntities = complainChatRepository.findByComplainId(complainVO.getComplainId());
        List<ComplainVO> complainVO1 = modelMapperUtil.mapList(chatRecordEntities, ComplainVO.class);
        if(chatRecordEntities == null){
            responseVO.setFailResponse(Messages.ComplainNotFound);
            return responseVO;
        }
        responseVO.setComplainRecord(complainVO1);
        responseVO.setSuccessResponse(Messages.Complain_List);
        return responseVO;

    }
       public ResponseVO getSocietyComplain(ComplainVO complainVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        complainInputValidator.getSocietyComplainInputValidator(complainVO);
        int count=0;
        Pageable paging = Functions.getPage(complainVO.getPage(), complainVO.getLimit());
        ComplainMasterEntity complainMasterEntity = new ComplainMasterEntity();

        Specification<ComplainMasterEntity> specification = Specification.where(!Functions.nonNullString(complainVO.getComplainStatus()) ? null : SpecificationService.likeSpecification(complainMasterEntity, EntityVariable.COMPLAIN_STATUS, complainVO.getComplainStatus()))
                .and(complainVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(complainMasterEntity, EntityVariable.SOCIETY_ID, complainVO.getSocietyId()))
                .and(!Functions.nonNullString(complainVO.getComplainTitle()) ? null : SpecificationService.equalSpecification(complainMasterEntity, EntityVariable.COMPLAIN_TITLE, complainVO.getComplainTitle()))
                .and(complainVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(complainMasterEntity, EntityVariable.UNIT_ID, complainVO.getUnitId()))
                .and(complainVO.getComplainDate() == null ? null : SpecificationService.equalSpecification(complainMasterEntity, EntityVariable.COMPLAIN_DATE, complainVO.getComplainDate()))
                .and(complainVO.getUserId() == 0 ? null : SpecificationService.equalSpecification(complainMasterEntity, EntityVariable.USER_ID, complainVO.getUserId()))
                .and(SpecificationService.descendingOrder(complainMasterEntity, EntityVariable.ID));

        Page<ComplainMasterEntity> complainMasterEntities = complainMasterRepository.findAll( specification, paging);
        count = (int) complainMasterRepository.count(specification);

        if(complainMasterEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }
        List<ComplainListVO> complainListVOs = modelMapperUtil.mapPage(complainMasterEntities, ComplainListVO.class);

        for (ComplainListVO complainListVO : complainListVOs) {
                ResponseVO responseVO1 = societyService.getSocietyDetail(complainListVO);
            }
            int pages = Functions.getPagesCount(count);
            responseVO.setCount(count);
            responseVO.setPages(pages);
            responseVO.setSuccessResponse(Messages.Complain_List);
            responseVO.setComplainDetails(complainListVOs);
            return responseVO;

    }

    public ResponseVO societyComplainDashBoard(ComplainVO complainVO) {

        ResponseVO responseVO = new ResponseVO();
        int loopCount = 7;

        List<ComplainVO> complainListVOs = new ArrayList<>();
        List<ComplainVO> complainListVOs1 = new ArrayList<>();
        List<ComplainVO> complainListVOs2 = new ArrayList<>();

        LocalDate fromDate = complainVO.getFromDate();
        LocalDate fromDate1 = complainVO.getFromDate();

        for (int i = 0; i < 12; i++) {

            if (i < loopCount) {
                ComplainVO complainVO1 = new ComplainVO();
                LocalDate fromCurrentDate = fromDate1 ;
                LocalDate toLastDate = fromDate1.plus(-1, ChronoUnit.WEEKS);

                int resolvedCount = complainMasterRepository.countBySocietyIdAndComplainStatusAndComplainDateBetween(complainVO.getSocietyId(), ComplainStatus.REJECTED, toLastDate, fromCurrentDate);
                int pendingCount = complainMasterRepository.countBySocietyIdAndComplainStatusAndComplainDateBetween(complainVO.getSocietyId(), ComplainStatus.PENDING, toLastDate, fromCurrentDate);
                int rejectedCount = complainMasterRepository.countBySocietyIdAndComplainStatusAndComplainDateBetween(complainVO.getSocietyId(), ComplainStatus.REJECTED, toLastDate, fromCurrentDate);
                complainVO1.setFromDate(fromDate1);
                complainVO1.setToDate(toLastDate);
                complainVO1.setResolvedCount(resolvedCount);
                complainVO1.setRejectedCount(rejectedCount);
                complainVO1.setPendingCount(pendingCount);
                complainListVOs.add(complainVO1);

                fromDate1 = toLastDate;
            }
            if (i < loopCount) {
                ComplainVO complainVO1 = new ComplainVO();
                LocalDate result = fromDate.plus(-i, ChronoUnit.DAYS);
                int resolvedCount = complainMasterRepository.countBySocietyIdAndStatus(complainVO.getSocietyId(),  ComplainStatus.RESOLVED, result);
                int pendingCount = complainMasterRepository.countBySocietyIdAndStatus(complainVO.getSocietyId(),  ComplainStatus.PENDING, result);
                int rejectedCount = complainMasterRepository.countBySocietyIdAndStatus(complainVO.getSocietyId(),  ComplainStatus.REJECTED, result);
                complainVO1.setFromDate(result);
                complainVO1.setResolvedCount(resolvedCount);
                complainVO1.setRejectedCount(rejectedCount);
                complainVO1.setPendingCount(pendingCount);
                complainListVOs1.add(complainVO1);
            }

            ComplainVO complainVO1 = new ComplainVO();
            LocalDate toLastDate = fromDate.plus(-i, ChronoUnit.MONTHS);

            int resolvedCount = complainMasterRepository.countBasedOnMonth(complainVO.getSocietyId(), ComplainStatus.RESOLVED, toLastDate.getMonthValue(), toLastDate.getYear());
            int pendingCount = complainMasterRepository.countBasedOnMonth(complainVO.getSocietyId(), ComplainStatus.PENDING, toLastDate.getMonthValue(), toLastDate.getYear());
            int rejectedCount = complainMasterRepository.countBasedOnMonth(complainVO.getSocietyId(), ComplainStatus.REJECTED, toLastDate.getMonthValue(), toLastDate.getYear());
            complainVO1.setMonth(toLastDate.getMonth());
            complainVO1.setYear(toLastDate.getYear());
            complainVO1.setResolvedCount(resolvedCount);
            complainVO1.setRejectedCount(rejectedCount);
            complainVO1.setPendingCount(pendingCount);
            complainListVOs2.add(complainVO1);

        }

        if (complainListVOs.size() == 0 && complainListVOs1.size() == 0 && complainListVOs2.size() == 0) {
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        } else {
            responseVO.setComplainMonthlyCount(complainListVOs);
            responseVO.setComplainWeeklyCount(complainListVOs1);
            responseVO.setComplainYearlyCount(complainListVOs2);

        }

        return responseVO;
    }
}
