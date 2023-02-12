package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.mail.MailPlaceHolder;
import com.pz.mysociety.common.mail.SMSService;
import com.pz.mysociety.common.mail.SMSTemplateName;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.guardEntity.GuardDocumentMasterEntity;
import com.pz.mysociety.entity.guardEntity.GuardEntity;
import com.pz.mysociety.entity.guardEntity.GuardHistoryEntity;
import com.pz.mysociety.entity.helperEntity.HelperDocumentTypeEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.repository.guardRepository.GuardDocumentMasterRepository;
import com.pz.mysociety.repository.guardRepository.GuardHistoryRepository;
import com.pz.mysociety.repository.guardRepository.GuardRepository;
import com.pz.mysociety.validation.GuardInputValidation;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
@Transactional
public class GuardService {

    @Autowired(required = false)
    private GuardRepository guardRepository;

    @Autowired
    private GuardHistoryRepository guardHistoryRepository;

    @Autowired
    private GuardInputValidation guardInputValidation;

    @Autowired
    private GuardValidationService guardValidationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private GuardDocumentMasterRepository guardDocumentMasterRepository;

    @Autowired
    SMSService smsService;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    public ResponseVO addGuard(GuardVO guardVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        societyService.isSocietyValid(guardVO);

        guardInputValidation.addGuardInputValidation(guardVO);

        GuardEntity guardEntity = guardRepository.findBySocietyIdAndMobileNumber(guardVO.getSocietyId(), guardVO.getMobileNumber());

            if (guardVO.getAction().equalsIgnoreCase(Status.ADD)) {

                if(guardVO.getGuardDocumentList().size()>4||guardVO.getGuardDocumentList().size()==0){

                    responseVO.setFailResponse(Messages.Document_max_error);
                    return responseVO;
                }
               // if (guardVO.getGuardDocumentList().size() <=4) {

                    if (guardEntity == null) {
                int pin = generatePin(guardVO.getSocietyId());
                GuardEntity guard = modelMapper.map(guardVO, GuardEntity.class);
                guard.setGuardPin(pin);
                guard.setIsActive(true);

                    if (Functions.nonNullString(guardVO.getPhoto())) {
                        String fileName = guardVO.getMobileNumber()+ '_' +guardVO.getGuardPin()+".png";
                        FileHandlingUtil.fileUpload(guardVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_GUARD, null);
                        guard.setPhoto(Types.GUARD_IMAGES + fileName);
                    }
                    GuardEntity guardEntity1 = guardRepository.save(guard);

                    if (guardVO.getGuardDocumentList() != null) {
                        // if (guardVO.getDocument().length() > 0 || guardVO.getDocument().length() < 5) {
                        for (GuardDocumentMasterVO guardVOs : guardVO.getGuardDocumentList()) {
                            GuardDocumentMasterEntity guardDocumentMasterEntity = modelMapper.map(guardVOs, GuardDocumentMasterEntity.class);
                            String fileName = guardEntity1.getId() + "_" + guardDocumentMasterEntity.getDocumentNumber() + ".png";
                            FileHandlingUtil.fileUpload(guardDocumentMasterEntity.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_GUARD, null);
                            guardDocumentMasterEntity.setDocument(Types.GUARD_IMAGES + fileName);
                            guardDocumentMasterEntity.setGuardId(guardEntity1.getId());

                            guardDocumentMasterRepository.save(guardDocumentMasterEntity);
                        }
                    }
                    responseVO.setGuardPin(guardEntity1.getGuardPin());
                    responseVO.setSuccessResponse(Messages.Guard_Added_Success);
                } else {
                    responseVO.setFailResponse(Messages.Guard_Exist_Error);
                }
        //   }
        }

        else if (guardVO.getAction().equalsIgnoreCase(Status.UPDATE)) {
            // ResponseVO responseVO = new ResponseVO();
            guardEntity = guardRepository.getOne(guardVO.getId());
            boolean isChange = false;

            if (Functions.compareValue(guardEntity.getMobileNumber(), guardVO.getMobileNumber())) {
                GuardEntity guardEntity1 = guardRepository.findBySocietyIdAndMobileNumber(guardEntity.getSocietyId(), guardVO.getMobileNumber());

                if (guardEntity1 != null) responseVO.setFailResponse(Messages.Already_Exist);

                guardEntity.setMobileNumber(guardVO.getMobileNumber());
                isChange = true;

            }
            if (Functions.compareValue(guardEntity.getName(), (guardVO.getName()))) {
                guardEntity.setName(guardVO.getName());
                isChange = true;
            }


            if (guardEntity.getIsActive() != guardVO.getIsActive()) {
                guardEntity.setIsActive(guardVO.getIsActive());
                isChange = true;
            }
            if (Functions.nonNullString(guardVO.getPhoto())) {
                String fileName = guardVO.getMobileNumber() + "_" + Functions.getRandomValue() + ".png";
                FileHandlingUtil.fileUpload(guardVO.getPhoto(), fileName, DocumentPath.UPLOAD_DIR_PATH_HELPER, guardEntity.getPhoto());
                guardVO.setPhoto(Types.HELPER_IMAGES + fileName);
                isChange = true;
            }

            if (isChange) {
                guardRepository.save(guardEntity);
                responseVO.setSuccessResponse(Messages.Guard_Update_Success);
            } else {
                responseVO.setFailResponse(Messages.No_Changes_Found);
                return responseVO;
            }
            // responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;
    }


    private int generatePin(int societyId) {

        Random random = new Random();
        int pin = 100000 + random.nextInt(899999);

        GuardEntity guardEntity = guardRepository.findBySocietyIdAndGuardPin(societyId, pin);

        if (guardEntity != null) {
            generatePin(societyId);
        }
        return pin;
    }

    public ResponseVO guardLogin(GuardVO guardVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        guardInputValidation.guardLoginInputValidation(guardVO);

        if (guardVO.getGuardPin() == 0) {

            GuardEntity guardEntity = guardRepository.findBySocietyIdAndMobileNumber(guardVO.getSocietyId(), guardVO.getMobileNumber());

            if (guardEntity != null) {

                if (!guardValidationService.isLoginValid(guardEntity)) {
                    responseVO.setFailResponse(Messages.Login_Attempt_Duration);
                    return responseVO;
                }

                int otp = 100000 + random.nextInt(899999);

                guardEntity.setValidateOtp(otp);
                guardEntity.setOtpRequestedTime(new Date());
                guardEntity.setOtpAttempt(5);
                guardRepository.save(guardEntity);

                //Send Otp on Mobile Number

//                Map<String,Object> model = smsService.getDefaultModel();
//                model.put(MailPlaceHolder.OTP.name(),otp);
//
//                String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);
//
//                smsService.sendSMS(guardEntity.getMobileNumber(),body);

                GuardVO guard = new GuardVO();
                guard.setId(guardEntity.getId());
                responseVO.setGuard(guard);
                responseVO.setSuccessResponse(Messages.Otp_Send_Attempt);
            } else {
                responseVO.setFailResponse(Messages.Fail_Attempt);
            }
        } else {

            GuardEntity guardEntity = guardRepository.getGuardDetail(guardVO.getSocietyId(), guardVO.getGuardPin());

            if (guardEntity != null) {
                SocietyVO societyVO = new SocietyVO();
                GuardHistoryEntity guardHistoryEntity = new GuardHistoryEntity();
                GuardHistoryVO guardHistoryVO = new GuardHistoryVO();
                societyVO.setId(guardEntity.getSocietyId());
                ResponseVO response = societyService.getSociety(societyVO);

                GuardVO guard = modelMapper.map(guardEntity, GuardVO.class);

                guardHistoryEntity.setGuardId(guardEntity.getId());
                guardHistoryEntity.setSocietyId(guardEntity.getSocietyId());
                String inTime = Functions.getDate();
                guardHistoryEntity.setInTime(inTime);

                GuardHistoryEntity guardHistory = guardHistoryRepository.save(guardHistoryEntity);
                guardHistoryVO.setId(guardHistory.getId());

                responseVO.setSociety(response.getSociety());
                responseVO.setGuard(guard);
                response.setGuardHistory(guardHistoryVO);
                response.setSuccessResponse(Messages.Login_Successfully);
            } else {
                responseVO.setFailResponse(Messages.Fail_Attempt);
            }
        }

        return responseVO;
    }

    public ResponseVO resendOtp(GuardVO guardVO) {
        ResponseVO responseVO = new ResponseVO();
        Random random = new Random();
        guardInputValidation.guardIdInputValidation(guardVO);
        GuardEntity guardEntity = guardRepository.getOne(guardVO.getId());

        if (guardEntity.getOtpAttempt() == 0) {
            guardEntity.setFailAttempt(new Date());
            guardEntity.setValidateOtp(0);
            guardEntity.setOtpRequestedTime(null);
            guardRepository.save(guardEntity);
            responseVO.setFailResponse(Messages.Otp_Login_Attempt);
            return responseVO;
        }

        int otp = 100000 + random.nextInt(899999);

        guardEntity.setOtpAttempt(guardEntity.getOtpAttempt() - 1);
        guardEntity.setValidateOtp(otp);
        guardEntity.setOtpRequestedTime(new Date());
        guardRepository.save(guardEntity);

//        Send OTP on Mobile

//        Map<String,Object> model = smsService.getDefaultModel();
//        model.put(MailPlaceHolder.OTP.name(),otp);
//
//        String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);
//
//        smsService.sendSMS(guardEntity.getMobileNumber(),body);

        responseVO.setSuccessResponse(Messages.Otp_Send_Attempt);
        return responseVO;

    }

    public ResponseVO verifyOtp(GuardVO guardVO) {
        ResponseVO responseVO = new ResponseVO();
        guardInputValidation.verifyInputValidation(guardVO);

        GuardEntity guardEntity = guardRepository.getOne(guardVO.getId());

        if (!guardValidationService.isOtpValid(guardEntity)) {
            responseVO.setFailResponse(Messages.Otp_Expired);
            return responseVO;
        }

        if (guardEntity.getValidateOtp() == guardVO.getValidateOtp()) {
            SocietyVO societyVO = new SocietyVO();
            GuardHistoryEntity guardHistoryEntity = new GuardHistoryEntity();
            GuardHistoryVO guardHistoryVO = new GuardHistoryVO();
            GuardEntity guardEntity1 = guardRepository.getGuardDetail(guardEntity.getSocietyId(), guardEntity.getGuardPin());

            societyVO.setId(guardEntity.getSocietyId());
            ResponseVO response = societyService.getSociety(societyVO);

            GuardVO guard = modelMapper.map(guardEntity1, GuardVO.class);

            guardEntity.setOtpRequestedTime(null);
            guardEntity.setValidateOtp(0);
            guardEntity.setOtpAttempt(0);
            guardRepository.save(guardEntity);

            guardHistoryEntity.setGuardId(guardEntity.getId());
            guardHistoryEntity.setSocietyId(guardEntity.getSocietyId());
            String inTime = Functions.getDate();
            guardHistoryEntity.setInTime(inTime);

            GuardHistoryEntity guardHistory = guardHistoryRepository.save(guardHistoryEntity);
            guardHistoryVO.setId(guardHistory.getId());

            responseVO.setSociety(response.getSociety());
            responseVO.setGuard(guard);
            response.setGuardHistory(guardHistoryVO);
            responseVO.setSuccessResponse(Messages.Otp_Verified_Successfully);
        } else {
            responseVO.setFailResponse(Messages.Otp_Invalid);
        }

        return responseVO;
    }

    public ResponseVO guardLogout(GuardVO guardVO) {
        ResponseVO responseVO = new ResponseVO();
        guardInputValidation.guardIdInputValidation(guardVO);

        GuardHistoryEntity guardHistoryEntity = guardHistoryRepository.getOne(guardVO.getId());

        String outTime = Functions.getDate();
        guardHistoryEntity.setOutTime(outTime);
        guardHistoryRepository.save(guardHistoryEntity);

        responseVO.setSuccessResponse(Messages.Logout_Successfully);
        return responseVO;
    }

    public ResponseVO getGuardList(GuardVO guardVO) throws PZConstraintViolationException {
        ResponseVO responseVO = new ResponseVO();
        int count = 0;
        Pageable paging = Functions.getPage(guardVO.getPage(), guardVO.getLimit());
        guardInputValidation.getGuardListInputValidation(guardVO);
        GuardEntity guardEntity = new GuardEntity();

        Specification<GuardEntity> specification = Specification.where(!Functions.nonNullString(guardVO.getName()) ? null : SpecificationService.equalSpecification(guardEntity, EntityVariable.NAME, guardVO.getName()))
                .and(guardVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(guardEntity, EntityVariable.SOCIETY_ID, guardVO.getSocietyId()))
                .and(!Functions.nonNullString(guardVO.getMobileNumber()) ? null : SpecificationService.likeSpecification(guardEntity, EntityVariable.MOBILE_NUMBER, guardVO.getMobileNumber()))
                .and(!Functions.nonNullString(guardVO.getEmail()) ? null : SpecificationService.likeSpecification(guardEntity, EntityVariable.EMAIL, guardVO.getEmail()))
                .and(guardVO.getGuardPin() == 0 ? null : SpecificationService.likeSpecification(guardEntity, EntityVariable.GUARD_PIN, guardVO.getGuardPin()))
                .and(Functions.nonNullString(guardVO.getAction()) && guardVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(guardEntity, EntityVariable.IS_ACTIVE, guardVO.getIsActive()) : null);


        Page<GuardEntity> guardList = guardRepository.findAll(specification, paging);
        count = (int) guardRepository.count(specification);

        if (guardList.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {

            List<GuardVO> activeGuardList = modelMapperUtil.mapPage(guardList, GuardVO.class);
            responseVO.setActiveGuardList(activeGuardList);
            responseVO.setCount(count);
            int pages = Functions.getPagesCount(count);
            responseVO.setPages(pages);
            responseVO.setSuccessResponse(Messages.List_Of_Guard);
        }
        return responseVO;
    }


    // GuardEntity guardEntity1 = guardRepository.findBySocietyIdAndMobileNumber(guardEntity.getSocietyId(), guardVO.getMobileNumber());

//            if (guardEntity1 != null && guardEntity1.getId() != guardVO.getId()) {
//                responseVO.setFailResponse(Messages.Already_Exist);
//            } else {
//
//                if (Functions.compareValue(guardEntity.getName(), (guardVO.getName()))) {
//                    guardEntity.setName(guardVO.getName());
//                    isChange = true;
//                }


    public ResponseVO getGuardAttendance(GuardHistoryVO guardHistoryVO) {
        ResponseVO responseVO = new ResponseVO();
        guardInputValidation.guardAttendanceInputValidation(guardHistoryVO);

        String fromDate = guardHistoryVO.getYear() + "-" + guardHistoryVO.getMonth() + "-01 00:00:00";
        String toDate = guardHistoryVO.getYear() + "-" + guardHistoryVO.getMonth() + "-31 23:59:59";

        List<GuardHistoryEntity> guardHistoryEntities = guardHistoryRepository.findByGuardIdAndInTimeGreaterThanAndInTimeLessThan(guardHistoryVO.getGuardId(), fromDate, toDate);

        if (!guardHistoryEntities.isEmpty()) {
            List<GuardHistoryVO> guardHistory = modelMapperUtil.mapList(guardHistoryEntities, GuardHistoryVO.class);
            responseVO.setGuardAttendance(guardHistory);
            responseVO.setSuccessResponse(Messages.Guest_Attendance_Success);
        } else {
            responseVO.setFailResponse(Messages.Not_Found);
        }

        return responseVO;
    }
}

//    public ResponseVO addGuardDocument(GuardDocumentMasterVO guardDocumentMasterVO) {
//        ResponseVO responseVO = new ResponseVO();
//        guardInputValidation.guardDocumentInputValidation(guardDocumentMasterVO);
//        GuardDocumentMasterEntity guardDocumentMasterEntity = guardDocumentMasterRepository.findBySocietyIdAndGuardIdAndDocumentTypeId(guardDocumentMasterVO.getSocietyId(), guardDocumentMasterVO.getGuardId(), guardDocumentMasterVO.getDocumentTypeId());
//        if (guardDocumentMasterEntity == null) {
//            if (guardDocumentMasterVO.getDocument().length() > 0 || guardDocumentMasterVO.getDocument().length() < 5) {
//                GuardDocumentMasterEntity guardDocumentMasterEntity1 = modelMapper.map(guardDocumentMasterVO, GuardDocumentMasterEntity.class);
//
//                String fileName = guardDocumentMasterVO.getDocumentTypeId() + "_" + guardDocumentMasterVO.getGuardId() + ".png";
//                FileHandlingUtil.fileUpload(guardDocumentMasterVO.getDocument(), fileName, DocumentPath.UPLOAD_DIR_PATH_GUARD, null);
//                guardDocumentMasterEntity1.setDocument(Types.GUARD_IMAGES + fileName);
//
//                guardDocumentMasterRepository.save(guardDocumentMasterEntity);
//                responseVO.setSuccessResponse(Messages.Document_Add_Success);
//
//            } else {
//
//                responseVO.setFailResponse(Messages.Already_Exist);
//            }
//            return responseVO;
//        }
//        return responseVO;
//    }
//}
//
//public ResponseVO addGuardDocument(GuardDocumentMasterVO guardDocumentMasterVO) {
//    ResponseVO responseVO = new ResponseVO();
//    guardInputValidation.guardDocumentInputValidation(guardDocumentMasterVO);
//    GuardDocumentMasterEntity guardDocumentMasterEntity = guardDocumentMasterRepository.getOne(guardDocumentMasterVO.getId());
//    if (guardDocumentMasterVO.getAction().equalsIgnoreCase(Status.ADD)) {
//        if (guardDocumentMasterVO.getDocument().length() > 0 || guardDocumentMasterVO.getDocument().length() < 5) {
//            if(guardDocumentMasterEntity==null){
//
//                GuardDocumentMasterEntity guardDocumentMasterEntity1 = modelMapper.map(guardDocumentMasterVO,GuardDocumentMasterEntity.class);
//            }
//
//
//        }
//
//    }
//
//}
//}
//
