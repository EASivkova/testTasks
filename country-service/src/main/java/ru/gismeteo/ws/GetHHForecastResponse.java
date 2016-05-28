
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
 *         &lt;element name="GetHHForecastResult" type="{http://ws.gismeteo.ru/}HHForecastResult"/>
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
    "getHHForecastResult"
})
@XmlRootElement(name = "GetHHForecastResponse")
public class GetHHForecastResponse {

    @XmlElement(name = "GetHHForecastResult", required = true)
    protected HHForecastResult getHHForecastResult;

    /**
     * Gets the value of the getHHForecastResult property.
     * 
     * @return
     *     possible object is
     *     {@link HHForecastResult }
     *     
     */
    public HHForecastResult getGetHHForecastResult() {
        return getHHForecastResult;
    }

    /**
     * Sets the value of the getHHForecastResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link HHForecastResult }
     *     
     */
    public void setGetHHForecastResult(HHForecastResult value) {
        this.getHHForecastResult = value;
    }

}
