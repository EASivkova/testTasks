package ru.acs.sod.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ru.acs.sod.model.exceptions.TechEnvProcessorException;
import ru.parser.utils.DateUtils;
import ru.parser.utils.GUIDGenerator;
import ru.parser.utils.GUIDGeneratorImpl;

public class UtilsTest {

    @Test
    public void dateGeneratorNewTest() throws Exception {
        String s1 = DateUtils.getStringDate(new Date(), DateUtils.DATE_TIME_PATTERN);
        assertEquals(19, s1.length());
        assertEquals("-", s1.substring(4, 5));
        assertEquals("-", s1.substring(7, 8));
        assertEquals("T", s1.substring(10, 11));
        assertEquals(":", s1.substring(13, 14));
        assertEquals(":", s1.substring(16, 17));
    }

    @Test
    public void dateGeneratorParseTest() throws Exception {
        Date d1 = new Date();
        Calendar c = Calendar.getInstance(/*TimeZone.getTimeZone("GMT+0000")*/);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String s1 = DateUtils.getStringDate(d1, DateUtils.DATE_TIME_PATTERN);
        assertEquals(19, s1.length());
        assertEquals(year, Integer.parseInt(s1.substring(0, 4)));
        assertEquals("-", s1.substring(4, 5));
        assertEquals(month, Integer.parseInt(s1.substring(5, 7)));
        assertEquals("-", s1.substring(7, 8));
        assertEquals(day, Integer.parseInt(s1.substring(8, 10)));
        assertEquals("T", s1.substring(10, 11));
        assertEquals(hour, Integer.parseInt(s1.substring(11, 13)));
        assertEquals(":", s1.substring(13, 14));
        assertEquals(minute, Integer.parseInt(s1.substring(14, 16)));
        assertEquals(":", s1.substring(16, 17));
        assertEquals(second, Integer.parseInt(s1.substring(17, 19)));
        String s2 = "1843-12-12T12:32:43";
        c.setTime(DateUtils.getDateFromString(s2, DateUtils.DATE_TIME_PATTERN));
        assertEquals(1843, c.get(Calendar.YEAR));
        assertEquals(11, c.get(Calendar.MONTH));
        assertEquals(12, c.get(Calendar.DAY_OF_MONTH));
        assertEquals(12, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(32, c.get(Calendar.MINUTE));
        assertEquals(43, c.get(Calendar.SECOND));
    }

    @Test
    public void errorTest() throws Exception {
        String es = "w9fhewifh892";
        try {
            DateUtils.getDateFromString(es, DateUtils.DATE_TIME_PATTERN);
            fail();
        } catch (TechEnvProcessorException e) {
            assertEquals("java.text.ParseException: Unparseable date: \"w9fhewifh892\"", e
                    .getMessage());
        }
    }

    @Test
    public void guidTest() {
        GUIDGenerator gguid = new GUIDGeneratorImpl();
        String[] guidMas = new String[100];
        for (int i = 0; i < guidMas.length; i++) {
            guidMas[i] = gguid.generate();
            try {
                Long.parseLong(guidMas[i].substring(0, 8), 16);
                assertEquals("-", guidMas[i].substring(8, 9));
                Integer.parseInt(guidMas[i].substring(9, 13), 16);
                assertEquals("-", guidMas[i].substring(13, 14));
                Integer.parseInt(guidMas[i].substring(14, 18), 16);
                assertEquals("-", guidMas[i].substring(18, 19));
                Integer.parseInt(guidMas[i].substring(19, 23), 16);
                assertEquals("-", guidMas[i].substring(23, 24));
                Long.parseLong(guidMas[i].substring(24), 16);
            } catch (NumberFormatException e) {
                fail("Получено не 16-ричное число " + e.getMessage());
            }
        }
        Arrays.sort(guidMas);
        for (int i = 0; i < guidMas.length - 1; i++) {
            assertFalse(guidMas[i].equals(guidMas[i + 1]));
        }
    }
}
