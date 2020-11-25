package com.lens.chatter.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

class DateUtilsTest {

    @Test
    void beginningOfDayTest() {
        ZonedDateTime time = ZonedDateTime.now();
        Assert.assertEquals(ZonedDateTime.ofInstant(DateUtils.getTheBeginningOfDay(), ZoneId.systemDefault()),
                time.truncatedTo(ChronoUnit.DAYS));
    }

    @Test
    void beginningOfWeekTest() {
        ZonedDateTime time = ZonedDateTime.now().with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
        Assert.assertEquals(ZonedDateTime.ofInstant(DateUtils.getTheBeginningOfWeek(), ZoneId.systemDefault()),
                time);
    }

    @Test
    void beginningOfMonthTest() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime time = ZonedDateTime.of(now.getYear(), now.getMonthValue() , 1, 0, 0, 0, 0,
                ZoneId.systemDefault());
        Assert.assertEquals(ZonedDateTime.ofInstant(DateUtils.getTheBeginningOfMonth(), ZoneId.systemDefault()),
                time);
    }

    @Test
    void beginningOfYearTest() {
        ZonedDateTime time = ZonedDateTime.of(ZonedDateTime.now().getYear(), 1, 1, 0, 0, 0, 0,
                ZoneId.systemDefault());
        Assert.assertEquals(ZonedDateTime.ofInstant(DateUtils.getTheBeginningOfYear(), ZoneId.systemDefault()),
                time);
    }

}
