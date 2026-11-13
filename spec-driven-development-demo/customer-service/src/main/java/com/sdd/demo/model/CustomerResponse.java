package com.sdd.demo.model;

import java.time.Instant;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"id", "firstName", "lastName", "email", "phoneNumber", "status", "createdAt",
		"updatedAt"})
public record CustomerResponse(UUID id, String firstName, String lastName, String email, String phoneNumber,
		CustomerStatus status, Instant createdAt, Instant updatedAt) {
}
