package ru.acs.sod.model.log.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ru.parser.annotation.log.LogEventSeverity;
import ru.parser.annotation.log.TechEnvLogEvent;

public class TechEnvLogEventTest {

    @Test
    public void testHashCode() {
        TechEnvLogEvent log = new TechEnvLogEvent();
        log.setId(1400L);
        assertEquals(1400L, log.hashCode());
    }

    @Test
    public void testTechEnvLogEvent() {
        TechEnvLogEvent log = new TechEnvLogEvent();
        assertNotNull(log);
        assertNull(log.getEnvId());
        assertNull(log.getCode());
        assertNotNull(log.getDateTime());
        assertNull(log.getDetail());
        assertNull(log.getMessage());
        assertNull(log.getSeverity());

    }

    @Test
    public void testTechEnvLogEventString() {
        TechEnvLogEvent log = new TechEnvLogEvent("code", "message", "detail",
                LogEventSeverity.ERROR);
        assertEquals("code", log.getCode());
        assertNotNull(log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertNull(log.getEnvId());
        assertNull(log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.ERROR, log.getSeverity());

    }

    @Test
    public void testGetEnvId() {
        TechEnvLogEvent log = new TechEnvLogEvent();
        Date date = new Date();
        log.setCode("code");
        log.setDateTime(date);
        log.setDetail("detail");
        log.setEnvId(2112L);
        log.setId(21L);
        log.setMessage("message");
        log.setSeverity(LogEventSeverity.SYSTEM_ERROR);
        // -----------------------------------------
        assertEquals("code", log.getCode());
        assertEquals(date, log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertEquals(Long.valueOf(2112), log.getEnvId());
        assertEquals(Long.valueOf(21), log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.SYSTEM_ERROR, log.getSeverity());
    }

    @Test
    public void testEqualsObject() {
        TechEnvLogEvent b1 = new TechEnvLogEvent();
        TechEnvLogEvent b2 = new TechEnvLogEvent();
        b2.setId(123L);
        assertFalse(b2.equals(b1));
        assertFalse(b1.equals(b2));
        TechEnvLogEvent b3 = new TechEnvLogEvent();
        b3.setId(123L);
        assertTrue(b2.equals(b3));
    }

}
