package com.pz.mysociety.model.Request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.RequestVO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyReviewVO extends RequestVO {

    private int id;
    private int ownerId;
    private int societyId;
    private int areaId;
    private String description;
    private String Action;
    private boolean IsNameDisplay;
   private String construction;
    private String prominent;
    private String Safe;
    private String Noise;
    private String Clean;
    private String Friction;
    private String Commute;
    private String residentType;
    private String amenityQuality;
    private String eventFull;
    private boolean name_Display;
    private String timestamp;
    private int rating;


    public boolean getNameDisplay()
    {return IsNameDisplay;}

    public void setNameDisplay(boolean isNameDisplay)
    {this.IsNameDisplay= isNameDisplay;}


}
