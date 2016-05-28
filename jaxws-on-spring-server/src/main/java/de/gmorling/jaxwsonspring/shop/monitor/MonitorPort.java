package de.gmorling.jaxwsonspring.shop.monitor;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jws.WebService;

import com.sun.xml.ws.developer.SchemaValidation;

import de.gmorling.jaxwsonspring.shop.RequestCountHandler;
import de.gmorling.jaxwsonspring.shop.monitor.types.GetServiceCallsCountRequest;
import de.gmorling.jaxwsonspring.shop.monitor.types.GetServiceCallsCountResponse;
import de.gmorling.jaxwsonspring.shop.monitor.types.GetServiceCallsCountResponse.RequestLogs;

@WebService(endpointInterface = "de.gmorling.jaxwsonspring.shop.monitor.MonitorPortType")
@SchemaValidation
public class MonitorPort implements MonitorPortType {

	public GetServiceCallsCountResponse getServiceCallsCount(
			GetServiceCallsCountRequest body) {

		GetServiceCallsCountResponse theValue = new GetServiceCallsCountResponse();

		RequestLogs requestLogs = new RequestLogs();

		for (Entry<String, AtomicInteger> oneEntry : RequestCountHandler
				.getRequestCounts().entrySet()) {
			de.gmorling.jaxwsonspring.shop.monitor.types.RequestLog requestLog = new de.gmorling.jaxwsonspring.shop.monitor.types.RequestLog();
			requestLog.setRequestName(oneEntry.getKey());
			requestLog.setCallCount(oneEntry.getValue().get());
			requestLogs.getRequestLog().add(requestLog);
		}

		theValue.setRequestLogs(requestLogs);
		return theValue;
	}

}