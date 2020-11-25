package com.lens.chatter.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */
@UtilityClass
public class DateUtils {

    // get today and clear time of day
    public static Instant getTheBeginningOfDay(){
        return getTheBeginningOfDayByCalendar().toInstant();
    }

    private static Calendar getTheBeginningOfDayByCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }

    public static Instant getTheBeginningOfWeek() {
        Calendar cal = getTheBeginningOfDayByCalendar();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.toInstant();

    }

    public static Instant getTheBeginningOfMonth() {
        Calendar cal = getTheBeginningOfDayByCalendar();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.toInstant();
    }

    public static Instant getTheBeginningOfYear() {
        Calendar cal = getTheBeginningOfDayByCalendar();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.toInstant();
    }
}
