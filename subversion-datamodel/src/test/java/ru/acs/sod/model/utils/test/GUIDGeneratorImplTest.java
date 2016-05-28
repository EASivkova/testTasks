package ru.acs.sod.model.utils.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ru.parser.utils.GUIDGenerator;
import ru.parser.utils.GUIDGeneratorImpl;

public class GUIDGeneratorImplTest {

    @Test
    public void testGenerate() {
        GUIDGenerator generator = new GUIDGeneratorImpl();
        Set<String> unicGuid = new HashSet<String>();
        for (int i = 0; i < 100000; i++) {
            unicGuid.add(generator.generate());
            assertEquals(i + 1, unicGuid.size());
        }
    }

}
