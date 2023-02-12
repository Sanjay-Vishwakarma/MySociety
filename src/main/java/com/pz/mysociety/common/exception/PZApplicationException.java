package com.pz.mysociety.common.exception;


import com.pz.mysociety.common.exception.constraint.PZAppConstraint;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
public class PZApplicationException extends RuntimeException {

    PZAppConstraint pzAppConstraint;

    public PZApplicationException() {
        super();
    }

    public PZApplicationException(String message) {
        super(message);
    }

    public PZApplicationException(String message, Throwable cause) {
        super(message, cause);
    }


    public PZApplicationException(String message, PZAppConstraint constraint) {
        super(message);
        pzAppConstraint = constraint;
    }

    public PZApplicationException(String message, Throwable cause, PZAppConstraint constraint) {
        super(message, cause);
        pzAppConstraint = constraint;
    }




}
