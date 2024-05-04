package com.example.message.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.message.test.payload.SingleResponse;
import com.example.message.test.service.MessageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/private")
@AllArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/export")
	public SingleResponse<String> getMessagesPerDayReport() {
		return new SingleResponse<>(messageService.getMessagesPerDayReport());
	}
}
