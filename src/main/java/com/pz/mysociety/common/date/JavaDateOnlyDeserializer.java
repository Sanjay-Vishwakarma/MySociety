package com.pz.mysociety.common.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZApplicationException;
import com.pz.mysociety.common.exception.PZTechnicalViolationException;
import com.pz.mysociety.common.exception.constraint.PZTechnicalConstraint;
import com.pz.mysociety.common.exception.constraintType.PZTechnicalExceptionEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 8/21/2021.
 */
@Component
public class JavaDateOnlyDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Date dateObj = null;
        try{
            String strDate=jsonParser.getText();
            dateObj = dateFormat.parse(strDate);
        }catch (ParseException e){
            new PZTechnicalViolationException(new PZTechnicalConstraint(PZTechnicalExceptionEnum.DATE_PARSE_EXCEPTION),ValidationMessages.DATE_JSON_EXCEPTION,e);
        }
        catch(IOException e)
        {
            new PZTechnicalViolationException(new PZTechnicalConstraint(PZTechnicalExceptionEnum.IO_EXCEPTION),ValidationMessages.DATE_JSON_EXCEPTION,e);
        }
        return dateObj;
    }
}
