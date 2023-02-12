package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.entity.complainEntity.ComplainMasterEntity;
import com.pz.mysociety.model.Request.ComplainVO;


import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.repository.complainRepository.ComplainMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainValidationService {

    @Autowired
    private ComplainMasterRepository complainMasterRepository;

    @Autowired
    private SocietyService societyService;

    @Autowired
    UserService userService;


    public void isUserMappingExist(ComplainVO complainVO) throws PZConstraintViolationException{
        if(complainVO.getUserId()!=0){
      userService.isUserComplainMapping(complainVO);}
       else {
      userService.isAdminComplainMapping(complainVO);
        }
    }
    public void isUserExist(ComplainVO complainVO) throws PZConstraintViolationException{

        ComplainMasterEntity complainMasterEntity=complainMasterRepository.getComplainById(complainVO.getUserId());
        if(complainMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("ComplainValidationService","isUserMappingExist", "Complain", "ComplainValidationService", ValidationMessages.User_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isComplainIdExist(ComplainVO complainVO) throws PZConstraintViolationException{
        ComplainMasterEntity complainMasterEntity=complainMasterRepository.getComplainById(complainVO.getComplainId());
        if(complainMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("ComplainValidationService","isComplainExist", "Complain", "ComplainValidationService", ValidationMessages.Data_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isComplainIdAndUserIdExist(ComplainVO complainVO) throws PZConstraintViolationException{

        ComplainMasterEntity complainMasterEntity=complainMasterRepository.findByIdAndUserId(complainVO.getComplainId(), complainVO.getUserId());
        if(complainMasterEntity == null){
            PZConstraint pzConstraint = new PZConstraint("ComplainValidationService","isComplainExist", "Complain", "ComplainValidationService", ValidationMessages.Data_Not_Found, PZConstraintExceptionEnum.INVALID_REQUEST);
            List<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
            pzConstraints.add(pzConstraint);

            throw new PZConstraintViolationException(ValidationMessages.User_Not_Found, pzConstraints);
        }
    }
    public void isUserMappingExist(int userId) throws PZConstraintViolationException {
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        userService.isUserExist(userVO);
    }

}
