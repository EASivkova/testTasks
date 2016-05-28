
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetMoonInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetMoonInfoResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="moonPhase" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="wax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "GetMoonInfoResult", propOrder = {
    "moonPhase",
    "wax",
    "result"
})
public class GetMoonInfoResult {

    protected float moonPhase;
    protected boolean wax;
    @XmlElement(required = true)
    protected ServiceResult result;

    /**
     * Gets the value of the moonPhase property.
     * 
     */
    public float getMoonPhase() {
        return moonPhase;
    }

    /**
     * Sets the value of the moonPhase property.
     * 
     */
    public void setMoonPhase(float value) {
        this.moonPhase = value;
    }

    /**
     * Gets the value of the wax property.
     * 
     */
    public boolean isWax() {
        return wax;
    }

    /**
     * Sets the value of the wax property.
     * 
     */
    public void setWax(boolean value) {
        this.wax = value;
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
