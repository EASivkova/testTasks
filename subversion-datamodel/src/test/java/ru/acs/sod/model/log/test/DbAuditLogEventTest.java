package ru.acs.sod.model.log.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ru.parser.annotation.log.DbAuditLogEvent;
import ru.parser.annotation.log.LogEventSeverity;

public class DbAuditLogEventTest {

    @Test
    public void testDbAuditLogEvent() {
        DbAuditLogEvent log = new DbAuditLogEvent();
        assertNotNull(log);
        assertNull(log.getCode());
        assertNotNull(log.getDateTime());
        assertNull(log.getDetail());
        assertNull(log.getId());
        assertNull(log.getMessage());
        assertNull(log.getSeverity());
    }

    @Test
    public void testDbAuditLogEventString() {
        DbAuditLogEvent log = new DbAuditLogEvent("code", "message", "detail",
                LogEventSeverity.ERROR);
        assertEquals("code", log.getCode());
        assertNotNull(log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertNull(log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.ERROR, log.getSeverity());
    }

    @Test
    public void testSetGet() {
        DbAuditLogEvent log = new DbAuditLogEvent();
        Date dateTime = new Date();
        log.setCode("code");
        log.setDateTime(dateTime);
        log.setDetail("detail");
        log.setId(123L);
        log.setMessage("message");
        log.setSeverity(LogEventSeverity.INFO);
        // -----------------------------
        assertEquals("code", log.getCode());
        assertEquals(dateTime, log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertEquals(Long.valueOf(123), log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.INFO, log.getSeverity());
    }
}
