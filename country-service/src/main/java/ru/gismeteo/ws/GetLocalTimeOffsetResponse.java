
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
 *         &lt;element name="GetLocalTimeOffsetResult" type="{http://ws.gismeteo.ru/}GetLocalTimeOffsetResult"/>
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
    "getLocalTimeOffsetResult"
})
@XmlRootElement(name = "GetLocalTimeOffsetResponse")
public class GetLocalTimeOffsetResponse {

    @XmlElement(name = "GetLocalTimeOffsetResult", required = true)
    protected GetLocalTimeOffsetResult getLocalTimeOffsetResult;

    /**
     * Gets the value of the getLocalTimeOffsetResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetLocalTimeOffsetResult }
     *     
     */
    public GetLocalTimeOffsetResult getGetLocalTimeOffsetResult() {
        return getLocalTimeOffsetResult;
    }

    /**
     * Sets the value of the getLocalTimeOffsetResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLocalTimeOffsetResult }
     *     
     */
    public void setGetLocalTimeOffsetResult(GetLocalTimeOffsetResult value) {
        this.getLocalTimeOffsetResult = value;
    }

}
