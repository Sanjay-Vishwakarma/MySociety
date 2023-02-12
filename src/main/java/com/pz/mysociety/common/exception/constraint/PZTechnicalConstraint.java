package com.pz.mysociety.common.exception.constraint;


import com.pz.mysociety.common.exception.constraintType.PZTechnicalExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PZTechnicalConstraint extends PZGenericConstraint
{
    private PZTechnicalExceptionEnum pzTechEnum;

}
