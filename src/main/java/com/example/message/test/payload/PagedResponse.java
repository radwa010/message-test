package com.example.message.test.payload;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NonNull;

@Data
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class PagedResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<T> data;
	@SuppressWarnings("rawtypes")
	private Map meta;

	public PagedResponse() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagedResponse(List<T> data, int page, int size, long totalElements, int totalPages, boolean isLast) {
		this.data = data;
		this.meta = new LinkedHashMap();
		this.meta.put("page", Long.valueOf(page) + 1);
		this.meta.put("size", Long.valueOf(size));
		this.meta.put("total-elements", Long.valueOf(totalElements));
		this.meta.put("total-pages", Long.valueOf(totalPages));
		this.meta.put("last-page", isLast);
	}

	public PagedResponse<T> of(@NonNull Page<T> pageable) {

		return new PagedResponse<>(pageable.getContent(), pageable.getNumber(), pageable.getSize(),
				pageable.getTotalElements(), pageable.getTotalPages(), pageable.isLast());

	}

	public PagedResponse(List<T> data) {
		this.data = data;
	}

}
