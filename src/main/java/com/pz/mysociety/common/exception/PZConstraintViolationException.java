package com.pz.mysociety.common.exception;


import com.pz.mysociety.common.exception.constraint.PZConstraint;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PZConstraintViolationException extends PZApplicationException
{
    private final List<PZConstraint> pzConstraints;
    private int statusCode;



    public PZConstraintViolationException(List<PZConstraint> constViolations)
    {
        if ( constViolations == null ) {
            this.pzConstraints = null;
        }
        else {
            this.pzConstraints = new ArrayList<>( constViolations );
        }


    }

    public PZConstraintViolationException(String message , List<PZConstraint> constViolations)
    {

        super( message );
        if ( constViolations == null ) {
            this.pzConstraints = null;
        }
        else {
            this.pzConstraints = new ArrayList<>( constViolations );
        }


    }


    private static String toString(List<PZConstraint> constraintViolations) {
        return constraintViolations.stream()
                .map(cv -> cv == null ? "null" : cv.getPzConstEnum()==null?"":cv.getPzConstEnum().name() + ": " + cv.getPropertyName() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
    }

    public PZConstraintViolationException(String message){
        super(message );
        this.statusCode = 405;
        this.pzConstraints = new ArrayList<>();
    }




}
