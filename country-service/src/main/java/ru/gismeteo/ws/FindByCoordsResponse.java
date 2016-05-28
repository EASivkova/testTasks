
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
 *         &lt;element name="FindByCoordsResult" type="{http://ws.gismeteo.ru/}LocationInfoShortResult"/>
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
    "findByCoordsResult"
})
@XmlRootElement(name = "FindByCoordsResponse")
public class FindByCoordsResponse {

    @XmlElement(name = "FindByCoordsResult", required = true)
    protected LocationInfoShortResult findByCoordsResult;

    /**
     * Gets the value of the findByCoordsResult property.
     * 
     * @return
     *     possible object is
     *     {@link LocationInfoShortResult }
     *     
     */
    public LocationInfoShortResult getFindByCoordsResult() {
        return findByCoordsResult;
    }

    /**
     * Sets the value of the findByCoordsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationInfoShortResult }
     *     
     */
    public void setFindByCoordsResult(LocationInfoShortResult value) {
        this.findByCoordsResult = value;
    }

}
