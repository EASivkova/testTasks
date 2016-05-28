package ru.acs.sod.model.utils.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ru.parser.utils.RegistrItem;
import ru.parser.utils.RegistrItemSign;

public class RegistrItemTest {

    @Test
    public void testSetGet() {
        byte[] base64Sign = new byte[] {
                12, 21, 32, 1, 53, 42, 32
        };
        Date dT = new Date();
        Calendar repDT = Calendar.getInstance();
        RegistrItem item = new RegistrItem();
        RegistrItemSign sign = new RegistrItemSign();
        sign.setBase64Sign(base64Sign);
        item.getSigns().add(sign);
        item.setBik("bik");
        item.setData("ТЕКСТ".getBytes());
        // test put java.util.Date
        item.setDT(dT);
        item.setFileName("fileName");
        item.setHeader("header");
        item.setId(23L);
        // test put Integer
        item.getSigns().get(0).setKA(0);
        assertEquals(Integer.valueOf(0), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA(1);
        assertEquals(Integer.valueOf(1), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA(2);
        assertEquals(Integer.valueOf(2), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA(3);
        assertEquals(Integer.valueOf(3), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA(4);
        assertEquals(Integer.valueOf(4), item.getSigns().get(0).getKA());
        try {
        	item.getSigns().get(0).setKA(5);
        } catch (IllegalArgumentException e) {
            assertEquals("Нет описания для крипто системы: 5", e.getMessage());
        }
        item.getSigns().get(0).setKA(6);
        assertEquals(Integer.valueOf(6), item.getSigns().get(0).getKA());
        item.setOKUD("oKUD");
        item.setRealBik("realBik");
        item.setRegnum("regnum");
        // test put java.util.Date
        item.setRepDT(repDT);
        item.setSize(13241);
        item.setSodId(123123L);
        item.setType("type");
        // ----------------------------------------------
        assertArrayEquals(base64Sign, item.getSigns().get(0).getBase64Sign());
        assertEquals("bik", item.getBik());
        assertArrayEquals("ТЕКСТ".getBytes(), item.getData());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss");
        //df.setTimeZone(TimeZone.getTimeZone("GMT+0000"));
        assertEquals(df.format(dT), item.getDT());
        assertEquals("fileName", item.getFileName());
        assertEquals("header", item.getHeader());
        assertEquals(Long.valueOf(23), item.getId());
        assertEquals("oKUD", item.getOKUD());
        assertEquals("realBik", item.getRealBik());
        assertEquals("regnum", item.getRegnum());
        assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(repDT.getTime()), item.getRepDT());
        assertEquals(Integer.valueOf(13241), item.getSize());
        assertEquals(Long.valueOf(123123), item.getSodId());
        assertEquals("type", item.getType());
        // ---------------------------------
        // test put String
        item.setDT("11-11-2012 22:34:43");
        assertEquals("11-11-2012 22:34:43", item.getDT());
        item.getSigns().get(0).setKA("САЭД");
        assertEquals(Integer.valueOf(6), item.getSigns().get(0).getKA());
        try {
        	item.getSigns().get(0).setKA("САЭД!");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Нет описания для крипто системы: САЭД!", e.getMessage());
        }

        item.getSigns().get(0).setKA("Сигнатура");
        assertEquals(Integer.valueOf(4), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA("КриптоМенеджер");
        assertEquals(Integer.valueOf(3), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA("TSign");
        assertEquals(Integer.valueOf(2), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA("Верба");
        assertEquals(Integer.valueOf(1), item.getSigns().get(0).getKA());
        item.getSigns().get(0).setKA("Без КА");
        assertEquals(Integer.valueOf(0), item.getSigns().get(0).getKA());
        item.setRepDT("21-11-2012 21:34:43");
        assertEquals("21-11-2012 21:34:43", item.getRepDT());
    }

}
