package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.noticeEntity.SocietyNoticeEntity;
import com.pz.mysociety.entity.noticeEntity.SocietyNoticeTypeEntity;
import com.pz.mysociety.model.Request.NoticeTypeVO;
import com.pz.mysociety.model.Request.SocietyNoticeVO;
import com.pz.mysociety.repository.noticeRepository.NoticeTypeRepository;
import com.pz.mysociety.repository.noticeRepository.SocietyNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SocietyNoticeValidationService {
    @Autowired(required = false)
    private SocietyNoticeRepository societyNoticeRepository;
    @Autowired(required = false)
    private NoticeTypeRepository noticeTypeRepository;
    @Autowired
    private  SocietyService societyService;

    public void addNoticeValidator(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {

        SocietyNoticeEntity societyNoticeEntity=societyNoticeRepository.getNoiceById(societyNoticeRequestVO.getId());
        if(societyNoticeEntity == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeValidationService","addNoticeInputValidaotr", "ID", "SocietyNoticeValidationService", ValidationMessages.Data_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
//    public void updateNoticeValidator(SocietyNoticeVO societyNoticeRequestVO) throws PZConstraintViolationException {
//
//        SocietyNoticeEntity societyNoticeEntity=societyNoticeRepository.findByIdAndSocietyId(societyNoticeRequestVO.getId(), societyNoticeRequestVO.getSocietyId());
//        if(societyNoticeEntity == null){
//            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeValidationService","updateNoticeInputValidaotr", "ID", "SocietyNoticeValidationService", ValidationMessages.Data_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
//            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
//            pzConstraints.add(pzConstraint);
//
//            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
//        }
//    }
    public void updateNoticeTypeValidator(NoticeTypeVO societyNoticeTypeRequestVO) throws PZConstraintViolationException {
        SocietyNoticeTypeEntity societyNoticeTypeEntity=noticeTypeRepository.getsocietyNoticeTypeById(societyNoticeTypeRequestVO.getId());
        if(societyNoticeTypeEntity == null){
            PZConstraint pzConstraint = new PZConstraint("SocietyNoticeValidationService","updateNoticeInputValidaotr", "ID", "SocietyNoticeValidationService", ValidationMessages.Data_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
}
