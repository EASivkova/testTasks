package de.gmorling.jaxwsonspring;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.gmorling.jaxwsonspring.shop.products.ProductsPortType;
import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdRequest;
import de.gmorling.jaxwsonspring.shop.products.types.GetProductByIdResponse;
import de.gmorling.jaxwsonspring.shop.products.types.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ShopClientMockTest {

	@Resource
	private ProductsPortType productsPort;

	@Resource
	private ShopClient shopClient;

	@Before
	public void instructMock() {
		GetProductByIdResponse response = new GetProductByIdResponse();
		Product product = new Product();
		product.setId(1);
		product.setName("Jeans");
		product.setPrice(new BigDecimal("89.99"));
		product.setSize("L");
		response.setProduct(product);

		when(productsPort.getProductById(any(GetProductByIdRequest.class)))
				.thenReturn(response);
	}

	@Test
	public void getProductName() {
		assertEquals("Jeans", shopClient.getProductNameByid(1));
	}

}