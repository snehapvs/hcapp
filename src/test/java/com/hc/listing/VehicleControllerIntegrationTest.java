package com.hc.listing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)

public class VehicleControllerIntegrationTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void vehicleListingControllerTest() {
		final ResponseEntity<?> searchResponse = restTemplate.getForEntity(createURLWithPort("/search?year=2016"),
				Object.class);
		final ResponseEntity<?> searchResponseFailure = restTemplate
				.getForEntity(createURLWithPort("/search?year=sdsjks"), Object.class);

		String entry = "\n" + "[\n" + "{\n" + "\"code\": \"ab\",\n" + "\"make\": \"renault\",\n"
				+ "\"model\": \"megane\",\n" + "\"kw\": 132,\n" + "\"color\": \"green\"\n" + "\n" + "}\n" + "]";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(entry, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicle_listings/1"),
				HttpMethod.POST, entity, String.class);

		assertThat(searchResponse.getStatusCode(), is(OK));
		assertThat(searchResponseFailure.getStatusCode(), is(BAD_REQUEST));
		assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/api/heycar" + uri;
	}

}
