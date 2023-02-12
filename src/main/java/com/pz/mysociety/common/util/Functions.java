package com.pz.mysociety.common.util;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Functions {

    private static String date;

    public static boolean nonNullString(String str)
    {
        if(Objects.nonNull(str) && !"".equalsIgnoreCase(str) && !"null".equalsIgnoreCase(str))
        {
            return true;
        }

        return false;
    }

    public static boolean compareValue(String oldValue, String newValue){

        if (nonNullString(newValue) && !newValue.equals(oldValue)) {
           return true;
        }
        return false;
    }


//    public static <T,S> boolean compare(T oldValue, S newValue){
//
//        if (nonNullString(T.class.) && !newValue.equals(oldValue)) {
//            return true;
//        }
//        return false;
//    }


    public static String generateKey(int size)
    {
        String pwdData = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = pwdData.length();
        Date date = new Date();
        Random rand = new Random(date.getTime());
        StringBuffer key = new StringBuffer();
        int index = -1;

        for (int i = 0; i < size; i++)
        {
            index = rand.nextInt(len);
            key.append(pwdData.substring(index, index + 1));
        }
        return key.toString();
    }
    public static int getDefault(int convertstr, int defaultval)
    {
        int val = defaultval;

        if (convertstr != 0)
        {
            try
                {
                    val =convertstr;
                }
                catch (NumberFormatException nfe)
                {
                    val = defaultval;
                }

        }
        return val;
    }

    public static int getPagesCount(int count){

        int pages = count/30;

        if(count%30 != 0){
            pages++;
        }

        return pages;
    }

    public static int getToken(int count){

        int pages = count/500;

        if(count%500 != 0){
            pages++;
        }

        return pages;
    }

    public static Pageable getPage(int page, int limit){
        if(page <1){
            page = 0;
        }else {
            page = page - 1;
        }

        if(limit > 30 || limit < 1){
            limit = 30;
        }
        return PageRequest.of(page, limit);
    }

    public static String getDateFormat(String date) throws ParseException {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        Date changedFormat = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        LocalDate localDate = Instant.ofEpochMilli(changedFormat.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return localDate.format(dateFormatter);
    }

    public static String getDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";

        DateFormat d = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return d.format(today);
    }

    public static Date getFormatDate(String date) {
//        String pattern = "yyyy-MM-dd HH:mm:ss";
        String pattern = "yyyy-MM-dd HH:mm";

        DateFormat d = new SimpleDateFormat(pattern);
        Date today = null;

        try {
            today = d.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }

    public static int getRandomValue() {

        Random random = new Random();
        return random.nextInt(9999);
    }

    public static String getDuration(String startDate, String endDate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start;
        Date end;
        long duration = 0;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
            duration = end.getTime() - start.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int diffInMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(duration);
        int diffInHours = (int)TimeUnit.MILLISECONDS.toHours(duration);
        int diffInDays = (int) TimeUnit.MILLISECONDS.toDays(duration);

        String interval = null;
        int hours = diffInHours - (int)TimeUnit.DAYS.toHours(diffInDays);
        int minutes = diffInMinutes - (int)TimeUnit.DAYS.toMinutes(diffInDays) - (int)TimeUnit.HOURS.toMinutes(hours);

        if(diffInDays != 0){
            interval = diffInDays + " day";
        }

        if(hours != 0){
            if(interval != null) {
                interval = interval + "," + hours + " hrs";
            }else {
                interval = hours +" hrs";
            }
        }

        if(minutes != 0){
            if(interval != null) {
                interval = interval + "," + minutes +" min";
            }else {
                interval = minutes + " min";
            }
        }


        return interval;
    }

    public static boolean isEndDateGreatThanStartDate(String startDate, String endDate) {
        boolean isGreat = false;

        if(Functions.nonNullString(startDate) && Functions.nonNullString(endDate)) {
            Date start = Functions.getFormatDate(startDate);
            Date end = Functions.getFormatDate(endDate);
            Date checkEndDuration = DateUtils.addMinutes(start, 30);

            long a = end.getTime();
            long b = end.getTime();

            if (end.getTime() >= checkEndDuration.getTime())
                isGreat = true;

        }
        return isGreat;
    }
}
