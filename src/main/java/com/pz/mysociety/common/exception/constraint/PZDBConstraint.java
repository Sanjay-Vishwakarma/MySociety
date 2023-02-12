package com.pz.mysociety.common.exception.constraint;


import com.pz.mysociety.common.exception.constraintType.PZDBExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PZDBConstraint extends PZGenericConstraint
{
    private PZDBExceptionEnum pzDBEnum;


}
