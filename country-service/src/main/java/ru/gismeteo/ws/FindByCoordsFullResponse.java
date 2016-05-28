
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
 *         &lt;element name="FindByCoordsFullResult" type="{http://ws.gismeteo.ru/}LocationInfoFullResult"/>
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
    "findByCoordsFullResult"
})
@XmlRootElement(name = "FindByCoordsFullResponse")
public class FindByCoordsFullResponse {

    @XmlElement(name = "FindByCoordsFullResult", required = true)
    protected LocationInfoFullResult findByCoordsFullResult;

    /**
     * Gets the value of the findByCoordsFullResult property.
     * 
     * @return
     *     possible object is
     *     {@link LocationInfoFullResult }
     *     
     */
    public LocationInfoFullResult getFindByCoordsFullResult() {
        return findByCoordsFullResult;
    }

    /**
     * Sets the value of the findByCoordsFullResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationInfoFullResult }
     *     
     */
    public void setFindByCoordsFullResult(LocationInfoFullResult value) {
        this.findByCoordsFullResult = value;
    }

}
