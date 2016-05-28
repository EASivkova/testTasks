
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GetSunInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetSunInfoResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="riseMinutesOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="setMinutesOffset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="durationMinutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="result" type="{http://ws.gismeteo.ru/}ServiceResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetSunInfoResult", propOrder = {
    "dt",
    "riseMinutesOffset",
    "setMinutesOffset",
    "durationMinutes",
    "result"
})
public class GetSunInfoResult {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dt;
    protected int riseMinutesOffset;
    protected int setMinutesOffset;
    protected int durationMinutes;
    @XmlElement(required = true)
    protected ServiceResult result;

    /**
     * Gets the value of the dt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Gets the value of the riseMinutesOffset property.
     * 
     */
    public int getRiseMinutesOffset() {
        return riseMinutesOffset;
    }

    /**
     * Sets the value of the riseMinutesOffset property.
     * 
     */
    public void setRiseMinutesOffset(int value) {
        this.riseMinutesOffset = value;
    }

    /**
     * Gets the value of the setMinutesOffset property.
     * 
     */
    public int getSetMinutesOffset() {
        return setMinutesOffset;
    }

    /**
     * Sets the value of the setMinutesOffset property.
     * 
     */
    public void setSetMinutesOffset(int value) {
        this.setMinutesOffset = value;
    }

    /**
     * Gets the value of the durationMinutes property.
     * 
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the value of the durationMinutes property.
     * 
     */
    public void setDurationMinutes(int value) {
        this.durationMinutes = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceResult }
     *     
     */
    public ServiceResult getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceResult }
     *     
     */
    public void setResult(ServiceResult value) {
        this.result = value;
    }

}
