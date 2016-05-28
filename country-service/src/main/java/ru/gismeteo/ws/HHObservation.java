
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HHObservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HHObservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="obsTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tod" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="t" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="p" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cl" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="humidity" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dd" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ff" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ph" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HHObservation", propOrder = {
    "obsTime",
    "tod",
    "t",
    "p",
    "cl",
    "humidity",
    "dd",
    "ff",
    "ph"
})
public class HHObservation {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar obsTime;
    protected int tod;
    protected double t;
    protected double p;
    protected int cl;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double humidity;
    protected double dd;
    protected double ff;
    protected int ph;

    /**
     * Gets the value of the obsTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getObsTime() {
        return obsTime;
    }

    /**
     * Sets the value of the obsTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setObsTime(XMLGregorianCalendar value) {
        this.obsTime = value;
    }

    /**
     * Gets the value of the tod property.
     * 
     */
    public int getTod() {
        return tod;
    }

    /**
     * Sets the value of the tod property.
     * 
     */
    public void setTod(int value) {
        this.tod = value;
    }

    /**
     * Gets the value of the t property.
     * 
     */
    public double getT() {
        return t;
    }

    /**
     * Sets the value of the t property.
     * 
     */
    public void setT(double value) {
        this.t = value;
    }

    /**
     * Gets the value of the p property.
     * 
     */
    public double getP() {
        return p;
    }

    /**
     * Sets the value of the p property.
     * 
     */
    public void setP(double value) {
        this.p = value;
    }

    /**
     * Gets the value of the cl property.
     * 
     */
    public int getCl() {
        return cl;
    }

    /**
     * Sets the value of the cl property.
     * 
     */
    public void setCl(int value) {
        this.cl = value;
    }

    /**
     * Gets the value of the humidity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * Sets the value of the humidity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHumidity(Double value) {
        this.humidity = value;
    }

    /**
     * Gets the value of the dd property.
     * 
     */
    public double getDd() {
        return dd;
    }

    /**
     * Sets the value of the dd property.
     * 
     */
    public void setDd(double value) {
        this.dd = value;
    }

    /**
     * Gets the value of the ff property.
     * 
     */
    public double getFf() {
        return ff;
    }

    /**
     * Sets the value of the ff property.
     * 
     */
    public void setFf(double value) {
        this.ff = value;
    }

    /**
     * Gets the value of the ph property.
     * 
     */
    public int getPh() {
        return ph;
    }

    /**
     * Sets the value of the ph property.
     * 
     */
    public void setPh(int value) {
        this.ph = value;
    }

}
