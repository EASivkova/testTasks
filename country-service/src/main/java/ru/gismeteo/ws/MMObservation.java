
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MMObservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MMObservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="obsTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="t" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="p" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cl" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dd" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ff" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MMObservation", propOrder = {
    "obsTime",
    "t",
    "p",
    "cl",
    "dd",
    "ff"
})
public class MMObservation {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar obsTime;
    protected double t;
    protected double p;
    protected int cl;
    protected double dd;
    protected double ff;

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

}
