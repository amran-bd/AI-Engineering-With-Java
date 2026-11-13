package com.sdd.demo.exception;

import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"timestamp", "status", "error", "code", "message", "path"})
public record ErrorResponse(Instant timestamp, int status, String error, @Schema(allowableValues = {
		"VALIDATION_ERROR", "INVALID_CUSTOMER_STATUS", "CUSTOMER_NOT_FOUND",
		"CUSTOMER_EMAIL_ALREADY_EXISTS"}) String code, String message, String path) {
}
