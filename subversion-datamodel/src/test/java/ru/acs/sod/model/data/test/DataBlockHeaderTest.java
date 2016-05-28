package ru.acs.sod.model.data.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import ru.parser.data.DataBlockHeader;
import ru.parser.data.DataBlockRegistration;

public class DataBlockHeaderTest {

    private static final Date CURRENT_DATE = new Date();

    @Test
    public void testDataBlockHeader() {
        DataBlockHeader header = new DataBlockHeader();
        assertNotNull(header);
        assertNull(header.getEncrypt());
        assertNull(header.getFormat());
        assertNull(header.getIncludes());
        assertNull(header.getInternalId());
    }

    @Test
    public void testDataBlockRegistration() {
        DataBlockRegistration registration = new DataBlockRegistration();
        assertNotNull(registration);
        assertNull(registration.getEmail());
        assertNull(registration.getGuid());
        assertNull(registration.getPresentationWay());
        assertNull(registration.getPresented());
        assertNull(registration.getRecDateTime());
        assertNull(registration.getSender());
    }

    @Test
    public void testGetSetDataBlockHeader() {
        DataBlockHeader header = new DataBlockHeader();
        header.setEncrypt("encrypt");
        header.setFormat("format");
        header.setIncludes("includes");
        header.setInternalId("sId");
        assertEquals("encrypt", header.getEncrypt());
        assertEquals("format", header.getFormat());
        assertEquals("includes", header.getIncludes());
        assertEquals("sId", header.getInternalId());
    }
    @Test
    public void testGetSetDataBlockRegistration() {
        DataBlockRegistration registration = new DataBlockRegistration();
        registration.setEmail("email");
        registration.setGuid("guid");
        registration.setPresentationWay("pway");
        registration.setPresented("present");
        registration.setRecDateTime(CURRENT_DATE);
        registration.setSender("sender");
        assertEquals("email", registration.getEmail());
        assertEquals("guid", registration.getGuid());
        assertEquals("pway", registration.getPresentationWay());
        assertEquals("present", registration.getPresented());
        assertEquals(CURRENT_DATE, registration.getRecDateTime());
        assertEquals("sender", registration.getSender());
    }
}
