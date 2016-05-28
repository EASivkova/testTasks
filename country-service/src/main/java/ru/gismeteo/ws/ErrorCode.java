
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="BadSerial"/>
 *     &lt;enumeration value="WrongInputData"/>
 *     &lt;enumeration value="WrongLanguageProvided"/>
 *     &lt;enumeration value="DataNotFound"/>
 *     &lt;enumeration value="InternalError"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorCode")
@XmlEnum
public enum ErrorCode {

    OK("OK"),
    @XmlEnumValue("BadSerial")
    BAD_SERIAL("BadSerial"),
    @XmlEnumValue("WrongInputData")
    WRONG_INPUT_DATA("WrongInputData"),
    @XmlEnumValue("WrongLanguageProvided")
    WRONG_LANGUAGE_PROVIDED("WrongLanguageProvided"),
    @XmlEnumValue("DataNotFound")
    DATA_NOT_FOUND("DataNotFound"),
    @XmlEnumValue("InternalError")
    INTERNAL_ERROR("InternalError");
    private final String value;

    ErrorCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorCode fromValue(String v) {
        for (ErrorCode c: ErrorCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
