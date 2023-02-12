package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.helperEntity.HelperEntity;
import com.pz.mysociety.entity.helperEntity.HelperTypeEntity;
import com.pz.mysociety.model.Request.*;
import com.pz.mysociety.repository.helperRepository.HelperRepository;
import com.pz.mysociety.repository.helperRepository.HelperTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HelperValidationService {

    @Autowired
    private HelperRepository helperRepository;
    @Autowired
    private SocietyService societyService;
    @Autowired
    private HelperTypeRepository helperTypeRepository;

    public void isHelperExist(HelperVO helperVO) throws PZConstraintViolationException {

        HelperEntity helperEntity= helperRepository.findById((helperVO.getId()));
        if(helperEntity == null){
            PZConstraint pzConstraint = new PZConstraint("HelperValidationService","isUserExist", "HelperService", "HelperValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isHelperIdExist(int helperId) throws PZConstraintViolationException {

        HelperEntity helperEntity=helperRepository.findById((helperId));
        if(helperEntity == null){
            PZConstraint pzConstraint = new PZConstraint("HelperValidationService","isUserExist", "HelperService", "HelperValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isSocietyIdExist(int societyId) throws PZConstraintViolationException {
        SocietyVO societyVO = new SocietyVO();
        societyVO.setId(societyId);
        societyService.societyIdExist(societyVO);
    }
    public void isHelperTypeIdExist(HelperTypeVO helperTypeVO) throws PZConstraintViolationException {

        HelperTypeEntity helperEntity=helperTypeRepository.findById((helperTypeVO.getId()));
        if(helperEntity == null){
            PZConstraint pzConstraint = new PZConstraint("HelperValidationService","isUserExist", "HelperService", "HelperValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }


}
