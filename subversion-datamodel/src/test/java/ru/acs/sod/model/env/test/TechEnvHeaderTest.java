package ru.acs.sod.model.env.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ru.parser.env.TechEnvHeader;

public class TechEnvHeaderTest {

    private static final Date CURRENT_DATE = new Date();

    @Test
    public void testTechEnvHeader() {
        TechEnvHeader header = new TechEnvHeader();
        assertNotNull(header);
        assertNull(header.getDateTime());
        assertNull(header.getGuid());
        assertNull(header.getReceiver());
        assertNull(header.getSender());
    }

    @Test
    public void testSetGet() {
        TechEnvHeader header = new TechEnvHeader();
        header.setDateTime(CURRENT_DATE);
        header.setGuid("guid");
        header.setReceiver("receiver");
        header.setSender("sender");
        // ----------------------------------------------------------
        assertEquals(CURRENT_DATE, header.getDateTime());
        assertEquals("guid", header.getGuid());
        assertEquals("receiver", header.getReceiver());
        assertEquals("sender", header.getSender());
    }
}
