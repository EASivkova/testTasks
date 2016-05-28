package ru.country.info.ws;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ru.country.info.model.Country;
import ru.country.info.model.Log;
import ru.country.info.model.ProcessingResult;
import ru.country.info.model.Status;
import ru.country.info.model.TypeRecord;
import ru.country.info.model.ValuteCursOnDate;
import ru.country.info.service.LogService;
import ru.country.info.xml.WSXMLProcessor;
import ru.country.info.xml.XMLUtils;
import ru.gismeteo.ws.ArrayOfHHForecast;
import ru.gismeteo.ws.HHForecastResult;
import ru.gismeteo.ws.LocationInfoFull;
import ru.gismeteo.ws.LocationInfoFullResult;
import ru.gismeteo.ws.Locations;
import ru.gismeteo.ws.LocationsSoap;
import ru.gismeteo.ws.Weather;
import ru.gismeteo.ws.WeatherSoap;

@Endpoint
public class CountryEndpoint {

    /** Логгер для журналирования обработки данных. */
    private static final Logger LOG = Logger.getLogger(CountryEndpoint.class);
    
    @Autowired
    private transient LogService logService;
	
    /** Ссылка на составитель ответов клиентов в виде XML данных. */
    @Autowired
    private transient WSXMLProcessor xmlProcessor;
    
    @Autowired
    private Locations locations;
    private LocationsSoap locationsSoap = null;
    
    @Autowired
    private Weather weather;
    private WeatherSoap weatherSoap = null;
    
    @Autowired
    private String serial;
    
    private static Long idSession;

    @PayloadRoot(localPart = "CountryRequest", namespace = "urn:country-input:v0.1")
    @Namespace(prefix = "info", uri = "urn:country-input:v0.1")
    @ResponsePayload
    public final Element getInfo(@XPathParam(value = "/info:CountryRequest/info:name") final String name, @RequestPayload Element requestPayload) {
        ProcessingResult result = new ProcessingResult();
        try {
        	setIdSession(logService.getNextIdSession());
        	Log logRequest = createRecordLog("client", "this", TypeRecord.REQUEST, idSession);
        	logRequest.setText(XMLUtils.domToString(requestPayload).getBytes());
        	LOG.debug(new String(logRequest.getText()));
        	logService.saveOrUpdate(logRequest);
        	Country country = logService.getCountryByName(name);
        	LOG.debug("Страна " + name);
        	if (country != null) {
                try {
                	result.setValuteCursOnDate(getCourses(country.getCurrency(), idSession));
                	result.getListHHForecast().addAll(getWeather(country, idSession).getHHForecast());
                } catch (Exception e) {
                    LOG.error("Unpredict error", e);
                    result.addErrorDetails("Произошла критичная ошибка при обработке запроса: " + e.getMessage());
                }
        	} else {
                LOG.info("Country didn`t find in database");
                result.addErrorDetails("Страна не найдена во внутреннем справочнике.");
        	}
        	Log logResponse = createRecordLog("this", "client", TypeRecord.RESPONSE, idSession);
        	logService.saveOrUpdate(logResponse);
        	result.setResponseId(logResponse.getId().toString());
        	Element response = xmlProcessor.createResponse(result);
        	logResponse.setText(XMLUtils.domToString(response).getBytes());
        	logService.saveOrUpdate(logResponse);
    		return response;
        } catch (DataAccessException e) {
        	LOG.error("Ошибка в работе с базой данных.", e);
            result.addErrorDetails("Ошибка в работе с базой данных.");
        	Element response = xmlProcessor.createResponse(result);
    		return response;
        } catch (Exception e) {
        	LOG.error("Ошибка в работе сервиса.", e);
            result.addErrorDetails("Ошибка в работе сервиса.");
        	Element response = xmlProcessor.createResponse(result);
    		return response;
        }
    }

