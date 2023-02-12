package com.pz.mysociety.service;

import com.pz.mysociety.common.constant.UserRoles;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserAuthService {

    public void isAdmin(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SUPER_ADMIN) && !role.equals(UserRoles.SOCIETY)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isSociety(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SOCIETY)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isSuperAdmin(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SUPER_ADMIN)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isUser(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.USER)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isSocietyOrUser(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SOCIETY) && !role.equals(UserRoles.USER)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isSocietyOrUserOrGuard(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SOCIETY) && !role.equals(UserRoles.USER) && !role.equals(UserRoles.GUARD)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isAllUser(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdminOrUser", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SUPER_ADMIN) && !role.equals(UserRoles.SOCIETY) && !role.equals(UserRoles.USER) && !role.equals(UserRoles.GUARD)) {

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdminOrUser", "InitiatedBY", "AuthService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }


    public void isGuard(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Role_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.GUARD)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }
    public void isSocietyOrGuard(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SOCIETY) && !role.equals(UserRoles.GUARD)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }

    public void isSuperAdminOrUser(String role) throws PZConstraintViolationException {

        ArrayList<PZConstraint> pzConstraints = new ArrayList<PZConstraint>();
        if(!Functions.nonNullString(role)){

            PZConstraint pzConstraint = new PZConstraint("UserAuthService","isAdmin", "InitiatedBY", "AuthService", ValidationMessages.Initiated_By_Required, PZConstraintExceptionEnum.MANDATORY_PARAMETER_MISSING);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
        if (!role.equals(UserRoles.SUPER_ADMIN) && !role.equals(UserRoles.USER)) {

            PZConstraint pzConstraint = new PZConstraint("UserInputValidator","adminSignupInputValidation", "InitiatedBY", "UserService", ValidationMessages.Forbidden_Access, PZConstraintExceptionEnum.INVALID_REQUEST);
            pzConstraints.add(pzConstraint);
            throw new PZConstraintViolationException(ValidationMessages.Forbidden_Access, pzConstraints);

        }
    }
}
