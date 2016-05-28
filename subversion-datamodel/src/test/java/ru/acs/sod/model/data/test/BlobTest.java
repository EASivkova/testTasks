package ru.acs.sod.model.data.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.parser.Blob;

public class BlobTest {

    private static final byte[] BYTES = new byte[] {
            15, 123, 112, 12, 54
    };
    private static final long ID = 12L;

    @Test
    public void testBlob() {
        Blob b = new Blob();
        assertNotNull(b);
        assertNull(b.getId());
        assertNull(b.getData());
    }

    @Test
    public void testBlobByteArray() {

        Blob b = new Blob(BYTES);
        assertNotNull(b);
        assertNull(b.getId());
        assertNotNull(b.getData());
        assertArrayEquals(BYTES, b.getData());
    }

    @Test
    public void testSetData() {
        Blob b = new Blob();
        b.setId(ID);
        b.setData(BYTES);
        assertEquals(ID, b.getId().longValue());
        assertArrayEquals(BYTES, b.getData());
    }

    @Test
    public void testEqualsObject() {
        Blob b1 = new Blob();
        Blob b2 = new Blob();
        b2.setId(ID);
        assertFalse(b2.equals(b1));
        assertFalse(b1.equals(b2));
        Blob b3 = new Blob();
        b3.setId(ID);
        assertTrue(b2.equals(b3));
    }

    @Test
    public void testIsEmpty() {
        Blob b = new Blob();
        assertTrue(b.isEmpty());
        b.setData(new byte[0]);
        assertTrue(b.isEmpty());
        b.setData(BYTES);
        assertFalse(b.isEmpty());
    }
}
