package com.assessment.shopx;

import com.assessment.shopx.controllers.ProductController;
import com.assessment.shopx.controllers.TestController;
import com.assessment.shopx.models.PurchaseType;
import com.assessment.shopx.payload.request.LoginRequest;

import com.assessment.shopx.payload.request.ProductPriceRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShopxApplicationTests {

	@Autowired
	private TestController testController;

	@Autowired
	private ProductController productController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String jwtToken;


	@Test
	void contextLoads() {
		assertThat(testController).isNotNull();
		assertThat(productController).isNotNull();
	}

	private void authenticate(){
		LoginRequest loginRequest = new LoginRequest("admin","admin");
		HashMap hashMap = this.restTemplate.postForObject("http://localhost:" + port + "/api/auth/signin",loginRequest, HashMap.class);
		assertThat(hashMap.get("accessToken")).isNotNull();
		this.jwtToken = (String) hashMap.get("accessToken");
	}


	@Test
	public void testListProducts() throws Exception {
		authenticate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(jwtToken);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/api/product/list",
				HttpMethod.GET,entity, Object.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		LinkedHashMap productResponse = (LinkedHashMap) responseEntity.getBody();
		List<HashMap> productResponses = (List<HashMap>) productResponse.get("content");
		assertThat(productResponses).isNotNull();
		assertThat(productResponses.get(0).get("name")).isEqualTo("Penguin-ears");
	}

	@Test
	public void testListProductsPrices() throws Exception {
		authenticate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(jwtToken);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity responseEntity = this.restTemplate
				.exchange("http://localhost:" + port + "/api/product/calculate-price-list?productId=1",
				HttpMethod.GET,entity, Object.class);
		List priceResponseList = (List) responseEntity.getBody();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(priceResponseList).isNotNull();
		assertThat(priceResponseList.size()).isEqualTo(50);
	}


	@Test
	public void testProductPriceCalculation() throws Exception {
		authenticate();

		ProductPriceRequest productPriceRequest = new ProductPriceRequest();
		productPriceRequest.setProductId(1);
		productPriceRequest.setPurchaseType(PurchaseType.UNIT);
		productPriceRequest.setQty(10);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(jwtToken);
		HttpEntity entity = new HttpEntity(productPriceRequest, headers);

		ResponseEntity responseEntity = this.restTemplate.
				exchange("http://localhost:" + port + "/api/product/calculate-price",
						HttpMethod.POST,entity, Object.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		HashMap body = (HashMap)responseEntity.getBody();

		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(body.get("onlyUnits")).isEqualTo(productPriceRequest.getQty());

	}



}
