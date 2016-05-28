package ru.acs.sod.model.datamodel.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.parser.dataloader.AddressDictionary;
import ru.parser.dataloader.OSDContainer;
import ru.parser.utils.ESValidity;

public class OSDContainerTest {

    @Test
    public void testOSDContainer() {
        OSDContainer container = new OSDContainer();
        assertNotNull(container);
        assertNull(container.getBlockId());
        assertNull(container.getData());
        assertNull(container.getDateTimeReg());
        assertNotNull(container.getDtExe());
        assertNull(container.getEmail());
        assertNull(container.getEncryptType());
        assertNull(container.getFormat());
        assertNull(container.getGuid());
        assertNull(container.getKaData());
        assertNull(container.getOsdId());
        assertNull(container.getPresentedIn());
        assertNull(container.getPresentedWay());
        assertNull(container.getRecivers());
        assertNull(container.getSender());
        assertNull(container.getSenderFiz());
        assertNull(container.getSodId());
        assertNull(container.getTypeES());
        assertNull(container.getValid());
    }

    @Test
    public void testOSDContainerLongLongByteArray() {
        OSDContainer container = new OSDContainer(12L, 43L, "НОВЫЕ ДАННЫЕ".getBytes());
        assertNotNull(container);
        assertNull(container.getBlockId());
        assertArrayEquals("НОВЫЕ ДАННЫЕ".getBytes(), container.getData());
        assertNull(container.getDateTimeReg());
        assertNotNull(container.getDtExe());
        assertNull(container.getEmail());
        assertNull(container.getEncryptType());
        assertNull(container.getFormat());
        assertNull(container.getGuid());
        assertNull(container.getKaData());
        assertEquals(Long.valueOf(43), container.getOsdId());
        assertNull(container.getPresentedIn());
        assertNull(container.getPresentedWay());
        assertNull(container.getRecivers());
        assertNull(container.getSender());
        assertNull(container.getSenderFiz());
        assertEquals(Long.valueOf(12), container.getSodId());
        assertNull(container.getTypeES());
        assertNull(container.getValid());
    }

    @Test
    public void testSetGet() {
        OSDContainer container = new OSDContainer();
        Calendar dateTimeReg = Calendar.getInstance();
        Calendar dtExe = Calendar.getInstance();
        Map<AddressDictionary, String> recivers = new HashMap<AddressDictionary, String>();
        AddressDictionary addressDictionary = new AddressDictionary();
        addressDictionary.setAddressLogical("addressLogical");
        recivers.put(addressDictionary, "ОтчетностьКО");
        container.setBlockId(2);
        container.setData("ДАННЫЕ".getBytes());
        container.setDateTimeReg(dateTimeReg.getTime());
        container.setDtExe(dtExe.getTime());
        container.setEmail("email");
        container.setEncryptType("encryptType");
        container.setFormat("formst");
        container.setGuid("guid");
        container.setKaData("kaData");
        container.setOsdId(11112L);
        container.setPresentedIn("presentedIn");
        container.setPresentedWay("presentedWay");
        container.setSender("sender");
        container.setSenderFiz("senderFiz");
        container.setSodId(123321L);
        container.setTypeES("typeES");
        container.setValid(ESValidity.VALID);
        container.setRecivers(recivers);
        // ------------------------------------------
        assertEquals(Integer.valueOf(2), container.getBlockId());
        assertArrayEquals("ДАННЫЕ".getBytes(), container.getData());
        assertEquals(dateTimeReg.getTime(), container.getDateTimeReg());
        assertEquals(dtExe.getTime(), container.getDtExe());
        assertEquals("email", container.getEmail());
        assertEquals("encryptType", container.getEncryptType());
        assertEquals("formst", container.getFormat());
        assertEquals("guid", container.getGuid());
        assertEquals("kaData", container.getKaData());
        assertEquals(Long.valueOf(11112), container.getOsdId());
        assertEquals("presentedIn", container.getPresentedIn());
        assertEquals("presentedWay", container.getPresentedWay());
        assertEquals("sender", container.getSender());
        assertEquals("senderFiz", container.getSenderFiz());
        assertEquals(Long.valueOf(123321), container.getSodId());
        assertEquals("typeES", container.getTypeES());
        assertEquals(ESValidity.VALID, container.getValid());
        assertEquals(recivers, container.getRecivers());
    }

    @Test
    public void testAddReceiver() {
        OSDContainer container = new OSDContainer();
        AddressDictionary addressDictionary = new AddressDictionary();
        addressDictionary.setAddressPhysical("fizAddress");
        container.addReciver(addressDictionary, "ОтчетностьКО");
        assertEquals(1, container.getRecivers().size());
        assertEquals("fizAddress", container.getRecivers().keySet().iterator().next().getAddressPhysical());
        container.setDateTimeReg(null);
        assertNull(container.getDateTimeReg());
    }
}
