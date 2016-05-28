package ru.country.info.xml;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XMLUtils {

    private static final Logger LOG = Logger.getLogger(XMLUtils.class);

    private XMLUtils() {
        super();
    }

    /**
     * Метод преобразует DOM ноду в строку содержащую XML.
     * @param node
     *        исходная DOM нода содержащая XML
     * @return строка содержащая текстовое представление XML или сообщение об
     *         ошибке парсинга
     */
    public static String domToString(final Element node) {
        try {
            StringWriter stw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(node), new StreamResult(stw));
            return stw.toString();
        } catch (TransformerException e) {
            LOG.error("invalid xml message", e);
            return "XML Unreadable. Cause " + e.getMessage();
        } catch (Exception e) {
            LOG.error("XML API problem", e);
            return "XML transform API problem. Cause " + e.getMessage();
        }
    }

    /**
     * Метод преобразует DOM ноду в строку содержащую XML.
     * @param node
     *        исходная DOM нода содержащая XML
     * @return строка содержащая текстовое представление XML или сообщение об
     *         ошибке парсинга
     */
    public static String nodeToString(final Node node) {
        try {
            StringWriter stw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(node), new StreamResult(stw));
            return stw.toString();
        } catch (TransformerException e) {
            LOG.error("invalid xml message", e);
            return "XML Unreadable. Cause " + e.getMessage();
        } catch (Exception e) {
            LOG.error("XML API problem", e);
            return "XML transform API problem. Cause " + e.getMessage();
        }
    }

}