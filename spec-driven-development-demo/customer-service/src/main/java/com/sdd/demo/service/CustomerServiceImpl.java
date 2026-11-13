package com.sdd.demo.service;

import com.sdd.demo.entity.CustomerEntity;
import com.sdd.demo.exception.CustomerEmailAlreadyExistsException;
import com.sdd.demo.exception.CustomerNotFoundException;
import com.sdd.demo.model.CreateCustomerRequest;
import com.sdd.demo.model.CustomerMapper;
import com.sdd.demo.model.CustomerResponse;
import com.sdd.demo.model.UpdateCustomerRequest;
import com.sdd.demo.repository.CustomerRepository;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public CustomerResponse create(CreateCustomerRequest request) {
		String normalizedEmail = normalize(request.email());
		if (customerRepository.existsByEmailNormalized(normalizedEmail)) {
			throw duplicateEmail(request.email());
		}
		try {
			return customerMapper.toResponse(customerRepository.saveAndFlush(customerMapper.toEntity(request)));
		} catch (DataIntegrityViolationException exception) {
			throw duplicateEmail(request.email());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public CustomerResponse getById(UUID customerId) {
		return customerMapper.toResponse(findCustomer(customerId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CustomerResponse> getAll() {
		return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
	}

	@Override
	public CustomerResponse update(UUID customerId, UpdateCustomerRequest request) {
		CustomerEntity customer = findCustomer(customerId);
		String normalizedEmail = normalize(request.email());
		if (customerRepository.existsByEmailNormalizedAndCustIdNot(normalizedEmail, customerId)) {
			throw duplicateEmail(request.email());
		}
		customerMapper.updateEntity(request, customer);
		try {
			return customerMapper.toResponse(customerRepository.saveAndFlush(customer));
		} catch (DataIntegrityViolationException exception) {
			throw duplicateEmail(request.email());
		}
	}

	@Override
	public void delete(UUID customerId) {
		CustomerEntity customer = findCustomer(customerId);
		customerRepository.delete(customer);
	}

	private CustomerEntity findCustomer(UUID customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
	}

	private CustomerEmailAlreadyExistsException duplicateEmail(String email) {
		return new CustomerEmailAlreadyExistsException("Customer email already exists: " + email);
	}

	private String normalize(String email) {
		return email.toLowerCase(Locale.ROOT);
	}
}
