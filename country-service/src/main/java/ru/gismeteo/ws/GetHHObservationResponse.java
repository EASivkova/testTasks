
package ru.gismeteo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetHHObservationResult" type="{http://ws.gismeteo.ru/}HHObservationResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getHHObservationResult"
})
@XmlRootElement(name = "GetHHObservationResponse")
public class GetHHObservationResponse {

    @XmlElement(name = "GetHHObservationResult", required = true)
    protected HHObservationResult getHHObservationResult;

    /**
     * Gets the value of the getHHObservationResult property.
     * 
     * @return
     *     possible object is
     *     {@link HHObservationResult }
     *     
     */
    public HHObservationResult getGetHHObservationResult() {
        return getHHObservationResult;
    }

    /**
     * Sets the value of the getHHObservationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link HHObservationResult }
     *     
     */
    public void setGetHHObservationResult(HHObservationResult value) {
        this.getHHObservationResult = value;
    }

}
