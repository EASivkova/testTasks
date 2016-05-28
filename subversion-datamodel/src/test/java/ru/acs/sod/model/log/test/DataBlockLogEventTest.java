package ru.acs.sod.model.log.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ru.parser.annotation.log.DataBlockLogEvent;
import ru.parser.annotation.log.LogEventSeverity;

public class DataBlockLogEventTest {

    private static final Long ID = 4234L;

    @Test
    public void testHashCode() {
        DataBlockLogEvent block = new DataBlockLogEvent();
        block.setId(1400L);
        assertEquals(1400L, block.hashCode());
    }

    @Test
    public void testDataBlockLogEvent() {
        DataBlockLogEvent log = new DataBlockLogEvent();
        assertNotNull(log);
        assertNull(log.getCode());
        assertNull(log.getDataBlockId());
        assertNotNull(log.getDateTime());
        assertNull(log.getDetail());
        assertNull(log.getId());
        assertNull(log.getMessage());
        assertNull(log.getSeverity());
    }

    @Test
    public void testDataBlockLogEventStringStringStringLogEventSeverity() {
        DataBlockLogEvent log = new DataBlockLogEvent("code", "message", "details",
                LogEventSeverity.WARN);
        assertEquals("code", log.getCode());
        assertNull(log.getDataBlockId());
        assertNotNull(log.getDateTime());
        assertEquals("details", log.getDetail());
        assertNull(log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.WARN, log.getSeverity());
    }

    @Test
    public void testEqualsObject() {
        DataBlockLogEvent b1 = new DataBlockLogEvent();
        DataBlockLogEvent b2 = new DataBlockLogEvent();
        b2.setId(ID);
        assertFalse(b2.equals(b1));
        assertFalse(b1.equals(b2));
        DataBlockLogEvent b3 = new DataBlockLogEvent();
        b3.setId(ID);
        assertTrue(b2.equals(b3));
    }

    @Test
    public void testSetGet() {
        Date dateTime = new Date();
        DataBlockLogEvent log = new DataBlockLogEvent();
        log.setCode("code");
        log.setDataBlockId(1L);
        log.setDateTime(dateTime);
        log.setDetail("detail");
        log.setId(2L);
        log.setMessage("message");
        log.setSeverity(LogEventSeverity.SYSTEM_ERROR);
        // ------------------------------------------------------
        assertEquals("code", log.getCode());
        assertEquals(Long.valueOf(1), log.getDataBlockId());
        assertEquals(dateTime, log.getDateTime());
        assertEquals("detail", log.getDetail());
        assertEquals(Long.valueOf(2), log.getId());
        assertEquals("message", log.getMessage());
        assertEquals(LogEventSeverity.SYSTEM_ERROR, log.getSeverity());
    }

}
