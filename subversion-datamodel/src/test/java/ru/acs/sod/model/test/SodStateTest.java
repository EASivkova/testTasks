package ru.acs.sod.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.SodState;

public class SodStateTest {

    @Test
    public void testToString() {
        assertEquals("regular mode", SodState.REGULAR_MODE.toString());
        assertEquals("technological mode", SodState.TECH_MODE.toString());
    }

    @Test
    public void testGetState() {
        assertEquals(SodState.REGULAR_MODE, SodState.getState("regular mode"));
        assertEquals(SodState.TECH_MODE, SodState.getState("technological mode"));
        try {
            SodState.getState(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Не удалось определить режим работы по строковому представлению\"null\"", e.getMessage());
        }
    }
}
