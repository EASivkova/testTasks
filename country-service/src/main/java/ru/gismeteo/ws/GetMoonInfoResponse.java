
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
 *         &lt;element name="GetMoonInfoResult" type="{http://ws.gismeteo.ru/}GetMoonInfoResult"/>
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
    "getMoonInfoResult"
})
@XmlRootElement(name = "GetMoonInfoResponse")
public class GetMoonInfoResponse {

    @XmlElement(name = "GetMoonInfoResult", required = true)
    protected GetMoonInfoResult getMoonInfoResult;

    /**
     * Gets the value of the getMoonInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetMoonInfoResult }
     *     
     */
    public GetMoonInfoResult getGetMoonInfoResult() {
        return getMoonInfoResult;
    }

    /**
     * Sets the value of the getMoonInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMoonInfoResult }
     *     
     */
    public void setGetMoonInfoResult(GetMoonInfoResult value) {
        this.getMoonInfoResult = value;
    }

}
