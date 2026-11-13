package com.sdd.demo.entity;

import com.sdd.demo.model.CustomerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerEntity {

	@Id
	@Column(name = "cust_id", nullable = false, updatable = false)
	private UUID custId;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@Column(name = "email_normalized", nullable = false, unique = true, length = 255)
	private String emailNormalized;

	@Column(name = "phone_number", nullable = false, length = 20)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private CustomerStatus status;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected CustomerEntity() {
	}

	public CustomerEntity(UUID custId, String firstName, String lastName, String email, String phoneNumber,
			CustomerStatus status) {
		this.custId = custId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	@PrePersist
	void onCreate() {
		if (custId == null) {
			custId = UUID.randomUUID();
		}
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
		synchronizeNormalizedEmail();
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
		synchronizeNormalizedEmail();
	}

	private void synchronizeNormalizedEmail() {
		emailNormalized = email.toLowerCase(Locale.ROOT);
	}

	public UUID getCustId() {
		return custId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailNormalized() {
		return emailNormalized;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
}
