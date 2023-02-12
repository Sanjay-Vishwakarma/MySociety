package com.pz.mysociety.common.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZTechnicalViolationException;
import com.pz.mysociety.common.exception.constraint.PZTechnicalConstraint;
import com.pz.mysociety.common.exception.constraintType.PZTechnicalExceptionEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 8/21/2021.
 */
@Component
public class JavaDateOnlySerializer extends JsonSerializer<Date>{

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        try{
            String formattedDate = dateFormat.format(date);
            jsonGenerator.writeString(formattedDate);

        }catch(IOException e)
        {
            new PZTechnicalViolationException(new PZTechnicalConstraint(PZTechnicalExceptionEnum.IO_EXCEPTION),ValidationMessages.DATE_JSON_EXCEPTION,e);
        }

    }
}
