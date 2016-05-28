package ru.acs.sod.model.exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.acs.sod.model.exceptions.TechEnvProcessorException;
import ru.acs.sod.model.exceptions.UnknownSodStateException;

public class ExceptionTest {

    @Test
    public void testTechEnvProcessorException() {
        assertNotNull(new TechEnvProcessorException());
        assertNotNull(new TechEnvProcessorException("except"));
        assertNotNull(new TechEnvProcessorException(new Exception("eeee")));
        assertNotNull(new TechEnvProcessorException("ex", new Exception("wwww")));
    }

    @Test
    public void testUnknownSodStateException() {
        assertNotNull(new UnknownSodStateException());
        assertNotNull(new UnknownSodStateException("except"));
        assertNotNull(new UnknownSodStateException(new Exception("eeee")));
        assertNotNull(new UnknownSodStateException("ex", new Exception("wwww")));
    }
}
