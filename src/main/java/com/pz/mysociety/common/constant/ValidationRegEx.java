package com.pz.mysociety.common.constant;


public class ValidationRegEx {

//    public static final String SafeString="^[a-zA-Z[-.,'&]]*$";
    public static final String SafeString="^[.\\p{Alnum}\\ \\p{L}\\P{M}\\p{M}]{0,1024}$";
    public static final String AlphaNum="^[a-zA-Z 0-9[-.,'()\n]\\ \\,]*$";
    public static final String Number="^[\\p{Space}\\r0-9]*$";
    public static final String Character_Pattern = "^[a-zA-Z [-.,' &]]*$";
//    public static final String URL = "^([htpfs]{3,5}\\:?\\/\\/[\\w\\.\\:\\/]*\\??[a-zA-Z0-9\\&!@()-_%#\=\\?]*)$|^([a-zA-Z0-9\\&!@()-_%#\=\\?]*)$|^([a-zA-Z0-9\\&!@()-_%#\=\\?]*)$|^([a-zA-Z0-9\\&!@()-_%#\=\\?]*)$";
    public static final String Timestamp = "^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public static final String Password = "^\\S[.*(?=.{4,20})(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%]*$|(?=.{4,20})(?=.*\\d)[a-zA-Z0-9!@#$%]]*$";
    public static final String Address = "^[\\p{L}\\P{M}\\p{M}\\p{Space}\\n\\ra-zA-Z0-9:/\\\\!@#$%^&{}\\[\\]()_+\\-=,.~'` ]{0,5000}$";
    public static final String State = "^[\\ra-zA-Z0-9\\\\!@#$*%^&©{}\\[\\]()_+\\p{L}\\p{M}\\-=,.~'` \\p{Space}]{0,1000}$";
    public static final String City = "^[a-zA-Z0-9\\\\#&©\\p{Space}\\p{L}\\p{M}\\-;.,~` ]{0,1000}$";
    public static final String Name = "^[A-Z a-z]*$";
    public static final String Role = "^[A-Z a-z [-.,]?]*$";
//    public static final String Date = "^([a-zA-Z0-9\\-\\.\\/\\p{Space}]*)$";
    public static final String DateTime = "[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
    public static final String Date = "[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}";
    public static final String Status = "^[A-Z a-z]*$";
    public static final String User_Role = "^[User|Society]*$";
    public static final String Invoice_File_Type = "^[xlsx|csv]*$";
    public static final String Template_Type = "^[header|term|footer]*$";
}
