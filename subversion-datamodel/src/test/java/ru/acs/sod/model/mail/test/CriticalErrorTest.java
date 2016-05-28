package ru.acs.sod.model.mail.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ru.parser.annotation.mail.Administrator;
import ru.parser.annotation.mail.CriticalError;

public class CriticalErrorTest {

    @Test
    public void testCriticalError() {
        CriticalError error = new CriticalError();
        assertNotNull(error);
        assertEquals(0, error.getAdministrators().size());
        assertNull(error.getCode());
        assertNull(error.getDetails());
        assertNull(error.getId());
        assertNull(error.getMessage());
    }

    @Test
    public void testCriticalErrorStringStringString() {
        CriticalError error = new CriticalError("code", "message", "details");
        assertEquals(0, error.getAdministrators().size());
        assertEquals("code", error.getCode());
        assertEquals("details", error.getDetails());
        assertNull(error.getId());
        assertEquals("message", error.getMessage());
    }

    @Test
    public void testSetGet() {
        CriticalError error = new CriticalError();
        Set<Administrator> administrators = new HashSet<Administrator>();
        error.setAdministrators(administrators);
        error.setCode("code");
        error.setDetails("details");
        error.setId(5234L);
        error.setMessage("message");
        // --------------------------------------------
        assertEquals(administrators, error.getAdministrators());
        assertEquals("code", error.getCode());
        assertEquals("details", error.getDetails());
        assertEquals(Long.valueOf(5234), error.getId());
        assertEquals("message", error.getMessage());
    }

    @Test
    public void testAddRemoveUser() {
        CriticalError error = new CriticalError();
        Administrator user = new Administrator();
        error.addUser(user);
        assertEquals(1, error.getAdministrators().size());
        error.removeUser(user);
        assertEquals(0, error.getAdministrators().size());
    }
}
