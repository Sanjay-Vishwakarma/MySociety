package com.pz.mysociety.validation;

import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.model.RequestVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public interface InputValidator {

//    public void validate (RequestVO requestVO) throws PZConstraintViolationException;

}
