package ru.acs.sod.model.log.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.annotation.log.LogEventSeverity;

public class LogEventSeverityTest {

    @Test
    public void testGetById() {
        assertEquals(LogEventSeverity.ERROR, LogEventSeverity.getById(0));
        assertEquals(LogEventSeverity.INFO, LogEventSeverity.getById(1));
        assertEquals(LogEventSeverity.SYSTEM_ERROR, LogEventSeverity.getById(2));
        assertEquals(LogEventSeverity.WARN, LogEventSeverity.getById(3));
    }
}
