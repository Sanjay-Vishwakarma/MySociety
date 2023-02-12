package com.pz.mysociety.common.exception.constraint;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PZGenericConstraint {

    private String className;
    private String methodName;
    private String propertyName;
    private String moduleName;
    private String message;

}
