package com.example.message.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {

	private static final long serialVersionUID = 6063665412721282574L;

	public NotFoundException(String messageKey, String sourceErrorMessage, String sourceErrorCode, String pointer,
			String locale) {
		super(messageKey, sourceErrorMessage, sourceErrorCode, pointer, locale);
	}

	public NotFoundException(String messageKey, String sourceErrorMessage, String sourceErrorCode, String pointer) {
		this(messageKey, sourceErrorMessage, sourceErrorCode, pointer, null);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}
}
