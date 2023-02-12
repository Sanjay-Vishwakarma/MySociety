package com.pz.mysociety.common.exception;


import com.pz.mysociety.common.exception.constraint.PZDBConstraint;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
public class PZDBViolationException extends PZApplicationException
{


    private PZDBConstraint pzdbConstraint ;

    public PZDBViolationException(PZDBConstraint dbConstraint, String msg, Throwable cause)
    {
        super(msg,cause);
        pzdbConstraint = dbConstraint;
    }


    public PZDBViolationException(PZDBConstraint dbConstraint, String msg)
    {
        super(msg);
        pzdbConstraint = dbConstraint;
    }

}
