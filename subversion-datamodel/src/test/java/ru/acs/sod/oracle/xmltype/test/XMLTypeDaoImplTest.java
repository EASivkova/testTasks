package ru.acs.sod.oracle.xmltype.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(value = "classpath:testHibernateDataSource.xml")
public class XMLTypeDaoImplTest {

    @Autowired
    public XMLTypeDao dao;

    @Test
    public void testSaveGetNullXML() {
        Long id = dao.addXMLTable(new XMLTable());
        assertNotNull(id);
        XMLTable tableFromDB = dao.getXMLTableById(id);
        assertEquals(id, tableFromDB.getId());
        assertNull(tableFromDB.getXmlData());
    }

    @Test
    public void testSaveGetEmptyXML() {
        Long id = dao.addXMLTable(new XMLTable("".getBytes()));
        assertNotNull(id);
        XMLTable tableFromDB = dao.getXMLTableById(id);
        assertEquals(id, tableFromDB.getId());
        assertEquals("", new String(tableFromDB.getXmlData()));
    }

    @Test
    public void testAddGetXMLTable() {
        Long id = dao.addXMLTable(new XMLTable("<one><two/></one>".getBytes()));
        assertNotNull(id);
        XMLTable tableFromDB = dao.getXMLTableById(id);
        assertEquals(id, tableFromDB.getId());
        assertArrayEquals("<one><two/></one>".getBytes(), tableFromDB.getXmlData());
    }
}
