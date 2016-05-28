
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
 *         &lt;element name="GetMMObservationResult" type="{http://ws.gismeteo.ru/}MMObservationResult"/>
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
    "getMMObservationResult"
})
@XmlRootElement(name = "GetMMObservationResponse")
public class GetMMObservationResponse {

    @XmlElement(name = "GetMMObservationResult", required = true)
    protected MMObservationResult getMMObservationResult;

    /**
     * Gets the value of the getMMObservationResult property.
     * 
     * @return
     *     possible object is
     *     {@link MMObservationResult }
     *     
     */
    public MMObservationResult getGetMMObservationResult() {
        return getMMObservationResult;
    }

    /**
     * Sets the value of the getMMObservationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MMObservationResult }
     *     
     */
    public void setGetMMObservationResult(MMObservationResult value) {
        this.getMMObservationResult = value;
    }

}
