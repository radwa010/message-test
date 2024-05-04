package com.example.message.test.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseException extends RuntimeException {
	private String error;
	private String pointer;
	private String messageKey;
	private String locale;
	private Map<String, String> placeholders;

	private static final long serialVersionUID = -6901475403815763652L;
	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String messageKey, String error, String sourceErrorCode, String pointer, String locale,
			Map<String, String> placeholders) {
		super(error);
		this.error = error;
		this.pointer = pointer != null ? pointer : "";
		this.locale = locale != null ? locale : "en";
		this.messageKey = messageKey != null ? messageKey : "";
		this.placeholders = placeholders;
	}

	public BaseException(String messageKey, String error, String sourceErrorCode, String pointer, String locale) {
		this(messageKey, error, sourceErrorCode, pointer, locale, null);
	}

	public BaseException(String messageKey, String error, String sourceErrorCode, String pointer) {
		this(messageKey, error, sourceErrorCode, pointer, "");
	}

	public abstract HttpStatus getStatusCode();

}
