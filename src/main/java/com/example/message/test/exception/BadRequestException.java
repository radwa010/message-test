package com.example.message.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
	private static final long serialVersionUID = 5063456549748350296L;

	public BadRequestException(String messageKey, String sourceErrorMessage, String sourceErrorCode, String pointer,
			String locale) {
		super(messageKey, sourceErrorMessage, sourceErrorCode, pointer, locale);
	}

	public BadRequestException(String messageKey, String sourceErrorMessage, String sourceErrorCode, String pointer) {
		this(messageKey, sourceErrorMessage, sourceErrorCode, pointer, null);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
