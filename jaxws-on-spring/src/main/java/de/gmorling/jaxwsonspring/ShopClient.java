package de.gmorling.jaxwsonspring;

import javax.annotation.Resource;

import de.gmorling.jaxwsonspring.shop.products.ProductsPortType;
import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdRequest;

public class ShopClient {

	@Resource
	private ProductsPortType productsPort;

	public String getProductNameByid(int productId) {

		GetProductByIdRequest request = new GetProductByIdRequest();
		request.setId(productId);

		return productsPort.getProductById(request).getProduct().getName();
	}

}