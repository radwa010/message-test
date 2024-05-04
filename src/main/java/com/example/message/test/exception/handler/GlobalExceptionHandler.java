package com.example.message.test.exception.handler;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.message.test.exception.BaseException;
import com.example.message.test.exception.ErrorResponse;
import com.example.message.test.exception.ItemNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException itemNotFoundException,
			WebRequest request) {
		log.error("Failed to find the property", itemNotFoundException);
		itemNotFoundException.setMessageKey("item.not.found");
		return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> handleBaseException(BaseException baseException, WebRequest request) {
		return buildErrorResponse(baseException, baseException.getStatusCode(), request);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllUncaughtException(RuntimeException exception, WebRequest request) {
		logger.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildErrorResponse(ex, status, request);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().traceId(MDC.get("traceId")).error(exception.getMessage())
				.status(httpStatus.value()).path(request.getDescription(false)).build();
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}
	
	private ResponseEntity<Object> buildErrorResponse(BaseException exception, HttpStatus httpStatus, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().traceId(MDC.get("traceId")).error(exception.getMessage())
				.message(exception.getMessage()).messageKey(exception.getMessageKey()).pointer(exception.getPointer())
				.status(httpStatus.value()).path(request.getDescription(false)).build();
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}
}
