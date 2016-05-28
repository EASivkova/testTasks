package ru.country.info.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import static ru.country.info.xml.XMLField.*;
import ru.country.info.model.ProcessingResult;
import ru.country.info.ws.exception.XMLParsingException;
import ru.gismeteo.ws.HHForecast;

public class SimpleXMLProcessing implements WSXMLProcessor {

    private static final Logger LOG = Logger.getLogger(SimpleXMLProcessing.class);

    /** {@inheritDoc} */
	public Element createResponse(ProcessingResult result) {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        Element root = doc.createElementNS("urn:country-input:v0.1", COUNTRY_RESPONCE.getName());
	        Element body = doc.createElement(BODY.getName());
	        root.appendChild(body);
	        // В случае отсутсвия токена, запрос считается не удачным и в тело
	        // ответа вставляется описание ошибки
	        if (result.getValuteCursOnDate() == null || result.getListHHForecast().isEmpty()) {
	            Element error = doc.createElement(ERROR_TEXT.getName());
	            body.appendChild(error);
	            Text errorText = doc.createTextNode(result.getErrorDetails());
	            error.appendChild(errorText);
	        } else {
	            Element countryInfo = doc.createElement(COUNTRY_INFO.getName());
	            body.appendChild(countryInfo);
	            addAttribute(countryInfo, ID.getName(), result.getResponseId());
	            Element courses = doc.createElement(COURSES.getName());
	            countryInfo.appendChild(courses);
	            Element vname = doc.createElement(COURSES_VNAME.getName());
	            vname.setTextContent(result.getValuteCursOnDate().getVname());
	            courses.appendChild(vname);
	            Element vnom = doc.createElement(COURSES_VNOM.getName());
	            vnom.setTextContent(result.getValuteCursOnDate().getVnom());
	            courses.appendChild(vnom);
	            Element vcurs = doc.createElement(COURSES_VCURS.getName());
	            vcurs.setTextContent(result.getValuteCursOnDate().getVcurs());
	            courses.appendChild(vcurs);
	            Element vcode = doc.createElement(COURSES_VCODE.getName());
	            vcode.setTextContent(result.getValuteCursOnDate().getVcode());
	            courses.appendChild(vcode);
	            Element vchcode = doc.createElement(COURSES_VCHCODE.getName());
	            vchcode.setTextContent(result.getValuteCursOnDate().getVchCode());
	            courses.appendChild(vchcode);
	            Element weather = doc.createElement(WEATHER.getName());
	            countryInfo.appendChild(weather);
	            for (HHForecast hhForecast : result.getListHHForecast()) {
		            Element forecast = doc.createElement(FORECAST.getName());
		            weather.appendChild(forecast);
		            Element time = doc.createElement(FORECAST_TIME.getName());
		            time.setTextContent(hhForecast.getTime().toString());
		            forecast.appendChild(time);
		            Element tod = doc.createElement(FORECAST_TOD.getName());
		            tod.setTextContent("" + hhForecast.getTod());
		            forecast.appendChild(tod);
		            Element t = doc.createElement(FORECAST_T.getName());
		            t.setTextContent("" + hhForecast.getT());
		            forecast.appendChild(t);
		            Element p = doc.createElement(FORECAST_P.getName());
		            p.setTextContent("" + hhForecast.getP());
		            forecast.appendChild(p);
		            Element cl = doc.createElement(FORECAST_CL.getName());
		            cl.setTextContent("" + hhForecast.getCl());
		            forecast.appendChild(cl);
		            Element prc = doc.createElement(FORECAST_PRC.getName());
		            prc.setTextContent("" + hhForecast.getPrc());
		            forecast.appendChild(prc);
		            Element prct = doc.createElement(FORECAST_PRCT.getName());
		            prct.setTextContent("" + hhForecast.getPrct());
		            forecast.appendChild(prct);
		            Element dd = doc.createElement(FORECAST_DD.getName());
		            dd.setTextContent("" + hhForecast.getDd());
		            forecast.appendChild(dd);
		            Element ff = doc.createElement(FORECAST_FF.getName());
		            ff.setTextContent("" + hhForecast.getFf());
		            forecast.appendChild(ff);
		            Element st = doc.createElement(FORECAST_ST.getName());
		            st.setTextContent("" + hhForecast.getSt());
		            forecast.appendChild(st);
		            Element humidity = doc.createElement(FORECAST_HUMIDITY.getName());
		            humidity.setTextContent("" + hhForecast.getHumidity());
		            forecast.appendChild(humidity);
 	            }
	        }
	        // Просто для дебага показываем XML в логе
	        LOG.debug(XMLUtils.domToString(root));
	        return root;
		} catch (ParserConfigurationException e) {
            LOG.error("Ошибка инициализации XML обработчика.", e);
            throw new XMLParsingException(e.getMessage(), e);
		}
	}

    private void addAttribute(final Element attrNodeElement, final String attrName,
            final String attrValue) {
        if (attrValue != null && attrValue.length() != 0) {
            attrNodeElement.setAttribute(attrName, attrValue);
        }
    }

}