    private ValuteCursOnDate getCourses(String currency, Long idSession) throws DatatypeConfigurationException, SOAPException {
    	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        String url = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(idSession), url);
        Log log = createRecordLog("courses", "this", TypeRecord.RESPONSE, idSession);
        log.setText(XMLUtils.nodeToString(soapResponse.getSOAPPart()).getBytes());
        LOG.debug(XMLUtils.nodeToString(soapResponse.getSOAPPart()));
        try {
        	SOAPBody soapBody = soapResponse.getSOAPPart().getEnvelope().getBody();
        	Node node = soapBody.getFirstChild().getFirstChild().getLastChild().getFirstChild();
			boolean isFound = false;
        	for (int i = 0; i < node.getChildNodes().getLength(); i++) {
        		if ("ValuteCursOnDate".equalsIgnoreCase(node.getChildNodes().item(i).getNodeName())) {
        			ValuteCursOnDate result = new ValuteCursOnDate();
                	for (int j = 0; j < node.getChildNodes().item(i).getChildNodes().getLength(); j++) {
                		if ("VName".equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getNodeName())) {
                			result.setVname(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent().trim());
                		} else if ("VNom".equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getNodeName())) {
                			result.setVnom(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent().trim());
                		} else if ("VCurs".equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getNodeName())) {
                			result.setVcurs(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent().trim());
                		} else if ("VCode".equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getNodeName())) {
                			result.setVcode(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent().trim());
                		} else if ("VchCode".equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getNodeName())
                				&& currency.equalsIgnoreCase(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent())) {
                			result.setVchCode(node.getChildNodes().item(i).getChildNodes().item(j).getTextContent().trim());
                			isFound = true;
                		}
                	}
                	if (isFound) {
	                    LOG.debug("result: " + XMLUtils.nodeToString(node.getChildNodes().item(i)));
	                	return result;
                	}
        		}
        	}
        	return null;
        } catch (NoSuchElementException e) {
        	log.setStatus(Status.FAIL);
        	LOG.error("Ошибка при получении ответа на запрос GetCursOnDate.", e);
        	throw new SOAPException(e);
        } finally {
            soapConnection.close();
            logService.saveOrUpdate(log);
        }
    }
    
    private SOAPMessage createSOAPRequest(Long idSession) throws SOAPException, DatatypeConfigurationException {
    	Log log = createRecordLog("this", "DailyInfo", TypeRecord.REQUEST, idSession);
    	try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.removeNamespaceDeclaration("SOAP-ENV");
            envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2001/12/soap-envelope");
            envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
            envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            envelope.setPrefix("soap");
            soapMessage.getSOAPHeader().detachNode();
            soapMessage.getSOAPBody().setPrefix("soap");
    		/* 
    		<?xml version="1.0" encoding="utf-8"?>
    		<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    		  <soap:Body>
    		    <GetCursOnDate xmlns="http://web.cbr.ru/">
    		      <On_date>dateTime</On_date>
    		    </GetCursOnDate>
    		  </soap:Body>
    		</soap:Envelope>
    		*/
            SOAPElement soapBodyElem = soapMessage.getSOAPBody().addChildElement("GetCursOnDate", "", "http://web.cbr.ru/");
            SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("On_date");
        	GregorianCalendar c = new GregorianCalendar();
        	c.setTime(log.getRegistrationDate());
        	XMLGregorianCalendar onDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            soapBodyElem1.addTextNode(onDate.toXMLFormat());
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", "http://web.cbr.ru/"  + "GetCursOnDate");
            soapMessage.saveChanges();
            LOG.debug("Request SOAP Message = " + XMLUtils.nodeToString(soapMessage.getSOAPPart()));
            log.setText(XMLUtils.nodeToString(soapMessage.getSOAPPart()).getBytes());
            return soapMessage;
    	} catch (SOAPException e) {
    		log.setStatus(Status.FAIL);
    		LOG.error("Ошибка формирования запроса GetCursOnDate.", e);
    		throw e;
    	} catch (DatatypeConfigurationException e) {
    		log.setStatus(Status.FAIL);
    		LOG.error("Ошибка формирования запроса GetCursOnDate (формирование даты).", e);
    		throw e;
		} finally {
            logService.saveOrUpdate(log);
    	}
    }

    private ArrayOfHHForecast getWeather(Country country, Long idSession) throws Exception {
    	if (locationsSoap == null) {
        	locationsSoap = locations.getLocationsSoap();
    	}
    	LocationInfoFullResult locationResult = locationsSoap.findByNameFull(serial, country.getCapital(), 20, "RU");
    	if (locationResult.getData() != null) {
        	for (LocationInfoFull location : locationResult.getData().getLocationInfoFull()) {
        		LOG.debug(location.getCountry());
        		if (location.getCountry().equalsIgnoreCase(country.getName())) {
        			int id = location.getId();
        			if (weatherSoap == null) {
            			weatherSoap = weather.getWeatherSoap();
        			}
        			HHForecastResult hhForecastResult = weatherSoap.getHHForecast(serial, id);
        			if (hhForecastResult.getData() != null) {
            	    	LOG.debug(hhForecastResult.getData().getHHForecast().size());
            	    	return hhForecastResult.getData();
        			} else {
        	        	LOG.error(locationResult.getResult().getErrorMessage());
        	        	throw new Exception(locationResult.getResult().getErrorMessage());
        			}
        		}
        	}
    	} else {
        	LOG.error(locationResult.getResult().getErrorMessage());
        	throw new Exception(locationResult.getResult().getErrorMessage());
    	}
    	return null;
    }
    
    private Log createRecordLog(String from, String to, TypeRecord type, Long idSession) {
    	Log log = new Log();
    	log.setFrom(from);
    	log.setTo(to);
    	log.setType(type);
    	log.setIdSession(idSession);
    	return log;
    }
    
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void setXmlProcessor(WSXMLProcessor xmlProcessor) {
		this.xmlProcessor = xmlProcessor;
	}

	public void setLocations(Locations locations) {
		this.locations = locations;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public static Long getIdSession() {
		return idSession;
	}

	public static void setIdSession(Long idSession) {
		CountryEndpoint.idSession = idSession;
	}

}
