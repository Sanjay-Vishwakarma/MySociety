package com.pz.mysociety.common.exception.constraint;

import com.pz.mysociety.common.exception.constraintType.PZAppExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PZAppConstraint extends PZGenericConstraint{

    private PZAppExceptionEnum pzAppExceptionEnum;
}
