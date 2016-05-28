package de.gmorling.jaxwsonspring;

import javax.xml.ws.BindingProvider;

import de.gmorling.jaxwsonspring.shop.products.ProductsPortType;
import de.gmorling.jaxwsonspring.shop.products.ProductsService;
import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdRequest;

public class ShopClientPlainJaxWs {

	private final static String END_POINT_URL = "http://localhost:8080/jaxws-on-spring-server/Products";

	public String getProductNameByid(int productId) {

		GetProductByIdRequest request = new GetProductByIdRequest();
		request.setId(productId);

		ProductsService productsService = new ProductsService();
		ProductsPortType productsPort = productsService.getProductsPort();
		((BindingProvider) productsPort).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, END_POINT_URL);

		return productsPort.getProductById(request).getProduct().getName();
	}
}