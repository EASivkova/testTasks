package de.gmorling.jaxwsonspring;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.ClassPathResource;

public class PortFactoryBean extends AbstractFactoryBean {

	private final Class<? extends Service> serviceType;

	private final String wsdlLocation;

	private final String endPointUrl;

	private final Class<?> portType;

	public PortFactoryBean(Class<? extends Service> serviceType,
			Class<?> portType, String wsdlLocation, String endPointUrl) {

		super();

		this.serviceType = serviceType;
		this.portType = portType;
		this.wsdlLocation = wsdlLocation;
		this.endPointUrl = endPointUrl;
	}

	@Override
	protected Object createInstance() throws Exception {

		javax.xml.ws.WebServiceClient wsClientAnnotation = serviceType
				.getAnnotation(javax.xml.ws.WebServiceClient.class);

		Service service = Service.create(new ClassPathResource(wsdlLocation)
				.getURL(), new QName(wsClientAnnotation.targetNamespace(),
				wsClientAnnotation.name()));

		Object port = service.getPort(this.portType);

		((BindingProvider) port).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUrl);

		return port;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getObjectType() {
		return portType;
	}

}