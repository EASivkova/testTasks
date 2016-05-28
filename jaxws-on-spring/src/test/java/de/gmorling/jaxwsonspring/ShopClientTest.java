package de.gmorling.jaxwsonspring;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ShopClientTest {

	@Resource
	private ShopClient shopClient;

	@Test
	public void getProductName() {
		assertEquals("Jeans", shopClient.getProductNameByid(1));
	}

}