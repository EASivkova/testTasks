package ru.acs.sod.model.mail.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.annotation.mail.Administrator;

public class AdministratorTest {

    @Test
    public void testHashCode() {
        Administrator admin = new Administrator();
        admin.setId(14300L);
        assertEquals(14300L, admin.hashCode());
    }

    @Test
    public void testAdministrator() {
        Administrator admin = new Administrator();
        assertNotNull(admin);
        assertNull(admin.getEmail());
        assertNull(admin.getId());
    }

    @Test
    public void testAdministratorString() {
        Administrator admin = new Administrator("email");
        assertNotNull(admin);
        assertEquals("email", admin.getEmail());
        assertNull(admin.getId());
    }

    @Test
    public void testSetGet() {
        Administrator admin = new Administrator();
        admin.setEmail("email");
        admin.setId(52L);
        // ------------------------------
        assertEquals("email", admin.getEmail());
        assertEquals(Long.valueOf(52), admin.getId());
    }

    @Test
    public void testEqualsObject() {
        Administrator b1 = new Administrator();
        Administrator b2 = new Administrator();
        b2.setId(123L);
        assertFalse(b2.equals(b1));
        assertFalse(b1.equals(b2));
        Administrator b3 = new Administrator();
        b3.setId(123L);
        assertTrue(b2.equals(b3));
    }
}
