
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
 *         &lt;element name="GetSunInfoResult" type="{http://ws.gismeteo.ru/}GetSunInfoResult"/>
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
    "getSunInfoResult"
})
@XmlRootElement(name = "GetSunInfoResponse")
public class GetSunInfoResponse {

    @XmlElement(name = "GetSunInfoResult", required = true)
    protected GetSunInfoResult getSunInfoResult;

    /**
     * Gets the value of the getSunInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSunInfoResult }
     *     
     */
    public GetSunInfoResult getGetSunInfoResult() {
        return getSunInfoResult;
    }

    /**
     * Sets the value of the getSunInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSunInfoResult }
     *     
     */
    public void setGetSunInfoResult(GetSunInfoResult value) {
        this.getSunInfoResult = value;
    }

}
