package com.pz.mysociety.common.exception.constraint;


import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PZConstraint extends PZGenericConstraint
{
    private PZConstraintExceptionEnum pzConstEnum;

    public PZConstraint(String className, String methodName, String propertyName, String moduleName, String message, PZConstraintExceptionEnum pzConstEnum) {
        super(className, methodName, propertyName, moduleName, message);
        this.pzConstEnum = pzConstEnum;
    }
}
