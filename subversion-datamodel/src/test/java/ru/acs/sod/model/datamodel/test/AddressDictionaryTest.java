package ru.acs.sod.model.datamodel.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.dataloader.AddressDictionary;

public class AddressDictionaryTest {

    @Test
    public void testAddressDictionary() {
        AddressDictionary dictionary = new AddressDictionary();
        assertNotNull(dictionary);
        assertNull(dictionary.getId());
        assertNull(dictionary.getAddressLogical());
        assertNull(dictionary.getAddressPhysical());
        assertEquals("САЭД", dictionary.getCryptoType()); // по умолчанию проставлено значение "САЭД"
//        assertNull(dictionary.getCryptoType());
        assertFalse(dictionary.getDefaultAddress());
        assertFalse(dictionary.getDelayFlag());
        assertNull(dictionary.getUserKey());
    }

    @Test
    public void testSetGetMethods() {
        AddressDictionary dictionary = new AddressDictionary();
        dictionary.setAddressLogical("addressLogical");
        dictionary.setAddressPhysical("addressPhysical");
        dictionary.setCryptoType("cryptoType");
        dictionary.setDefaultAddress(true);
        dictionary.setDelayFlag(true);
        dictionary.setId(1L);
        dictionary.setUserKey("userKey");
        assertEquals("addressLogical", dictionary.getAddressLogical());
        assertEquals("addressPhysical", dictionary.getAddressPhysical());
        assertEquals("cryptoType", dictionary.getCryptoType());
        assertTrue(dictionary.getDefaultAddress());
        assertTrue(dictionary.getDelayFlag());
        assertEquals(1L, dictionary.getId().longValue());
        assertEquals("userKey", dictionary.getUserKey());
    }

}
