package com.pz.mysociety.common.exception;


import com.pz.mysociety.common.exception.constraint.PZTechnicalConstraint;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class PZTechnicalViolationException extends PZApplicationException
{
    private PZTechnicalConstraint pzTechnicalConstraint;

    public PZTechnicalViolationException(PZTechnicalConstraint technicalConstraint,String msg,Throwable cause)
    {
        super(msg,cause);
        pzTechnicalConstraint = technicalConstraint;
    }

    public PZTechnicalViolationException(PZTechnicalConstraint technicalConstraint,String msg)
    {
        super(msg);
        pzTechnicalConstraint = technicalConstraint;
    }


}
