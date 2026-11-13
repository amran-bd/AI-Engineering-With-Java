package com.sdd.demo.controller;

import com.sdd.demo.model.CreateCustomerRequest;
import com.sdd.demo.model.CustomerResponse;
import com.sdd.demo.model.UpdateCustomerRequest;
import com.sdd.demo.service.CustomerService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.sdd.demo.exception.ErrorResponse;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "Customer management operations")
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	@Operation(summary = "Create a customer", operationId = "createCustomer")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Customer created successfully", content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Customer email already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		LOGGER.info("Creating customer");
		CustomerResponse response = customerService.create(request);
		return ResponseEntity.created(URI.create("/api/v1/customers/" + response.id())).body(response);
	}

	@GetMapping
	@Operation(summary = "Retrieve all customers", operationId = "getAllCustomers")
	@ApiResponse(responseCode = "200", description = "Customers retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class))))
	public List<CustomerResponse> getAllCustomers() {
		LOGGER.info("Retrieving all customers");
		return customerService.getAll();
	}

	@GetMapping("/{customerId}")
	@Operation(summary = "Retrieve a customer by ID", operationId = "getCustomerById")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Customer retrieved successfully", content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public CustomerResponse getCustomerById(@PathVariable UUID customerId) {
		LOGGER.info("Retrieving customer {}", customerId);
		return customerService.getById(customerId);
	}

	@PutMapping("/{customerId}")
	@Operation(summary = "Update a customer", operationId = "updateCustomer")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Customer updated successfully", content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid customer data or status", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Customer email already belongs to another customer", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public CustomerResponse updateCustomer(@PathVariable UUID customerId,
			@Valid @RequestBody UpdateCustomerRequest request) {
		LOGGER.info("Updating customer {}", customerId);
		return customerService.update(customerId, request);
	}

	@DeleteMapping("/{customerId}")
	@Operation(summary = "Delete a customer", operationId = "deleteCustomer")
	@ApiResponses({@ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
		LOGGER.info("Deleting customer {}", customerId);
		customerService.delete(customerId);
		return ResponseEntity.noContent().build();
	}
}
