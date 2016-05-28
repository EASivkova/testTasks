package ru.acs.sod.model.transformer.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.mockrunner.mock.jdbc.MockBlob;

import ru.parser.BigBlob;
import ru.parser.data.DataBlock;
import ru.parser.data.DataBlockHeader;
import ru.parser.dataloader.OSDContainer;
import ru.parser.env.TechEnvHeader;
import ru.parser.env.TechEnvelope;
import ru.parser.ta.TaEnvelope;
import ru.parser.ta.TaHeader;
import ru.parser.transformer.ContainerTransformer;
import ru.parser.transformer.DataBlockToOSDTransformer;
import ru.parser.utils.DirectionType;
import ru.parser.utils.ESValidity;

public class DataBlockToOSDTransformerTest {

    private ContainerTransformer<DataBlock, OSDContainer> blockTransformer = new DataBlockToOSDTransformer();

    @Test
    public void testSaveFormNullBlock() throws SQLException {
        try {
            blockTransformer.transform(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Пустой блок данных", e.getMessage());
        }
    }

    @Test
    public void testSaveFormFullEmptyBlock() throws SQLException {
        try {
            blockTransformer.transform(new DataBlock());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("В блоке данных отсутствует заголовок", e.getMessage());
        }
    }

    @Test
    public void testSaveFormEmptyBlockWithHeader() throws SQLException {
        try {
            DataBlock input = new DataBlock();
            input.setHeader(new DataBlockHeader());
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует блок данных в форме", e.getMessage());
        }
    }

    @Test
    public void testSaveFormEmptyBlockWithData() throws SQLException {
        try {
            DataBlock input = new DataBlock();
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует идентификатор формы", e.getMessage());
        }
    }

    @Test
    public void testSaveFormEmptyBlockWithDataAndId() throws SQLException {
        try {
            DataBlock input = new DataBlock();
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует отправитель", e.getMessage());
        }
    }

    @Test
    public void testSaveFormBlockTKNoHeader() throws SQLException {
        try {
            TechEnvelope inputTK = new TechEnvelope();
            inputTK.setDirection(DirectionType.INPUT);
            DataBlock input = new DataBlock();
            input.setDirection(DirectionType.INPUT);
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            input.getEnvelopTK().add(inputTK);
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует заголовок технологического конверта текущего блока данных", e.getMessage());
        }
    }

    @Test
    public void testSaveFormBlockWithAddressWrongTK() throws SQLException {
        try {
            TechEnvelope inputTK = new TechEnvelope();
            inputTK.setDirection(DirectionType.INPUT);
            TaEnvelope env = new TaEnvelope();
            inputTK.setTaEnvelope(env);
            TaHeader header = new TaHeader();
            env.setHeader(header);
            header.setSenderReciver("ADDRESS");
            DataBlock input = new DataBlock();
            input.setDirection(DirectionType.INPUT);
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            input.getEnvelopTK().add(inputTK);
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует заголовок технологического конверта текущего блока данных", e.getMessage());
        }
    }

    @Test
    public void testSaveFormBlockWithAddress() throws SQLException {
        try {
            TechEnvelope inputTK = new TechEnvelope();
            inputTK.setDirection(DirectionType.INPUT);
            TaEnvelope env = new TaEnvelope();
            inputTK.setTaEnvelope(env);
            TaHeader header = new TaHeader();
            env.setHeader(header);
            header.setSenderReciver("ADDRESS");
            inputTK.setHeader(new TechEnvHeader());
            DataBlock input = new DataBlock();
            input.setDirection(DirectionType.INPUT);
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            input.getEnvelopTK().add(inputTK);
            blockTransformer.transform(input);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует тип сохраняемого блока", e.getMessage());
        }
    }

    @Test
    public void testTransformInput() throws SQLException {
        TechEnvelope inputTK = new TechEnvelope();
        inputTK.setDirection(DirectionType.INPUT);
        TaEnvelope env = new TaEnvelope();
        inputTK.setTaEnvelope(env);
        TaHeader header = new TaHeader();
        env.setHeader(header);
        header.setSenderReciver("ADDRESS");
        inputTK.setHeader(new TechEnvHeader());
        DataBlock input = new DataBlock();
        input.setDirection(DirectionType.INPUT);
        DataBlockHeader blockHeader = new DataBlockHeader();
        blockHeader.setIncludes("UYT");
        input.setHeader(blockHeader);
        byte[] data = new byte[] {
                123, 32, 2, 43, 53
        };
        BigBlob bb = new BigBlob();
        bb.setData(new MockBlob(new byte[] {
                123, 32, 2, 43, 53
        }));
        input.setDecryptedData(bb);
        input.setId(1234L);
        input.getEnvelopTK().add(inputTK);
        OSDContainer container = blockTransformer.transform(input);
        assertEquals(0, container.getBlockId().intValue());
        assertArrayEquals(data, container.getData());
        assertNotNull(container.getDateTimeReg());
        assertNotNull(container.getDtExe());
        assertNull(container.getEmail());
        assertNull(container.getEncryptType());
        assertNull(container.getFormat());
        assertNull(container.getGuid());
        assertNull(container.getKaData());
        assertNull(container.getLifeCycle());
        assertNull(container.getOkud());
        assertNull(container.getOsdId());
        assertNull(container.getPresentedIn());
        assertNull(container.getPresentedWay());
        assertNull(container.getProcessToSODDate());
        assertNull(container.getRecivers());
        assertNull(container.getRegNum());
        assertNull(container.getRepDT());
        assertNull(container.getSender());
        assertEquals("ADDRESS", container.getSenderFiz());
        assertEquals(1234, container.getSodId().longValue());
        assertNotNull(container.getTkCreateDate());
        assertEquals("UYT", container.getTypeES());
        assertEquals(ESValidity.INVALID, container.getValid());
    }

    @Test
    public void testEmptyOutBlock() throws SQLException {
        try {
            DataBlock outBlock = new DataBlock();
            outBlock.setDirection(DirectionType.OUTPUT);
            blockTransformer.transform(outBlock);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует связанный входяший блок данных", e.getMessage());
        }
    }

    @Test
    public void testOutBlockNoData() throws SQLException {
        try {
            DataBlock outBlock = new DataBlock();
            outBlock.setDirection(DirectionType.OUTPUT);
            TechEnvelope inputTK = new TechEnvelope();
            inputTK.setDirection(DirectionType.INPUT);
            TaEnvelope env = new TaEnvelope();
            env.setDirection(DirectionType.INPUT);
            inputTK.setTaEnvelope(env);
            TaHeader header = new TaHeader();
            env.setHeader(header);
            header.setSenderReciver("ADDRESS");
            inputTK.setHeader(new TechEnvHeader());
            DataBlock input = new DataBlock();
            input.setDirection(DirectionType.INPUT);
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            input.getEnvelopTK().add(inputTK);
            outBlock.setParentBlock(input);
            blockTransformer.transform(outBlock);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует блок данных в форме", e.getMessage());
        }
    }

    @Test
    public void testOutBlockNoType() throws SQLException {
        try {
            DataBlock outBlock = new DataBlock();
            outBlock.setDirection(DirectionType.OUTPUT);
            byte[] data = new byte[] {
                    112, 32, 45, 65, 86
            };
            BigBlob b2 = new BigBlob();
            b2.setData(new MockBlob(data));
            outBlock.setDecryptedData(b2);
            TechEnvelope inputTK = new TechEnvelope();
            inputTK.setDirection(DirectionType.INPUT);
            TaEnvelope env = new TaEnvelope();
            env.setDirection(DirectionType.INPUT);
            inputTK.setTaEnvelope(env);
            TaHeader header = new TaHeader();
            env.setHeader(header);
            header.setSenderReciver("ADDRESS");
            inputTK.setHeader(new TechEnvHeader());
            DataBlock input = new DataBlock();
            input.setDirection(DirectionType.INPUT);
            input.setHeader(new DataBlockHeader());
            BigBlob bb = new BigBlob();
            bb.setData(new MockBlob(new byte[] {
                    123, 32, 2, 43, 53
            }));
            input.setDecryptedData(bb);
            input.setId(1234L);
            input.getEnvelopTK().add(inputTK);
            outBlock.setParentBlock(input);
            blockTransformer.transform(outBlock);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Отсутствует тип сохраняемого блока", e.getMessage());
        }
    }

    @Test
    public void testOutBlock() throws SQLException {
        DataBlock outBlock = new DataBlock();
        outBlock.setDirection(DirectionType.OUTPUT);
        DataBlockHeader blockHeader = new DataBlockHeader();
        blockHeader.setIncludes("ИЭС2");
        outBlock.setHeader(blockHeader);
        byte[] data = new byte[] {
                112, 32, 45, 65, 86
        };
        BigBlob b2 = new BigBlob();
        b2.setData(new MockBlob(data));
        outBlock.setDecryptedData(b2);
        TechEnvelope inputTK = new TechEnvelope();
        inputTK.setDirection(DirectionType.INPUT);
        TaEnvelope env = new TaEnvelope();
        env.setDirection(DirectionType.INPUT);
        inputTK.setTaEnvelope(env);
        TaHeader header = new TaHeader();
        env.setHeader(header);
        header.setSenderReciver("ADDRESS");
        inputTK.setHeader(new TechEnvHeader());
        DataBlock input = new DataBlock();
        input.setDirection(DirectionType.INPUT);
        input.setHeader(new DataBlockHeader());
        BigBlob bb = new BigBlob();
        bb.setData(new MockBlob(new byte[] {
                123, 32, 2, 43, 53
        }));
        input.setDecryptedData(bb);
        input.setId(1234L);
        input.getEnvelopTK().add(inputTK);
        outBlock.setParentBlock(input);
        OSDContainer container = blockTransformer.transform(outBlock);
        assertEquals(0, container.getBlockId().intValue());
        assertArrayEquals(data, container.getData());
        assertNotNull(container.getDateTimeReg());
        assertNotNull(container.getDtExe());
        assertNull(container.getEmail());
        assertNull(container.getEncryptType());
        assertNull(container.getFormat());
        assertNull(container.getGuid());
        assertNull(container.getKaData());
        assertNull(container.getLifeCycle());
        assertNull(container.getOkud());
        assertNull(container.getOsdId());
        assertNull(container.getPresentedIn());
        assertNull(container.getPresentedWay());
        assertNull(container.getProcessToSODDate());
        assertNull(container.getRecivers());
        assertNull(container.getRegNum());
        assertNull(container.getRepDT());
        assertNull(container.getSender());
        assertEquals("ADDRESS", container.getSenderFiz());
        assertEquals(1234, container.getSodId().longValue());
        assertNotNull(container.getTkCreateDate());
        assertEquals("ИЭС2", container.getTypeES());
        assertEquals(ESValidity.INVALID, container.getValid());
    }
}
