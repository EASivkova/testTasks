
package ru.gismeteo.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMMForecast complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMMForecast">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MMForecast" type="{http://ws.gismeteo.ru/}MMForecast" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMMForecast", propOrder = {
    "mmForecast"
})
public class ArrayOfMMForecast {

    @XmlElement(name = "MMForecast")
    protected List<MMForecast> mmForecast;

    /**
     * Gets the value of the mmForecast property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mmForecast property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMMForecast().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MMForecast }
     * 
     * 
     */
    public List<MMForecast> getMMForecast() {
        if (mmForecast == null) {
            mmForecast = new ArrayList<MMForecast>();
        }
        return this.mmForecast;
    }

}
