package ru.acs.sod.model.xsd.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import ru.parser.xsd.XSDType;

public class XSDTypeTest {

    @Test
    public void testHashCode() {
        XSDType xsd = new XSDType();
        xsd.setId(14389L);
        assertEquals(14389L, xsd.hashCode());
    }

    @Test
    public void testXSDType() {
        XSDType type = new XSDType();
        assertNotNull(type);
        assertFalse(type.getDelay());
        assertNull(type.getDescription());
        assertNull(type.getFormCodePath());
        assertNull(type.getGeneratedDatePath());
        assertNull(type.getGuidPath());
        assertNull(type.getId());
        assertNull(type.getName());
        assertNull(type.getOkatoPath());
        assertNull(type.getOkudPath());
        assertNull(type.getOrgCodePath());
        assertNull(type.getOrgTypePath());
        assertNull(type.getParsedSchema());
        assertNull(type.getPeriodicalPath());
        assertNotNull(type.getRegistrationDate());
        assertNull(type.getReportDatePath());
        assertNull(type.getReportTypePath());
        assertNull(type.getXml());
    }

    @Test
    public void testXSDTypeStringByteArray() {
        XSDType type = new XSDType("name", new byte[] {
                123, 32, 23, 12, 53, 1, 3
        });
        assertNotNull(type);
        assertFalse(type.getDelay());
        assertNull(type.getDescription());
        assertNull(type.getFormCodePath());
        assertNull(type.getGeneratedDatePath());
        assertNull(type.getGuidPath());
        assertNull(type.getId());
        assertEquals("name", type.getName());
        assertNull(type.getOkatoPath());
        assertNull(type.getOkudPath());
        assertNull(type.getOrgCodePath());
        assertNull(type.getOrgTypePath());
        assertNull(type.getParsedSchema());
        assertNull(type.getPeriodicalPath());
        assertNotNull(type.getRegistrationDate());
        assertNull(type.getReportDatePath());
        assertNull(type.getReportTypePath());
        assertArrayEquals(new byte[] {
                123, 32, 23, 12, 53, 1, 3
        }, type.getXml());
    }

    @Test
    public void testGetId() {
        XSDType type = new XSDType();
        Object xsdSchema = new Object();
        Calendar registrationDate = Calendar.getInstance();
        byte[] xml = new byte[] {
                2, 3, 5, 2, 54
        };
        type.setDelay(true);
        type.setDescription("description");
        type.setFormCodePath("formCodePath");
        type.setGeneratedDatePath("generatedDatePath");
        type.setGuidPath("guidPath");
        type.setId(123L);
        type.setName("name");
        type.setOkatoPath("okatoPath");
        type.setOkudPath("okudPath");
        type.setOrgCodePath("orgCodePath");
        type.setOrgTypePath("orgTypePath");
        type.setParsedSchema(xsdSchema);
        type.setPeriodicalPath("periodicalPath");
        type.setRegistrationDate(registrationDate);
        type.setReportDatePath("reportDatePath");
        type.setReportTypePath("reportTypePath");
        type.setXml(xml);
        // -----------------------------------------------------------
        assertTrue(type.getDelay());
        assertEquals("description", type.getDescription());
        assertEquals("formCodePath", type.getFormCodePath());
        assertEquals("generatedDatePath", type.getGeneratedDatePath());
        assertEquals("guidPath", type.getGuidPath());
        assertEquals(Long.valueOf(123), type.getId());
        assertEquals("name", type.getName());
        assertEquals("okatoPath", type.getOkatoPath());
        assertEquals("okudPath", type.getOkudPath());
        assertEquals("orgCodePath", type.getOrgCodePath());
        assertEquals("orgTypePath", type.getOrgTypePath());
        assertEquals(xsdSchema, type.getParsedSchema());
        assertEquals("periodicalPath", type.getPeriodicalPath());
        assertEquals(registrationDate, type.getRegistrationDate());
        assertEquals("reportDatePath", type.getReportDatePath());
        assertEquals("reportTypePath", type.getReportTypePath());
        assertEquals(xml, type.getXml());
    }

    @Test
    public void testEqualsObject() {
        XSDType b1 = new XSDType();
        XSDType b2 = new XSDType();
        b2.setId(123L);
        assertFalse(b2.equals(b1));
        assertFalse(b1.equals(b2));
        XSDType b3 = new XSDType();
        b3.setId(123L);
        assertTrue(b2.equals(b3));
    }
}
