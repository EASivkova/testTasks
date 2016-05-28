package ru.acs.sod.model.transformer.test;

import java.sql.SQLException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import ru.parser.dataloader.OSDContainer;
import ru.parser.transformer.ContainerTransformer;
import ru.parser.transformer.OSDToRegistrTransformer;
import ru.parser.utils.DateUtils;
import ru.parser.utils.RegistrItem;

public class OSDToRegistrTransformerTest {

    private ContainerTransformer<OSDContainer, RegistrItem> transformer = new OSDToRegistrTransformer();

    @Test
    public void testNullTransform() throws SQLException {
        Assert.assertNull(transformer.transform(null));
    }

    @Test
    public void testEmptyTransform() throws SQLException {
        OSDContainer container = new OSDContainer();
        RegistrItem item = transformer.transform(container);
        Assert.assertNull(item.getSigns().get(0));
        Assert.assertNull(item.getBik());
        Assert.assertNull(item.getData());
        Assert.assertNull(item.getDT());
        Assert.assertNull(item.getFileName());
        Assert.assertNull(item.getHeader());
        Assert.assertNull(item.getId());
        Assert.assertNull(item.getOKUD());
        Assert.assertNull(item.getRealBik());
        Assert.assertNull(item.getRegnum());
        Assert.assertNull(item.getRepDT());
        Assert.assertEquals(0, item.getSize().intValue());
        Assert.assertNull(item.getSodId());
        Assert.assertNull(item.getType());
    }

    @Test
    public void testFullTransform() throws SQLException {
        OSDContainer container = new OSDContainer();
        container.setOsdId(12L);
        container.setData("ВСЯКО".getBytes());
        Calendar repDT = Calendar.getInstance();
        container.setRepDT(repDT.getTime());
        container.setOkud("okud");
        container.setRegNum("regNum");
        container.setTypeES("typeES");
        container.setSodId(21L);
        RegistrItem item = transformer.transform(container);
        Assert.assertNull(item.getSigns().get(0));
        Assert.assertNull(item.getBik());
        Assert.assertEquals("ВСЯКО", new String(item.getData()));
        Assert
                .assertEquals(DateUtils.getStringDate(repDT.getTime(), "dd-MM-yyyy"), item
                        .getRepDT());
        Assert.assertNull(item.getFileName());
        Assert.assertNull(item.getHeader());
        Assert.assertEquals(12, item.getId().longValue());
        Assert.assertEquals("okud", item.getOKUD());
        Assert.assertNull(item.getRealBik());
        Assert.assertEquals("regNum", item.getRegnum());
        Assert.assertNull(item.getDT());
        Assert.assertEquals("ВСЯКО".getBytes().length, item.getSize().intValue());
        Assert.assertEquals(21, item.getSodId().longValue());
        Assert.assertEquals("typeES", item.getType());
    }
}
