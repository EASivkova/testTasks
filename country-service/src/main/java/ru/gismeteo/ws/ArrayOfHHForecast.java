
package ru.gismeteo.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHHForecast complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHHForecast">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HHForecast" type="{http://ws.gismeteo.ru/}HHForecast" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHHForecast", propOrder = {
    "hhForecast"
})
public class ArrayOfHHForecast {

    @XmlElement(name = "HHForecast")
    protected List<HHForecast> hhForecast;

    /**
     * Gets the value of the hhForecast property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hhForecast property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHHForecast().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HHForecast }
     * 
     * 
     */
    public List<HHForecast> getHHForecast() {
        if (hhForecast == null) {
            hhForecast = new ArrayList<HHForecast>();
        }
        return this.hhForecast;
    }

}
