package com.sdd.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"firstName", "lastName", "email", "phoneNumber"})
public record CreateCustomerRequest(@NotBlank @Size(max = 100) String firstName,
		@NotBlank @Size(max = 100) String lastName, @NotBlank @Email @Size(max = 255) String email,
		@NotBlank @Size(max = 20) String phoneNumber) {
}
