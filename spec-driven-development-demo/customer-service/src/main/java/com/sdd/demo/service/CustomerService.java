package com.sdd.demo.service;

import com.sdd.demo.model.CreateCustomerRequest;
import com.sdd.demo.model.CustomerResponse;
import com.sdd.demo.model.UpdateCustomerRequest;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

	CustomerResponse create(CreateCustomerRequest request);

	CustomerResponse getById(UUID customerId);

	List<CustomerResponse> getAll();

	CustomerResponse update(UUID customerId, UpdateCustomerRequest request);

	void delete(UUID customerId);
}
