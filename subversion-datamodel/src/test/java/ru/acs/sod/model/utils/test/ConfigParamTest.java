package ru.acs.sod.model.utils.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.utils.ConfigParam;

public class ConfigParamTest {

    @Test
    public void testConfigParam() {
        ConfigParam param = new ConfigParam();
        assertNotNull(param);
        assertNull(param.getKey());
        assertNull(param.getValue());
    }

    @Test
    public void testConfigParamString() {
        ConfigParam param = new ConfigParam("key", "value");
        assertNotNull(param);
        assertEquals("key", param.getKey());
        assertEquals("value", param.getValue());
    }

    @Test
    public void testSetGet() {
        ConfigParam param = new ConfigParam();
        param.setKey("key1");
        param.setValue("value1");
        assertEquals("key1", param.getKey());
        assertEquals("value1", param.getValue());
    }
}
