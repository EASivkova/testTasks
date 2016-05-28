
package ru.gismeteo.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGeoMagneticForecast complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGeoMagneticForecast">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GeoMagneticForecast" type="{http://ws.gismeteo.ru/}GeoMagneticForecast" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGeoMagneticForecast", propOrder = {
    "geoMagneticForecast"
})
public class ArrayOfGeoMagneticForecast {

    @XmlElement(name = "GeoMagneticForecast")
    protected List<GeoMagneticForecast> geoMagneticForecast;

    /**
     * Gets the value of the geoMagneticForecast property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoMagneticForecast property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGeoMagneticForecast().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoMagneticForecast }
     * 
     * 
     */
    public List<GeoMagneticForecast> getGeoMagneticForecast() {
        if (geoMagneticForecast == null) {
            geoMagneticForecast = new ArrayList<GeoMagneticForecast>();
        }
        return this.geoMagneticForecast;
    }

}
