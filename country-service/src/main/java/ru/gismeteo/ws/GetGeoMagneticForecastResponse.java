
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
 *         &lt;element name="GetGeoMagneticForecastResult" type="{http://ws.gismeteo.ru/}GeoMagneticForecastResult"/>
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
    "getGeoMagneticForecastResult"
})
@XmlRootElement(name = "GetGeoMagneticForecastResponse")
public class GetGeoMagneticForecastResponse {

    @XmlElement(name = "GetGeoMagneticForecastResult", required = true)
    protected GeoMagneticForecastResult getGeoMagneticForecastResult;

    /**
     * Gets the value of the getGeoMagneticForecastResult property.
     * 
     * @return
     *     possible object is
     *     {@link GeoMagneticForecastResult }
     *     
     */
    public GeoMagneticForecastResult getGetGeoMagneticForecastResult() {
        return getGeoMagneticForecastResult;
    }

    /**
     * Sets the value of the getGeoMagneticForecastResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoMagneticForecastResult }
     *     
     */
    public void setGetGeoMagneticForecastResult(GeoMagneticForecastResult value) {
        this.getGeoMagneticForecastResult = value;
    }

}
