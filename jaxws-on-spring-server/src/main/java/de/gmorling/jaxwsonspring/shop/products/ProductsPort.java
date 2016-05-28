package de.gmorling.jaxwsonspring.shop.products;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import javax.jws.WebService;

import com.sun.xml.ws.developer.SchemaValidation;

import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdRequest;
import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdResponse;
import de.gmorling.jaxwsonspring.shop.products.types.Product;

@WebService(endpointInterface = "de.gmorling.jaxwsonspring.shop.products.ProductsPortType")
@SchemaValidation
public class ProductsPort implements ProductsPortType {

	private Map<Integer, Product> sampleProducts = new TreeMap<Integer, Product>();

	public ProductsPort() {

		Product product = new Product();
		product.setId(1);
		product.setName("Jeans");
		product.setPrice(new BigDecimal("89.99"));
		product.setSize("L");
		sampleProducts.put(product.getId(), product);

		product = new Product();
		product.setId(2);
		product.setName("Shirt");
		product.setPrice(new BigDecimal("49.99"));
		product.setSize("M");
		sampleProducts.put(product.getId(), product);
	}

	public GetProductByIdResponse getProductById(GetProductByIdRequest request) {

		GetProductByIdResponse response = new GetProductByIdResponse();
		response.setProduct(sampleProducts.get(request.getId()));
		return response;
	}

}
