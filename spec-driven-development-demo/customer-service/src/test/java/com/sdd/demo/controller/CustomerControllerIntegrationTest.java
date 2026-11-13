package com.sdd.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sdd.demo.CustomerServiceApplication;
import com.sdd.demo.repository.CustomerRepository;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class CustomerControllerIntegrationTest {

	private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
	private static ConfigurableApplicationContext applicationContext;
	private static int serverPort;

	private CustomerRepository customerRepository;

	@BeforeAll
	static void startApplication() {
		applicationContext = SpringApplication.run(CustomerServiceApplication.class, "--server.port=0");
		serverPort = applicationContext.getEnvironment().getRequiredProperty("local.server.port", Integer.class);
	}

	@AfterAll
	static void stopApplication() {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

	@BeforeEach
	void clearCustomers() {
		customerRepository = applicationContext.getBean(CustomerRepository.class);
		customerRepository.deleteAll();
	}

	@Test
	void createsCustomerWithGeneratedIdActiveStatusAndTimestamps() throws Exception {
		HttpResponse<String> response = send("POST", "/api/v1/customers", createJson());
		assertEquals(201, response.statusCode());
		String firstId = value(response.body(), "id");
		assertEquals("ACTIVE", value(response.body(), "status"));
		assertTrue(value(response.body(), "createdAt").length() > 0);
		assertTrue(value(response.body(), "updatedAt").length() > 0);

		String secondId = createCustomer("unique-id-two@example.com");
		assertNotEquals(firstId, secondId);
	}

	@Test
	void rejectsInvalidRequiredValuesAndEmail() throws Exception {
		HttpResponse<String> blankName = send("POST", "/api/v1/customers",
				"{\"firstName\":\" \",\"lastName\":\"Doe\",\"email\":\"jane@example.com\",\"phoneNumber\":\"1\"}");
		assertEquals(400, blankName.statusCode());
		assertEquals("VALIDATION_ERROR", value(blankName.body(), "code"));

		HttpResponse<String> invalidEmail = send("POST", "/api/v1/customers",
				"{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"bad\",\"phoneNumber\":\"1\"}");
		assertEquals(400, invalidEmail.statusCode());
		assertEquals("VALIDATION_ERROR", value(invalidEmail.body(), "code"));
	}

	@Test
	void rejectsCaseInsensitiveDuplicateEmail() throws Exception {
		createCustomer("customer@example.com");
		HttpResponse<String> response = send("POST", "/api/v1/customers", createJson("Customer@Example.com"));
		assertEquals(409, response.statusCode());
		assertEquals("CUSTOMER_EMAIL_ALREADY_EXISTS", value(response.body(), "code"));
	}

	@Test
	void retrievesExistingCustomerAndReturnsNotFoundForMissingCustomer() throws Exception {
		String id = createCustomer("read@example.com");
		HttpResponse<String> existing = send("GET", "/api/v1/customers/" + id, null);
		assertEquals(200, existing.statusCode());
		assertEquals("read@example.com", value(existing.body(), "email"));

		HttpResponse<String> missing = send("GET", "/api/v1/customers/" + UUID.randomUUID(), null);
		assertEquals(404, missing.statusCode());
		assertEquals("CUSTOMER_NOT_FOUND", value(missing.body(), "code"));
	}

	@Test
	void listsCustomersAndReturnsEmptyArrayWhenEmpty() throws Exception {
		HttpResponse<String> empty = send("GET", "/api/v1/customers", null);
		assertEquals(200, empty.statusCode());
		assertEquals("[]", empty.body());

		createCustomer("list1@example.com");
		createCustomer("list2@example.com");
		HttpResponse<String> listed = send("GET", "/api/v1/customers", null);
		assertEquals(200, listed.statusCode());
		assertEquals(2, countOccurrences(listed.body(), "\"id\""));
	}

	@Test
	void updatesCustomerRetainingIdentityCreationTimeAndOwnEmail() throws Exception {
		String id = createCustomer("update@example.com");
		String beforeJson = send("GET", "/api/v1/customers/" + id, null).body();
		String createdAt = value(beforeJson, "createdAt");
		String beforeUpdatedAt = value(beforeJson, "updatedAt");

		HttpResponse<String> updated = send("PUT", "/api/v1/customers/" + id,
				updateJson("update@example.com", "INACTIVE"));
		assertEquals(200, updated.statusCode());
		assertEquals(id, value(updated.body(), "id"));
		assertEquals(createdAt, value(updated.body(), "createdAt"));
		assertNotEquals(beforeUpdatedAt, value(updated.body(), "updatedAt"));
		assertEquals("INACTIVE", value(updated.body(), "status"));
	}

	@Test
	void rejectsEmailOwnedByAnotherCustomerAndInvalidStatusOrData() throws Exception {
		String firstId = createCustomer("first@example.com");
		createCustomer("second@example.com");

		HttpResponse<String> duplicateEmail = send("PUT", "/api/v1/customers/" + firstId,
				updateJson("second@example.com", "ACTIVE"));
		assertEquals(409, duplicateEmail.statusCode());

		HttpResponse<String> invalidStatus = send("PUT", "/api/v1/customers/" + firstId,
				updateJson("first@example.com", "UNKNOWN"));
		assertEquals(400, invalidStatus.statusCode());
		assertEquals("INVALID_CUSTOMER_STATUS", value(invalidStatus.body(), "code"));

		HttpResponse<String> invalidData = send("PUT", "/api/v1/customers/" + firstId,
				"{\"firstName\":\"\",\"lastName\":\"Doe\",\"email\":\"first@example.com\",\"phoneNumber\":\"1\",\"status\":\"ACTIVE\"}");
		assertEquals(400, invalidData.statusCode());
		assertEquals("VALIDATION_ERROR", value(invalidData.body(), "code"));
	}

	@Test
	void updatesAndDeletesNonExistingAndExistingCustomers() throws Exception {
		String id = createCustomer("delete@example.com");
		assertEquals(204, send("DELETE", "/api/v1/customers/" + id, null).statusCode());
		assertEquals(404, send("GET", "/api/v1/customers/" + id, null).statusCode());

		String missingId = UUID.randomUUID().toString();
		HttpResponse<String> updateMissing = send("PUT", "/api/v1/customers/" + missingId,
				updateJson("missing@example.com", "ACTIVE"));
		assertEquals(404, updateMissing.statusCode());
		assertEquals(404, send("DELETE", "/api/v1/customers/" + missingId, null).statusCode());
	}

	@Test
	void exposesOpenApiDocumentation() throws Exception {
		HttpResponse<String> response = send("GET", "/v3/api-docs", null);
		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("\"title\":\"Customer Management Service API\""));
		assertTrue(response.body().contains("\"version\":\"1.0.0\""));
		assertTrue(response.body().contains("\"201\""));
		assertTrue(response.body().contains("\"409\""));
		assertTrue(response.body().contains("\"204\""));
	}

	private String createCustomer(String email) throws Exception {
		HttpResponse<String> response = send("POST", "/api/v1/customers", createJson(email));
		assertEquals(201, response.statusCode());
		return value(response.body(), "id");
	}

	private HttpResponse<String> send(String method, String path, String body) throws Exception {
		HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://localhost:" + serverPort + path));
		if (body == null) {
			request.method(method, HttpRequest.BodyPublishers.noBody());
		} else {
			request.header("Content-Type", "application/json");
			request.method(method, HttpRequest.BodyPublishers.ofString(body));
		}
		return HTTP_CLIENT.send(request.build(), HttpResponse.BodyHandlers.ofString());
	}

	private String value(String json, String key) {
		Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
		if (!matcher.find()) {
			throw new IllegalStateException("Missing JSON field: " + key);
		}
		return matcher.group(1);
	}

	private int countOccurrences(String value, String needle) {
		int count = 0;
		int index = 0;
		while ((index = value.indexOf(needle, index)) >= 0) {
			count++;
			index += needle.length();
		}
		return count;
	}

	private String createJson() {
		return createJson("amran@example.com");
	}

	private String createJson(String email) {
		return "{\"firstName\":\"Amran\",\"lastName\":\"Hossain\",\"email\":\"" + email
				+ "\",\"phoneNumber\":\"9876543211\"}";
	}

	private String updateJson(String email, String status) {
		return "{\"firstName\":\"Amran Updated\",\"lastName\":\"Hossain\",\"email\":\"" + email
				+ "\",\"phoneNumber\":\"9876543211\",\"status\":\"" + status + "\"}";
	}
}
