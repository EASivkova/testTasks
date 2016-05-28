package ru.acs.sod.model.env.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import ru.parser.quitance.QuitanceHeader;

public class QuitanceHeaderTest {

    private static final Date CURRENT_DATE = new Date();

    @Test
    public void testQuitanceHeader() {
        QuitanceHeader header = new QuitanceHeader();
        assertNotNull(header);
        assertNull(header.getDateTimeResived());
        assertNull(header.getFile());
        assertNull(header.getGuidTK());
    }

    @Test
    public void testSetGet() {
        QuitanceHeader header = new QuitanceHeader();
        header.setDateTimeResived(CURRENT_DATE);
        header.setFile("file");
        header.setGuidTK("guidTK");
        assertEquals(CURRENT_DATE, header.getDateTimeResived());
        assertEquals("file", header.getFile());
        assertEquals("guidTK", header.getGuidTK());
    }
}
