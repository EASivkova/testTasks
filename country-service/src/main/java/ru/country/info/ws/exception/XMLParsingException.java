package ru.country.info.ws.exception;

import org.springframework.xml.XmlException;

public class XMLParsingException extends XmlException {

    private static final long serialVersionUID = 1L;

    public XMLParsingException(final String message) {
        super(message);
    }

    public XMLParsingException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
