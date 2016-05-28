package ru.acs.sod.model.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.parser.utils.TypeES;

public class TypeESTest {

    @Test
    public void testGetText() {
        assertEquals("ОЭС", TypeES.OES.getText());
        assertEquals("ИЭС1", TypeES.IES1.getText());
        assertEquals("ИЭС2", TypeES.IES2.getText());
        assertEquals("КА", TypeES.KA.getText());
    }

    @Test
    public void testGetType() {
        assertEquals(TypeES.OES, TypeES.getType("ОЭС"));
        assertEquals(TypeES.IES1, TypeES.getType("ИЭС1"));
        assertEquals(TypeES.IES2, TypeES.getType("ИЭС2"));
        assertEquals(TypeES.KA, TypeES.getType("КА"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongType() {
        TypeES.getType("QWE");
    }
}
