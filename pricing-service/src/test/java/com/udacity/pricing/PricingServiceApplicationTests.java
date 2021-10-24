package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

	@LocalServerPort // (Spring) allow injection of server`s Port
	private Integer port;

	@Test
	public void priceTesting() throws PriceException {
		Price price;
		Long vehicleId = 1L;
		try {
			TestRestTemplate rest = new TestRestTemplate();
			ResponseEntity<Price> entity = rest.exchange("http://localhost:" + port +
							"/services/price?vehicleId=" + vehicleId,
					HttpMethod.GET,
					null,
					Price.class);

			price = entity.getBody();

			Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
			Assertions.assertNotNull(price);
			Assertions.assertEquals("USD", price.getCurrency());
			Assertions.assertEquals(vehicleId, price.getVehicleId());

		} catch (Exception e) {
			throw new PriceException(e.getMessage());
		}
	}
}
