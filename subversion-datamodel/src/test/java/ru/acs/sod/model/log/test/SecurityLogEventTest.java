package ru.acs.sod.model.log.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ru.parser.annotation.log.LogEventSeverity;
import ru.parser.annotation.log.SecurityLogEvent;

public class SecurityLogEventTest {

    @Test
    public void testSecurityLogEvent() {
        SecurityLogEvent log = new SecurityLogEvent();
        assertNotNull(log);
        assertNull(log.getCode());
        assertNotNull(log.getDateTime());
        assertNull(log.getDetail());
        assertNull(log.getId());
        assertNull(log.getMessage());
        assertNull(log.getSeverity());
    }

    @Test
    public void testSecurityLogEventString() {
        SecurityLogEvent log = new SecurityLogEvent("code", "message", "detail",
                LogEventSeverity.INFO);
        assertEquals("code", log.getCode());
        assertNotNull(log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertNull(log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.INFO, log.getSeverity());
    }

    @Test
    public void testSetGet() {
        SecurityLogEvent log = new SecurityLogEvent();
        Date dateTime = new Date();
        log.setCode("code");
        log.setDateTime(dateTime);
        log.setDetail("detail");
        log.setId(1231231L);
        log.setMessage("message");
        log.setSeverity(LogEventSeverity.INFO);
        // -----------------------------------------------------
        assertEquals("code", log.getCode());
        assertEquals(dateTime, log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertEquals(Long.valueOf(1231231), log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.INFO, log.getSeverity());
    }
}
