package com.pz.mysociety.common.exception;

import com.pz.mysociety.common.constant.ErrorMessages;
import com.pz.mysociety.model.ErrorVO;
import com.pz.mysociety.model.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@ResponseStatus
@Slf4j
public class PZExceptionHandler{

    ResponseVO responseVO;

    @ExceptionHandler(PZApplicationException.class)
    public ResponseEntity<ResponseVO> applicationException(PZApplicationException e){
        responseVO = new ResponseVO(HttpStatus.NOT_FOUND,e.getMessage());
        log.info(e.getMessage() + e);
        //Send mail to admin
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(PZConstraintViolationException.class)
    public ResponseEntity<ResponseVO> constraintViolationException(PZConstraintViolationException e){


        List<ErrorVO> errorVOList =
                e.getPzConstraints()
                        .stream()
                        .map(element -> new ErrorVO(element.getPzConstEnum() == null ? "" : element.getPzConstEnum().name(), element.getMessage(),element.getPropertyName()))
                        .collect(Collectors.toList());

        responseVO = new ResponseVO(HttpStatus.BAD_REQUEST,e.getMessage(),errorVOList);

        log.info(e.getMessage() + e);
        //Send mail to admin
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(PZTechnicalViolationException.class)
    public ResponseEntity<ResponseVO> technicalException(PZTechnicalViolationException e){
        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        log.info(e.getMessage() + e);
        //Send mail to admin
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(PZDBViolationException.class)
    public ResponseEntity<ResponseVO> dbViolationException(PZDBViolationException e){
        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        log.info(e.getMessage() + e);
        //Send mail to admin
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity allException(Exception e){
        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.Internal_Server_Error);
        log.info(e.getMessage() + e);
        //Send mail to admin

        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodException(MethodArgumentNotValidException e){

        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR,e.getBindingResult().getFieldError().getDefaultMessage());
        log.info(e.getMessage() + e);
        //Send mail to admin

        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> requestType(HttpRequestMethodNotSupportedException e){

        responseVO = new ResponseVO(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());

        log.info(e.getMessage() + e);

        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintException(ConstraintViolationException e){

        //Need to check
        List<ErrorVO> errorVOList =
                e.getConstraintViolations().stream().map(element -> new ErrorVO("", element.getMessage(), "" + element.getPropertyPath())).collect(Collectors.toList());

        responseVO = new ResponseVO(HttpStatus.BAD_REQUEST,e.getMessage(),errorVOList);


        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<?> rollbackException(RollbackException e){

        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.Internal_Server_Error);

        log.info(e.getMessage() + e);
        // Need to send mail to admin
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPoint(NullPointerException e){
        responseVO = new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.Internal_Server_Error);
        log.info(e.getMessage() + e);

        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpDataType(HttpMessageNotReadableException e){
        responseVO = new ResponseVO(HttpStatus.BAD_REQUEST,ErrorMessages.Incorrect_Request_Data);
        log.info(e.getMessage() + e);

        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFoundEntity(EntityNotFoundException e){
        responseVO = new ResponseVO(HttpStatus.NOT_FOUND,ErrorMessages.Data_Not_Found);
        log.info(e.getMessage() + e);
         return new ResponseEntity(responseVO, HttpStatus.OK);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> dataNotPresentInDatabase(IllegalStateException e){
        responseVO = new ResponseVO(HttpStatus.NOT_FOUND,ErrorMessages.Data_Not_Found);
        log.info(e.getMessage() + e);
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> notPresentInDatabase(EmptyResultDataAccessException e){
        responseVO = new ResponseVO(HttpStatus.NOT_FOUND,ErrorMessages.Data_Not_Found);
        log.info(e.getMessage() + e);
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }

    //Creating an exception for addFlatList Api
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity mathException(ArithmeticException e){
        responseVO = new ResponseVO(HttpStatus.NOT_FOUND,ErrorMessages.Duplicate_Data_Found);
        log.info(e.getMessage() + e);
        return new ResponseEntity(responseVO, HttpStatus.OK);
    }


}
