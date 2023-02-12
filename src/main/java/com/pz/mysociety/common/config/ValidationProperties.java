package com.pz.mysociety.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Admin on 8/26/2021.
 */
@Data
@AllArgsConstructor
public class ValidationProperties {

    private final String value;

    private final String key;
}
