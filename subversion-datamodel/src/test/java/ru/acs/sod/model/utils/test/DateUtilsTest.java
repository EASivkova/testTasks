package ru.acs.sod.model.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ru.parser.utils.DateUtils;

public class DateUtilsTest {

    @Test
    public void testGetOnlyDate() {
        Date d = new Date();
        Date round = DateUtils.getOnlyDate(d);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        assertEquals(c.getTime(), round);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullDate() {
        DateUtils.getOnlyDate(null);
    }

    @Test
    public void testGetStringDate() {
        assertNull(DateUtils.getStringDate(new Date(), null));
        assertNull(DateUtils.getStringDate(null, ""));
        Date d = new Date(123123123);
        String g = DateUtils.getStringDate(d, "dd-mm-yyyy");
        assertEquals("02-12-1970", g);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDateFromExceptionString() throws Exception {
        DateUtils.getDateFromString("02-12-1970", "dd-mi-yyyy");
    }

    @Test
    public void testNullGetDate() throws Exception {
        assertNull(DateUtils.getDateFromString("", "dd-mm-yyyy"));
        assertNull(DateUtils.getDateFromString(null, "dd-mm-yyyy"));
    }

    @Test
    public void testStringToDate() throws Exception {
        Date d = DateUtils.getDateFromString("1989-07-23T15:00:00", DateUtils.DATE_TIME_PATTERN);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        assertEquals(15, c.get(Calendar.HOUR_OF_DAY));
    }
}
