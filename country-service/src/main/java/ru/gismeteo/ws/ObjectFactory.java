
package ru.gismeteo.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.gismeteo.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HHForecastResult_QNAME = new QName("http://ws.gismeteo.ru/", "HHForecastResult");
    private final static QName _MMForecastResult_QNAME = new QName("http://ws.gismeteo.ru/", "MMForecastResult");
    private final static QName _HHObservationResult_QNAME = new QName("http://ws.gismeteo.ru/", "HHObservationResult");
    private final static QName _GetMoonInfoResult_QNAME = new QName("http://ws.gismeteo.ru/", "GetMoonInfoResult");
    private final static QName _GetSunInfoResult_QNAME = new QName("http://ws.gismeteo.ru/", "GetSunInfoResult");
    private final static QName _GeoMagneticForecastResult_QNAME = new QName("http://ws.gismeteo.ru/", "GeoMagneticForecastResult");
    private final static QName _GetLocalTimeOffsetResult_QNAME = new QName("http://ws.gismeteo.ru/", "GetLocalTimeOffsetResult");
    private final static QName _MMObservationResult_QNAME = new QName("http://ws.gismeteo.ru/", "MMObservationResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.gismeteo.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfMMForecast }
     * 
     */
    public ArrayOfMMForecast createArrayOfMMForecast() {
        return new ArrayOfMMForecast();
    }

    /**
     * Create an instance of {@link GetMoonInfoResult }
     * 
     */
    public GetMoonInfoResult createGetMoonInfoResult() {
        return new GetMoonInfoResult();
    }

    /**
     * Create an instance of {@link ArrayOfGeoMagneticForecast }
     * 
     */
    public ArrayOfGeoMagneticForecast createArrayOfGeoMagneticForecast() {
        return new ArrayOfGeoMagneticForecast();
    }

    /**
     * Create an instance of {@link ArrayOfHHForecast }
     * 
     */
    public ArrayOfHHForecast createArrayOfHHForecast() {
        return new ArrayOfHHForecast();
    }

    /**
     * Create an instance of {@link GeoMagneticForecast }
     * 
     */
    public GeoMagneticForecast createGeoMagneticForecast() {
        return new GeoMagneticForecast();
    }

    /**
     * Create an instance of {@link GetLocalTimeOffsetResponse }
     * 
     */
    public GetLocalTimeOffsetResponse createGetLocalTimeOffsetResponse() {
        return new GetLocalTimeOffsetResponse();
    }

    /**
     * Create an instance of {@link GetLocalTimeOffsetResult }
     * 
     */
    public GetLocalTimeOffsetResult createGetLocalTimeOffsetResult() {
        return new GetLocalTimeOffsetResult();
    }

    /**
     * Create an instance of {@link HHForecastResult }
     * 
     */
    public HHForecastResult createHHForecastResult() {
        return new HHForecastResult();
    }

    /**
     * Create an instance of {@link GetMMForecastResponse }
     * 
     */
    public GetMMForecastResponse createGetMMForecastResponse() {
        return new GetMMForecastResponse();
    }

    /**
     * Create an instance of {@link MMObservation }
     * 
     */
    public MMObservation createMMObservation() {
        return new MMObservation();
    }

    /**
     * Create an instance of {@link HHObservationResult }
     * 
     */
    public HHObservationResult createHHObservationResult() {
        return new HHObservationResult();
    }

    /**
     * Create an instance of {@link GetMoonInfo }
     * 
     */
    public GetMoonInfo createGetMoonInfo() {
        return new GetMoonInfo();
    }

    /**
     * Create an instance of {@link GetSunInfoResult }
     * 
     */
    public GetSunInfoResult createGetSunInfoResult() {
        return new GetSunInfoResult();
    }

    /**
     * Create an instance of {@link GetMMObservationResponse }
     * 
     */
    public GetMMObservationResponse createGetMMObservationResponse() {
        return new GetMMObservationResponse();
    }

    /**
     * Create an instance of {@link GetLocalTimeOffset }
     * 
     */
    public GetLocalTimeOffset createGetLocalTimeOffset() {
        return new GetLocalTimeOffset();
    }

    /**
     * Create an instance of {@link GetSunInfoResponse }
     * 
     */
    public GetSunInfoResponse createGetSunInfoResponse() {
        return new GetSunInfoResponse();
    }

    /**
     * Create an instance of {@link HHForecast }
     * 
     */
    public HHForecast createHHForecast() {
        return new HHForecast();
    }

    /**
     * Create an instance of {@link GetSunInfo }
     * 
     */
    public GetSunInfo createGetSunInfo() {
        return new GetSunInfo();
    }

    /**
     * Create an instance of {@link GetMoonInfoResponse }
     * 
     */
    public GetMoonInfoResponse createGetMoonInfoResponse() {
        return new GetMoonInfoResponse();
    }

    /**
     * Create an instance of {@link ServiceResult }
     * 
     */
    public ServiceResult createServiceResult() {
        return new ServiceResult();
    }

    /**
     * Create an instance of {@link GetHHForecast }
     * 
     */
    public GetHHForecast createGetHHForecast() {
        return new GetHHForecast();
    }

    /**
     * Create an instance of {@link MMObservationResult }
     * 
     */
    public MMObservationResult createMMObservationResult() {
        return new MMObservationResult();
    }

    /**
     * Create an instance of {@link GetGeoMagneticForecastResponse }
     * 
     */
    public GetGeoMagneticForecastResponse createGetGeoMagneticForecastResponse() {
        return new GetGeoMagneticForecastResponse();
    }

    /**
     * Create an instance of {@link GetGeoMagneticForecast }
     * 
     */
    public GetGeoMagneticForecast createGetGeoMagneticForecast() {
        return new GetGeoMagneticForecast();
    }

    /**
     * Create an instance of {@link GetMMObservation }
     * 
     */
    public GetMMObservation createGetMMObservation() {
        return new GetMMObservation();
    }

    /**
     * Create an instance of {@link GeoMagneticForecastResult }
     * 
     */
    public GeoMagneticForecastResult createGeoMagneticForecastResult() {
        return new GeoMagneticForecastResult();
    }

    /**
     * Create an instance of {@link MMForecastResult }
     * 
     */
    public MMForecastResult createMMForecastResult() {
        return new MMForecastResult();
    }

    /**
     * Create an instance of {@link GetMMForecast }
     * 
     */
    public GetMMForecast createGetMMForecast() {
        return new GetMMForecast();
    }

    /**
     * Create an instance of {@link GetHHObservationResponse }
     * 
     */
    public GetHHObservationResponse createGetHHObservationResponse() {
        return new GetHHObservationResponse();
    }

    /**
     * Create an instance of {@link MMForecast }
     * 
     */
    public MMForecast createMMForecast() {
        return new MMForecast();
    }

    /**
     * Create an instance of {@link GetHHObservation }
     * 
     */
    public GetHHObservation createGetHHObservation() {
        return new GetHHObservation();
    }

    /**
     * Create an instance of {@link HHObservation }
     * 
     */
    public HHObservation createHHObservation() {
        return new HHObservation();
    }

    /**
     * Create an instance of {@link GetHHForecastResponse }
     * 
     */
    public GetHHForecastResponse createGetHHForecastResponse() {
        return new GetHHForecastResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HHForecastResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "HHForecastResult")
    public JAXBElement<HHForecastResult> createHHForecastResult(HHForecastResult value) {
        return new JAXBElement<HHForecastResult>(_HHForecastResult_QNAME, HHForecastResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MMForecastResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "MMForecastResult")
    public JAXBElement<MMForecastResult> createMMForecastResult(MMForecastResult value) {
        return new JAXBElement<MMForecastResult>(_MMForecastResult_QNAME, MMForecastResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HHObservationResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "HHObservationResult")
    public JAXBElement<HHObservationResult> createHHObservationResult(HHObservationResult value) {
        return new JAXBElement<HHObservationResult>(_HHObservationResult_QNAME, HHObservationResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMoonInfoResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "GetMoonInfoResult")
    public JAXBElement<GetMoonInfoResult> createGetMoonInfoResult(GetMoonInfoResult value) {
        return new JAXBElement<GetMoonInfoResult>(_GetMoonInfoResult_QNAME, GetMoonInfoResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSunInfoResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "GetSunInfoResult")
    public JAXBElement<GetSunInfoResult> createGetSunInfoResult(GetSunInfoResult value) {
        return new JAXBElement<GetSunInfoResult>(_GetSunInfoResult_QNAME, GetSunInfoResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeoMagneticForecastResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "GeoMagneticForecastResult")
    public JAXBElement<GeoMagneticForecastResult> createGeoMagneticForecastResult(GeoMagneticForecastResult value) {
        return new JAXBElement<GeoMagneticForecastResult>(_GeoMagneticForecastResult_QNAME, GeoMagneticForecastResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLocalTimeOffsetResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "GetLocalTimeOffsetResult")
    public JAXBElement<GetLocalTimeOffsetResult> createGetLocalTimeOffsetResult(GetLocalTimeOffsetResult value) {
        return new JAXBElement<GetLocalTimeOffsetResult>(_GetLocalTimeOffsetResult_QNAME, GetLocalTimeOffsetResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MMObservationResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.gismeteo.ru/", name = "MMObservationResult")
    public JAXBElement<MMObservationResult> createMMObservationResult(MMObservationResult value) {
        return new JAXBElement<MMObservationResult>(_MMObservationResult_QNAME, MMObservationResult.class, null, value);
    }

}
