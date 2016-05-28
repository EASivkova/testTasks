
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HHForecast complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HHForecast">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tod" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="t" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="p" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cl" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="prct" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dd" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ff" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="st" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="humidity" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HHForecast", propOrder = {
    "time",
    "tod",
    "t",
    "p",
    "cl",
    "prc",
    "prct",
    "dd",
    "ff",
    "st",
    "humidity"
})
public class HHForecast {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    protected int tod;
    protected double t;
    protected double p;
    protected int cl;
    protected double prc;
    protected int prct;
    protected double dd;
    protected double ff;
    protected int st;
    protected short humidity;

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
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
     * Gets the value of the prc property.
     * 
     */
    public double getPrc() {
        return prc;
    }

    /**
     * Sets the value of the prc property.
     * 
     */
    public void setPrc(double value) {
        this.prc = value;
    }

    /**
     * Gets the value of the prct property.
     * 
     */
    public int getPrct() {
        return prct;
    }

    /**
     * Sets the value of the prct property.
     * 
     */
    public void setPrct(int value) {
        this.prct = value;
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
     * Gets the value of the st property.
     * 
     */
    public int getSt() {
        return st;
    }

    /**
     * Sets the value of the st property.
     * 
     */
    public void setSt(int value) {
        this.st = value;
    }

    /**
     * Gets the value of the humidity property.
     * 
     */
    public short getHumidity() {
        return humidity;
    }

    /**
     * Sets the value of the humidity property.
     * 
     */
    public void setHumidity(short value) {
        this.humidity = value;
    }

}
