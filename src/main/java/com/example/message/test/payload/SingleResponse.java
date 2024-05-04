package com.example.message.test.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class SingleResponse<T> {
	private T data;

	public SingleResponse() {
	}

	public SingleResponse(T data) {
		this.data = data;
	}
}
