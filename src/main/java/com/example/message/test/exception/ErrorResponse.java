package com.example.message.test.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private int status;
	private String traceId;
	private String error;
	private String path;
	@Builder.Default
	private String message = "";
	@Builder.Default
	private String messageKey = "";
	@Builder.Default
	private String pointer = "";
}
