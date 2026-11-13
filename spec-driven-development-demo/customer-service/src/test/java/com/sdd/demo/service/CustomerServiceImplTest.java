package com.sdd.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sdd.demo.entity.CustomerEntity;
import com.sdd.demo.exception.CustomerEmailAlreadyExistsException;
import com.sdd.demo.exception.CustomerNotFoundException;
import com.sdd.demo.model.CreateCustomerRequest;
import com.sdd.demo.model.CustomerMapper;
import com.sdd.demo.model.CustomerResponse;
import com.sdd.demo.model.CustomerStatus;
import com.sdd.demo.repository.CustomerRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private CustomerMapper customerMapper;
	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void createChecksEmailWithoutCaseAndReturnsMappedCustomer() {
		CreateCustomerRequest request = new CreateCustomerRequest("Jane", "Doe", "Jane@Example.com", "5550100");
		CustomerEntity entity = customer("jane@example.com");
		CustomerResponse response = response(entity);
		when(customerRepository.existsByEmailNormalized("jane@example.com")).thenReturn(false);
		when(customerMapper.toEntity(request)).thenReturn(entity);
		when(customerRepository.saveAndFlush(entity)).thenReturn(entity);
		when(customerMapper.toResponse(entity)).thenReturn(response);

		CustomerResponse result = customerService.create(request);

		assertEquals(response, result);
		verify(customerRepository).existsByEmailNormalized("jane@example.com");
		verify(customerRepository).saveAndFlush(entity);
	}

	@Test
	void createRejectsEmailAlreadyInUse() {
		CreateCustomerRequest request = new CreateCustomerRequest("Jane", "Doe", "Jane@Example.com", "5550100");
		when(customerRepository.existsByEmailNormalized("jane@example.com")).thenReturn(true);

		assertThrows(CustomerEmailAlreadyExistsException.class, () -> customerService.create(request));

		verify(customerRepository, never()).saveAndFlush(org.mockito.ArgumentMatchers.any());
	}

	@Test
	void retrieveRejectsUnknownCustomerId() {
		UUID customerId = UUID.randomUUID();
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> customerService.getById(customerId));
	}

	private CustomerEntity customer(String email) {
		return new CustomerEntity(UUID.randomUUID(), "Jane", "Doe", email, "5550100", CustomerStatus.ACTIVE);
	}

	private CustomerResponse response(CustomerEntity entity) {
		Instant now = Instant.now();
		return new CustomerResponse(entity.getCustId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(),
				entity.getPhoneNumber(), entity.getStatus(), now, now);
	}
}
