package ru.country.info.logging.handler;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.log4j.Logger;
import ru.country.info.ApplicationContextHolder;
import ru.country.info.model.Log;
import ru.country.info.model.Status;
import ru.country.info.model.TypeRecord;
import ru.country.info.service.LogService;
import ru.country.info.ws.CountryEndpoint;

public class LocationsLoggingHandler implements SOAPHandler<SOAPMessageContext> {
	
    private static final Logger LOG = Logger.getLogger(LocationsLoggingHandler.class);
    
    @Override
	public Set<QName> getHeaders() {
		return null;
	}
	 
	@Override
	public void close(MessageContext context) {
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    	Log log = new Log();
    	log.setStatus(Status.FAIL);
		if (!outboundProperty.booleanValue()) {
	    	createRecordLog(log, "this", "Locations", TypeRecord.REQUEST, CountryEndpoint.getIdSession());
		} else {
	    	createRecordLog(log, "Locations", "this", TypeRecord.RESPONSE, CountryEndpoint.getIdSession());
		}
		contextToString(context, log);
		return true;
	}
	 
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    	Log log = new Log();
		if (!outboundProperty.booleanValue()) {
	    	createRecordLog(log, "this", "Locations", TypeRecord.REQUEST, CountryEndpoint.getIdSession());
		} else {
	    	createRecordLog(log, "Locations", "this", TypeRecord.RESPONSE, CountryEndpoint.getIdSession());
		}
		contextToString(context, log);
		return true;
	}
	 
	private void contextToString(SOAPMessageContext smc, Log log) {
		try {
			SOAPMessage message = smc.getMessage();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			message.writeTo(stream);
			log.setText(stream.toString().getBytes());
			LOG.debug(new String(log.getText()));
		} catch (Exception e) {
			LOG.error("Exception in handler Locations. ", e);
		}
		LogService service = ApplicationContextHolder.getContext().getBean(LogService.class);
		service.saveOrUpdate(log);
	}

    private void createRecordLog(Log log, String from, String to, TypeRecord type, Long idSession) {
    	log.setFrom(from);
    	log.setTo(to);
    	log.setType(type);
    	log.setIdSession(idSession);
    }
    
}